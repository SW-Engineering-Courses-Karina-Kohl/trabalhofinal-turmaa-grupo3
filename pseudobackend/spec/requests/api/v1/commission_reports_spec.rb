require "swagger_helper"

RSpec.describe 'Commission Reports API', type: :request do
  path '/api/v1/commisions/batches' do
    get 'Get List of all reports' do
      tags 'Commission Reports'
      produces 'application/json'
 
      response '200', 'reports' do
        schema type: :object,
          properties: {
            data: { 
              type: :array, 
              items: {
                type: :object,
                properties: {
                  id:              { type: :integer },
                  filename:        { type: :string },
                  batch_number:    { type: :string },
                  status:          { 
                    type: :string , 
                    enum: ["received", "processing", "processed", "failed"]
                  },
                  commission_pool: { type: :number},
                  seller_count:    { type: :integer },
                  average_payout:  { type: :number},
                  validation_passed: { type: :string },
                  created_at:      { type: :string, format: 'date-time' },
                  updated_at:      { type: :string, format: 'date-time' }
                }
              } 
            },
            page:      { type: :integer },
            page_size: { type: :integer },
            total:     { type: :integer },
            total_pages: { type: :integer }
          },
          required: ['data', 'page', 'page_size', 'total', 'total_pages']
        run_test!
      end
    end
  end

  path '/api/v1/commisions/batches/{id}' do
    get 'Get a report' do
      tags 'Commission Reports'
      produces 'application/json'

      parameter name: :id, in: :path, type: :integer, required: true
 
      response '200', 'report' do
        let(:id) { 1 }

        schema type: :object,
          properties: {
            id:              { type: :integer },
            filename:        { type: :string },
            batch_number:    { type: :string },
            status:          { 
              type: :string, 
              enum: ["received", "processing", "processed", "failed"]
            },
            commission_pool: { type: :number},
            seller_count:    { type: :integer },
            average_payout:  { type: :number},
            validation_passed: { type: :string },
            created_at:      { type: :string, format: 'date-time' },
            updated_at:      { type: :string, format: 'date-time' }
          },
          required: ['id', 'filename', 'status', 'commission_pool', 'seller_count', 'average_payout']

        run_test!
      end
    end
  end

  path '/api/v1/commisions/batches/{id}/sellers' do
    get 'Get the sellers of a report' do
      tags 'Commission Sellers'
      produces 'application/json'

      parameter name: :id, in: :path, type: :integer, required: true
 
      response '200', 'report_sellers' do
        let(:id) { 1 }

        schema type: :object,
          properties: {
            data: { 
              type: :array, 
              items: {
                type: :object,
                properties: {
                  id:               { type: :integer },
                  name:             { type: :string },
                  initials:         { type: :string },
                  total_sales:      { type: :number },
                  commission_rate:  { type: :number},
                  final_commission: { type: :number}
                }
              } 
            },
            page:      { type: :integer },
            page_size: { type: :integer },
            total:     { type: :integer },
            total_pages: { type: :integer }
          },
          required: ['data', 'page', 'page_size', 'total', 'total_pages']

        run_test!
      end
    end
  end
end
