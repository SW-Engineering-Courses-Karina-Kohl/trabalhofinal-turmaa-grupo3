class SellerCommissionReportItem < ApplicationRecord
  belongs_to :seller_commission_report

  validates :name,            presence: true
  validates :initials,        presence: true
  validates :total_sales,     numericality: { greater_than_or_equal_to: 0 }
  validates :commission_rate, numericality: { greater_than_or_equal_to: 0, less_than_or_equal_to: 1 }
  validates :commission,      numericality: { greater_than_or_equal_to: 0 }
end
