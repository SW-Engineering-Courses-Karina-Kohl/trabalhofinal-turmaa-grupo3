class Avo::Resources::SellerCommissionReportItem < Avo::BaseResource
  self.model_class = ::SellerCommissionReportItem
  self.title       = :name
  self.description = "Individual seller entries within a commission batch"

  def fields
    field :id,              as: :id
    field :name,            as: :text,   required: true
    field :initials,        as: :text,   required: true
    field :total_sales,     as: :number, format_using: -> { "$#{value.to_f.round(2)}" }
    field :commission_rate, as: :number,
          format_using: -> { "#{(value.to_f * 100).round(2)}%" }
    field :commission,      as: :number, format_using: -> { "$#{value.to_f.round(2)}" }

    field :seller_commission_report, as: :belongs_to, name: "Report"
  end
end
