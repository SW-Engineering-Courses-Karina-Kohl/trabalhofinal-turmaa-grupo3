module Api
  module V1
    class CommissionReportsController < BaseController
      before_action :set_report, only: %i[show sellers]

      # GET /api/v1/commissions/
      def index
        scope  = SellerCommissionReport.order(created_at: :desc)
        result = paginate(scope)

        render json: {
          data:        result[:data].map { |r| serialize_report(r) },
          page:        result[:page],
          size:        result[:size],
          total:       result[:total],
          total_pages: result[:total_pages]
        }
      end

      # GET /api/v1/commissions/:id
      def show
        render json: serialize_report(@report, detailed: true)
      end

      # GET /api/v1/commissions/:id/sellers
      def sellers
        scope = @report.seller_commission_report_items

        scope = scope.where("name ILIKE ?", "%#{params[:search]}%") if params[:search].present?

        result = paginate(scope.order(:name))

        render json: {
          data:        result[:data].map { |i| serialize_item(i) },
          page:        result[:page],
          size:        result[:size],
          total:       result[:total],
          total_pages: result[:total_pages]
        }
      end

      # POST /api/v1/commissions/:id/export
      def export
        report = SellerCommissionReport.find(params[:id])
        type = params[:doc_type]

        if type != 'csv' && type != 'pdf'
          render json: {
            message:     "Type not supported"
          }, status: :unprocessable_entity
        else
          hash = Digest::MD5.hexdigest(report.filename)[0..12]
          filename = "#{report.filename}#{hash}.#{type}"

          export = Export.create!(
            filename: filename,
            comission_report_id: report.id,
            status: "created",
            type: type
          )

          ProcessExportJob.perform_later(export)

          render json: serialize_export(export), status: :created
        end
      end

      private

      def set_report
        @report = SellerCommissionReport.find(params[:id])
      end

      def serialize_export(export)
        {
          id:                       export.id,
          comission_report_id:      export.seller_commission_report_id,
          filename:                 export.filename,
          url:                      export.url,
          type:                     export.type,
          status:                   export.status,
          created_at:               export.created_at
        }
      end

      def serialize_report(report, detailed: false)
        base = {
          id:              report.id,
          filename:        report.filename,
          status:          report.status,
          commission_pool: report.commission_pool,
          seller_count:    report.seller_count,
          average_payout:  report.average_payout,
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
