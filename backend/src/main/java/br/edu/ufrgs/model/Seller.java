package br.edu.ufrgs.model;

import br.edu.ufrgs.dto.Sale;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "sellers")
public class Seller {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private int sellerId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String initials;

  @JsonbProperty("total_sales")
  @Column(name = "total_sales", nullable = false)
  private double totalSales = 0.0f;

  @JsonbProperty("commission_rate")
  @Column(name = "commission_rate", nullable = false)
  private float commissionRate = 0.0f;

  @JsonbProperty("final_commission")
  @Column(nullable = false)
  private double commission = 0.0f;

  @JsonbTransient
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "commission_report_id", nullable = false)
  private CommissionReport commissionReport;

  @Transient
  private List<Sale> sales = new ArrayList<>();

  public Seller() {}

  public Seller(int sellerId, String name, String initials, double totalSales, float commissionRate, double commission, CommissionReport commissionReport) {
    this.sellerId = sellerId;
    this.name = name;
    this.commissionReport = commissionReport;
    this.initials = initials;
    this.totalSales = totalSales;
    this.commissionRate = commissionRate;
    this.commission = commission;
  }

  public int getId() {
    return id;
  }

  public int getSellerId() {
    return sellerId;
  }

  public String getInitials() {
    return initials;
  }

  public double getTotalSales() {
    return totalSales;
  }

  public float getCommissionRate() {
    return commissionRate;
  }

  public double getCommission() {
    return commission;
  }

  public String getName() {
    return name;
  }
  public CommissionReport getCommissionReport() {
    return commissionReport;
  }

  public List<Sale> getSales() {
    return sales;
  }

  public void addSale(Sale sale) {
    sales.add(sale);
  }
}
