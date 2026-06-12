package br.edu.ufrgs.dao.export;

import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CommissionReportPdfWriter implements ExportFileContract{
    @Override
    public void write(List<Seller> sellers, Path path) throws IOException {
        if (Files.exists(path)) Files.delete(path);

        try (PdfWriter pdfWriter = new PdfWriter(path.toFile());
             PdfDocument pdf = new PdfDocument(pdfWriter);
             Document document = new Document(pdf)) {

            // 4 columns, full page width
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3, 2, 2}))
                    .useAllAvailableWidth();

            // Header row
            for (String header : new String[]{"ID", "Name", "Total Sales", "Commission"}) {
                table.addHeaderCell(new Cell().add(new Paragraph(header)));
            }

            // Data rows
            for (Seller seller : sellers) {
                table.addCell(String.valueOf(seller.getSellerId()));
                table.addCell(seller.getName());
                table.addCell(String.format(java.util.Locale.US, "%.2f", seller.getTotalSales()));
                table.addCell(String.format(java.util.Locale.US, "%.2f", seller.getCommission()));
            }

            document.add(table);
        }
    }
}