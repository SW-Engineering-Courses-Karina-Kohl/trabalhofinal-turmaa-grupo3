package br.edu.ufrgs.model;

import java.util.List;
import java.util.ArrayList;

public class Seller {
  private String name;
  private int sellerId;
  private List<Sale> sales; // all sales made by the seller

  public Seller(String name, int sellerId) {
    this.name = name;
    this.sellerId = sellerId;
    this.sales = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public int getSellerId() {
    return sellerId;
  }

  public List<Sale> getSales() {
    return this.sales;

  }

  public void addSale(Sale sale) {
    this.sales.add(sale);
  }

}
