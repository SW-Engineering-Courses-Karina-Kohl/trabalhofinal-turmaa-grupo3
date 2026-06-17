import { useNavigate } from "react-router-dom";
import { Archive, ChevronRight } from "lucide-react";

import Pagination from "../ui/Pagination";
import { useCommissionsApi, COMISSIONS_PAGE_SIZE } from "@/api";
import { CommissionReport } from "@/models";
import { Currency } from "../ui/UIHelpers";

export default function CommissionReportsPage() {
  const navigate = useNavigate();
  const APIController = useCommissionsApi();
  const pageControls = APIController.getAll.paginationControls;
  const data = APIController.getAll.items;

  return (
    <div className="max-w-4xl space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-slate-900">Relatórios de Comissões</h1>
      </div>

      <div className="space-y-3">
        {data?.map((commissionReport : CommissionReport) => (
          <button
            key={commissionReport.id}
            onClick={() => navigate(`/sellers/${commissionReport.id}`)}
            className="card w-full p-5 flex items-center gap-6 text-left hover:shadow-md hover:-translate-y-0.5 transition-all group"
          >
            <div className="w-11 h-11 rounded-xl bg-brand-50 flex items-center justify-center shrink-0">
              <Archive size={18} className="text-brand-600" />
            </div>

            <div className="flex-1 min-w-0">
              <p className="font-semibold text-slate-800 truncate">{commissionReport.filename}</p>
              <p className="text-sm text-slate-400 mt-0.5">
                {commissionReport.seller_count} vendedores 
              </p>
            </div>

            <div className="text-right shrink-0">
              <p className="text-lg font-bold text-brand-700">{Currency(commissionReport.commission_pool)}</p>
              <p className="text-xs text-slate-400 mt-0.5">Total de Comissões</p>
            </div>

            <ChevronRight
              size={18}
              className="text-slate-300 group-hover:text-slate-500 transition-colors shrink-0"
            />
          </button>
        ))}
      </div>

      <Pagination
        page={pageControls.page ?? 1}
        totalPages={pageControls.totalPages ?? 0}
        total={pageControls.total ?? 0}
        pageSize={COMISSIONS_PAGE_SIZE}
        nextPage={pageControls.nextPage}
        prevPage={pageControls.prevPage} 
        canNext={pageControls.canNext} 
        canPrev={pageControls.canPrev} 
        goTo={pageControls.goTo}
      />
  </div>
  );
}
