import { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  ShieldCheck,
  SlidersHorizontal,
  Download,
  TrendingUp,
  Users,
  DollarSign,
  Target,
  ArrowLeft,
} from "lucide-react";
import SellerAvatar from "@/components/ui/SellerAvatar";
import StatusBadge from "@/components/ui/StatusBadge";
import Pagination from "@/components/ui/Pagination";
import type { Seller } from "@/types";

// ── Mock data (remove when API is wired) ──────────────────────────────────────
const MOCK_SELLERS: Seller[] = [
  { id: "1", name: "Sarah Mitchell", initials: "SM", totalSales: 245000, commissionRate: 6.5, finalCommission: 15925 },
  { id: "2", name: "James Hiller",   initials: "JH", totalSales: 182400, commissionRate: 5.2, finalCommission: 9484.8 },
  { id: "3", name: "Linda Yang",     initials: "LY", totalSales: 312900, commissionRate: 7.0, finalCommission: 21903 },
  { id: "4", name: "Robert Kane",    initials: "RK", totalSales: 98000,  commissionRate: 4.8, finalCommission: 4704 },
  { id: "5", name: "Elena Dubois",   initials: "ED", totalSales: 420500, commissionRate: 8.5, finalCommission: 35742.5 },
];

const BATCH = {
  fileName: "Q3_Final_Sales_Report.csv",
  batchNumber: "#8821",
  totalCommissionPool: 412850.42,
  activeSellers: 124,
  averagePayout: 3329.44,
  totalSellersCount: 124,
};

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
export default function BatchDetailPage() {
  const { batchId } = useParams<{ batchId: string }>();
  const navigate = useNavigate();
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const pageSize = 5;

  // TODO: replace with commissionsApi.getBatch(batchId) + commissionsApi.getSellers(...)
  const sellers = MOCK_SELLERS.filter((s) =>
    s.name.toLowerCase().includes(search.toLowerCase())
  );

  function handleExport() {
    // TODO: commissionsApi.exportPdf(batchId!)
    console.info("Export PDF for batch", batchId);
  }

  return (
    <div className="max-w-5xl space-y-6">
      {/* Back + title */}
      <button
        onClick={() => navigate("/archive")}
        className="flex items-center gap-1.5 text-sm text-slate-500 hover:text-slate-700 transition-colors"
      >
        <ArrowLeft size={14} /> Back to Archive
      </button>

      <div className="flex items-start justify-between">
        <div>
          <h1 className="text-2xl font-bold text-slate-900">{BATCH.fileName}</h1>
          <p className="text-sm text-slate-500 mt-1">
            Commission batch {BATCH.batchNumber}. Data validation passed for{" "}
            {BATCH.activeSellers} individual contributors.
          </p>
        </div>
        <div className="text-right shrink-0">
          <p className="text-xs text-slate-400 uppercase tracking-widest font-medium">
            Total Commission Pool
          </p>
          <p className="text-4xl font-bold text-brand-700 tabular-nums">
            {fmt(BATCH.totalCommissionPool)}
          </p>
        </div>
      </div>

      {/* Stat cards */}
      <div className="grid grid-cols-3 gap-4">
        <StatCard
          icon={<Users size={18} />}
          label="Active Sellers"
          value={String(BATCH.activeSellers)}
        />
        <StatCard
          icon={<DollarSign size={18} />}
          label="Average Payout"
          value={fmt(BATCH.averagePayout)}
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
          <div className="relative flex-1 max-w-xs">
            <input
              type="search"
              placeholder="Filter by name or region..."
              value={search}
              onChange={(e) => { setSearch(e.target.value); setPage(1); }}
              className="input-search"
              aria-label="Filter sellers"
            />
          </div>
          <div className="flex gap-2 ml-auto">
            <button className="btn-secondary text-sm">
              <SlidersHorizontal size={14} /> Filter
            </button>
            <button className="btn-secondary text-sm" onClick={handleExport}>
              <Download size={14} /> Export PDF
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
            {sellers.map((s) => (
              <tr key={s.id} className="hover:bg-slate-50/50 transition-colors">
                <td className="py-4">
                  <div className="flex items-center gap-2.5">
                    <SellerAvatar initials={s.initials} />
                    <span className="font-medium text-slate-800">{s.name}</span>
                  </div>
                </td>
                <td className="py-4 font-mono text-slate-700">
                  {s.totalSales.toLocaleString("en-US", { style: "currency", currency: "USD" })}
                </td>
                <td className="py-4 text-slate-700">{fmtPct(s.commissionRate)}</td>
                <td className="py-4 font-mono font-semibold text-brand-700">
                  {fmt(s.finalCommission)}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <Pagination
          page={page}
          totalPages={Math.ceil(BATCH.totalSellersCount / pageSize)}
          total={BATCH.totalSellersCount}
          pageSize={pageSize}
          onPageChange={setPage}
        />
      </div>
    </div>
  );
}
