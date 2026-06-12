import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Archive, ChevronRight } from "lucide-react";
import { commissionsApi } from "@/api";
import type { CommissionBatch } from "@/types";

function fmt(n: number) {
  return n.toLocaleString("en-US", { style: "currency", currency: "USD" });
}

export default function ResultsArchivePage() {
  const navigate = useNavigate();
  const [batches, setBatches] = useState<CommissionBatch[]>([]);

  useEffect(() => {
    commissionsApi.listBatches().then((result) => setBatches(result.data)).catch(() => {});
  }, []);

  return (
    <div className="max-w-4xl space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-slate-900">Results Archive</h1>
        <p className="mt-1 text-slate-500">All finalized commission batches.</p>
      </div>

      <div className="space-y-3">
        {batches.length === 0 ? (
          <p className="text-slate-400 text-sm">No commission batches yet. Upload a CSV to get started.</p>
        ) : (
          batches.map((batch) => (
            <button
              key={batch.id}
              onClick={() => navigate(`/archive/${batch.id}`)}
              className="card w-full p-5 flex items-center gap-6 text-left hover:shadow-md hover:-translate-y-0.5 transition-all group"
            >
              <div className="w-11 h-11 rounded-xl bg-brand-50 flex items-center justify-center shrink-0">
                <Archive size={18} className="text-brand-600" />
              </div>

              <div className="flex-1 min-w-0">
                <p className="font-semibold text-slate-800 truncate">{batch.fileName}</p>
                <p className="text-sm text-slate-400 mt-0.5">
                  Batch {batch.batchNumber} · {batch.activeSellers} sellers
                </p>
              </div>

              <div className="text-right shrink-0">
                <p className="text-lg font-bold text-brand-700">{fmt(batch.totalCommissionPool)}</p>
                <p className="text-xs text-slate-400 mt-0.5">Commission Pool</p>
              </div>

              <ChevronRight
                size={18}
                className="text-slate-300 group-hover:text-slate-500 transition-colors shrink-0"
              />
            </button>
          ))
        )}
      </div>
    </div>
  );
}
