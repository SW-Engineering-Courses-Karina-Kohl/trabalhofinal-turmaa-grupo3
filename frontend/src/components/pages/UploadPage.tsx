import { useRef, useState } from "react";
import { Upload, SlidersHorizontal } from "lucide-react";
import { useUploadApi } from "@/api/uploads";

export default function UploadPage() {
  const inputRef = useRef<HTMLInputElement>(null);
  const [dragging, setDragging] = useState(false);


  const UploadAPIController = useUploadApi();

  function handleFileSelect(file: File) {
    UploadAPIController.uploadSalesReport(file);
    console.info("Arquivo selecionado:", file.name);
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
        <h1 className="text-3xl font-bold text-slate-900">Enviar Relatório de Vendas</h1>
        <p className="mt-1 text-slate-500">
          Envie seus dados de vendas para processar as comissões automaticamente.
          Verifique se o CSV segue o padrão descrito no Esquema Padrão.
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
          }`}
          onClick={() => inputRef.current?.click()}
          role="button"
          tabIndex={0}
          onKeyDown={(e) => e.key === "Enter" && inputRef.current?.click()}
          aria-label="Upload CSV file"
        >
          <div className="w-14 h-14 rounded-2xl bg-brand-50 flex items-center justify-center">
            <Upload size={24} className="text-brand-600" />
          </div>
          <div className="text-center">
            <p className="font-semibold text-slate-800">Enviar CSV</p>
            <p className="text-sm text-slate-500 mt-1">
              Puxe e coloque o arquivo aqui, ou clique para{" "}
              <span className="text-brand-600 underline underline-offset-2">selecionar o arquivo</span>
            </p>
          </div>
          <div className="flex gap-4 text-xs text-slate-400">
            <span>✓ CSV, XLSX </span>
            <span>✓ ISO-8859-1 </span>
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
            Esquema Padrão 
          </p>
          <div className="flex items-start gap-2 text-sm text-slate-600">
            <SlidersHorizontal size={14} className="text-slate-400 mt-0.5 shrink-0" />
            <span>venda_id, vendedor_id, nome, valor_venda</span>
          </div>
        </div>
      </div>
    </div>
  );
}
