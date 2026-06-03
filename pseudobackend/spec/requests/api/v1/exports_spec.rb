
require "swagger_helper"

RSpec.describe 'Export API', type: :request do

  path '/api/v1/exports' do
    post 'Get all exports (.csv or .pdf)' do
      tags 'Exports type .csv and .pdf'

      produces 'application/json'

      parameter name: :page, in: :query, type: :integer, required: true
      parameter name: :size, in: :query, type: :integer, required: true

      response '200', 'sales csv received' do
        let(:page) { 1 }
        let(:size) { 20 }

        schema type: :object,
          properties: {
            data: { 
              type: :array, 
              items: {
                type: :object,
                properties: {
                  id:                    { type: :integer },
                  comission_report_id:   { type: :integer },
                  filename:              { type: :string },
                  url:                   { type: :string },
                  type:                  { type: :string },
                  status:                { type: :string}
                }
              } 
            },
            page:        { type: :integer },
            size:        { type: :integer },
            total:       { type: :integer },
            total_pages: { type: :integer }
          },
          required: ['data', 'page', 'size', 'total', 'total_pages']

        run_test!
      end
    end
  end

  path '/api/v1/exports/{id}' do
    post 'Get all exports (.csv or .pdf)' do
      tags 'Exports type .csv and .pdf of a comission report'

      produces 'application/json'

      parameter name: :id, in: :path, type: :integer, required: true

      response '200', 'sales csv received' do
        schema type: :object,
          properties: {
            id:                    { type: :integer },
            comission_report_id:   { type: :integer },
            filename:              { type: :string },
            url:                   { type: :string },
            type:                  { type: :string },
            status:                { type: :string }
          },
          required: ['id', 'comission_report_id', 'filename', 'url', 'type', 'status']

        run_test!
      end
    end
  end

end
