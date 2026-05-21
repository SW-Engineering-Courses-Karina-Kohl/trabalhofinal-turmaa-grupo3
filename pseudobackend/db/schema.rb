# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# This file is the source Rails uses to define your schema when running `bin/rails
# db:schema:load`. When creating a new database, `bin/rails db:schema:load` tends to
# be faster and is potentially less error prone than running all of your
# migrations from scratch. Old migrations may fail to apply correctly if those
# migrations use external dependencies or application code.
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema[8.1].define(version: 2024_01_01_000000) do
  # These are extensions that must be enabled in order to support this database
  enable_extension "pg_catalog.plpgsql"

  create_table "seller_commission_report_items", force: :cascade do |t|
    t.float "commission", default: 0.0, null: false
    t.float "commission_rate", default: 0.0, null: false
    t.datetime "created_at", null: false
    t.string "initials", null: false
    t.string "name", null: false
    t.bigint "seller_commission_report_id", null: false
    t.float "total_sales", default: 0.0, null: false
    t.datetime "updated_at", null: false
    t.index ["seller_commission_report_id"], name: "idx_on_seller_commission_report_id_53c6466c36"
  end

  create_table "seller_commission_reports", force: :cascade do |t|
    t.float "average_payout", default: 0.0, null: false
    t.float "commission_pool", default: 0.0, null: false
    t.datetime "created_at", null: false
    t.string "filename", null: false
    t.integer "seller_count", default: 0, null: false
    t.string "status", default: "received", null: false
    t.datetime "updated_at", null: false
  end

  add_foreign_key "seller_commission_report_items", "seller_commission_reports"
end
