class Avo::Resources::SellerCommissionReport < Avo::BaseResource
  self.model_class = ::SellerCommissionReport
  self.title       = :filename
  self.description = "Commission processing batches"

  def actions
    action Avo::Actions::SwitchReportStatus
    action Avo::Actions::DeleteReport
  end

  def fields
    field :id,              as: :id
    field :filename,        as: :text,   required: true
    field :status,          as: :select,
          options: SellerCommissionReport.aasm.states.map { |s| [s.name.to_s.humanize, s.name.to_s] },
          readonly: true
    field :commission_pool, as: :number, readonly: true, format_using: -> { "$#{value.to_f.round(2)}" }
    field :seller_count,    as: :number, readonly: true
    field :average_payout,  as: :number, readonly: true, format_using: -> { "$#{value.to_f.round(2)}" }
    field :created_at,      as: :date_time, readonly: true
    field :updated_at,      as: :date_time, readonly: true

    field :seller_commission_report_items,
          as:    :has_many,
          name:  "Sellers"
  end
end
