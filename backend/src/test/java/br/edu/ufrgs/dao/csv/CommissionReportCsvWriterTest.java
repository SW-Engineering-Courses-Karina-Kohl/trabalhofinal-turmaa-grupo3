package br.edu.ufrgs.dao.csv;

import br.edu.ufrgs.service.CommissionProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;

public class CommissionReportCsvWriterTest {
  private CommissionReportCsvWriter writer;
  private List<CommissionProcessing.Result> results = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    CommissionProcessing.Result lineOne = new CommissionProcessing.Result(01, "João", 11000.00, 880.00);
    CommissionProcessing.Result lineTwo = new CommissionProcessing.Result(01, "Maria", 12000.00, 960.00);
    results.add(lineOne);
    results.add(lineTwo);
    this.writer = new CommissionReportCsvWriter();
  }

  @Test
  public void writeTest() throws Exception{
    String filePath = "src/test/resources/data/comissoes_consolidadas.csv";

    writer.write(results, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists(), "Arquivo de saída deve existir.");

    try {
      List<String> fileLines = Files.readAllLines(Paths.get(filePath));
      String lineTwo = fileLines.get(0);
      String[] charLineTwo = lineTwo.split(",");

      assertEquals(results.get(0).sellerId(), Integer.parseInt(charLineTwo[0]), "Id do vendendor deve ser igual na linha 2 do arquivo.");
      assertEquals(results.get(0).name(), charLineTwo[1], "Nome do vendedor deve ser igual na linha 2 do arquivo.");
      assertEquals(results.get(0).totalSales(), Double.parseDouble(charLineTwo[2]), "Total de vendas deve ser igual na linha 2 do arquivo.");
      assertEquals(results.get(0).commission(), Double.parseDouble(charLineTwo[3]), "Comissão deve ser igual na linha 2 do arquivo.");

      String lineThree = fileLines.get(1);
      String[] charLineThree = lineThree.split(",");

      assertEquals(results.get(1).sellerId(), Integer.parseInt(charLineThree[0]), "Id do vendendor deve ser igual na linha 3 do arquivo.");
      assertEquals(results.get(1).name(), charLineThree[1], "Nome do vendedor deve ser igual na linha 3 do arquivo.");
      assertEquals(results.get(1).totalSales(), Double.parseDouble(charLineThree[2]), "Total de vendas deve ser igual na linha 3 do arquivo.");
      assertEquals(results.get(1).commission(), Double.parseDouble(charLineThree[3]), "Comissão deve ser igual na linha 3 do arquivo.");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

  }
}
