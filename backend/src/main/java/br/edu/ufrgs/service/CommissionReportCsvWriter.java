package br.edu.ufrgs.service;

import br.edu.ufrgs.service.CommissionProcessing;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



public class CommissionReportCsvWriter {
  public void write(List<CommissionProcessing.Result> results, String filePath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write("seller_id,name,total_sales,commission");
      writer.newLine();

      for (CommissionProcessing.Result result : results) {
        writer.write(String.format("%d, %s, %.2f, %.2f", result.sellerId(),
          escapeCsv(result.name()), result.totalSales(), result.commission()));
          writer.newLine();
      }
    }
  }

  private String escapeCsv(String value) {
    if (value == null) {
        return "";
    }
    if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
    return value;
  }
}
