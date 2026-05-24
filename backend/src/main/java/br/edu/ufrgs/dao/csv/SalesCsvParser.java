package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.Sale;
import br.edu.ufrgs.model.Seller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesCsvParser {
  public List<Sale> readSales(String filePath) throws IOException {
    List<Sale> sales = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      br.readLine(); // Ignore CSV header
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        /*
         * Data[] vetor should contain>
         * data[0] : saleID,
         * data[1]: sellerID,
         * data[3]: sale price
         */
        try {
          Sale sale = new Sale(Integer.parseInt(data[1]), data[0], Double.parseDouble(data[3]));
          sales.add(sale);

        } catch (IllegalArgumentException e) {
          System.out.println("Error parsing sale in CSV: " + e.getMessage());

        }
      }
    }
    return sales;
  }

  public List<Seller> readSellers(String filePath) throws IOException {
    List<Seller> sellers = new ArrayList<>();
    List<String> instantiated = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;

      br.readLine(); // Ignore CSV header

      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");

        try {
          if (!instantiated.contains(data[1])) {
            Seller seller = new Seller(data[2], Integer.parseInt(data[1]));
            sellers.add(seller);
            instantiated.add(data[1]);

          }

        } catch (IllegalArgumentException e) {
          System.out.println("Error parsing seller in CSV: " + e.getMessage());

        }
      }
    }

    return sellers;
  }
}
