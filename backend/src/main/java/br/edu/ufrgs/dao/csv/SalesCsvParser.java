package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.Sale;
import br.edu.ufrgs.model.Seller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SalesCsvParser {
  public List<Seller> getSellerList(String filePath) throws IOException {
    Map<Integer, Seller> sellerIdMap = new HashMap<>();
    List<Seller> sellers = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      br.readLine(); // ignore CSV header
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        /*
         * data[0]: saleID
         * data[1]: sellerID
         * data[2]: Seller.name
         * data[3]: salePrice
         */
        try {
          Seller seller = sellerIdMap.get(data[1]);
          if (seller == null) {
            seller = new Seller(data[2], Integer.parseInt(data[1]));
            sellerIdMap.put(Integer.parseInt(data[1]), seller);
            sellers.add(seller);
          }
          Sale sale = new Sale(Integer.parseInt(data[1]), data[0], Double.parseDouble(data[3]));
          seller.addSale(sale);
        } catch (IllegalArgumentException e) {
          System.out.println("Error parsing sale in CSV: " + e.getMessage());
        }
      }
    }
    return sellers;
  }
}
