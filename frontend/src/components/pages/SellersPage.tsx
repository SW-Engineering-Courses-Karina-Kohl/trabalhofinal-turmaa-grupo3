import { useNavigate, useParams } from "react-router-dom";
import {
  ShieldCheck,
  Download,
  Users,
  DollarSign,
  ArrowLeft,
} from "lucide-react";
import SellerAvatar from "@/components/ui/SellerAvatar";
import Pagination from "@/components/ui/Pagination";
import type { Seller } from "@/models";

import { useSellerApi } from "@/api/sellers";
import { SELLERS_PAGE_SIZE, SELLERS_QUERY_KEY, useCommissionsApi } from "@/api";
import { useQueryClient } from "@tanstack/react-query";
import { useEffect } from "react";
import { Currency } from "../ui/UIHelpers";

function fmt(n: number) {
  return n.toLocaleString("en-US", { style: "currency", currency: "USD" });
}

function fmtPct(n: number) {
  return `${n.toFixed(1)}%`;
}

// ── Stat card ─────────────────────────────────────────────────────────────────
interface StatCardProps {
  icon: React.ReactNode;
  label: string;
  value: string;
  sub?: React.ReactNode;
  progress?: number; // 0–100
}

function StatCard({ icon, label, value, sub, progress }: StatCardProps) {
  return (
    <div className="card p-5 flex gap-4">
      <div className="w-11 h-11 rounded-xl bg-slate-100 flex items-center justify-center shrink-0 text-slate-500">
        {icon}
      </div>
      <div className="flex-1 min-w-0">
        <p className="text-xs text-slate-400 uppercase tracking-wider font-medium">{label}</p>
        <p className="text-2xl font-bold text-slate-900 mt-0.5">{value}</p>
        {sub && <div className="mt-1">{sub}</div>}
        {progress !== undefined && (
          <div className="mt-2 h-1.5 rounded-full bg-slate-100 overflow-hidden">
            <div
              className="h-full rounded-full bg-brand-600 transition-all"
              style={{ width: `${progress}%` }}
            />
          </div>
  )}
      </div>
    </div>
  );
}

// ── Page ──────────────────────────────────────────────────────────────────────
export default function SellersPage() {
  const navigate = useNavigate();
  const { id } = useParams();

  if(id === undefined) return <p>Erro ao carregar!</p> 

  const queryClient = useQueryClient();
  useEffect(() => {
    queryClient.invalidateQueries({ queryKey: SELLERS_QUERY_KEY })  
  }, [])

  const CommissionReportAPIClient = useCommissionsApi();
  const commissionReportId = parseInt(id);
  const commission = CommissionReportAPIClient.get(commissionReportId);

  const SellerAPIClient = useSellerApi(commissionReportId);
  const pageControls = SellerAPIClient.getAll.paginationControls;
  const sellers = SellerAPIClient.getAll.items ?? [];
  
  return (
        <div className="max-w-5xl space-y-7">
          {/* Back + title */}
          <button
            onClick={() => navigate("/commissions")}
            className="flex items-center gap-1.5 text-sm text-slate-500 hover:text-slate-700 transition-colors"
          >
            <ArrowLeft size={14} /> Back to Archive
          </button>

          <div className="flex items-start justify-between">
            <div>
              <h1 className="text-2xl font-bold text-slate-900">{commission?.filename ?? ""}</h1>
            </div>
            <div className="text-right shrink-0">
              <p className="text-xs text-slate-400 uppercase tracking-widest font-medium">
                Total Commission Pool
              </p>
              <p className="text-4xl font-bold text-brand-700 tabular-nums">
                {Currency(commission?.commission_pool ?? 0)}
              </p>
            </div>
          </div>

          {/* Stat cards */}
          <div className="grid grid-cols-3 gap-4">
            <StatCard
              icon={<Users size={18} />}
              label="Active Sellers"
              value={String(commission?.seller_count ?? 0)}
            />
            <StatCard
              icon={<DollarSign size={18} />}
              label="Average Payout"
              value={Currency(commission?.average_payout ?? 0)}
            />
          </div>

          {/* Financial Integrity Protocol banner */}
          <div className="card p-4 flex gap-3 bg-brand-50/50 border-brand-100">
            <div className="w-8 h-8 rounded-lg bg-brand-100 flex items-center justify-center shrink-0 text-brand-700">
              <ShieldCheck size={16} />
            </div>
            <div>
              <p className="text-sm font-semibold text-slate-800">Financial Integrity Protocol</p>
              <p className="text-sm text-slate-500 mt-0.5">
                This report is locked for editing. Any changes to commission logic must be applied via a
                new calculation version to maintain the audit trail. Exporting this data will generate a
                timestamped manifest.
              </p>
            </div>
          </div>

          {/* Sellers table */}
          <div className="card p-6">
            <div className="flex items-center gap-3 mb-5">
              <div className="flex gap-2 ml-auto">
                <button className="btn-secondary text-sm" onClick={() => CommissionReportAPIClient.exportTo(commissionReportId, "pdf")}>
                  <Download size={14} /> Export PDF
                </button>
                <button className="btn-secondary text-sm" onClick={() => CommissionReportAPIClient.exportTo(commissionReportId, "csv")}>
                  <Download size={14} /> Export CSV 
                </button>
              </div>
            </div>

            <table className="w-full text-sm">
              <thead>
                <tr className="border-b border-slate-100">
                  {[
                    "Seller Name",
                    "Total Sales ($)",
                    "Commission Rate (%)",
                    "Final Commission ($)",
                  ].map((h) => (
                    <th
                      key={h}
                      className="pb-3 text-left text-[11px] font-bold text-slate-400 uppercase tracking-wider"
                    >
                      {h}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-50">
                {sellers?.map((s : Seller) => (
                  <tr key={s.id} className="hover:bg-slate-50/50 transition-colors">
                    <td className="py-4">
                      <div className="flex items-center gap-2.5">
                        <SellerAvatar initials={s.initials} />
                        <span className="font-medium text-slate-800">{s.name}</span>
                      </div>
                    </td>
                    <td className="py-4 font-mono text-slate-700">
                      {Currency(s.total_sales)}
                    </td>
                    <td className="py-4 text-slate-700">{fmtPct(s.commission_rate)}</td>
                    <td className="py-4 font-mono font-semibold text-brand-700">
                      {Currency(s.final_commission)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <Pagination
              page={pageControls?.page ?? 1}
              totalPages={Math.ceil((commission?.seller_count ?? 0) / SELLERS_PAGE_SIZE)}
              total={commission?.seller_count ?? 0}
              pageSize={SELLERS_PAGE_SIZE}
              nextPage={pageControls?.nextPage ?? null}
              prevPage={pageControls?.prevPage ?? null} 
              canNext={pageControls?.canNext ?? null} 
              canPrev={pageControls?.canPrev ?? null} 
              goTo={pageControls?.goTo ?? null}
            />
          </div>
        </div>
      );
}
