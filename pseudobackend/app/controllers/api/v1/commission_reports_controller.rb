module Api
  module V1
    class CommissionReportsController < BaseController
      before_action :set_report, only: %i[show sellers]

      # GET /api/v1/commissions/batches
      def index
        scope  = SellerCommissionReport.order(created_at: :desc)
        result = paginate(scope)

        render json: {
          data:        result[:data].map { |r| serialize_report(r) },
          page:        result[:page],
          page_size:   result[:page_size],
          total:       result[:total],
          total_pages: result[:total_pages]
        }
      end

      # GET /api/v1/commissions/batches/:id
      def show
        render json: serialize_report(@report, detailed: true)
      end

      # GET /api/v1/commissions/batches/:id/sellers
      def sellers
        scope = @report.seller_commission_report_items

        scope = scope.where("name ILIKE ?", "%#{params[:search]}%") if params[:search].present?

        result = paginate(scope.order(:name))

        render json: {
          data:        result[:data].map { |i| serialize_item(i) },
          page:        result[:page],
          page_size:   result[:page_size],
          total:       result[:total],
          total_pages: result[:total_pages]
        }
      end

      # GET /api/v1/commissions/batches/:id/export
      def export
        report = SellerCommissionReport.find(params[:id])
        # TODO: generate real PDF — returning JSON manifest for now
        render json: {
          exported_at: Time.current,
          report_id:   report.id,
          filename:    report.filename,
          message:     "PDF export not yet implemented — wire your PDF gem here."
        }
      end

      private

      def set_report
        @report = SellerCommissionReport.find(params[:id])
      end

      def serialize_report(report, detailed: false)
        base = {
          id:              report.id,
          filename:        report.filename,
          batch_number:    "##{report.id}",
          status:          report.status,
          commission_pool: report.commission_pool,
          seller_count:    report.seller_count,
          average_payout:  report.average_payout,
          validation_passed: report.processed?,
          created_at:      report.created_at,
          updated_at:      report.updated_at
        }

        base
      end

      def serialize_item(item)
        # Map commission_rate 0.0–1.0 → percentage for frontend (e.g. 0.065 → 6.5)
        {
          id:               item.id,
          name:             item.name,
          initials:         item.initials,
          total_sales:      item.total_sales,
          commission_rate:  (item.commission_rate * 100).round(2),
          final_commission: item.commission,
        }
      end
    end
  end
end
