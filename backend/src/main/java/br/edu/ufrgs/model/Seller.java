// TODO: LINK SALES TO THEIR SELLERS

package br.edu.ufrgs.model;

import java.util.ArrayList;
import java.util.List;

public class Seller {
  private String name;
  private int sellerId;
  private double salary;
  private List<Sale> sales;
  
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

  public double getSalary() {
    return this.salary; 
  }

  public void setSalary(double salary) {
    this.salary = salary;
  }

  public List<Sale> getSales() {
    return this.sales;
  }

  public void addSale(Sale sale) {
    this.sales.add(sale);
  }
}
