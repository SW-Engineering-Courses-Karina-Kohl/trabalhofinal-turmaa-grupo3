class Avo::Resources::Export < Avo::BaseResource
  self.model_class = ::Export
  self.title       = :filename
  self.description = "Commission Report Generated File"

  def fields
    field :id,              as: :id
    field :filename,        as: :text,   required: true
    field :doc_type,            as: :text,   required: true
    field :url,             as: :text,   required: true
    field :status,          as: :select,
          options: Export.aasm.states.map { |s| [s.name.to_s.humanize, s.name.to_s] },
          readonly: true
    field :created_at,      as: :date_time, readonly: true
    field :updated_at,      as: :date_time, readonly: true
  end
end
