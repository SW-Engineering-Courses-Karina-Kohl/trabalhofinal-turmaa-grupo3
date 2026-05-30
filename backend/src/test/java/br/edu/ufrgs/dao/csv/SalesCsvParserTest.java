package br.edu.ufrgs.dao.csv;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

public class SalesCsvParserTest {
  private SalesCsvParser parser;
  private List<Seller> sellers;
  private List<Seller> parserSellers;
  private Seller sellerOne;
  private Seller sellerTwo;
  private Sale saleOneSellerOne;
  private Sale saleTwoSellerOne;
  private Sale saleOneSellerTwo;

  // @what: set up mocks for testing
  @BeforeEach
  public void setUp() {
    this.sellers = new ArrayList<>();
    this.parserSellers = new ArrayList<>();
    this.sellerOne = new Seller("João", 1);
    this.sellerTwo = new Seller("Maria", 2);
    this.saleOneSellerOne = new Sale(1, "V01", 5000.00);
    this.saleTwoSellerOne = new Sale(1, "V02", 6000.00);
    this.saleOneSellerTwo = new Sale(2, "V03", 12000.00);
    this.sellerOne.addSale(saleOneSellerOne);
    this.sellerOne.addSale(saleTwoSellerOne);
    this.sellerTwo.addSale(saleOneSellerTwo);
    this.sellers.add(sellerOne);
    this.sellers.add(sellerTwo);
    this.parser = new SalesCsvParser();
  }

  // @what: test parser
  @Test
  public void testSalesCsvParser() throws Exception {
    parserSellers = this.parser.getSellerList("src/test/resources/data/vendas.csv");
    assertEquals(this.sellers.size(), parserSellers.size(), "parser list should have 2 sellers.");
    assertEquals(this.sellers.get(0).getName(), parserSellers.get(0).getName(),
        "name of first seller should be the same.");
    assertEquals(sellers.get(0).getSellerId(), parserSellers.get(0).getSellerId(),
        "sellerId of first seller should be the same.");
    assertEquals(sellers.get(0).getSales().get(0).getSalePrice(),
        parserSellers.get(0).getSales().get(0).getSalePrice(),
        "salePrice of first sale of first seller should be the same.");
    assertEquals(sellers.get(0).getSales().get(1).getSalePrice(),
        parserSellers.get(0).getSales().get(1).getSalePrice(),
        "salePrice of second sale of first seller should be the same.");
    assertEquals(sellers.get(1).getName(), parserSellers.get(1).getName(),
        "name of second seller should be the same.");
    assertEquals(sellers.get(1).getSellerId(), parserSellers.get(1).getSellerId(),
        "sellerId of second seller should be the same.");
    assertEquals(sellers.get(1).getSales().get(0).getSalePrice(),
        parserSellers.get(1).getSales().get(0).getSalePrice(),
        "salePrice of first sale of second seller should be the same.");
  }

  // @what: test parser with invalid value
  @Test
  public void testSalesCsvParserInvalidArgument() throws IOException {
    assertThrows(IllegalArgumentException.class,
        () -> this.parser.getSellerList("src/test/resources/data/vendas_invalid.csv"),
        "invalid values should not be accepted");
  }
}
