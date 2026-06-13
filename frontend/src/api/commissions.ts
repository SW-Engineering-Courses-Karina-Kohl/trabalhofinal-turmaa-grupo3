import { http } from "./client";
import type { PaginatedResult } from "@/types";
import { usePaginatedQuery } from "./usePaginatedQuery";
import { CommissionReport } from "@/models/CommissionReport";
import { useQuery } from "@tanstack/react-query";
import { NotificationHandler } from "@/services/NotificationHandler";

// ── CONFIGURATION  ────────────────────────────────────────────────────────
export const PAGE_SIZE = 10;
export const QUERY_KEY = ['commission-reports'] as const;

// ── ROUTES ────────────────────────────────────────────────────────────────
const API_ROUTE = '/api/v1';
const COMMISSIONS_ROUTE = '/commissions';
const EXPORT_ENDPOINT = '/export';

// ── API FUNCTIONS ─────────────────────────────────────────────────────────
const fetchAll = (page : number, pageSize : number) : Promise<PaginatedResult<CommissionReport>> => {
  return http.get<PaginatedResult<CommissionReport>>(`${API_ROUTE}${COMMISSIONS_ROUTE}/?page=${page}&size=${pageSize}`)
}

const fetchOne = (id : number) : Promise<CommissionReport> => {
  return http.get<CommissionReport>(`${API_ROUTE}${COMMISSIONS_ROUTE}/${id}`);
}

interface DeleteOneResponse { id : number; }
const deleteOne = (id : number) : Promise<DeleteOneResponse> => {
  return http.delete<DeleteOneResponse>(`${API_ROUTE}${COMMISSIONS_ROUTE}/${id}`).then(s => {
    NotificationHandler.onComissionReportDeleted(id);
    return s;
  });
}

interface ExportReportResponse { commission_report_id : number; }
const exportReport = (id : number, type : string) : Promise<ExportReportResponse> => {
  return http.post<ExportReportResponse>(`${API_ROUTE}${COMMISSIONS_ROUTE}/${id}${EXPORT_ENDPOINT}/?doc_type=${type}`, "").then(s => {
    NotificationHandler.onCommissionReportExportRequested(type);
    return s;
  });
}

// ── API CONTROLLER ────────────────────────────────────────────────────────
export const useCommissionsApi = () => {
  const getAll = usePaginatedQuery<CommissionReport>({
    queryKey: QUERY_KEY,
    queryFn:  fetchAll,
    pageSize: PAGE_SIZE,
  })

  const get = (id : number) : CommissionReport | null => {
    const {data : commission } = useQuery({
        queryKey: ['commissions', id],
        queryFn: () => fetchOne(id),
        enabled: !!id
    })
    return commission ?? null;
  }

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
    },
    get,
    delete: deleteOne,
    exportTo: exportReport
  }
}





// const commissionsRoutes = {
//   // async fetchAll(page: number, pageSize: number){
//   //   return http.get<PaginatedResult<CommissionReport>>(`${API_ROUTE}${ROUTE}/?page=${page}&size=${pageSize}`);
//   // },
//   //
//   // get(id : number) : Promise<PaginatedResult<CommissionReport>> {
//   //   return http.get<PaginatedResult<CommissionReport>>(`${API_ROUTE}${ROUTE}/${id}`);
//   // },
//   //
//   // delete(id : number) : Promise<PaginatedResult<CommissionReport>> {
//   //   return http.delete<PaginatedResult<CommissionReport>>(`${API_ROUTE}${ROUTE}/${id}`);
//   // },
//
//   async exportReport(id : number, type : string) : Promise<string> {
//     return fetch(`/api/v1/commissions/${id}/export`, {
//       method: 'POST',
//       headers: { 'Content-Type': 'application/json' },
//       body: JSON.stringify({ type: type }),
//     }).then(async (res) => {
//       if (!res.ok) throw new Error("Export failed");
//       const blob = await res.blob();
//       return URL.createObjectURL(blob);
//     });
//   },
//   /**
//    * Upload a CSV/XLSX file for commission processing.
//    * @param file  The file selected by the user.
//    * @returns     The newly created Upload record.
//    */
//   uploadFile(file: File): Promise<Upload> {
//     const form = new FormData();
//     form.append("file", file);
//     return http.postForm<Upload>(`${API_ROUTE}/uploads`, form);
//   }
// }



// export const commissionsApi = {
//   /**
//    * Export the batch as a PDF manifest (returns a Blob URL).
//    * The caller is responsible for revoking the object URL after use.
//    */
//   // exportPdf(batchId: string): Promise<string> {
//   //   return fetch(
//   //     `http://localhost:8080/api/commissions/batches/${batchId}/export`,
//   //   ).then(async (res) => {
//   //     if (!res.ok) throw new Error("Export failed");
//   //     const blob = await res.blob();
//   //     return URL.createObjectURL(blob);
//   //   });
//   // },
// };


