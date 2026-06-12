package br.edu.ufrgs.model;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufrgs.dto.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {
  private Seller seller;
  private CommissionReport commissionReport;
  @BeforeEach
  public void setUp() {
    this.commissionReport = new CommissionReport("", "", 0.0, 3, 0.0, new ArrayList<>());
    this.seller = new Seller(896, "Adriana", "A", 0, 0.0f, 12000.00, this.commissionReport);
  }

  @Test
  public void testGetName() {
    String name = seller.getName();
    assertEquals("Adriana", name, "Seller name should be Adriana");
  }

  @Test
  public void testGetSellerId() {
    int sellerId = seller.getSellerId();
    assertEquals(896, sellerId, "SellerId should be 896");
  }

  @Test
  public void testGetSales() {
    List<Sale> sales = seller.getSales();
    assertTrue(sales.isEmpty(), "List of sales should be empty");
  }

  @Test
  public void testAddSale() {
    Sale sale = new Sale(384, "V23", 294.99);
    seller.addSale(sale);
    List<Sale> sales = seller.getSales();
    assertEquals(1, sales.size(), "There should be exactly one sale");
    assertTrue(sales.contains(sale), "Sales should contain added sale");
  }
}
