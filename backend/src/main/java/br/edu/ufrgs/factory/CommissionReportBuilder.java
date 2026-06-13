package br.edu.ufrgs.factory;

import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;

import java.util.List;

public class CommissionReportBuilder {
    private CommissionReport commissionReport;
    private String filename;
    private String status;
    private double commissionPool = 0.0;
    private int sellerCount = 0;
    private double averagePayout = 0.0;
    private List<Seller> sellers = null;

    public CommissionReportBuilder(CommissionReport commissionReport) {
        this.commissionReport = commissionReport;
    }

    public CommissionReportBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }
    public CommissionReportBuilder status(String status) {
        this.status = status;
        return this;
    }

    public CommissionReportBuilder sellers(List<Seller> sellers) {
        this.sellers = sellers;
        return this;
    }

    public CommissionReport build() {
        if(this.sellers == null)
            return null;

        this.sellerCount = this.sellers.size();

        double totalCommission = 0.0;
        for (Seller seller : this.sellers) {
            totalCommission += seller.getCommission();
        }

        this.commissionPool = totalCommission;
        this.averagePayout = totalCommission / this.sellerCount;

        this.commissionReport.setFilename(this.filename);
        this.commissionReport.setStatus(this.status);
        this.commissionReport.setCommissionPool(this.commissionPool);
        this.commissionReport.setSellerCount(this.sellerCount);
        this.commissionReport.setAveragePayout(this.averagePayout);
        this.commissionReport.setSellers(this.sellers);

        return this.commissionReport;
    }
}
