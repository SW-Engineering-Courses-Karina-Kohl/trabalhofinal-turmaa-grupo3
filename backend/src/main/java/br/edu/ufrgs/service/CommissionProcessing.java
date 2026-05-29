package br.edu.ufrgs.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;

public class CommissionProcessing {
  // custom structure to store information for output CSV
  public record Result(int sellerId, String name, double totalSales, double commission) {}

  private CommissionPolicy commissions;

  public CommissionProcessing(CommissionPolicy commissionsList) {
    this.commissions = commissionsList;
  }

  // @what: calculate seller commission and prepare write-ready info for output CSV
  // @param: List<Seller> seller -> list of sellers
  // @return: List<Result> results -> lines to write to output CSV
  public List<Result> processCommissions(List<Seller> sellers) {
    List<Result> results = new ArrayList<>();
    // rules is a pointer
    List<CommissionRule> rules = this.commissions.getRules();

    for (Seller seller : sellers) {
      double totalSales = 0.0;

      for (Sale sale : seller.getSales()) {
        totalSales += sale.getSalePrice();
      }

      double commissionValue = 0.0;

      // check what commission range seller belongs to
      for (CommissionRule rule : rules) {
        if (totalSales >= rule.getMinimumGoal()) {
          commissionValue = totalSales * rule.getPercentage();
          break; // terminate loop when range found        
        }
      } 
        results.add(new Result(seller.getSellerId(), seller.getName(), totalSales, commissionValue)); 
    }

    return results;
  }
}
