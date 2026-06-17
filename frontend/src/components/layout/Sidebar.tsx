import { NavLink } from "react-router-dom";
import { BarChart2, Upload, Archive } from "lucide-react";

const NAV = [
  { to: "/upload",  label: "Enviar Relatório de Vendas",   icon: Upload },
  { to: "/commissions", label: "Relatórios de Comissões",  icon: Archive },
];

export default function Sidebar() {
  return (
    <aside className="fixed inset-y-0 left-0 flex flex-col w-[var(--sidebar-w)] bg-white border-r border-slate-100 z-20">
      {/* Logo */}
      <div className="flex items-center gap-3 px-5 pt-6 pb-5">
        <div className="flex items-center justify-center w-9 h-9 rounded-xl bg-brand-700 text-white shadow-sm">
          <BarChart2 size={18} strokeWidth={2.5} />
        </div>
        <span className="font-bold text-slate-800 leading-tight text-sm">
          SalesOps
        </span>
      </div>

      {/* Main nav */}
      <nav className="px-3 space-y-0.5">
        {NAV.map(({ to, label, icon: Icon }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) =>
              `flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition-colors ${
                isActive
                  ? "bg-brand-50 text-brand-700"
                  : "text-slate-500 hover:bg-slate-50 hover:text-slate-700"
              }`
            }
          >
            {({ isActive }) => (
              <>
                <Icon
                  size={16}
                  strokeWidth={2}
                  className={isActive ? "text-brand-600" : "text-slate-400"}
                />
                {label}
              </>
            )}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
