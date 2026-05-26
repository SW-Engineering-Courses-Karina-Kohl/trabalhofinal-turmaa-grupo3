package br.edu.ufrgs.model;

import java.util.List;
import java.util.Comparator;
import br.edu.ufrgs.model.CommissionRule;

public class CommissionPolicy {
  private List<CommissionRule> rules;

  public CommissionPolicy(List<CommissionRule> rules) {
    this.rules = rules;
    // sorts rules by highest minimumGoal to guarantee that
    // the seller will get the highest applicable comission
    this.rules.sort(Comparator.comparing(CommissionRule::getMinimumGoal).reversed());
  }

  public List<CommissionRule> getRules() {
    return this.rules;
  }
}
