package br.edu.ufrgs.dao.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufrgs.model.CommissionRule;
import br.edu.ufrgs.model.CommissionPolicy;

public class CommissionRuleCsvParser {
  public CommissionPolicy readRules(String filePath) throws IOException {

    List<CommissionRule> rules = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

      String line;

      // ignore CSV header
      br.readLine();

      while ((line = br.readLine()) != null) {
        // split the line by the comma
        String[] data = line.split(",");

        try {
          double minimumGoal = Double.parseDouble(data[0]);
          double percentage = Double.parseDouble(data[1]);
          rules.add(new CommissionRule(minimumGoal, percentage));

        } catch (IllegalArgumentException e) {
          System.out.println("Error: Invalid value in csv! " + e.getMessage());
        }
      }
    }

    return new CommissionPolicy(rules);
  }
}
