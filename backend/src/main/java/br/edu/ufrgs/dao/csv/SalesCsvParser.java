package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesCsvParser {
  // @what: read sales from CSV
  // @param: String filePath -> file location (with file name)
  // @return: List<Seller> sellers -> each contains their own List<Sale>
  public List<Seller> getSellerList(String filePath) throws IOException {
    // hashmap to keep track of instantiated sellers
    Map<Integer, Seller> sellerIdMap = new HashMap<>();
    List<Seller> sellers = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      // ignore CSV header
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        // data[0]: saleID
        // data[1]: sellerID
        // data[2]: seller name
        // data[3]: salePrice
        try {
          // checks if seller with that sellerId has already been instantiated
          Seller seller = sellerIdMap.get(Integer.parseInt(data[1]));
          if (seller == null) {
            seller = new Seller(data[2], Integer.parseInt(data[1]));
            sellerIdMap.put(Integer.parseInt(data[1]), seller);
            sellers.add(seller);
          }
          Sale sale = new Sale(Integer.parseInt(data[1]), data[0], Double.parseDouble(data[3]));
          seller.addSale(sale);
        } catch (IllegalArgumentException e) {
          System.out.println("error parsing sale in CSV: " + e.getMessage());
        }
      }
    }

    return sellers;
  }
}
