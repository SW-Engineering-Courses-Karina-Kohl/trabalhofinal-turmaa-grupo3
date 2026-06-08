class SellerCommissionReport < ApplicationRecord
  include AASM

  has_many :seller_commission_report_items, dependent: :destroy
  has_many :exports, dependent: :destroy

  validates :filename, presence: true
  validates :status,   presence: true

  aasm column: :status do
    state :received, initial: true
    state :processing
    state :processed
    state :failed

    event :start_processing do
      transitions from: :received, to: :processing
    end

    event :mark_processed do
      transitions from: :processing, to: :processed
    end

    event :mark_failed do
      transitions from: %i[received processing], to: :failed
    end
  end

  # Computed helpers (populated after processing)
  def quota_attainment
    return 0.0 if seller_count.zero?
    # Simple proxy: % of sellers with commission above average
    above = seller_commission_report_items.count { |i| i.commission >= average_payout }
    (above.to_f / seller_count * 100).round(1)
  end

  def regions_count
    # Static for mock — real impl would store region per item
    5
  end
end
