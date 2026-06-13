import type { Seller } from "@/models";

import { usePaginatedQuery } from "./usePaginatedQuery";
import { PaginatedResult } from "@/types";
import { http } from "./client";

// ── CONFIGURATION  ────────────────────────────────────────────────────────
export const PAGE_SIZE = 20;
export const QUERY_KEY = ['sellers'] as const;

// ── ROUTES ────────────────────────────────────────────────────────────────
const API_ROUTE = '/api/v1';
const COMMISSIONS_ROUTE = '/commissions';
const SELLERS_ENDPOINT = '/sellers';

// ── ROUTES ────────────────────────────────────────────────────────────────
const fetchAllBuilder = (commissionReportId : number) : ((page : number, pageSize : number) => Promise<PaginatedResult<Seller>>) => {
  return (page : number, pageSize : number) => http.get<PaginatedResult<Seller>>(`${API_ROUTE}${COMMISSIONS_ROUTE}/${commissionReportId}${SELLERS_ENDPOINT}/?page=${page}&size=${pageSize}`)
}

// ── API CONTROLLER ────────────────────────────────────────────────────────
export const useSellerApi = (commissionReportId : number) => {
  const getAll = usePaginatedQuery<Seller>({
    queryKey: QUERY_KEY,
    queryFn:  fetchAllBuilder(commissionReportId),
    pageSize: PAGE_SIZE,
  })

  return {
    getAll: {
      items: getAll.data?.data,
      paginationControls: {
        page: getAll.data?.page,
        pageSize: getAll.data?.pageSize,
        total: getAll.data?.total,
        totalPages: getAll.data?.totalPages,
        goTo: getAll.goTo,
        nextPage: getAll.nextPage,
        prevPage: getAll.prevPage,
        canNext: getAll.canNext,
        canPrev: getAll.canPrev,
      }
    }
  }

}
