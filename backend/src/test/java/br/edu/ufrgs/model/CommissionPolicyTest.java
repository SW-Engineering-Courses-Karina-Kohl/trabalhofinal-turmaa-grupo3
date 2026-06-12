package br.edu.ufrgs.model;

import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.dto.CommissionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class CommissionPolicyTest {
  private CommissionPolicy commissionPolicy;
  private CommissionRule ruleOne = new CommissionRule(5453.99, 0.05);
  private List<CommissionRule> commissionRuleList = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    commissionRuleList.add(ruleOne);
    this.commissionPolicy = new CommissionPolicy(commissionRuleList);
  }

  @Test
  public void testContainsRules() {
    List<CommissionRule> rules = commissionPolicy.getRules(); 
    assertEquals(1, rules.size(), "List of rules from method should have only one rule");
    assertTrue(rules.contains(ruleOne), "List of rules from method should contain the added list");
  }
}
