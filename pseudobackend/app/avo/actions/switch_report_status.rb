class Avo::Actions::SwitchReportStatus < Avo::BaseAction
  self.name            = "Switch Status"
  self.message         = "Select the target status for the selected report(s)."
  self.confirm_button_label = "Apply"

  def fields
    field :target_status, as: :select,
          label:   "Target status",
          options: { "Received" => "received", "Processing" => "processing", "Processed" => "processed", "Failed" => "failed" },
          required: true
  end

  def handle(records:, fields:, **_args)
    target = fields[:target_status]

    records.each do |report|
      transition_to(report, target)

      ActionCable.server.broadcast("commission_reports", {
        type:            "status_changed",
        report_id:       report.id,
        status:          report.status,
        filename:        report.filename,
        commission_pool: report.commission_pool,
        seller_count:    report.seller_count,
        average_payout:  report.average_payout
      })
    end

    succeed "Status updated and broadcast sent."
  end

  private

  def transition_to(report, target)
    case target
    when "processing" then report.start_processing! if report.may_start_processing?
    when "processed"  then report.mark_processed!   if report.may_mark_processed?
    when "failed"     then report.mark_failed!       if report.may_mark_failed?
    when "received"
      # AASM doesn't have a back-to-received transition by design;
      # force it directly for admin override purposes only
      report.update_column(:status, "received")
    end
  rescue AASM::InvalidTransition => e
    fail "Cannot transition #{report.filename}: #{e.message}"
  end
end
