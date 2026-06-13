import { UploadSalesReportResponse } from '@/api/uploads';
import { BACKEND_URL } from '@/config/config';
import { toast } from 'sonner'


export interface NotificationCallbacks {
  onProcessingCommissionReport: (obj : any) => void;
  onProcessedCommissionReport: (obj : any) => void;
}

class NotificationHandlerSingleton {
  onProcessingCommissionReport: ((obj : any) => void) | null = null;
  onProcessedCommissionReport: ((obj : any) => void) | null = null;

  setupCallbacks(callbacks : NotificationCallbacks) : void {
    this.onProcessedCommissionReport = callbacks.onProcessedCommissionReport;
    this.onProcessingCommissionReport = callbacks.onProcessingCommissionReport;
  }

  parseNotificationPayload(payload : any) : void {

    if(payload.type == 'welcome') {
      toast("Conexão WS estabelecida com o servidor.")
      return
    }
    console.log(payload)
    if(payload.type == 'ping') console.log(payload)

    switch(payload.type) {
      case 'processing':
        if (this.onProcessingCommissionReport) {
          this.onProcessingCommissionReport(payload)
          toast(`Arquivo ${payload.fileName} está sendo processado.`)
        }
        break
      case 'processed':
        if (this.onProcessedCommissionReport) {
          this.onProcessedCommissionReport(payload);
          toast.success(`Arquivo ${payload.fileName} foi processado com sucesso!`)
        }
        break
      case 'pdf':
      case 'csv':
        this.downloadFile(payload.url, payload.fileName)
        this.onComissionReportExported()
        break
    }
    
  }


  
  onSalesReportUploaded(response : UploadSalesReportResponse) : void {
    if(response.filename !== undefined){
      toast(`Arquivo ${response.filename} enviado!`)
    }
  }

  onCommissionReportExportRequested(type : string) : void {
    toast(`Arquivo ${type} solicitado!`)
  }

  onComissionReportDeleted(id : number) : void {
    toast.success(`Arquivo ${id} deletado!`)
  }

  private onComissionReportExported() : void {
    toast.success(`Arquivo exportado com sucesso!`)
  }

  private async downloadFile(downloadLink : string, filename : string) {
    const response = await fetch(`${BACKEND_URL}${downloadLink}`)
    const blob = await response.blob()
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = filename
    a.click()
    URL.revokeObjectURL(url)
  }
}

export const NotificationHandler = new NotificationHandlerSingleton();
