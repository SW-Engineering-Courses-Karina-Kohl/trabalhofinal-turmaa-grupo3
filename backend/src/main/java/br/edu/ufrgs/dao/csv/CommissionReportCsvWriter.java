package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.service.CommissionProcessing;
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
  public void write(List<CommissionProcessing.Result> results, String filePath) throws IOException {
    File outputFile = new File(filePath);
    // check if file exists
    // if not, create one
    if (outputFile.exists()) {
      outputFile.delete();
      outputFile.createNewFile();
    }

    // write-to-file logic 
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write("seller_id,name,total_sales,commission");
      writer.newLine();

      for (CommissionProcessing.Result result : results) {
        writer.write(String.format("%d,%s,%.2f,%.2f", result.sellerId(),
              result.name(), result.totalSales(), result.commission()));
        writer.newLine();
      }

      writer.close();
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
