package br.edu.ufrgs.model;

import br.edu.ufrgs.dto.CommissionRule;
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
    assertEquals(2356.99, minimumGoal, "minimumGoal should be 2356.99");
  }

  @Test
  public void testGetPercentage() {
    double percentage = commissionRule.getPercentage();
    assertEquals(0.05, percentage, "percentage should be 0.05");
  }

  @Test
  public void testNegativeMinimumGoal() {
    assertThrows(IllegalArgumentException.class,
        () -> new CommissionRule(-34.98, 0.9),
        "negative minimumGoal should not be accepted");
  }

  @Test
  public void testNegativePercentage() {
    assertThrows(IllegalArgumentException.class,
        () -> new CommissionRule(34.98, -0.9),
        "negative percentage should not be accepted");
  }

  @Test
  public void testInvalidPercentage() {
    assertThrows(IllegalArgumentException.class,
        () -> new CommissionRule(38.80, 1.3),
        "percentage over 1.0 should not be accepted");
  }
}
