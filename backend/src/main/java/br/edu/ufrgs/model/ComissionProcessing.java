package br.edu.ufrgs.model;

import java.util.List;

public class ComissionProcessing {

  public void processComissions(List<Seller> sellers, ComissionPolicy policy) {
    List<ComissionRule> rules = policy.getRules();
    
    for (Seller seller : sellers) {
      double totalSales = 0.0;
      
      for (Sale sale : seller.getSales()) {
        totalSales += sale.getSalePrice();
      }
      
      double comission = 0.0;
      
      for (ComissionRule rule : rules) {
        if (totalSales >= rule.getMinimumGoal()) {
          comission = totalSales * rule.getPercentage();
          break; // Garante que pegou a maior faixa aplicável e encerra a busca
        }
      }
      
      seller.setSalary(comission);
    }
  }
}
