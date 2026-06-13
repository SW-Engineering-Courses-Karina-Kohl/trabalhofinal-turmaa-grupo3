import { NotificationHandler } from "@/services/NotificationHandler";
import { http } from "./client";

// ── ROUTES ────────────────────────────────────────────────────────────────
const API_ROUTE = '/api/v1';
const UPLOAD_ENDPOINT = '/uploads';

// ── API FUNCTIONS ────────────────────────────────────────────────────────────────
export interface UploadSalesReportResponse {
  id? : number;
  filename? : string;
  status? : string;
  created_at?: string;
  message? : string;
}
const uploadSalesReport = (file : File) : Promise<UploadSalesReportResponse> => {
  const form = new FormData();
  form.append("file", file);
  return http.postForm<UploadSalesReportResponse>(`${API_ROUTE}${UPLOAD_ENDPOINT}`, form).then(r => {
    NotificationHandler.onSalesReportUploaded(r);
    return r;
  }); 
}

// ── API CONTROLLER ────────────────────────────────────────────────────────────────
export const useUploadApi = () => {
  return {
    uploadSalesReport,
  }
}


