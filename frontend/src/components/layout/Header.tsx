import { Search, Bell } from "lucide-react";
import { useState } from "react";

export default function Header() {
  const [query, setQuery] = useState("");

  return (
    <header className="sticky top-0 z-10 flex items-center gap-4 bg-white/80 backdrop-blur border-b border-slate-100 px-8 py-3">
      {/* Search */}
      <div className="relative flex-1 max-w-sm">
        <Search
          size={15}
          className="absolute left-3 top-1/2 -translate-y-1/2 text-slate-400"
        />
        <input
          type="search"
          placeholder="Search archive..."
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          className="input-search pl-9"
          aria-label="Search archive"
        />
      </div>

      <div className="flex items-center gap-3 ml-auto">
        {/* Notifications */}
        <button
          className="relative p-2 rounded-xl text-slate-500 hover:bg-slate-50 hover:text-slate-700 transition-colors"
          aria-label="Notifications"
        >
          <Bell size={18} strokeWidth={2} />
          <span className="absolute top-1.5 right-1.5 w-2 h-2 rounded-full bg-red-500 ring-2 ring-white" />
        </button>

        {/* Avatar */}
        <div className="flex items-center gap-2.5">
          <div className="text-right leading-none">
            <p className="text-sm font-semibold text-slate-800">Alex Rivera</p>
            <p className="text-[10px] text-slate-400 uppercase tracking-wider mt-0.5">
              Admin
            </p>
          </div>
          <div className="w-9 h-9 rounded-full bg-slate-800 flex items-center justify-center text-white text-xs font-bold">
            AR
          </div>
        </div>
      </div>
    </header>
  );
}
