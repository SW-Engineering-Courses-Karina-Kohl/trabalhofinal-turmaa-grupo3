// TODO: LINK SALES TO THEIR SELLERS

package br.edu.ufrgs.model;

public class Seller {
  private String name;
  private int sellerId;
  private double salary;                                    // base salary + bonus
  private Sale sales[];                                     // all sales made by the seller
  
  public Seller(String name, int sellerId) {
    this.name = name;
    this.sellerId = sellerId;
  }

  public String getName() {
    return name;
  }

  public int getSellerId() {
    return sellerId;
  }

  public double getSalary() {
    return salario; 
  }

  public double calcSalary() {                              // method do calculate base salary + bonus
  
  }
}
