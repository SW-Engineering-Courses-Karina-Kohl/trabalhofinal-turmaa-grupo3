import { ChevronLeft, ChevronRight } from "lucide-react";

interface Props {
  page: number;
  totalPages: number;
  total: number;
  pageSize: number;
  nextPage:  () => void;
  prevPage:  () => void;
  canNext:  boolean; 
  canPrev:  boolean;
  goTo: (page: number) => void;
}

export default function Pagination({ page, totalPages, total, pageSize, nextPage, prevPage, canNext, canPrev, goTo }: Props) {
  const from = (page - 1) * pageSize + 1;
  const to   = Math.min(page * pageSize, total);

  const pages: (number | "…")[] = [];
  if (totalPages <= 5) {
    for (let i = 1; i <= totalPages; i++) pages.push(i);
  } else {
    pages.push(1);
    if (page > 3) pages.push("…");
    for (let i = Math.max(2, page - 1); i <= Math.min(totalPages - 1, page + 1); i++) {
      pages.push(i);
    }
    if (page < totalPages - 2) pages.push("…");
    pages.push(totalPages);
  }

  return (
    <div className="flex items-center justify-between pt-4 border-t border-slate-100">
      <p className="text-sm text-slate-400">
        Showing {from}–{to} of {total} results
      </p>
      <div className="flex items-center gap-1">
        <button
          onClick={prevPage}
          disabled={canPrev}
          className="p-1.5 rounded-lg text-slate-500 hover:bg-slate-100 disabled:opacity-30 disabled:cursor-not-allowed transition"
          aria-label="Previous page"
        >
          <ChevronLeft size={16} />
        </button>
        {pages.map((p, i) =>
          p === "…" ? (
            <span key={`ellipsis-${i}`} className="px-2 text-slate-400 text-sm">…</span>
          ) : (
            <button
              key={p}
              onClick={() => goTo(p as number)}
              className={`w-8 h-8 rounded-lg text-sm font-medium transition ${
                p === page
                  ? "bg-brand-700 text-white"
                  : "text-slate-600 hover:bg-slate-100"
              }`}
              aria-current={p === page ? "page" : undefined}
            >
              {p}
            </button>
          ),
        )}
        <button
          onClick={nextPage}
          disabled={canNext}
          className="p-1.5 rounded-lg text-slate-500 hover:bg-slate-100 disabled:opacity-30 disabled:cursor-not-allowed transition"
          aria-label="Next page"
        >
          <ChevronRight size={16} />
        </button>
      </div>
    </div>
  );
}
