class Avo::Actions::DeleteReport < Avo::BaseAction
  self.name                 = "Delete Report"
  self.message              = "This will permanently delete the selected report(s) and all their seller items."
  self.confirm_button_label = "Delete"

  def handle(records:, **_args)
    records.each do |report|
      report_id = report.id
      filename  = report.filename

      report.destroy!

      ActionCable.server.broadcast("commission_reports", {
        type:      "deleted",
        report_id: report_id,
        filename:  filename
      })
    end

    succeed "Report(s) deleted and frontend notified."
  end
end
