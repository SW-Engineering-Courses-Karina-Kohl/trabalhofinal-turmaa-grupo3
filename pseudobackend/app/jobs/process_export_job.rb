require 'prawn'

class ProcessExportJob < ApplicationJob
  queue_as :default

  def perform(export)
    sleep 5

    export.start_processing!
    broadcast(export, "processing")

    sleep 30

    if rand < 0.3
      export.mark_failed!
      broadcast(export, "failed")
      return
    end

    case export.doc_type
    when 'csv'
      io = create_csv
      content_type = "text/csv"
    when 'pdf'
      io = create_pdf
      content_type = "application/pdf"
    end

    export.file.attach(
      io: io,
      filename: "report_#{Time.current.strftime('%Y%m%d_%H%M%S')}.#{export.type}",
      content_type: content_type
    )
    
    export.mark_processed!
    broadcast(export, "processed")
  end

  private

  def create_pdf
    pdf = Prawn::Document.new do |pdf|
      pdf.font_size(20)
      pdf.text "Sales Report", align: :center
      pdf.move_down 20

      pdf.font_size(12)
      pdf.text "Generated on: #{Time.current.strftime('%B %d, %Y at %H:%M')}"
      pdf.move_down 30

      # Table
      pdf.table([
        ["ID", "Name", "Amount"],
        [1, "John Doe", "$1,250.75"],
        [2, "Jane Smith", "$890.50"],
        [3, "Bob Wilson", "$2,100.00"]
      ], header: true, row_colors: ["F0F0F0", "FFFFFF"])

      pdf.move_down 20
      pdf.text "Total: $4,241.25", style: :bold
    end

    return StringIO.new(pdf.render)
  end

  def create_csv
    data = [
      ["ID", "Name", "Email", "Amount", "Date"],
      [1, "John Doe", "john@example.com", 1250.75, "2025-06-01"],
      [2, "Jane Smith", "jane@example.com", 890.50, "2025-06-02"],
      [3, "Bob Wilson", "bob@example.com", 2100.00, "2025-06-03"]
    ]

    csv_content = CSV.generate do |csv|
      data.each { |row| csv << row }
    end

    return StringIO.new(csv_content)
  end

  def broadcast(export, event_type)
    ActionCable.server.broadcast(
      "commission_reports",
      {
        id:                       export.id,
        comission_report_id:      export.seller_commission_report_id,
        filename:                 export.filename,
        url:                      export.url,
        type:                     export.type,
        status:                   export.status,
        created_at:               export.created_at
      }
    )
  end
end
