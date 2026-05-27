module Api
  module V1
    class BaseController < ActionController::API
      rescue_from ActiveRecord::RecordNotFound do |e|
        render json: { message: e.message }, status: :not_found
      end

      rescue_from ActiveRecord::RecordInvalid do |e|
        render json: { message: e.message }, status: :unprocessable_entity
      end

      rescue_from AASM::InvalidTransition do |e|
        render json: { message: e.message, code: "INVALID_TRANSITION" }, status: :unprocessable_entity
      end

      private

      def paginate(scope)
        page      = (params[:page]     || 1).to_i
        page_size = (params[:pageSize] || 10).to_i.clamp(1, 100)
        total     = scope.count

        {
          data:        scope.offset((page - 1) * page_size).limit(page_size),
          page:        page,
          page_size:   page_size,
          total:       total,
          total_pages: (total.to_f / page_size).ceil
        }
      end
    end
  end
end
