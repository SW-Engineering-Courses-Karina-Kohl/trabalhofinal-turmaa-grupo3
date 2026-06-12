import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  ShieldCheck,
  SlidersHorizontal,
  Download,
  Users,
  DollarSign,
  ArrowLeft,
} from "lucide-react";
import SellerAvatar from "@/components/ui/SellerAvatar";
import Pagination from "@/components/ui/Pagination";
import { commissionsApi } from "@/api";
import type { CommissionBatch, Seller } from "@/types";

function fmt(n: number) {
  return n.toLocaleString("en-US", { style: "currency", currency: "USD" });
}

function fmtPct(n: number) {
  return `${n.toFixed(1)}%`;
}

interface StatCardProps {
  icon: React.ReactNode;
  label: string;
  value: string;
}

function StatCard({ icon, label, value }: StatCardProps) {
  return (
    <div className="card p-5 flex gap-4">
      <div className="w-11 h-11 rounded-xl bg-slate-100 flex items-center justify-center shrink-0 text-slate-500">
        {icon}
      </div>
      <div className="flex-1 min-w-0">
        <p className="text-xs text-slate-400 uppercase tracking-wider font-medium">{label}</p>
        <p className="text-2xl font-bold text-slate-900 mt-0.5">{value}</p>
      </div>
    </div>
  );
}

export default function BatchDetailPage() {
  const { batchId } = useParams<{ batchId: string }>();
  const navigate = useNavigate();
  const [batch, setBatch] = useState<CommissionBatch | null>(null);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(1);
  const pageSize = 5;

  useEffect(() => {
    if (!batchId) return;
    commissionsApi.getBatch(batchId).then(setBatch).catch(() => { });
  }, [batchId]);

  const allSellers: Seller[] = batch?.sellers ?? [];
  const filtered = allSellers.filter((s) =>
    s.name.toLowerCase().includes(search.toLowerCase())
  );
  const totalPages = Math.ceil(Math.max(filtered.length, 1) / pageSize);
  const paginated = filtered.slice((page - 1) * pageSize, page * pageSize);

  function handleExport() {
    const base = import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080/api";
    fetch(`${base}/commissions/batches/${batchId}/export`)
      .then((res) => res.blob())
      .then((blob) => {
        const a = document.createElement("a");
        a.href = URL.createObjectURL(blob);
        a.download = `${batch?.fileName?.replace(".csv", "") ?? "commissions"}_export.csv`;
        a.click();
        URL.revokeObjectURL(a.href);
      });
  }

  if (!batch) {
    return <div className="text-slate-400 text-sm p-8">Loading…</div>;
  }

  return (
    <div className="max-w-5xl space-y-6">
      <button
        onClick={() => navigate("/archive")}
        className="flex items-center gap-1.5 text-sm text-slate-500 hover:text-slate-700 transition-colors"
      >
        <ArrowLeft size={14} /> Back to Archive
      </button>

      <div className="flex items-start justify-between">
        <div>
          <h1 className="text-2xl font-bold text-slate-900">{batch.fileName}</h1>
          <p className="text-sm text-slate-500 mt-1">
            Commission batch {batch.batchNumber}. Data validation passed for{" "}
            {batch.activeSellers} individual contributors.
          </p>
        </div>
        <div className="text-right shrink-0">
          <p className="text-xs text-slate-400 uppercase tracking-widest font-medium">
            Total Commission Pool
          </p>
          <p className="text-4xl font-bold text-brand-700 tabular-nums">
            {fmt(batch.totalCommissionPool)}
          </p>
        </div>
      </div>

      <div className="grid grid-cols-3 gap-4">
        <StatCard icon={<Users size={18} />} label="Active Sellers" value={String(batch.activeSellers)} />
        <StatCard icon={<DollarSign size={18} />} label="Average Payout" value={fmt(batch.averagePayout)} />
      </div>

      <div className="card p-4 flex gap-3 bg-brand-50/50 border-brand-100">
        <div className="w-8 h-8 rounded-lg bg-brand-100 flex items-center justify-center shrink-0 text-brand-700">
          <ShieldCheck size={16} />
        </div>
        <div>
          <p className="text-sm font-semibold text-slate-800">Financial Integrity Protocol</p>
          <p className="text-sm text-slate-500 mt-0.5">
            This report is locked for editing. Any changes to commission logic must be applied via a
            new calculation version to maintain the audit trail.
          </p>
        </div>
      </div>

      <div className="card p-6">
        <div className="flex items-center gap-3 mb-5">
          <div className="relative flex-1 max-w-xs">
            <input
              type="search"
              placeholder="Filter by name…"
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
              <Download size={14} /> Export CSV
            </button>
          </div>
        </div>

        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["Seller Name", "Total Sales ($)", "Commission Rate (%)", "Final Commission ($)"].map((h) => (
                <th key={h} className="pb-3 text-left text-[11px] font-bold text-slate-400 uppercase tracking-wider">
                  {h}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {paginated.length === 0 ? (
              <tr>
                <td colSpan={4} className="py-8 text-center text-slate-400 text-sm">No sellers found.</td>
              </tr>
            ) : (
              paginated.map((s) => (
                <tr key={s.id} className="hover:bg-slate-50/50 transition-colors">
                  <td className="py-4">
                    <div className="flex items-center gap-2.5">
                      <SellerAvatar initials={s.initials} />
                      <span className="font-medium text-slate-800">{s.name}</span>
                    </div>
                  </td>
                  <td className="py-4 font-mono text-slate-700">
                    {fmt(s.totalSales)}
                  </td>
                  <td className="py-4 text-slate-700">{fmtPct(s.commissionRate)}</td>
                  <td className="py-4 font-mono font-semibold text-brand-700">
                    {fmt(s.finalCommission)}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        <Pagination
          page={page}
          totalPages={totalPages}
          total={filtered.length}
          pageSize={pageSize}
          onPageChange={setPage}
        />
      </div>
    </div>
  );
}
