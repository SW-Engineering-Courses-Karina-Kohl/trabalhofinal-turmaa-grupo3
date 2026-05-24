package br.edu.ufrgs.service;

import java.util.List;
import br.edu.ufrgs.model.ComissionPolicy;
import br.edu.ufrgs.model.ComissionRule;

public class ComissionProcessing {
  private ComissionPolicy comissions;

  public ComissionProcessing (ComissionPolicy comissions) {
    this.comissions = comissions;
  }

  public void processComissions(List<Seller> sellers) {
    for (Seller seller: sellers) {
      for (ComissionRule interval: comissions) {
        if (seller.totalSold >= ComissionRule.minimumGoal) {
          return seller.totalSold * ComissionRule.percentage;
        }
      }
    }
  }
}
