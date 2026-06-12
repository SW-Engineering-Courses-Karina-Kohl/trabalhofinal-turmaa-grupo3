import { useRef, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { FileText, Upload, SlidersHorizontal } from "lucide-react";
import StatusBadge from "@/components/ui/StatusBadge";
import Pagination from "@/components/ui/Pagination";
import { uploadsApi } from "@/api";
import type { Upload as UploadType } from "@/types";

function formatDate(iso: string) {
  const d = new Date(iso);
  return `${d.toLocaleDateString("en-US", { month: "short", day: "numeric", year: "numeric" })} • ${d.toLocaleTimeString("en-US", { hour: "2-digit", minute: "2-digit", hour12: false })}`;
}

export default function UploadPage() {
  const navigate = useNavigate();
  const inputRef = useRef<HTMLInputElement>(null);
  const [dragging, setDragging] = useState(false);
  const [page, setPage] = useState(1);
  const pageSize = 10;

  const [uploads, setUploads] = useState<UploadType[]>([]);
  const [total, setTotal] = useState(0);
  const [uploading, setUploading] = useState(false);

  function loadUploads(p: number) {
    uploadsApi.listUploads({ page: p, pageSize }).then((result) => {
      setUploads(result.data);
      setTotal(result.total);
    }).catch(() => {});
  }

  useEffect(() => {
    loadUploads(page);
  }, [page]);

  function handleFileSelect(file: File) {
    setUploading(true);
    uploadsApi
      .uploadFile(file)
      .then(() => loadUploads(page))
      .finally(() => {
        setUploading(false);
        if (inputRef.current) inputRef.current.value = "";
      });
  }

  function handleDrop(e: React.DragEvent) {
    e.preventDefault();
    setDragging(false);
    const file = e.dataTransfer.files[0];
    if (file) handleFileSelect(file);
  }

  return (
    <div className="max-w-4xl space-y-8">
      <div>
        <h1 className="text-3xl font-bold text-slate-900">Upload &amp; Manage</h1>
        <p className="mt-1 text-slate-500">
          Import your sales data for automated commission processing. Ensure your
          CSV follows the standardized schema for accurate quarterly modeling.
        </p>
      </div>

      <div className="flex gap-6 items-start">
        {/* Drop zone */}
        <div
          onDragOver={(e) => { e.preventDefault(); setDragging(true); }}
          onDragLeave={() => setDragging(false)}
          onDrop={handleDrop}
          className={`flex-1 card flex flex-col items-center justify-center gap-4 py-16 px-8 border-2 border-dashed transition-colors cursor-pointer ${
            dragging ? "border-brand-400 bg-brand-50" : "border-slate-200 hover:border-slate-300"
          } ${uploading ? "opacity-60 pointer-events-none" : ""}`}
          onClick={() => !uploading && inputRef.current?.click()}
          role="button"
          tabIndex={0}
          onKeyDown={(e) => e.key === "Enter" && !uploading && inputRef.current?.click()}
          aria-label="Upload CSV file"
        >
          <div className="w-14 h-14 rounded-2xl bg-brand-50 flex items-center justify-center">
            <Upload size={24} className="text-brand-600" />
          </div>
          <div className="text-center">
            <p className="font-semibold text-slate-800">
              {uploading ? "Processing…" : "Upload CSV"}
            </p>
            <p className="text-sm text-slate-500 mt-1">
              Drag and drop your transaction file here, or{" "}
              <span className="text-brand-600 underline underline-offset-2">browse files</span>
            </p>
          </div>
          <div className="flex gap-4 text-xs text-slate-400">
            <span>✓ CSV, XLSX up to 25MB</span>
            <span>✓ ISO-8859-1 Compatible</span>
          </div>
          <input
            ref={inputRef}
            type="file"
            accept=".csv,.xlsx"
            className="hidden"
            onChange={(e) => { const f = e.target.files?.[0]; if (f) handleFileSelect(f); }}
          />
        </div>

        {/* Schema card */}
        <div className="card p-5 w-56 shrink-0 space-y-3">
          <p className="text-[11px] font-bold tracking-widest text-slate-400 uppercase">
            Standardized Schema
          </p>
          <div className="flex items-start gap-2 text-sm text-slate-600">
            <SlidersHorizontal size={14} className="text-slate-400 mt-0.5 shrink-0" />
            <span>Deal_ID, Amount, Date, Rep_Code, Status</span>
          </div>
          <div className="flex items-start gap-2 text-sm text-slate-600">
            <FileText size={14} className="text-slate-400 mt-0.5 shrink-0" />
            <span>
              Download the{" "}
              <a href="#" className="text-brand-600 font-medium hover:underline">
                Latest Template
              </a>{" "}
            </span>
          </div>
        </div>
      </div>

      {/* Recent Uploads table */}
      <div className="card p-6">
        <div className="flex items-center justify-between mb-5">
          <h2 className="font-semibold text-slate-800">Recent Uploads</h2>
        </div>

        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-100">
              {["File Name", "Date & Time", "Size", "Status", "Actions"].map((h) => (
                <th key={h} className="pb-3 text-left text-xs font-semibold text-slate-400 uppercase tracking-wider">
                  {h}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-slate-50">
            {uploads.length === 0 ? (
              <tr>
                <td colSpan={5} className="py-8 text-center text-slate-400 text-sm">
                  No uploads yet. Upload a CSV to get started.
                </td>
              </tr>
            ) : (
              uploads.map((u) => (
                <tr key={u.id} className="hover:bg-slate-50/50 transition-colors">
                  <td className="py-4">
                    <div className="flex items-center gap-2.5">
                      <FileText size={15} className="text-slate-400 shrink-0" />
                      <span className="font-medium text-slate-700">{u.fileName}</span>
                    </div>
                  </td>
                  <td className="py-4 text-slate-500">{formatDate(u.uploadedAt)}</td>
                  <td className="py-4 text-slate-500">{u.sizeMb > 0 ? `${u.sizeMb.toFixed(1)} MB` : "—"}</td>
                  <td className="py-4">
                    <StatusBadge status={u.status} withDot />
                  </td>
                  <td className="py-4">
                    <button
                      disabled={u.status === "Processing"}
                      className="btn-primary text-xs py-2 disabled:opacity-50 disabled:cursor-not-allowed"
                      onClick={() => navigate(`/archive/${u.id}`)}
                    >
                      View Results
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>

        <Pagination
          page={page}
          totalPages={Math.ceil(Math.max(total, 1) / pageSize)}
          total={total}
          pageSize={pageSize}
          onPageChange={setPage}
        />
      </div>
    </div>
  );
}
