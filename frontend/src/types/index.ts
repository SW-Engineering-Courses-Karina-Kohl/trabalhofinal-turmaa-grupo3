// ── Enumerations ──────────────────────────────────────────────────────────────

export type UploadStatus = "Completed" | "Processing" | "Failed";

// ── Core domain models ────────────────────────────────────────────────────────

export interface Seller {
  id: string;
  name: string;
  initials: string;
  totalSales: number;
  commissionRate: number;
  finalCommission: number;
}

export interface CommissionBatch {
  id: string;
  /** e.g. "Q3_Final_Sales_Report.csv" */
  fileName: string;
  batchNumber: string;
  totalCommissionPool: number;
  activeSellers: number;
  averagePayout: number;
  validationPassed: boolean;
  sellers: Seller[];
  totalSellersCount: number;
}

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
