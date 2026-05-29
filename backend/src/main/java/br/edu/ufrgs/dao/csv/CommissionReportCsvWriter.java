package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.service.CommissionProcessing;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CommissionReportCsvWriter {
  public void write(List<CommissionProcessing.Result> results, String filePath) throws IOException {

    // String filePath = "src/main/resources/data/comissoes_consolidadas.csv";

    try {
      // put it all inside the try catch
      File outputFile = new File(filePath);
      if (outputFile.exists()) {
        outputFile.delete();
        outputFile.createNewFile();
      }
      BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)); 
      writer.write("seller_id,name,total_sales,commission");
      writer.newLine();

      for (CommissionProcessing.Result result : results) {
        writer.write(String.format("%d, %s, %.2f, %.2f", result.sellerId(),
                     escapeCsv(result.name()), result.totalSales(), result.commission()));
        writer.newLine();
      }
      writer.close();
    }
  }

  // helper for invalid csv values input (, ", \n)
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
