package br.edu.ufrgs.model;

import java.util.List;
import java.util.Comparator;

public class ComissionPolicy {
  private List<ComissionRule> rules;

  public ComissionPolicy(List<ComissionRule> rules) {
    this.rules = rules;
    // sorts rules by highest minimumGoal to guarantee that
    // the seller will get the highest applicable comission
    this.rules.sort(Comparator.comparing(ComissionRule::getMinimumGoal()).reversed());
  }

  public List<ComissionRule> getRules() {
    return this.rules;
  }
}
