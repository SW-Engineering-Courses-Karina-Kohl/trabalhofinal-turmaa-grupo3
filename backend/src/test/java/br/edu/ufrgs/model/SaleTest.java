package br.edu.ufrgs.model;

import br.edu.ufrgs.dto.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SaleTest {
  private Sale sale;

  @BeforeEach
  public void setUp() {
    this.sale = new Sale(8019, "V24", 340.99);
  }

  @Test
  public void testGetSellerId() {
    int sellerId = sale.getSellerId();
    assertEquals(8019, sellerId, "SellerId should be 8019");
  }

  @Test
  public void testGetSaleId() {
    String saleId = sale.getSaleId();
    assertEquals("V24", saleId, "SaleId should be V24");
  }

  @Test
  public void testGetSalePrice() {
    double salePrice = sale.getSalePrice();
    assertEquals(340.99, salePrice, "SalePrice should be 340.99");
  }
}
