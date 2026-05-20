import { Routes, Route, Navigate } from "react-router-dom";
import Sidebar from "@/components/layout/Sidebar";
import Header from "@/components/layout/Header";
import UploadPage from "@/components/pages/UploadPage";
import ResultsArchivePage from "@/components/pages/ResultsArchivePage";
import BatchDetailPage from "@/components/pages/BatchDetailPage";

export default function App() {
  return (
    <div className="flex min-h-screen">
      <Sidebar />
      <div className="flex flex-col flex-1 ml-[var(--sidebar-w)]">
        <Header />
        <main className="flex-1 p-8">
          <Routes>
            <Route path="/" element={<Navigate to="/upload" replace />} />
            <Route path="/upload" element={<UploadPage />} />
            <Route path="/archive" element={<ResultsArchivePage />} />
            <Route path="/archive/:batchId" element={<BatchDetailPage />} />
          </Routes>
        </main>
      </div>
    </div>
  );
}
