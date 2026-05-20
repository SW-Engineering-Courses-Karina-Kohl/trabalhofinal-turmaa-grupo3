import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import StatusBadge from "@/components/ui/StatusBadge";

describe("StatusBadge", () => {
  it("renders APPROVED status", () => {
    render(<StatusBadge status="APPROVED" />);
    expect(screen.getByText("APPROVED")).toBeInTheDocument();
  });

  it("renders REVIEWING status", () => {
    render(<StatusBadge status="REVIEWING" />);
    expect(screen.getByText("REVIEWING")).toBeInTheDocument();
  });

  it("renders PENDING status", () => {
    render(<StatusBadge status="PENDING" />);
    expect(screen.getByText("PENDING")).toBeInTheDocument();
  });

  it("renders upload Completed status", () => {
    render(<StatusBadge status="Completed" />);
    expect(screen.getByText("Completed")).toBeInTheDocument();
  });

  it("renders upload Processing status", () => {
    render(<StatusBadge status="Processing" withDot />);
    expect(screen.getByText("Processing")).toBeInTheDocument();
  });

  it("shows a dot indicator when withDot is true", () => {
    const { container } = render(<StatusBadge status="APPROVED" withDot />);
    // The dot is a span inside the badge
    const dot = container.querySelector("span > span");
    expect(dot).toBeInTheDocument();
  });
});
