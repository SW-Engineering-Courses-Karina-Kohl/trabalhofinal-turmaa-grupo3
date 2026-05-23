package br.edu.ufrgs.infrastructure.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufrgs.model.ComissionRule;

public class ComissionRuleCsvParser {
  public List<ComissionRule> readRules(String filePath) throws IOException {

    List<ComissionRule> rules = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

      String line;
      
      // Ignore CSV header
      br.readLine();

      while ((line = br.readLine()) != null) {
        /*Split the line by the comma.*/
        String[] data = line.split(",");

        try {
          double minimumGoal = Double.parseDouble(data[0]);
          double percentage = Double.parseDouble(data[1]);
          rules.add(new ComissionRule(minimumGoal, percentage));
          
        } catch (NumberFormatException | IllegalArgumentException e) {
          System.out.println("Error: Invalid value in csv! " + e.getMessage());
        }
      }
    }

    return rules;
  }
}
