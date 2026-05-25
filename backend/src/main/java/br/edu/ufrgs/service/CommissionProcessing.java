package br.edu.ufrgs.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import br.edu.ufrgs.model.Commission;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;

public class CommissionProcessing {

  private CommissionPolicy commissions;

  public CommissionProcessing(CommissionPolicy commisionsList) {
    this.commissions = commisionsList;
  }

  public List<Commission> processCommissions(List<Seller> sellers) {
    List<Commission> results = new ArrayList<>();
    List<CommissionRule> rules = this.commissions.getRules();

    for (Seller seller : sellers) {
      double totalSales = 0.0;

      // soma todas as vendas que foram previamente associadas a este vendedor
      for (Sale sale : seller.getSales()) {
        totalSales += sale.getSalePrice();
      }

      double commissionValue = 0.0;

      // descobre em qual faixa de comissão o valor se encaixa
      for (CommissionRule rule : rules) {
        if (totalSales >= rule.getMinimumGoal()) {
          commissionValue = totalSales * rule.getPercentage();
          break; // break encerra a busca assim que acha a faixa correta
        }
      }

      // cria a "folha de pagamento" (Commission) final deste vendedor e guarda
      Commission finalCommission = new Commission(seller.getName(), seller.getSellerId(), totalSales, commissionValue);
      results.add(finalCommission);
    }

    return results;
  }
}
