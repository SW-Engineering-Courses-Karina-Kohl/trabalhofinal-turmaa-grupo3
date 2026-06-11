package br.edu.ufrgs.controller;

import br.edu.ufrgs.dao.csv.CommissionRuleCsvParser;
import br.edu.ufrgs.dao.csv.SalesCsvParser;
import br.edu.ufrgs.dto.CommissionBatchDto;
import br.edu.ufrgs.dto.ExportDto;
import br.edu.ufrgs.dto.PaginatedResultDto;
import br.edu.ufrgs.dto.SellerDto;
import br.edu.ufrgs.model.CommissionPolicy;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.service.CommissionProcessing;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/commissions")
public class CommissionController {

    @GetMapping("/batches")
public PaginatedResultDto<CommissionBatchDto> listBatches() {

    try {

        CommissionRuleCsvParser rulesParser =
                new CommissionRuleCsvParser();

        SalesCsvParser salesParser =
                new SalesCsvParser();

        var regrasResource = getClass()
                .getClassLoader()
                .getResource("data/regras_comissao.csv");

        var vendasResource = getClass()
                .getClassLoader()
                .getResource("data/vendas.csv");

        if (regrasResource == null || vendasResource == null) {
            throw new RuntimeException("CSV files not found");
        }

        CommissionPolicy policy =
                rulesParser.readRules(regrasResource.getPath());

        List<Seller> sellers =
                salesParser.getSellerList(vendasResource.getPath());

        CommissionProcessing processor =
                new CommissionProcessing(policy);

        List<CommissionProcessing.Result> results =
                processor.processCommissions(sellers);

        List<SellerDto> sellerDtos =
                results.stream()
                        .map(r -> new SellerDto(
                                String.valueOf(r.sellerId()),
                                r.name(),
                                getInitials(r.name()),
                                r.totalSales(),
                                calculateRate(r.totalSales()),
                                r.commission()
                        ))
                        .toList();

        double totalCommissionPool =
                results.stream()
                        .mapToDouble(CommissionProcessing.Result::commission)
                        .sum();

        int activeSellers = results.size();

        double averagePayout =
                activeSellers == 0
                        ? 0
                        : totalCommissionPool / activeSellers;

        CommissionBatchDto batch =
                new CommissionBatchDto(
                        "1",
                        "vendas.csv",
                        "BATCH-1",
                        totalCommissionPool,
                        activeSellers,
                        averagePayout,
                        true,
                        sellerDtos,
                        activeSellers
                );

        return new PaginatedResultDto<>(
                List.of(batch),
                1,
                10,
                1,
                1
        );

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    @GetMapping("/batches/{batchId}")
public CommissionBatchDto getBatch(
        @PathVariable String batchId
) {

    try {

        CommissionRuleCsvParser rulesParser =
                new CommissionRuleCsvParser();

        SalesCsvParser salesParser =
                new SalesCsvParser();

        var regrasResource = getClass()
                .getClassLoader()
                .getResource("data/regras_comissao.csv");

        var vendasResource = getClass()
                .getClassLoader()
                .getResource("data/vendas.csv");

        if (regrasResource == null || vendasResource == null) {
            throw new RuntimeException("CSV files not found");
        }

        CommissionPolicy policy =
                rulesParser.readRules(regrasResource.getPath());

        List<Seller> sellers =
                salesParser.getSellerList(vendasResource.getPath());

        CommissionProcessing processor =
                new CommissionProcessing(policy);

        List<CommissionProcessing.Result> results =
                processor.processCommissions(sellers);

        List<SellerDto> sellerDtos =
                results.stream()
                        .map(r -> new SellerDto(
                                String.valueOf(r.sellerId()),
                                r.name(),
                                getInitials(r.name()),
                                r.totalSales(),
                                calculateRate(r.totalSales()),
                                r.commission()
                        ))
                        .toList();

        double totalCommissionPool =
                results.stream()
                        .mapToDouble(CommissionProcessing.Result::commission)
                        .sum();

        int activeSellers = results.size();

        double averagePayout =
                activeSellers == 0
                        ? 0
                        : totalCommissionPool / activeSellers;

        return new CommissionBatchDto(
                batchId,
                "vendas.csv",
                "BATCH-" + batchId,
                totalCommissionPool,
                activeSellers,
                averagePayout,
                true,
                sellerDtos,
                activeSellers
        );

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    @GetMapping("/batches/{batchId}/sellers")
public PaginatedResultDto<SellerDto> getSellers(
        @PathVariable String batchId
) {

    try {

        CommissionRuleCsvParser rulesParser =
                new CommissionRuleCsvParser();

        SalesCsvParser salesParser =
                new SalesCsvParser();

        var regrasResource = getClass()
        .getClassLoader()
        .getResource("data/regras_comissao.csv");

var vendasResource = getClass()
        .getClassLoader()
        .getResource("data/vendas.csv");

if (regrasResource == null || vendasResource == null) {
    throw new RuntimeException("CSV files not found in resources/data");
}

String regrasPath = regrasResource.getPath();
String vendasPath = vendasResource.getPath();

CommissionPolicy policy =
        rulesParser.readRules(regrasPath);

List<Seller> sellers =
        salesParser.getSellerList(vendasPath);

        CommissionProcessing processor =
                new CommissionProcessing(policy);

        List<CommissionProcessing.Result> results =
                processor.processCommissions(sellers);

        List<SellerDto> sellerDtos =
                results.stream()
                        .map(r -> new SellerDto(
                                String.valueOf(r.sellerId()),
                                r.name(),
                                getInitials(r.name()),
                                r.totalSales(),
                                calculateRate(r.totalSales()),
                                r.commission()
                        ))
                        .toList();

        return new PaginatedResultDto<>(
                sellerDtos,
                1,
                10,
                sellerDtos.size(),
                1
        );

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    @GetMapping("/batches/{batchId}/export")
public ExportDto exportBatch(
        @PathVariable String batchId
) {

    try {

        CommissionRuleCsvParser rulesParser =
                new CommissionRuleCsvParser();

        SalesCsvParser salesParser =
                new SalesCsvParser();

        var regrasResource = getClass()
                .getClassLoader()
                .getResource("data/regras_comissao.csv");

        var vendasResource = getClass()
                .getClassLoader()
                .getResource("data/vendas.csv");

        if (regrasResource == null || vendasResource == null) {
            throw new RuntimeException("CSV files not found");
        }

        CommissionPolicy policy =
                rulesParser.readRules(regrasResource.getPath());

        List<Seller> sellers =
                salesParser.getSellerList(vendasResource.getPath());

        CommissionProcessing processor =
                new CommissionProcessing(policy);

        List<CommissionProcessing.Result> results =
                processor.processCommissions(sellers);

        double totalCommissionPool =
                results.stream()
                        .mapToDouble(CommissionProcessing.Result::commission)
                        .sum();

        return new ExportDto(
                batchId,
                "vendas.csv",
                LocalDateTime.now().toString(),
                totalCommissionPool,
                "Export generated successfully"
        );

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

    private String getInitials(String name) {

    String[] parts = name.split(" ");

    StringBuilder initials = new StringBuilder();

    for (String p : parts) {
        if (!p.isBlank()) {
            initials.append(
                    Character.toUpperCase(p.charAt(0))
            );
        }
    }

    return initials.toString();
}

private double calculateRate(double totalSales) {

    if (totalSales >= 10000.0) {
        return 8.0;
    }

    return 5.0;
}
}