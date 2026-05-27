class ProcessSellerCommissionReportJob < ApplicationJob
  queue_as :default

  REGIONS = %w[North\ America EMEA APAC LATAM].freeze

  def perform(report)
    # Step 2 — wait (pseudo processing)
    sleep 5

    # Step 3 — switch to processing
    report.start_processing!
    broadcast(report, "processing")

    # Step 4 — longer wait
    sleep 30

    # Step 5 — random success/failure gate
    processing_test = rand

    if processing_test < 0.3
      # Step 5a — fail
      report.mark_failed!
      broadcast(report, "failed")
      return
    end

    # Step 6 — generate fake sellers
    seller_count = rand(25..120)
    items        = seller_count.times.map { build_item(report) }

    # Step 7 — batch insert
    SellerCommissionReportItem.insert_all!(
      items.map(&:attributes).map { |a| a.except("id").merge("created_at" => Time.current, "updated_at" => Time.current) }
    )

    # Step 8 — aggregate & switch to processed
    total_pool     = items.sum(&:commission)
    avg_payout     = items.empty? ? 0.0 : total_pool / items.size

    report.update!(
      commission_pool: total_pool.round(2),
      seller_count:    seller_count,
      average_payout:  avg_payout.round(2)
    )
    report.mark_processed!
    broadcast(report, "processed")
  end

  private

  def build_item(report)
    name            = Faker::Name.name
    initials        = name.split.map { |w| w[0].upcase }.first(2).join
    total_sales     = rand(0.0..500_000.0).round(2)
    commission_rate = rand(0.005..0.10).round(2)   # 0.5% – 10%
    commission      = (commission_rate * total_sales).round(2)

    SellerCommissionReportItem.new(
      seller_commission_report: report,
      name:,
      initials:,
      total_sales:,
      commission_rate:,
      commission:
    )
  end

  def broadcast(report, event_type)
    ActionCable.server.broadcast(
      "commission_reports",
      {
        type:      event_type,
        report_id: report.id,
        status:    report.status,
        filename:  report.filename,
        # Carry enough data so the frontend can update without a refetch
        commission_pool: report.commission_pool,
        seller_count:    report.seller_count,
        average_payout:  report.average_payout
      }
    )
  end
end
