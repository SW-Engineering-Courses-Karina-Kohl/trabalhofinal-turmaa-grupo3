import { describe, it, expect } from "vitest";
import { render, screen } from "@testing-library/react";
import SellerAvatar from "@/components/ui/SellerAvatar";

describe("SellerAvatar", () => {
  it("renders initials", () => {
    render(<SellerAvatar initials="SM" />);
    expect(screen.getByText("SM")).toBeInTheDocument();
  });

  it("applies small size class by default", () => {
    const { container } = render(<SellerAvatar initials="SM" />);
    expect(container.firstChild).toHaveClass("w-8");
  });

  it("applies medium size class when size=md", () => {
    const { container } = render(<SellerAvatar initials="SM" size="md" />);
    expect(container.firstChild).toHaveClass("w-10");
  });

  it("is aria-hidden (decorative)", () => {
    const { container } = render(<SellerAvatar initials="SM" />);
    expect(container.firstChild).toHaveAttribute("aria-hidden", "true");
  });
});
