package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.service.SalesReportProcessing;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.List;
import java.io.File;

public class CommissionReportCsvWriter {
  // @what: generate and write data to output CSV
  // @param: List<Result> results -> list of lines to be written 
  // @param: String filePath -> output location (with file name)
  // @return: void

    private CommissionReport commissionReport;
    private List<Seller> sellers;

    public CommissionReportCsvWriter(CommissionReport commissionReport) {
        this.commissionReport = commissionReport;
        this.sellers = commissionReport.getSellers();
    }

    public void write(String filePath) throws IOException {
        File outputFile = new File(filePath);
        outputFile.delete();
        outputFile.createNewFile();

        // write-to-file logic
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
          writer.write("seller_id,name,total_sales,commission");
          writer.newLine();

          for(Seller seller : this.sellers) {
              String line = String.format(
                      java.util.Locale.US,
                      "%d,%s,%.2f,%.2f",
                      seller.getSellerId(),
                      seller.getName(),
                      seller.getTotalSales(),
                      seller.getCommission()
              );
              writer.write(line);
              writer.newLine();
          }
        }
      }

  /*
  // @what: helper for invalid seller name
  // @param: String value -> seller's name
  // @return: "corrected" value
  private String escapeCsv(String value) {
  if (value == null) {
  return "";
  }

  if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
  return "\"" + value.replace("\"", "\"\"") + "\"";
  }

  return value;
  }
  */
}
