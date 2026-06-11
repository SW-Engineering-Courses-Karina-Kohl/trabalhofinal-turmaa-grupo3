class Export < ApplicationRecord
  include AASM
  
  belongs_to :seller_commission_report

  has_one_attached :file

  validates :filename, presence: true
  validates :status,   presence: true
  validates :doc_type,     presence: true

  aasm column: :status do
    state :created, initial: true
    state :processing
    state :processed
    state :failed

    event :start_processing do
      transitions from: :created, to: :processing
    end

    event :mark_processed do
      transitions from: :processing, to: :processed
    end

    event :mark_failed do
      transitions from: %i[received processing], to: :failed
    end
  end
end
