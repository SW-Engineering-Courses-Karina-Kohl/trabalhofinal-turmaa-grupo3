package br.edu.ufrgs.dao.export;

import br.edu.ufrgs.dao.export.ExportFileContract;
import br.edu.ufrgs.model.Seller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

public class CommissionReportCsvWriter implements ExportFileContract {
  // @what: generate and write data to output CSV
  // @param: List<Result> results -> list of lines to be written 
  // @param: String filePath -> output location (with file name)
  // @return: void
    public void write(List<Seller> sellers, Path path) throws IOException {
        if(Files.exists(path)) Files.delete(path);
        Files.createFile(path);
        this.writeToFile(sellers, path.toFile());
    }


    public void write(List<Seller> sellers, String filePath) throws IOException {
        File outputFile = new File(filePath);
        outputFile.delete();
        outputFile.createNewFile();

        this.writeToFile(sellers, outputFile);
      }

      private void writeToFile(List<Seller> sellers, File file) throws IOException {
          sellers.sort(Comparator.comparingInt(Seller::getSellerId));

          // write-to-file logic
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
              writer.write("vendedor_id,nome,total_vendas,comissao");
              writer.newLine();

              for(Seller seller : sellers) {
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
