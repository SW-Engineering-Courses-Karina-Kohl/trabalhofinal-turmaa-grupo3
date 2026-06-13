class CreateSellerCommissionReports < ActiveRecord::Migration[8.0]
  def change
    create_table :seller_commission_reports do |t|
      t.string  :filename,         null: false
      t.string  :status,           null: false, default: "received"
      t.float   :commission_pool,  null: false, default: 0.0
      t.integer :seller_count,     null: false, default: 0
      t.float   :average_payout,   null: false, default: 0.0

      t.timestamps
    end

    create_table :seller_commission_report_items do |t|
      t.references :seller_commission_report, null: false, foreign_key: true, index: true
      t.string  :name,            null: false
      t.string  :initials,        null: false
      t.float   :total_sales,     null: false, default: 0.0
      t.float   :commission_rate, null: false, default: 0.0
      t.float   :commission,      null: false, default: 0.0

      t.timestamps
    end

    create_table :exports do |t|
      t.references :seller_commission_report, null: false, foreign_key: true, index: true
      t.string  :filename, null: false
      t.string  :doc_type, null: false
      t.string  :url,     default: "" 
      t.string  :status,   null: false, default: "created"
      t.timestamps
    end
  end
end
