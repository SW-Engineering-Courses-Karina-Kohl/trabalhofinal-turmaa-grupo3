import { describe, it, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Pagination from "@/components/ui/Pagination";

const BASE = {
  page: 1,
  totalPages: 25,
  total: 124,
  pageSize: 5,
  onPageChange: vi.fn(),
};

describe("Pagination", () => {
  it("shows correct item range", () => {
    render(<Pagination {...BASE} />);
    expect(screen.getByText(/Showing 1–5 of 124/)).toBeInTheDocument();
  });

  it("calls onPageChange with next page when next is clicked", async () => {
    const onPageChange = vi.fn();
    render(<Pagination {...BASE} onPageChange={onPageChange} />);
    await userEvent.click(screen.getByLabelText("Next page"));
    expect(onPageChange).toHaveBeenCalledWith(2);
  });

  it("disables previous button on first page", () => {
    render(<Pagination {...BASE} page={1} />);
    expect(screen.getByLabelText("Previous page")).toBeDisabled();
  });

  it("disables next button on last page", () => {
    render(<Pagination {...BASE} page={25} />);
    expect(screen.getByLabelText("Next page")).toBeDisabled();
  });

  it("marks current page as active", () => {
    render(<Pagination {...BASE} page={1} />);
    const btn = screen.getByRole("button", { name: "1" });
    expect(btn).toHaveAttribute("aria-current", "page");
  });

  it("shows last page number", () => {
    render(<Pagination {...BASE} />);
    expect(screen.getByRole("button", { name: "25" })).toBeInTheDocument();
  });
});
