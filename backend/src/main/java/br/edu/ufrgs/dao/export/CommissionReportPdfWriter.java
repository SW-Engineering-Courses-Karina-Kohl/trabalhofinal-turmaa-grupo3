package br.edu.ufrgs.dao.export;

import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CommissionReportPdfWriter implements ExportFileContract {

    // Colors from the design
    private static final DeviceRgb GREEN       = new DeviceRgb(0, 128, 80);
    private static final DeviceRgb LIGHT_GRAY  = new DeviceRgb(245, 247, 250);
    private static final DeviceRgb BORDER_GRAY = new DeviceRgb(220, 225, 230);
    private static final DeviceRgb TEXT_DARK   = new DeviceRgb(30, 35, 40);
    private static final DeviceRgb TEXT_GRAY   = new DeviceRgb(100, 110, 120);

    private static final int SELLERS_PER_PAGE = 10;

    @Override
    public void write(List<Seller> sellers, Path path) throws IOException {
        if (Files.exists(path)) Files.delete(path);

        sellers.sort(Comparator.comparingInt(Seller::getSellerId));

        try (PdfWriter pdfWriter = new PdfWriter(path.toFile());
             PdfDocument pdf = new PdfDocument(pdfWriter);
             Document doc = new Document(pdf)) {

            doc.setMargins(40, 50, 40, 50);

            // Partition sellers into pages
            int total = sellers.size();
            int pages = (int) Math.ceil((double) total / SELLERS_PER_PAGE);

            for (int p = 0; p < pages; p++) {
                List<Seller> pageSellers = sellers.subList(
                        p * SELLERS_PER_PAGE,
                        Math.min((p + 1) * SELLERS_PER_PAGE, total)
                );

                if (p == 0) {
                    addFirstPage(doc, pageSellers, sellers, p + 1, pages);
                } else {
                    doc.add(new AreaBreak());
                    addContinuationPage(doc, pageSellers, p + 1, pages);
                }
            }
        }
    }

    // ── First page ──────────────────────────────────────────────────────────

    private void addFirstPage(Document doc, List<Seller> pageSellers,
                              List<Seller> allSellers, int pageNum, int totalPages) {
        addHeader(doc);
        addDivider(doc);
        addExecutiveSummary(doc, allSellers);
        addSectionTitle(doc, "Lista detalhada de vendedores");
        addTable(doc, pageSellers);
        addFooter(doc, pageNum, totalPages);
    }

    // ── Continuation pages ───────────────────────────────────────────────────

    private void addContinuationPage(Document doc, List<Seller> pageSellers,
                                     int pageNum, int totalPages) {
        addSectionTitle(doc, "Lista detalhada de vendedores (continuação)");
        addTable(doc, pageSellers);
        addFooter(doc, pageNum, totalPages);
    }

    // ── Header ───────────────────────────────────────────────────────────────

    private void addHeader(Document doc) {
        Table header = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                .useAllAvailableWidth();

        // Left: title block
        Cell left = new Cell().setBorder(Border.NO_BORDER).setPadding(0);
        left.add(new Paragraph("SalesOps")
                .setFontSize(20).setBold().setFontColor(TEXT_DARK).setMarginBottom(2));
        left.add(new Paragraph("Relatório de Comissões")
                .setFontSize(9).setBold().setFontColor(GREEN).setMarginBottom(0));
        header.addCell(left);

        // Right: date block
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        Cell right = new Cell().setBorder(Border.NO_BORDER).setPadding(0)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        right.add(new Paragraph("Data do Relatório")
                .setFontSize(8).setFontColor(TEXT_GRAY).setMarginBottom(2));
        right.add(new Paragraph(date)
                .setFontSize(12).setBold().setFontColor(TEXT_DARK));
        header.addCell(right);

        doc.add(header);
        doc.add(new Paragraph().setMarginBottom(8));
    }

    // ── Divider ──────────────────────────────────────────────────────────────

    private void addDivider(Document doc) {
        doc.add(new Paragraph()
                .setBorderBottom(new SolidBorder(BORDER_GRAY, 1))
                .setMarginBottom(16));
    }

    // ── Executive summary cards ───────────────────────────────────────────────

    private void addExecutiveSummary(Document doc, List<Seller> sellers) {
        addSectionTitle(doc, "SUMÁRIO");

        double totalPool = sellers.stream().mapToDouble(Seller::getCommission).sum();
        int sellerCount = sellers.size();
        double avgPayout = sellerCount > 0 ? totalPool / sellerCount : 0;

        Table cards = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1}))
                .useAllAvailableWidth()
                .setMarginBottom(24);

        cards.addCell(summaryCard("Total de Comissões", formatMoney(totalPool), true));
        cards.addCell(summaryCard("Número de Vendedores", String.valueOf(sellerCount), false));
        cards.addCell(summaryCard("Média das Comissões", formatMoney(avgPayout), true));

        doc.add(cards);
    }

    private Cell summaryCard(String label, String value, boolean greenValue) {
        Cell card = new Cell()
                .setBackgroundColor(LIGHT_GRAY)
                .setBorder(new SolidBorder(BORDER_GRAY, 1))
                .setBorderRadius(new com.itextpdf.layout.properties.BorderRadius(6))
                .setPadding(16)
                .setMargin(4);

        card.add(new Paragraph(label)
                .setFontSize(9).setFontColor(TEXT_GRAY).setMarginBottom(6));
        card.add(new Paragraph(value)
                .setFontSize(16).setBold()
                .setFontColor(greenValue ? GREEN : TEXT_DARK)
                .setMarginBottom(0));

        return card;
    }

    // ── Section title ─────────────────────────────────────────────────────────

    private void addSectionTitle(Document doc, String title) {
        doc.add(new Paragraph(title)
                .setFontSize(9).setBold().setFontColor(TEXT_GRAY)
                .setMarginBottom(8));
    }

    // ── Seller table ──────────────────────────────────────────────────────────

    private void addTable(Document doc, List<Seller> sellers) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2}))
                .useAllAvailableWidth()
                .setBorder(new SolidBorder(BORDER_GRAY, 1))
                .setBorderRadius(new com.itextpdf.layout.properties.BorderRadius(6))
                .setMarginBottom(16);

        // Header row
        String[] headers = {"VENDEDOR", "TOTAL DE VENDAS", "COMISSÃO (%)", "TOTAL DE COMISSÂO"};
        for (String h : headers) {
            table.addHeaderCell(new Cell()
                    .setBackgroundColor(LIGHT_GRAY)
                    .setBorderBottom(new SolidBorder(GREEN, 2))
                    .setBorderTop(Border.NO_BORDER)
                    .setBorderLeft(Border.NO_BORDER)
                    .setBorderRight(Border.NO_BORDER)
                    .setPadding(10)
                    .add(new Paragraph(h)
                            .setFontSize(8).setBold().setFontColor(TEXT_GRAY)));
        }

        // Data rows
        for (Seller seller : sellers) {
            String rate = String.format(Locale.US, "%.1f%%",
                    seller.getCommissionRate() * 100);

            table.addCell(tableCell(seller.getName(), TEXT_DARK, true));
            table.addCell(tableCell(formatMoney(seller.getTotalSales()), TEXT_DARK, false));
            table.addCell(tableCell(rate, TEXT_DARK, false));
            table.addCell(tableCell(formatMoney(seller.getCommission()), GREEN, false));
        }

        doc.add(table);
    }

    private Cell tableCell(String value, DeviceRgb color, boolean bold) {
        Paragraph p = new Paragraph(value).setFontSize(10).setFontColor(color);
        if (bold) p.setBold();
        return new Cell()
                .setBorderLeft(Border.NO_BORDER)
                .setBorderRight(Border.NO_BORDER)
                .setBorderTop(Border.NO_BORDER)
                .setBorderBottom(new SolidBorder(BORDER_GRAY, 1))
                .setPadding(10)
                .add(p);
    }

    // ── Footer ────────────────────────────────────────────────────────────────

    private void addFooter(Document doc, int pageNum, int totalPages) {
        String hash = "0x" + Integer.toHexString(
                (pageNum + totalPages + System.identityHashCode(this))
        ).toUpperCase();

        Table footer = new Table(UnitValue.createPercentArray(new float[]{1}))
                .useAllAvailableWidth();

        Cell cell = new Cell().setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT);
        cell.add(new Paragraph(String.format("Página %02d/%02d", pageNum, totalPages))
                .setFontSize(8).setFontColor(TEXT_GRAY));
        cell.add(new Paragraph("Código: " + hash)
                .setFontSize(8).setFontColor(TEXT_GRAY));
        footer.addCell(cell);

        doc.add(footer);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String formatMoney(double value) {
        return String.format(Locale.US, "R$%,.2f", value);
    }
}