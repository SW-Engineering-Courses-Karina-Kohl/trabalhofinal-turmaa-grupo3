package br.edu.ufrgs.dao.csv;

import java.util.List;
import java.util.ArrayList;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommissionRuleCsvParserTest {
  private CommissionRuleCsvParser parser;
  private List<CommissionRule> commissionRuleList = new ArrayList<>();
  private CommissionPolicy commissionPolicy;
  private CommissionPolicy parserCommissionPolicy;

  @BeforeEach 
  public void setUp() {
    CommissionRule ruleOne = new CommissionRule(0.00, 0.05);
    CommissionRule ruleTwo = new CommissionRule(10000.00, 0.08);
    commissionRuleList.add(ruleOne);
    commissionRuleList.add(ruleTwo);
    commissionPolicy = new CommissionPolicy(commissionRuleList);
  }

  @Test
  public void testReadRules() throws Exception {
    this.parser = new CommissionRuleCsvParser();
    parserCommissionPolicy = parser.readRules("src/test/resources/data/regras_comissao.csv");
    //test every field
    assertEquals(commissionPolicy.getRules().size(), parserCommissionPolicy.getRules().size(),
                 "Tamanho das listas de regras deve ser igual.");
    assertEquals(commissionPolicy.getRules().get(0).getMinimumGoal(),
                 parserCommissionPolicy.getRules().get(0).getMinimumGoal(),
                 "Objetivo mínimo da faixa maior deve ser igual.");
     assertEquals(commissionPolicy.getRules().get(0).getPercentage(),
                  parserCommissionPolicy.getRules().get(0).getPercentage(),
                  "Porcentagem da faixa maior deve ser igual.");
     assertEquals(commissionPolicy.getRules().get(1).getMinimumGoal(),
                  parserCommissionPolicy.getRules().get(1).getMinimumGoal(),
                  "Objetivo mínimo da faixa menor deve ser igual.");
     assertEquals(commissionPolicy.getRules().get(1).getPercentage(),
                  parserCommissionPolicy.getRules().get(1).getPercentage(),
                  "Porcentagem da faixa menor deve ser igual.");
  }
}
