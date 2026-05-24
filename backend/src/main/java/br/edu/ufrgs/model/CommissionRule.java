package br.edu.ufrgs.model;

public class CommissionRule {
  private double minimumGoal;
  private double percentage;

  public CommissionRule(double minimumGoal, double percentage) {

    /* Verify values's integrity */
    if (minimumGoal < 0 || percentage < 0 || percentage > 1) {
      throw new IllegalArgumentException("Invalid values on commissions rule!");
    }

    this.minimumGoal = minimumGoal;
    this.percentage = percentage;
  }

  public double getMinimumGoal() {
    return minimumGoal;
  }

  public double getPercentage() {
    return percentage;
  }
}
