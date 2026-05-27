module Api
  module V1
    class UploadsController < BaseController
      # POST /api/v1/uploads
      def create
        file = params[:file]

        unless file.present? && file.content_type.in?(valid_content_types)
          ActionCable.server.broadcast("commission_reports", {
            type:    "upload_error",
            message: "Invalid file. Please upload a CSV file."
          })
          return render json: { message: "Only CSV files are accepted." }, status: :unprocessable_entity
        end

        filename = file.original_filename
        report   = SellerCommissionReport.create!(filename:)

        ProcessSellerCommissionReportJob.perform_later(report)

        render json: serialize_report(report), status: :created
      end

      private

      def valid_content_types
        %w[text/csv application/csv text/plain]
      end

      def serialize_report(report)
        {
          id:             report.id,
          filename:       report.filename,
          status:         report.status,
          created_at:     report.created_at
        }
      end
    end
  end
end
