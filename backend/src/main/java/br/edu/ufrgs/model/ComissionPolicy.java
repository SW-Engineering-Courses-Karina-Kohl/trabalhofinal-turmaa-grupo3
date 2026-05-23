package br.edu.ufrgs.model;

import java.util.List;

public class ComissionPolicy {
  private List<ComissionRule> rules;

  public ComissionPolicy(List<ComissionRule> rules) {
    this.rules = rules;
    // Ordena as regras da maior meta para a menor, para garantir 
    // que o valor caia na faixa mais alta aplicável.
    this.rules.sort((r1, r2) -> Double.compare(r2.getMinimumGoal(), r1.getMinimumGoal()));
  }

  public List<ComissionRule> getRules() {
    return this.rules;
  }
}
