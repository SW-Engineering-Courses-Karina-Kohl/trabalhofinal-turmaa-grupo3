puts "Seeding commission reports..."

STATUSES  = %w[received processing processed processed processed failed].freeze
FILENAMES = -> { "#{%w[Q1 Q2 Q3 Q4].sample}_#{Faker::Lorem.word.capitalize}_Sales_#{Faker::Number.number(digits: 4)}.csv" }

5.times do
  status = STATUSES.sample

  report = SellerCommissionReport.create!(
    filename:        FILENAMES.call,
    status:          status,
    commission_pool: status == "processed" ? rand(100_000.0..600_000.0).round(2) : 0.0,
    seller_count:    status == "processed" ? rand(25..120)                       : 0,
    average_payout:  0.0
  )

  next unless status == "processed"

  items = report.seller_count.times.map do
    name            = Faker::Name.name
    initials        = name.split.map { |w| w[0].upcase }.first(2).join
    total_sales     = rand(0.0..500_000.0).round(2)
    commission_rate = rand(0.005..0.10).round(2)
    commission      = (commission_rate * total_sales).round(2)

    {
      seller_commission_report_id: report.id,
      name:,
      initials:,
      total_sales:,
      commission_rate:,
      commission:,
      created_at: Time.current,
      updated_at: Time.current
    }
  end

  SellerCommissionReportItem.insert_all!(items)

  total_pool = report.seller_commission_report_items.sum(:commission)
  avg        = total_pool / report.seller_count

  report.update!(
    commission_pool: total_pool.round(2),
    average_payout:  avg.round(2)
  )

  puts "  ✓ #{report.filename} — #{report.seller_count} sellers, pool: $#{report.commission_pool}"
end

puts "Done. #{SellerCommissionReport.count} reports, #{SellerCommissionReportItem.count} items."
