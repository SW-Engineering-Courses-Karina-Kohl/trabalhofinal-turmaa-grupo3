// ── Enumerations ──────────────────────────────────────────────────────────────

export type UploadStatus = "Completed" | "Processing" | "Failed";

// ── Core domain models ────────────────────────────────────────────────────────

export interface Upload {
  id: string;
  fileName: string;
  uploadedAt: string; // ISO 8601
  sizeMb: number;
  status: UploadStatus;
}

// ── Pagination ────────────────────────────────────────────────────────────────

export interface PaginatedResult<T> {
  data: T[];
  page: number;
  pageSize: number;
  total: number;
  totalPages: number;
}

// ── Request/Response DTOs ─────────────────────────────────────────────────────

export interface GetSellersParams {
  batchId: string;
  page?: number;
  pageSize?: number;
  search?: string;
}

export interface GetUploadsParams {
  page?: number;
  pageSize?: number;
}

export interface ApiError {
  message: string;
  status: number;
  code?: string;
}
