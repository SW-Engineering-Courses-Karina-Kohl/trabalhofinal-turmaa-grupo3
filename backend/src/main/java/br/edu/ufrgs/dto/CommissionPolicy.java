package br.edu.ufrgs.dto;

import br.edu.ufrgs.dto.CommissionRule;
import java.util.Comparator;
import java.util.List;

// @what: class to encapsulate list of rules
public class CommissionPolicy {
  private List<CommissionRule> rules;

  public CommissionPolicy() {}

  public CommissionPolicy(List<CommissionRule> rules) {
    this.rules = rules;
    // sorts rules by highest minimumGoal to guarantee that
    // the seller will get the highest applicable comission
    this.rules.sort(Comparator.comparing(CommissionRule::getMinimumGoal).reversed());
  }

  public List<CommissionRule> getRules() {
    return this.rules;
  }

  public double getCommissionRate(double totalSales) {
    // check what commission range seller belongs to
    for (CommissionRule rule : rules) {
      if (totalSales >= rule.getMinimumGoal()) {
        return rule.getPercentage();
      }
    }

    return 0.0;
  }
}
