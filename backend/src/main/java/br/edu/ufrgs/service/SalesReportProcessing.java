package br.edu.ufrgs.service;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.ufrgs.dao.csv.SalesCsvParser;
import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.factory.CommissionReportBuilder;
import br.edu.ufrgs.factory.SellerBuilder;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;

public class SalesReportProcessing {
  private CommissionPolicy commissionPolicy;
  private CommissionReport commissionReport;
  private List<Seller> sellers;

  public SalesReportProcessing(CommissionPolicy commissionPolicy) {
    this.commissionPolicy = commissionPolicy;
    this.commissionReport = new CommissionReport();
  }

  public void process(String filename, Reader salesCSVReader) throws IOException {
    SalesCsvParser salesCsvParser = new SalesCsvParser();
    List<SellerBuilder> sellerBuilders = salesCsvParser.getSellerList(salesCSVReader);

    this.sellers = processSellerBuilders(sellerBuilders);

    CommissionReportBuilder commissionReportBuilder = new CommissionReportBuilder(this.commissionReport);
    commissionReportBuilder.filename(filename);
    commissionReportBuilder.status("processed");
    commissionReportBuilder.sellers(this.sellers);

    this.commissionReport = commissionReportBuilder.build();
  }

  public CommissionReport getCommissionReport() {
    if (commissionReport == null || commissionReport.getFilename() == null)
      throw new IllegalStateException("process() must be called before getCommissionReport()");
    return commissionReport;
  }

  public List<Seller> getSellers() {
    if (sellers == null)
      throw new IllegalStateException("process() must be called before getSellers()");
    return sellers;
  }

  // @what: calculate seller commission and prepare write-ready info for output CSV
  // @param: List<Seller> seller -> list of sellers
  // @return: List<Result> results -> lines to write to output CSV
  private List<Seller> processSellerBuilders(List<SellerBuilder> sellerBuilders) {
    return sellerBuilders.stream().map(this::buildSeller).collect(Collectors.toList());
  }

  private Seller buildSeller(SellerBuilder sellerBuilder) {
    return sellerBuilder.setup(this.commissionPolicy).build(this.commissionReport);
  }
}
