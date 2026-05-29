package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.service.CommissionProcessing;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.io.File;

public class CommissionReportCsvWriterTest {
  private CommissionReportCsvWriter writer;
  private List<CommissionProcessing.Result> results = new ArrayList<>();

  // @what: set up mocks for testing
  @BeforeEach
  public void setUp() {
    CommissionProcessing.Result lineOne = new CommissionProcessing.Result(01, "João,", 11000.00, 880.00);
    CommissionProcessing.Result lineTwo = new CommissionProcessing.Result(01, "M\naria", 12000.00, 960.00);
    results.add(lineOne);
    results.add(lineTwo);
    this.writer = new CommissionReportCsvWriter();
  }

  // @what: test output CSV writer and compare to expected result
  @Test
  public void writeTest() throws Exception{
    String filePath = "src/test/resources/data/comissoes_consolidadas.csv";
    writer.write(results, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists(), "Ouput file should exist.");
    try {
      // get all lines from output CSV
      List<String> fileLines = Files.readAllLines(Paths.get(filePath));
      String lineTwo = fileLines.get(0);
      String[] charLineTwo = lineTwo.split(",");

      assertEquals(results.get(0).sellerId(), Integer.parseInt(charLineTwo[0]), "sellerId should be the same in line 2.");
      assertEquals(results.get(0).name(), charLineTwo[1], "seller name should be the same in line 2.");
      assertEquals(results.get(0).totalSales(), Double.parseDouble(charLineTwo[2]), "totalSales should be the same in line 2.");
      assertEquals(results.get(0).commission(), Double.parseDouble(charLineTwo[3]), "commission should be the same in line 2.");

      String lineThree = fileLines.get(1);
      String[] charLineThree = lineThree.split(",");

      assertEquals(results.get(1).sellerId(), Integer.parseInt(charLineThree[0]), "sellerId should be the same in line 3.");
      assertEquals(results.get(1).name(), charLineThree[1], "seller name should be the same in line 3.");
      assertEquals(results.get(1).totalSales(), Double.parseDouble(charLineThree[2]), "totalSales should be the same in line 3.");
      assertEquals(results.get(1).commission(), Double.parseDouble(charLineThree[3]), "commission should be the same in line 3.");
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }
}
