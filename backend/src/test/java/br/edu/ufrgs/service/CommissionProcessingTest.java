package br.edu.ufrgs.service;

import static org.junit.jupiter.api.Assertions.*;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;
import java.util.ArrayList;
import java.util.List;

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
  CommissionProcessing.Result lineOne = new CommissionProcessing.Result(67, "João", 1400.00, 0.00);
  CommissionProcessing.Result lineTwo = new CommissionProcessing.Result(37, "Ismael", 9000.00, 900.00);
 
  // @what: set up mocks for testing
  @BeforeEach
  public void setUp() {
    commissionRuleList.add(commissionRuleOne);
    commissionRuleList.add(commissionRuleTwo);
    commissionPolicy = new CommissionPolicy(commissionRuleList);
    sellerOne.addSale(saleOneSellerOne);
    sellerOne.addSale(saleTwoSellerOne);
    sellerTwo.addSale(saleOneSellerTwo);
    sellerTwo.addSale(saleTwoSellerTwo);
    sellers.add(sellerOne);
    sellers.add(sellerTwo);
    commissionProcessor = new CommissionProcessing(commissionPolicy);
    expectedResults.add(lineOne);
    expectedResults.add(lineTwo);
  }

  // @what: test processing of commissions
  @Test
  public void testProcessCommissions() {
    results = commissionProcessor.processCommissions(sellers);
    assertFalse(expectedResults.isEmpty(), "expectedResults list should not be empty.");
    assertFalse(results.isEmpty(), "results list should not be empty.");
    assertEquals(expectedResults.get(0).sellerId(), results.get(0).sellerId(),
                 "sellerId of first seller should be the same.");
    assertEquals(expectedResults.get(0).name(), results.get(0).name(),
                 "name of first seller should be the same.");
    assertEquals(expectedResults.get(0).totalSales(), results.get(0).totalSales(),
                 "totalSales of first seller should be the same.");
    assertEquals(expectedResults.get(0).commission(), results.get(0).commission(),
                 "commission of first seller should be the same.");
    assertEquals(expectedResults.get(1).name(), results.get(1).name(),
                 "name of second seller should be the same.");
    assertEquals(expectedResults.get(1).sellerId(), results.get(1).sellerId(),
                 "sellerId of second seller should be the same.");
    assertEquals(expectedResults.get(1).totalSales(), results.get(1).totalSales(),
                 "totalSales of second seller should be the same.");
    assertEquals(expectedResults.get(1).commission(), results.get(1).commission(),
                 "commission of second seller should be the same.");
  }
} 
