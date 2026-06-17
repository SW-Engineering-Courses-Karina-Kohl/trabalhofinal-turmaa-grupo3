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

    float commissionRate = 0f;
    double totalSales = 0.0;

    Sale saleOneSellerOne = new Sale(1, "V01", 5000.00);
    Sale saleTwoSellerOne = new Sale(1, "V02", 6000.00);
    totalSales = saleOneSellerOne.getSalePrice() + saleTwoSellerOne.getSalePrice();
    commissionRate = (float) this.commissionPolicy.getCommissionRate(totalSales);
    Seller sellerOne = new Seller(1, "João", "J", totalSales, commissionRate, totalSales * commissionRate, this.commissionReport);
    sellerOne.addSale(saleOneSellerOne);
    sellerOne.addSale(saleTwoSellerOne);
    this.sellers.add(sellerOne);

    Sale saleOneSellerTwo = new Sale(2, "V03", 12000.00);
    totalSales = saleOneSellerTwo.getSalePrice();
    commissionRate = (float) this.commissionPolicy.getCommissionRate(totalSales);
    Seller sellerTwo = new Seller(2, "Maria", "M",  totalSales, commissionRate, totalSales * commissionRate, this.commissionReport);
    sellerTwo.addSale(saleOneSellerTwo);
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


    assertEquals(this.sellers.size(), parserSellers.size(), String.format("parser list should have %s sellers.", sellers.size()));
    for(Seller parsedSeller : parserSellers) {
      for(Seller seller : this.sellers) {
        if (parsedSeller.getSellerId() == seller.getSellerId()) {

          assertEquals(seller.getName(), parsedSeller.getName(),"name of seller should be the same.");
          assertEquals(seller.getCommissionRate(), parsedSeller.getCommissionRate(), 0.001,"commissionRate of first seller should be the same.");
          assertEquals(seller.getCommission(), parsedSeller.getCommission(), 0.001,"commission of first seller should be the same.");
          assertEquals(seller.getSales().size(), parsedSeller.getSales().size(),"sales count of should be the same.");

          for(Sale sale : seller.getSales()) {
            for(Sale parsedSale : parsedSeller.getSales()) {
              if(sale.getSaleId().equals(parsedSale.getSaleId())) {
                assertEquals(sale.getSalePrice(), parsedSale.getSalePrice(), 0.001,"salePrice of first sale of first seller should be the same.");
              }
            }
          }

          break;
        }
      }
    }
  }

  // @what: test parser with invalid value
  @Test
  public void testSalesCsvParserInvalidArgument() throws IOException {
    assertThrows(IllegalArgumentException.class,
        () -> this.parser.getSellerList(new FileReader("src/test/resources/data/vendas_invalid.csv")),
        "invalid values should not be accepted");
  }
}
