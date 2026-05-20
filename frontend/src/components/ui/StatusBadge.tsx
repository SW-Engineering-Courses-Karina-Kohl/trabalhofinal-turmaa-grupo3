import type { SellerStatus, UploadStatus } from "@/types";

type BadgeStatus = SellerStatus | UploadStatus;

const MAP: Record<BadgeStatus, string> = {
  APPROVED:  "status-badge--approved",
  REVIEWING: "status-badge--reviewing",
  PENDING:   "status-badge--pending",
  REJECTED:  "status-badge--rejected",
  Completed: "status-badge--approved",
  Processing:"status-badge--reviewing",
  Failed:    "status-badge--rejected",
};

const DOT: Record<BadgeStatus, string> = {
  APPROVED:  "bg-brand-500",
  REVIEWING: "bg-amber-500",
  PENDING:   "bg-slate-400",
  REJECTED:  "bg-red-500",
  Completed: "bg-brand-500",
  Processing:"bg-amber-500",
  Failed:    "bg-red-500",
};

interface Props {
  status: BadgeStatus;
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
