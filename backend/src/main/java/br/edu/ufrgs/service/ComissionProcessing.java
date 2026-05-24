package br.edu.ufrgs.service;

import java.util.List;
import java.util.ArrayList;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import br.edu.ufrgs.model.Commission;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.model.Sale;

public class ComissionProcessing {

  private CommissionPolicy comissions;

  public ComissionProcessing(CommissionPolicy commisionsList) {
    this.comissions = commisionsList;

  }

  public List<Commission> processComissions(List<Seller> sellers) {
    List<Commission> results = new ArrayList<>();
    List<CommissionRule> rules = this.comissions.getRules();

    for (Seller seller : sellers) {
      double totalSales = 0.0;

      // Soma todas as vendas que foram previamente associadas a este vendedor
      for (Sale sale : seller.getSales()) {
        totalSales += sale.getSalePrice();
      }

      double commissionValue = 0.0;

      // Descobre em qual faixa de comissão o valor se encaixa
      for (CommissionRule rule : rules) {
        if (totalSales >= rule.getMinimumGoal()) {
          commissionValue = totalSales * rule.getPercentage();
          break; // O break encerra a busca assim que acha a faixa correta
        }
      }

      // Cria a "folha de pagamento" (Commission) final deste vendedor e guarda
      Commission finalCommission = new Commission(seller.getName(), seller.getSellerId(), totalSales, commissionValue);
      results.add(finalCommission);
    }

    return results;
  }

}
