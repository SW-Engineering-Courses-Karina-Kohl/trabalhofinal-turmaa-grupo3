require "swagger_helper"

RSpec.describe 'Uploads API', type: :request do

  path '/api/v1/uploads' do
    post 'Upload Sales Report (.csv)' do
      tags 'Sales Report'

      consumes 'application/csv'
      produces 'application/json'

      parameter name: :file, in: :body, type: :string, required: true

      response '201', 'sales csv received' do
        let(:file) { 
          Rack::Test::UploadedFile.new(
            StringIO.new("a,b,c,d\ne,f,g,h"),
            'application/csv',
            true,  # binary
            'sales_report.csv'
          ) 
        }

        schema type: :object,
          properties: {
            id:             { type: :integer },
            filename:       { type: :string },
            status:         { 
              type: :string, 
              enum: ["received", "processing", "processed", "failed"]
            },
            created_at:     { type: :string, format: 'date-time'}
          },
          required: ['id', 'filename', 'status', 'created_at']

        run_test!
      end

      response '422', 'invalid csv (send null block)' do
        let(:file) { nil }

        schema type: :object,
          properties: {
            message: { type: :string }
          },
          required: ['message']

        run_test!
      end
      
      response '422', 'invalid csv (send invalid type)' do
        let(:file) { 
          Rack::Test::UploadedFile.new(
            StringIO.new("{ file: \"a,b,c,d\" }"),
            'application/json',
            true,  # binary
            'sales_report.json'
          ) 
        }

        schema type: :object,
          properties: {
            message: { type: :string }
          },
          required: ['message']

        run_test!
      end

    end
  end

end
