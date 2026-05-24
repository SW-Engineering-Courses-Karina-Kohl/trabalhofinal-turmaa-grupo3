package br.edu.ufrgs.model;

public class ComissionRule {
  private double minimumGoal;
  private double percentage;

  public ComissionRule(double minimumGoal, double percentage) {
    // verify value interval
    if (minimumGoal < 0 || percentage < 0 || percentage > 1 ) {
      throw new IllegalArgumentException("Invalid values on commissions rule!");
    }

    this.minimumGoal = minimumGoal;
    this.percentage = percentage;
  }

  public double getMinimumGoal() {
    return this.minimumGoal;
  }

  public double getPercentage() {
    return this.percentage;
  }
}
