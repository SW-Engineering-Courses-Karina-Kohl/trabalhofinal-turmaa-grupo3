package br.edu.ufrgs.factory;

import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.dto.Sale;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class SellerBuilder {
    private int sellerId;
    private String name;
    private String initials;
    private double totalSales = 0.0;
    private float commissionRate = 0.0f;
    private double commission = 0.0;
    private List<Sale> sales = new ArrayList<>();

    public SellerBuilder sellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public SellerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SellerBuilder setup(CommissionPolicy policy) {
        if(policy == null) return this;

        // initials
        this.initials = name.substring(0, 1);

        this.commissionRate = (float) policy.getCommissionRate(this.totalSales);
        this.commission = this.totalSales * this.commissionRate;

        return this;
    }

    public SellerBuilder addSale(Sale sale) {
        this.sales.add(sale);
        this.totalSales += sale.getSalePrice();
        return this;
    }

    public Seller build(CommissionReport commissionReport) {
        if (name == null || commissionReport == null)
            throw new IllegalStateException("name and commissionReport are required");
        return new Seller(sellerId, name, initials, totalSales, commissionRate, commission, commissionReport);
    }
}
