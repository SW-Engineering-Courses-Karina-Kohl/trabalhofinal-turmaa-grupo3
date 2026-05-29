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
   this.writer = new CommissionReportCsvWriter();
  }

  // @what: test write with badly formatted seller name
  @Test
  public void writeTestCommaInName() throws Exception{
    CommissionProcessing.Result inputLineOne = new CommissionProcessing.Result
      (01, "João,", 11000.00, 880.00);
    CommissionProcessing.Result inputLineTwo = new CommissionProcessing.Result
      (01, "Maria", 12000.00, 960.00);
    results.add(inputLineOne);
    results.add(inputLineTwo);
    String filePath = "src/test/resources/data/comissoes_consolidadas.csv";
    writer.write(results, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists(), "output file should exist.");
    try {
      // get all lines from output CSV
      List<String> fileLines = Files.readAllLines(Paths.get(filePath));
      String lineTwo = fileLines.get(0);
      String[] charLineTwo = lineTwo.split(",");

      assertEquals(results.get(0).sellerId(), Integer.parseInt(charLineTwo[0]), 
          "sellerId should be the same in line 2.");
      assertEquals("João", charLineTwo[1], "seller name should be the same in line 2.");
      assertEquals(results.get(0).totalSales(), Double.parseDouble(charLineTwo[2]),
          "totalSales should be the same in line 2.");
      assertEquals(results.get(0).commission(), Double.parseDouble(charLineTwo[3]),
          "commission should be the same in line 2.");

      String lineThree = fileLines.get(1);
      String[] charLineThree = lineThree.split(",");

      assertEquals(results.get(1).sellerId(), Integer.parseInt(charLineThree[0]),
          "sellerId should be the same in line 3.");
      assertEquals("Maria", charLineThree[1], "seller name should be the same in line 3.");
      assertEquals(results.get(1).totalSales(), Double.parseDouble(charLineThree[2]),
          "totalSales should be the same in line 3.");
      assertEquals(results.get(1).commission(), Double.parseDouble(charLineThree[3]),
          "commission should be the same in line 3.");
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }

  // @what: test write with badly formatted seller name
  @Test
  public void writeTestLineBreakInName() throws Exception{
    CommissionProcessing.Result inputLineOne = new CommissionProcessing.Result
      (01, "João", 11000.00, 880.00);
    CommissionProcessing.Result inputLineTwo = new CommissionProcessing.Result
      (01, "Maria\n", 12000.00, 960.00);
    results.add(inputLineOne);
    results.add(inputLineTwo);
    String filePath = "src/test/resources/data/comissoes_consolidadas.csv";
    writer.write(results, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists(), "output file should exist.");
    try {
      // get all lines from output CSV
      List<String> fileLines = Files.readAllLines(Paths.get(filePath));
      String lineTwo = fileLines.get(0);
      String[] charLineTwo = lineTwo.split(",");

      assertEquals(results.get(0).sellerId(), Integer.parseInt(charLineTwo[0]),
          "sellerId should be the same in line 2.");
      assertEquals("João", charLineTwo[1], "seller name should be the same in line 2.");
      assertEquals(results.get(0).totalSales(), Double.parseDouble(charLineTwo[2]),
          "totalSales should be the same in line 2.");
      assertEquals(results.get(0).commission(), Double.parseDouble(charLineTwo[3]),
          "commission should be the same in line 2.");

      String lineThree = fileLines.get(1);
      String[] charLineThree = lineThree.split(",");

      assertEquals(results.get(1).sellerId(), Integer.parseInt(charLineThree[0]),
          "sellerId should be the same in line 3.");
      assertEquals("Maria", charLineThree[1], "seller name should be the same in line 3.");
      assertEquals(results.get(1).totalSales(), Double.parseDouble(charLineThree[2]),
          "totalSales should be the same in line 3.");
      assertEquals(results.get(1).commission(), Double.parseDouble(charLineThree[3]),
          "commission should be the same in line 3.");
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }
  
  // @what: test write with badly formatted seller name
  @Test
  public void writeTestBacklashQuoteInName() throws Exception{
    CommissionProcessing.Result inputLineOne = new CommissionProcessing.Result
      (01, "João", 11000.00, 880.00);
    CommissionProcessing.Result inputLineTwo = new CommissionProcessing.Result
      (01, "Maria\"", 12000.00, 960.00);
    results.add(inputLineOne);
    results.add(inputLineTwo);
    String filePath = "src/test/resources/data/comissoes_consolidadas.csv";
    writer.write(results, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists(), "output file should exist.");
    try {
      // get all lines from output CSV
      List<String> fileLines = Files.readAllLines(Paths.get(filePath));
      String lineTwo = fileLines.get(0);
      String[] charLineTwo = lineTwo.split(",");

      assertEquals(results.get(0).sellerId(), Integer.parseInt(charLineTwo[0]),
          "sellerId should be the same in line 2.");
      assertEquals("João", charLineTwo[1], "seller name should be the same in line 2.");
      assertEquals(results.get(0).totalSales(), Double.parseDouble(charLineTwo[2]),
          "totalSales should be the same in line 2.");
      assertEquals(results.get(0).commission(), Double.parseDouble(charLineTwo[3]),
          "commission should be the same in line 2.");

      String lineThree = fileLines.get(1);
      String[] charLineThree = lineThree.split(",");

      assertEquals(results.get(1).sellerId(), Integer.parseInt(charLineThree[0]),
          "sellerId should be the same in line 3.");
      assertEquals("João", charLineThree[1], "seller name should be the same in line 3.");
      assertEquals(results.get(1).totalSales(), Double.parseDouble(charLineThree[2]),
          "totalSales should be the same in line 3.");
      assertEquals(results.get(1).commission(), Double.parseDouble(charLineThree[3]),
          "commission should be the same in line 3.");
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }
 
  // @what: test write with badly formatted seller name
  @Test
  public void writeTestNullName() throws Exception{
    CommissionProcessing.Result inputLineOne = new CommissionProcessing.Result
      (01, null, 11000.00, 880.00);
    CommissionProcessing.Result inputLineTwo = new CommissionProcessing.Result
      (01, "Maria", 12000.00, 960.00);
    results.add(inputLineOne);
    results.add(inputLineTwo);
    String filePath = "src/test/resources/data/comissoes_consolidadas.csv";
    writer.write(results, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists(), "output file should exist.");
    try {
      // get all lines from output CSV
      List<String> fileLines = Files.readAllLines(Paths.get(filePath));
      String lineTwo = fileLines.get(0);
      String[] charLineTwo = lineTwo.split(",");

      assertEquals(results.get(0).sellerId(), Integer.parseInt(charLineTwo[0]),
          "sellerId should be the same in line 2.");
      assertEquals("João", charLineTwo[1], "seller name should be the same in line 2.");
      assertEquals(results.get(0).totalSales(), Double.parseDouble(charLineTwo[2]),
          "totalSales should be the same in line 2.");
      assertEquals(results.get(0).commission(), Double.parseDouble(charLineTwo[3]),
          "commission should be the same in line 2.");

      String lineThree = fileLines.get(1);
      String[] charLineThree = lineThree.split(",");

      assertEquals(results.get(1).sellerId(), Integer.parseInt(charLineThree[0]),
          "sellerId should be the same in line 3.");
      assertEquals("Maria", charLineThree[1], "seller name should be the same in line 3.");
      assertEquals(results.get(1).totalSales(), Double.parseDouble(charLineThree[2]),
          "totalSales should be the same in line 3.");
      assertEquals(results.get(1).commission(), Double.parseDouble(charLineThree[3]),
          "commission should be the same in line 3.");
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }
}
