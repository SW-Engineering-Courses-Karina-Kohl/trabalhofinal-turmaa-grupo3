import type { UploadStatus } from "@/types";

const MAP: Record<UploadStatus, string> = {
  Completed: "status-badge--approved",
  Processing:"status-badge--reviewing",
  Failed:    "status-badge--rejected",
};

const DOT: Record<UploadStatus, string> = {
  Completed: "bg-brand-500",
  Processing:"bg-amber-500",
  Failed:    "bg-red-500",
};

interface Props {
  status: UploadStatus;
  withDot?: boolean;
}

export default function StatusBadge({ status, withDot = false }: Props) {
  return (
    <span className={`status-badge ${MAP[status]}`}>
      {withDot && (
        <span className={`mr-1.5 w-1.5 h-1.5 rounded-full ${DOT[status]}`} />
      )}
      {status}
    </span>
  );
}
