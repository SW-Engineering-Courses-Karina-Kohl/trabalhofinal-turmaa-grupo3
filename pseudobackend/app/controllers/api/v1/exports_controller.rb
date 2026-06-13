module Api
  module V1
    class ExportsController < BaseController
    
      # GET /api/v1/exports/
      def index
        scope  = Export.order(created_at: :desc)
        
        result = paginate(scope)

        render json: {
          data:        result[:data].map { |e| serialize_export(e) },
          page:        result[:page],
          size:        result[:size],
          total:       result[:total],
          total_pages: result[:total_pages]
        }
      end
        
      
      # GET /api/v1/exports/{id}
      def show        
        export = Export.find(params[:id])
        render json: serialize_report(export)
      end

      private

      def serialize_export(export)
        file = export.file
        filename = file.filename.to_s || ""
        url = rails_blob_url(file, disposition: "attachment") || ""
        return {
          id:                       export.id,
          comission_report_id:      export.seller_commission_report_id,
          filename:                 filename,
          url:                      url,
          type:                     export.doc_type,
          status:                   export.status,
          created_at:               export.created_at
        }
      end
    end
  end
end
