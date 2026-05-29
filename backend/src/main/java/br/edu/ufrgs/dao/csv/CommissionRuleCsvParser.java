package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.CommissionRule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CommissionRuleCsvParser {
  // @what: read commission rules from CSV
  // @param: String filePath -> file location (with file name)
  // @return: CommissionPolicy -> contains List<CommissionRule>
  public CommissionPolicy readRules(String filePath) throws IOException {
    List<CommissionRule> rules = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      // ignore CSV header
      br.readLine();
      while ((line = br.readLine()) != null) {
        // split the line by the comma
        String[] data = line.split(",");
        // catch type errors
        try {
          double minimumGoal = Double.parseDouble(data[0]);
          double percentage = Double.parseDouble(data[1]);
          rules.add(new CommissionRule(minimumGoal, percentage));
        } catch (IllegalArgumentException e) {
          System.out.println("error: invalid value in CSV! " + e.getMessage());
        }
      }
    }

    return new CommissionPolicy(rules);
  }
}
