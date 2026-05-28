package br.edu.ufrgs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;
import br.edu.ufrgs.model.CommissionRule;
import br.edu.ufrgs.model.CommissionPolicy;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CommissionProcessingTest {
  private CommissionProcessing commissionProcessor;
  private CommissionPolicy commissionPolicy;
  private List<CommissionRule> commissionRuleList = new ArrayList<>();
  private CommissionRule commissionRuleOne = new CommissionRule(5600.99, 0.08);
  private CommissionRule commissionRuleTwo = new CommissionRule(7600.99, 0.1);
  private List<Seller> sellers = new ArrayList<>();
  private Seller sellerOne = new Seller("João", 67);
  private Seller sellerTwo = new Seller("Ismael", 37);
  private Sale saleOneSellerOne = new Sale(67, "V01", 600.00);
  private Sale saleTwoSellerOne = new Sale(67, "V02", 800.00);
  private Sale saleOneSellerTwo = new Sale(37, "V03", 8800.00);
  private Sale saleTwoSellerTwo = new Sale(37, "V04", 200.00);
  List<CommissionProcessing.Result> results = new ArrayList<>();
  List<CommissionProcessing.Result> expectedResults = new ArrayList<>();
  
  // this might be problematic as CommissionProcessing has not been instantiated yet
  CommissionProcessing.Result lineOne = new CommissionProcessing.Result(67, "João", 1400.00, 0.00);
  CommissionProcessing.Result lineTwo = new CommissionProcessing.Result(37, "Ismael", 9000.00, 900.00);
  
  @BeforeEach
  public void setUp() {
    commissionRuleList.add(commissionRuleOne);
    commissionRuleList.add(commissionRuleTwo);
    this.commissionPolicy = new CommissionPolicy(commissionRuleList);
    sellerOne.addSale(saleOneSellerOne);
    sellerOne.addSale(saleOneSellerTwo);
    sellerTwo.addSale(saleOneSellerTwo);
    sellerTwo.addSale(saleTwoSellerTwo);
    sellers.add(sellerOne);
    sellers.add(sellerTwo);
    this.commissionProcessor = new CommissionProcessing(commissionPolicy);
    expectedResults.add(lineOne);
    expectedResults.add(lineTwo);
  }

  @Test
  public void testProcessCommissions() {
    results = commissionProcessor.processCommissions(sellers);
  
    // make all necessary comparisons, field by field
    assertEquals(expectedResults.get(0).sellerId(), results.get(0).sellerId(),
                 "SellerId do primeiro vendedor deve ser igual");
    assertEquals(expectedResults.get(0).name(), results.get(0).name(),
                 "Nome do primeiro vendedor deve ser igual");
    assertEquals(expectedResults.get(0).totalSales(), results.get(0).totalSales(),
                 "Total de vendas do primeiro vendedor deve ser igual");
    assertEquals(expectedResults.get(0).commission(), results.get(0).commission(),
                 "Comissão do primeiro vendedor deve ser igual");
    assertEquals(expectedResults.get(1).sellerId(), results.get(1).sellerId(),
                 "SellerId do segundo vendedor deve ser igual");
    assertEquals(expectedResults.get(1).name(), results.get(1).name(),
                 "Nome do segundo vendedor deve ser igual");
    assertEquals(expectedResults.get(1).totalSales(), results.get(1).totalSales(),
                 "Total de vendas do segundo vendedor deve ser igual");
    assertEquals(expectedResults.get(1).commission(), results.get(1).commission(),
                 "Comissão do segundo vendedor deve ser igual");
  }
} 
