const COLORS = [
  "bg-violet-100 text-violet-700",
  "bg-sky-100 text-sky-700",
  "bg-amber-100 text-amber-700",
  "bg-rose-100 text-rose-700",
  "bg-teal-100 text-teal-700",
];

function colorFor(initials: string) {
  const idx = initials.charCodeAt(0) % COLORS.length;
  return COLORS[idx];
}

interface Props {
  initials: string;
  size?: "sm" | "md";
}

export default function SellerAvatar({ initials, size = "sm" }: Props) {
  const sizeClass = size === "sm" ? "w-8 h-8 text-xs" : "w-10 h-10 text-sm";
  return (
    <div
      className={`${sizeClass} ${colorFor(initials)} rounded-full flex items-center justify-center font-semibold shrink-0`}
      aria-hidden="true"
    >
      {initials}
    </div>
  );
}
