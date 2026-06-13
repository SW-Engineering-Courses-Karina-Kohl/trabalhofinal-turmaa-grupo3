import { Routes, Route, Navigate } from "react-router-dom";
import Sidebar from "@/components/layout/Sidebar";
import UploadPage from "@/components/pages/UploadPage";
import CommissionReportsPage from "./components/pages/CommissionReportsPage";
import SellersPage from "./components/pages/SellersPage";
import { useEffect } from "react";
import { NotificationHandler } from "./services/NotificationHandler";
import { Toaster } from 'sonner';
import { useQueryClient } from "@tanstack/react-query";
import { COMISSIONS_QUERY_KEY } from "./api";
import { WEBSOCKET_ADDRESS } from "./config/config";

export default function App() {
  const queryClient = useQueryClient();

  useEffect(() => {
    const ws = new WebSocket(WEBSOCKET_ADDRESS);

    setInterval(() => {
        if (ws.readyState === WebSocket.OPEN) {
            ws.send(JSON.stringify({ command: "ping" }));
        }
    }, 30000);

    const onProcessedCommissionReport = (obj : any) => {
      queryClient.invalidateQueries({ queryKey: COMISSIONS_QUERY_KEY });
      console.log(obj);
    };

    //   queryClient
    NotificationHandler.setupCallbacks({
      onProcessingCommissionReport: (obj : any) => {
        console.log(obj);
      },
      onProcessedCommissionReport
    });

    ws.onopen = () => {
      ws.send(JSON.stringify({
        command: 'subscribe',
        identifier: JSON.stringify({ channel: 'CommissionReportsChannel' })
      }))
    }

    ws.onmessage = (event) => {
      console.log(event)
      const data = JSON.parse(event.data)
      NotificationHandler.parseNotificationPayload(data);
    }

    return () => ws.close() // cleanup on unmount
  }, [])

  return (
    <div className="flex min-h-screen">
      <Toaster richColors expand={false} position="bottom-right" />
      <Sidebar />
      <div className="flex flex-col flex-1 ml-[var(--sidebar-w)]">
        <main className="flex-1 p-8">
          <Routes>
            <Route path="/" element={<Navigate to="/upload" replace />} />
            <Route path="/upload" element={<UploadPage />} />
            <Route path="/commissions" element={<CommissionReportsPage />} />
            <Route path="/sellers/:id" element={<SellersPage />} />
          </Routes>
        </main>
      </div>
    </div>
  );
}
