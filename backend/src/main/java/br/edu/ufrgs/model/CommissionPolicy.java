package br.edu.ufrgs.model;

import br.edu.ufrgs.model.CommissionRule;
import java.util.Comparator;
import java.util.List;

// @what: class to encapsulate list of rules
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
