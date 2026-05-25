package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.Sale;
import br.edu.ufrgs.model.Seller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SalesCsvParser {
  public Map<Seller, List<Sale>> getSalesMap(String filePath) throws IOException {
    Map<Seller, List<Sale>> salesMap = new HashMap<>();
    Map<String, Seller> sellerIdMap = new HashMap<>();
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
            sellerIdMap.put(data[1], seller);
          }
          Sale sale = new Sale(Integer.parseInt(data[1]), data[0], Double.parseDouble(data[3]));
          seller.addSale(sale);
          // deal with saleMap now
          salesMap.putIfAbsent(seller, new ArrayList<>());
          salesMap.get(seller).add(sale);
        } catch (IllegalArgumentException e) {
          System.out.println("Error parsing sale in CSV: " + e.getMessage());
        }
      }
    }
    return salesMap;
  }
}
