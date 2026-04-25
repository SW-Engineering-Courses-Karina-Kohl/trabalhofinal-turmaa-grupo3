package br.edu.ufrgs.model;

public class Sale {
  private int sellerId;
  private String saleId;
  private double salePrice;

  public Sale (int sellerId, String saleId, double salePrice) {
    this.sellerId = sellerId;
    this.saleId = saleId;
    this.salePrice = salePrice;
  }

  public int getSellerId() {
    return sellerId;
  }

  public String getSaleId() {
    return saleId;
  }

  public double getSalePrice() {
    return salePrice;
  }
}
