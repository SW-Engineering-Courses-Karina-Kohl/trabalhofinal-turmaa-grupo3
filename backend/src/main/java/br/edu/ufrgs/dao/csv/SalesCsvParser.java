package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesCsvParser {

  public List<Seller> getSellerList(String filePath) throws IOException {
    try (FileReader fr = new FileReader(filePath)) {
      return getSellerListFromReader(fr);
    }
  }

  public List<Seller> getSellerListFromReader(Reader reader) throws IOException {
    Map<Integer, Seller> sellerIdMap = new HashMap<>();
    List<Seller> sellers = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(reader)) {
      String line;
      br.readLine(); // skip header
      while ((line = br.readLine()) != null) {
        String[] data = line.split(",");
        try {
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
          throw new IllegalArgumentException("error parsing sale in CSV.");
        }
      }
    }
    return sellers;
  }
}
