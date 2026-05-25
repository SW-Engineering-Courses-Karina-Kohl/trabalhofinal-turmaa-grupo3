package br.edu.ufrgs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
// import regular Sale class to test adding sales

public class SellerTest {
  private Seller seller;

  @BeforeEach
  public void setUp() {
    seller = new Seller("Adriana", 896);
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
    assertNull(sales, "List of sales should be empty");
  }

  public void testAddSale() {
    Sale sale = new Sale (/* pass sale parameters */);
  }
}

