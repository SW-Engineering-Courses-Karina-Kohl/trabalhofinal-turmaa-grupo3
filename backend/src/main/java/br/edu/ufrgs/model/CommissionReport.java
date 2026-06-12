package br.edu.ufrgs.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "commission_reports")
public class CommissionReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String status;

    @Column(name = "commission_pool", nullable = false)
    private double commissionPool = 0.0;

    @Column(name = "seller_count", nullable = false)
    private int sellerCount = 0;

    @Column(name = "average_payout", nullable = false)
    private double averagePayout = 0.0;

    @OneToMany(mappedBy = "commissionReport", fetch = FetchType.LAZY)
    private List<Seller> sellers;

    public CommissionReport(){}

    public CommissionReport(String filename, String status, double commissionPool, int sellerCount, double averagePayout, List<Seller> sellers) {
        this.filename = filename;
        this.status = status;
        this.commissionPool = commissionPool;
        this.sellerCount = sellerCount;
        this.averagePayout = averagePayout;
        this.sellers = sellers;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getStatus() {
        return status;
    }

    public double getCommissionPool() {
        return commissionPool;
    }

    public int getSellerCount() {
        return sellerCount;
    }

    public double getAveragePayout() {
        return averagePayout;
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCommissionPool(double commissionPool) {
        this.commissionPool = commissionPool;
    }

    public void setSellerCount(int sellerCount) {
        this.sellerCount = sellerCount;
    }

    public void setAveragePayout(double averagePayout) {
        this.averagePayout = averagePayout;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }
}
