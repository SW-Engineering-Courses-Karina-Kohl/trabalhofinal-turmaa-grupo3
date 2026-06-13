package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.dto.CommissionRule;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CommissionRuleCsvParser {
  // @what: read commission rules from CSV
  // @param: String filePath -> file location (with file name)
  // @return: CommissionPolicy -> contains List<CommissionRule>
  public CommissionPolicy readRules(InputStream stream) throws IOException {
    List<CommissionRule> rules = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
      String line;
      // ignore CSV header
      br.readLine();
      while ((line = br.readLine()) != null) {
        // split the line by the comma
        String[] data = line.split(",");
        // catch type errors
        double minimumGoal = Double.parseDouble(data[0]);
        double percentage = Double.parseDouble(data[1]);
        rules.add(new CommissionRule(minimumGoal, percentage));
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid value in CSV: " + e.getMessage());
    }

    return new CommissionPolicy(rules);
  }
}
