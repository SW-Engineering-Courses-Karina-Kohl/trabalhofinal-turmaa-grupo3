package br.edu.ufrgs.dao.csv;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;
import java.util.ArrayList;
import java.util.List;

public class SalesCsvParserTest {
  SalesCsvParser parser;
  List<Seller> sellers = new ArrayList<>();
  List<Seller> parserSellers = new ArrayList<>();
  Seller sellerOne = new Seller("João", 1);
  Seller sellerTwo = new Seller("Maria", 2);
  Sale saleOneSellerOne = new Sale(1, "V01", 5000.00);
  Sale saleTwoSellerOne = new Sale(1, "V02", 6000.00);
  Sale saleOneSellerTwo = new Sale(2, "V03", 12000.00);

  // @what: set up mocks for testing
  @BeforeEach
  public void setUp() {
    sellerOne.addSale(saleOneSellerOne);
    sellerOne.addSale(saleTwoSellerOne);
    sellerTwo.addSale(saleOneSellerTwo);
    sellers.add(sellerOne);
    sellers.add(sellerTwo);
    parser = new SalesCsvParser();
  }

  // @what: test parser
  @Test
  public void testSalesCsvParser() throws Exception {
    parserSellers = this.parser.getSellerList("src/test/resources/data/vendas.csv");
    assertEquals(sellers.size(), parserSellers.size(), "parser list should not have more than 2 sellers.");
    assertEquals(sellers.get(0).getName(), parserSellers.get(0).getName(),
        "name of first seller should be the same.");
    assertEquals(sellers.get(0).getSellerId(), parserSellers.get(0).getSellerId(),
        "sellerId of first seller should be the same.");
    assertEquals(sellers.get(0).getSales().get(0).getSalePrice(), parserSellers.get(0).getSales().get(0).getSalePrice(),
        "salePrice of first sale of first seller should be the same.");
    assertEquals(sellers.get(0).getSales().get(1).getSalePrice(), parserSellers.get(0).getSales().get(1).getSalePrice(),
        "salePrice of second sale of first seller should be the same.");
    assertEquals(sellers.get(1).getName(), parserSellers.get(1).getName(),
        "name of second seller should be the same.");
    assertEquals(sellers.get(1).getSellerId(), parserSellers.get(1).getSellerId(),
        "sellerId of second seller should be the same.");
    assertEquals(sellers.get(1).getSales().get(0).getSalePrice(), parserSellers.get(1).getSales().get(0).getSalePrice(),
        "salePrice of first sale of second seller should be the same.");
  }
}
