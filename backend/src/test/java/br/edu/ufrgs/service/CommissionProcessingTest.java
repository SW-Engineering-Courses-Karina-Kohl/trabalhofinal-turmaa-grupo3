package br.edu.ufrgs.service;

import static org.junit.jupiter.api.Assertions.*;
import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.dto.CommissionRule;
import br.edu.ufrgs.model.CommissionReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.dto.Sale;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CommissionProcessingTest {
  private SalesReportProcessing commissionProcessor;
  private List<Seller> mockSellers = new ArrayList<>();

  private final CommissionReport commissionReport = new CommissionReport("", "", 0.0, 3, 0.0, mockSellers);

  private String csv = "";

  private final double[][] commissionRuleSet = new double[][]{
          new double[]{ 5600.99, 0.08 },
          new double[]{ 7600.99, 0.1  }
  };

  private String newSaleLine(Seller seller, Sale sale) {
    return String.format("%s,%s,%s,%.2f\n", sale.getSaleId(), seller.getSellerId(), seller.getName(), sale.getSalePrice());
  }

  // @what: set up mocks for testing
  @BeforeEach
  public void setUp() {
    List<CommissionRule> commissionRuleList = new ArrayList<>();
    for (double[] rule : commissionRuleSet) {
      commissionRuleList.add(new CommissionRule(rule[0], rule[1]));
    }
    CommissionPolicy commissionPolicy = new CommissionPolicy(commissionRuleList);
    commissionProcessor = new SalesReportProcessing(commissionPolicy);

    StringBuilder csvBuilder = new StringBuilder("vendaId,vendedorId,nome,valorVenda\n");

    mockSellers = new ArrayList<>();

    // -------- Seller 1 --------
    Seller seller1 = new Seller(67, "João", "J", 1400.00, 0.0f, 0.00, this.commissionReport);

    Sale saleV01 = new Sale(seller1.getSellerId(), "V01", 600.00);
    seller1.addSale(saleV01);
    csvBuilder.append(newSaleLine(seller1, saleV01));

    Sale saleV02 = new Sale(seller1.getSellerId(), "V02", 800.00);
    seller1.addSale(saleV02);
    csvBuilder.append(newSaleLine(seller1, saleV02));

    mockSellers.add(seller1);

    // -------- Seller 2 --------
    Seller seller2 = new Seller(37, "Ismael", "I", 9000.00, 0.1f, 900.00, this.commissionReport);

    Sale saleV03 = new Sale(seller2.getSellerId(), "V03", 8800.00);
    seller2.addSale(saleV03);
    csvBuilder.append(newSaleLine(seller2, saleV03));

    Sale saleV04 = new Sale(seller2.getSellerId(), "V04", 200.00);
    seller2.addSale(saleV04);
    csvBuilder.append(newSaleLine(seller2, saleV04));

    mockSellers.add(seller2);

    this.csv = csvBuilder.toString();
  }

  // @what: test processing of commissions
  @Test
  public void testProcessCommissions() {
      Seller mockSeller1 = mockSellers.get(0);
      Seller mockSeller2 = mockSellers.get(1);

      assertDoesNotThrow(() -> commissionProcessor.process("test", new StringReader(csv)));

      List<Seller> sellers = commissionProcessor.getSellers();

      Seller seller1 = sellers.get(0);
      Seller seller2 = sellers.get(1);

      assertEquals(mockSellers.size(), sellers.size(), "sellers size mismatch");

      Seller[][] pairings = new Seller[][]{
              new Seller[] {mockSeller1, seller1},
              new Seller[] {mockSeller2, seller2}
      };

      for (int i = 0; i < pairings.length; i++) {
        Seller[] pair = pairings[i];
        Seller mockSeller = pair[0];
        Seller seller = pair[1];

        assertEquals(mockSeller.getSellerId(),   seller.getSellerId(),   String.format("#%s sellerId mismatch", i));
        assertEquals(mockSeller.getName(),       seller.getName(),       String.format("#%s name mismatch", i));
        assertEquals(mockSeller.getTotalSales(), seller.getTotalSales(),0.001, String.format("#%s totalSales mismatch", i));
        assertEquals(mockSeller.getCommission(), seller.getCommission(), 0.001, String.format("#%s commission mismatch", i));
      }
  }
} 
