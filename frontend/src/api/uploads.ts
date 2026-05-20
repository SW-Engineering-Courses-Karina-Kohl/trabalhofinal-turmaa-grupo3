/**
 * api/uploads.ts
 *
 * File upload and recent-uploads management.
 * TODO (implementor): wire real endpoints when the backend is ready.
 */

import { http } from "./client";
import type { GetUploadsParams, PaginatedResult, Upload } from "@/types";

export const uploadsApi = {
  /**
   * List recent uploads with pagination.
   */
  listUploads({ page = 1, pageSize = 10 }: GetUploadsParams = {}): Promise<
    PaginatedResult<Upload>
  > {
    return http.get<PaginatedResult<Upload>>("/uploads", { page, pageSize });
  },

  /**
   * Upload a CSV/XLSX file for commission processing.
   * @param file  The file selected by the user.
   * @returns     The newly created Upload record.
   */
  uploadFile(file: File): Promise<Upload> {
    const form = new FormData();
    form.append("file", file);
    return http.postForm<Upload>("/uploads", form);
  },

  /**
   * Delete an upload record (and its associated data).
   */
  deleteUpload(uploadId: string): Promise<void> {
    return http.delete<void>(`/uploads/${uploadId}`);
  },

  /**
   * Poll upload status until it leaves the "Processing" state.
   * TODO (implementor): replace polling with WebSocket / SSE if preferred.
   */
  getUploadStatus(uploadId: string): Promise<Upload> {
    return http.get<Upload>(`/uploads/${uploadId}`);
  },
};
