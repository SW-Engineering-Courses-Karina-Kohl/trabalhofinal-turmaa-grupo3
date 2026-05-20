/**
 * api/commissions.ts
 *
 * All commission-related API calls.
 * TODO (implementor): wire real endpoints when the backend is ready.
 */

import { http } from "./client";
import type {
  CommissionBatch,
  GetSellersParams,
  PaginatedResult,
  Seller,
} from "@/types";

export const commissionsApi = {
  /**
   * Fetch a single commission batch by ID (Results Archive detail).
   */
  getBatch(batchId: string): Promise<CommissionBatch> {
    return http.get<CommissionBatch>(`/commissions/batches/${batchId}`);
  },

  /**
   * List all commission batches (Results Archive index).
   */
  listBatches(page = 1, pageSize = 10): Promise<PaginatedResult<CommissionBatch>> {
    return http.get<PaginatedResult<CommissionBatch>>("/commissions/batches", {
      page,
      pageSize,
    });
  },

  /**
   * Paginated seller list for a given batch, with optional search / filter.
   */
  getSellers({
    batchId,
    page = 1,
    pageSize = 5,
    search,
    status,
  }: GetSellersParams): Promise<PaginatedResult<Seller>> {
    return http.get<PaginatedResult<Seller>>(`/commissions/batches/${batchId}/sellers`, {
      page,
      pageSize,
      search,
      status,
    });
  },

  /**
   * Export the batch as a PDF manifest (returns a Blob URL).
   * The caller is responsible for revoking the object URL after use.
   */
  exportPdf(batchId: string): Promise<string> {
    return fetch(
      `${import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api"}/commissions/batches/${batchId}/export`,
    ).then(async (res) => {
      if (!res.ok) throw new Error("Export failed");
      const blob = await res.blob();
      return URL.createObjectURL(blob);
    });
  },
};
