package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.Sale;
import br.edu.ufrgs.model.Seller;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SalesCsvParserTest {
  SalesCsvParser parser;
  List<Seller> sellers = new ArrayList<>();
  List<Seller> parserSellers = new ArrayList<>();
  Seller sellerOne = new Seller("João", 1);
  Seller sellerTwo = new Seller("Maria", 2);
  Sale saleOneSellerOne = new Sale(1, "V01", 5000.00);
  Sale saleTwoSellerOne = new Sale(1, "V02", 6000.00);
  Sale saleOneSellerTwo = new Sale(2, "V03", 12000.00);

  @BeforeEach
  public void setUp() {
    sellerOne.addSale(saleOneSellerOne);
    sellerOne.addSale(saleTwoSellerOne);
    sellerTwo.addSale(saleOneSellerTwo);
    sellers.add(sellerOne);
    sellers.add(sellerTwo);
  }
  
  @Test
  public void testSalesCsvParser() throws Exception {
    parser = new SalesCsvParser();
    parserSellers = parser.getSellerList("src/test/resources/data/vendas.csv");

    assertEquals(sellers.size(), parserSellers.size(), "Lista do parser não pode ter mais do que 2 vendedores.");
    //test field by field
    assertEquals(sellers.get(0).getName(), parserSellers.get(0).getName(),
                 "Nome do primeiro vendedor deve ser igual.");
    assertEquals(sellers.get(0).getSellerId(), parserSellers.get(0).getSellerId(),
                 "Id do primeiro vendedor deve ser igual.");
    assertEquals(sellers.get(0).getSales().get(0).getSalePrice(), parserSellers.get(0).getSales().get(0).getSalePrice(),
                 "Valor da primeira venda do primeiro vendedor deve ser igual.");
    
    /* assertEquals(sellers.get(0).getSales().get(1).getSalePrice(), parserSellers.get(0).getSales().get(1).getSalePrice(),
                 "Valor da segunda venda do primeiro vendedor deve ser igual."); */

    assertEquals(sellers.get(1).getName(), parserSellers.get(1).getName(),
                 "Nome do segundo vendedor deve ser igual.");
    assertEquals(sellers.get(1).getSellerId(), parserSellers.get(1).getSellerId(),
                 "Id do segundo vendedor deve ser igual.");
    assertEquals(sellers.get(1).getSales().get(0).getSalePrice(), parserSellers.get(1).getSales().get(0).getSalePrice(),
                 "Valor da primeira venda do segundo vendedor deve ser igual.");
  }
}
