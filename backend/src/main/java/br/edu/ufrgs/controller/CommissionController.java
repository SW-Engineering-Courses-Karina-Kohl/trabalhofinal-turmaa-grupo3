package br.edu.ufrgs.controller;

import br.edu.ufrgs.dto.CommissionBatchDto;
import br.edu.ufrgs.dto.PaginatedResultDto;
import br.edu.ufrgs.dto.SellerDto;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.repository.CommissionReportRepository;
import br.edu.ufrgs.service.CommissionProcessing;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/commissions")
public class CommissionController {

    private final CommissionReportRepository reportRepository;

    public CommissionController(CommissionReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @GetMapping("/batches")
    public PaginatedResultDto<CommissionBatchDto> listBatches(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<CommissionReport> all = reportRepository.findAll();
        int total = all.size();
        int totalPages = total == 0 ? 1 : (int) Math.ceil((double) total / pageSize);
        int fromIndex = Math.min((page - 1) * pageSize, total);
        int toIndex = Math.min(fromIndex + pageSize, total);

        List<CommissionBatchDto> batches = all.subList(fromIndex, toIndex)
                .stream()
                .map(this::toCommissionBatchDto)
                .toList();

        return new PaginatedResultDto<>(batches, page, pageSize, total, totalPages);
    }

    @GetMapping("/batches/{batchId}")
    public ResponseEntity<CommissionBatchDto> getBatch(
            @PathVariable String batchId
    ) {
        return reportRepository.findById(batchId)
                .map(report -> ResponseEntity.ok(toCommissionBatchDto(report)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/batches/{batchId}/sellers")
    public ResponseEntity<PaginatedResultDto<SellerDto>> getSellers(
            @PathVariable String batchId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return reportRepository.findById(batchId)
                .map(report -> {
                    List<SellerDto> all = report.getResults()
                            .stream()
                            .map(this::toSellerDto)
                            .toList();

                    int total = all.size();
                    int totalPages = total == 0 ? 1 : (int) Math.ceil((double) total / pageSize);
                    int fromIndex = Math.min((page - 1) * pageSize, total);
                    int toIndex = Math.min(fromIndex + pageSize, total);

                    return ResponseEntity.ok(new PaginatedResultDto<>(
                            all.subList(fromIndex, toIndex),
                            page,
                            pageSize,
                            total,
                            totalPages
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/batches/{batchId}/export")
    public ResponseEntity<byte[]> exportBatch(@PathVariable String batchId) {
        return reportRepository.findById(batchId)
                .map(report -> {
                    StringBuilder csv = new StringBuilder();
                    csv.append("seller_id,name,total_sales,commission\n");
                    for (CommissionProcessing.Result r : report.getResults()) {
                        csv.append(String.format("%d,%s,%.2f,%.2f\n",
                                r.sellerId(), r.name(), r.totalSales(), r.commission()));
                    }
                    byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
                    String filename = report.getFileName().replace(".csv", "") + "_commissions.csv";
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                            .contentType(MediaType.parseMediaType("text/csv"))
                            .body(bytes);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private CommissionBatchDto toCommissionBatchDto(CommissionReport report) {
        List<SellerDto> sellerDtos = report.getResults()
                .stream()
                .map(this::toSellerDto)
                .toList();

        double totalCommissionPool = report.getResults()
                .stream()
                .mapToDouble(CommissionProcessing.Result::commission)
                .sum();

        int activeSellers = report.getResults().size();

        double averagePayout = activeSellers == 0
                ? 0
                : totalCommissionPool / activeSellers;

        return new CommissionBatchDto(
                report.getId(),
                report.getFileName(),
                "BATCH-" + report.getId(),
                totalCommissionPool,
                activeSellers,
                averagePayout,
                true,
                sellerDtos,
                activeSellers
        );
    }

    private SellerDto toSellerDto(CommissionProcessing.Result r) {
        double rate = r.totalSales() == 0
                ? 0
                : r.commission() / r.totalSales() * 100;

        return new SellerDto(
                String.valueOf(r.sellerId()),
                r.name(),
                getInitials(r.name()),
                r.totalSales(),
                rate,
                r.commission()
        );
    }

    private String getInitials(String name) {
        String[] parts = name.split(" ");
        StringBuilder initials = new StringBuilder();
        for (String p : parts) {
            if (!p.isBlank()) {
                initials.append(Character.toUpperCase(p.charAt(0)));
            }
        }
        return initials.toString();
    }
}
