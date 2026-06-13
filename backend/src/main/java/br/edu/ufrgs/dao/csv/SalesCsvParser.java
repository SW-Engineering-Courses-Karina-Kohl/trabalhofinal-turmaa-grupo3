package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.factory.SellerBuilder;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.dto.Sale;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesCsvParser {
  // @what: read sales from CSV
  // @param: String filePath -> file location (with file name)
  // @return: List<sellerBuilders> sellerBuilders -> each contains their own List<Sale>
  public List<SellerBuilder> getSellerList(Reader reader) throws IOException {
    // hashmap to keep track of instantiated sellerBuilders
    Map<Integer, SellerBuilder> sellerIdMap = new HashMap<>();
    List<SellerBuilder> sellerBuilders = new ArrayList<>();

    //try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    try (BufferedReader br = new BufferedReader(reader)) {
      String line;
      // ignore CSV header
      br.readLine();
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");

        // data[0]: saleID
        // data[1]: sellerID
        // data[2]: seller name
        // data[3]: salePrice

        String saleId = data[0];
        int sellerId = Integer.parseInt(data[1]);
        String sellerName = data[2];
        double salePrice = Double.parseDouble(data[3]);

        try {
          // checks if seller with that sellerId has already been instantiated
          SellerBuilder sellerBuilder = sellerIdMap.get(sellerId);
          if (sellerBuilder == null) {
            sellerBuilder = new SellerBuilder();
            sellerBuilder.sellerId(sellerId);
            sellerBuilder.name(sellerName);

            sellerIdMap.put(sellerId, sellerBuilder);
            sellerBuilders.add(sellerBuilder);
          }

          Sale sale = new Sale(sellerId, saleId, salePrice);
          sellerBuilder.addSale(sale);

        } catch (IllegalArgumentException e) {
          System.out.println("error parsing sale in CSV: " + e.getMessage());
          throw new IllegalArgumentException("error parsing parsing sale in CSV.");
        }
      }
    }

    return sellerBuilders;
  }
}
