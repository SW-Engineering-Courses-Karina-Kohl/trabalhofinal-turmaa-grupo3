class Export < ApplicationRecord
  include AASM
  
  belongs_to :seller_commission_report

  validates :filename, presence: true
  validates :status,   presence: true
  validates :type,     presence: true

  aasm column: :status do
    state :created, initial: true
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
end
