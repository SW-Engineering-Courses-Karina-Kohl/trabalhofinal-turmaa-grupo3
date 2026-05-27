package br.edu.ufrgs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommissionRuleTest {
  private CommissionRule commissionRule;

  @BeforeEach
  public void setUp() {
    this.commissionRule = new CommissionRule(2356.99, 0.05);
  }

  @Test
  public void testGetMinimumGoal() {
    double minimumGoal = commissionRule.getMinimumGoal();
    assertEquals(2356.99, minimumGoal, "MinimumGoal should be 2356.99");
  }

  @Test
  public void testGetPercentage() {
    double percentage = commissionRule.getPercentage();
    assertEquals(0.05, percentage, "Percentage should be 0.05");
  }

  @Test
  public void testNegativeMinimumGoal() {
    assertThrows(IllegalArgumentException.class,
                 () -> new CommissionRule(-34.98, 0.9),
                 "Negative minimumGoal should not be accepted");
  }

  @Test
  public void testNegativePercentage() {
    assertThrows(IllegalArgumentException.class,
                 () -> new CommissionRule(34.98, -0.9),
                 "Negative percentage should not be accepted");
  }
}
