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

    case export.type
    when 'csv'
      
    when 'pdf'
      
    end
    
    export.mark_processed!
    broadcast(export, "processed")
  end

  private

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
