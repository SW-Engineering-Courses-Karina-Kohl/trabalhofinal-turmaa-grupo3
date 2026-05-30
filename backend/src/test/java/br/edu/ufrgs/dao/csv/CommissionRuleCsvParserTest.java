package br.edu.ufrgs.dao.csv;

import static org.junit.jupiter.api.Assertions.*;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class CommissionRuleCsvParserTest {
  private CommissionRuleCsvParser parser;
  private List<CommissionRule> commissionRuleList;
  private CommissionPolicy commissionPolicy;
  private CommissionPolicy parserCommissionPolicy;

  // @what: set up mocks for testing
  @BeforeEach 
  public void setUp() {
    CommissionRule ruleOne = new CommissionRule(0.00, 0.05);
    CommissionRule ruleTwo = new CommissionRule(10000.00, 0.08);
    this.commissionRuleList = new ArrayList<>();
    commissionRuleList.add(ruleOne);
    commissionRuleList.add(ruleTwo);
    commissionPolicy = new CommissionPolicy(commissionRuleList);
    this.parser = new CommissionRuleCsvParser();
  }

  // @what: test parser
  @Test
  public void testReadRules() throws Exception {
    parserCommissionPolicy = this.parser.readRules("src/test/resources/data/regras_comissao.csv");
    assertEquals(commissionPolicy.getRules().size(), parserCommissionPolicy.getRules().size(),
        "size of commission rule list should be the same.");
    assertEquals(commissionPolicy.getRules().get(0).getMinimumGoal(),
        parserCommissionPolicy.getRules().get(0).getMinimumGoal(),
        "minimumGoal of lower range should be the same.");
    assertEquals(commissionPolicy.getRules().get(0).getPercentage(),
        parserCommissionPolicy.getRules().get(0).getPercentage(),
        "percentage of lower range should be the same.");
    assertEquals(commissionPolicy.getRules().get(1).getMinimumGoal(),
        parserCommissionPolicy.getRules().get(1).getMinimumGoal(),
        "minimumGoal of higher range should be the same.");
    assertEquals(commissionPolicy.getRules().get(1).getPercentage(),
        parserCommissionPolicy.getRules().get(1).getPercentage(),
        "percentage of higher range should be the same.");
  }

  // @what: test invalid value in CSV
  @Test
  public void testReadRulesInvalid() throws Exception {
    assertThrows(IllegalArgumentException.class, 
        () -> this.parser.readRules("src/test/resources/data/regras_comissao_invalid.csv"),
        "invalid values should not be accepted.");
  }
}
