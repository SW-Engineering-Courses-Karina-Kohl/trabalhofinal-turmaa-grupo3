package br.edu.ufrgs.dao.csv;

import static org.junit.jupiter.api.Assertions.*;

import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.dto.CommissionRule;
import br.edu.ufrgs.factory.SellerBuilder;
import br.edu.ufrgs.model.CommissionReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.dto.Sale;

import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SalesCsvParserTest {
  private SalesCsvParser parser;
  private List<Seller> sellers;
  private List<Seller> parserSellers;
  private Seller sellerOne;
  private Seller sellerTwo;
  private Sale saleOneSellerOne;
  private Sale saleTwoSellerOne;
  private Sale saleOneSellerTwo;
  private CommissionReport commissionReport;
  private CommissionPolicy commissionPolicy;

  // @what: set up mocks for testing
  @BeforeEach
  public void setUp() {
    List<CommissionRule> commissionRuleList = new ArrayList<>();
    commissionRuleList.add(new CommissionRule(0.00, 0.05));
    commissionRuleList.add(new CommissionRule(10000.00, 0.08));
    this.commissionPolicy = new CommissionPolicy(commissionRuleList);

    this.sellers = new ArrayList<>();
    this.parserSellers = new ArrayList<>();
    this.commissionReport = new CommissionReport("", "", 0.0, 3, 0.0, this.sellers);

    this.sellerOne = new Seller(1, "João", "J", 2, 0.0f, 11000.00, this.commissionReport);
    this.sellerTwo = new Seller(2, "Maria", "M", 1, 0.0f, 12000.00, this.commissionReport);

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
    List< SellerBuilder> builders = this.parser.getSellerList(new FileReader("src/test/resources/data/vendas.csv"));

    parserSellers = builders.stream()
            .map(b -> b.setup(this.commissionPolicy).build(this.commissionReport))
            .collect(Collectors.toList());


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
        () -> this.parser.getSellerList(new FileReader("src/test/resources/data/vendas_invalid.csv")),
        "invalid values should not be accepted");
  }
}
