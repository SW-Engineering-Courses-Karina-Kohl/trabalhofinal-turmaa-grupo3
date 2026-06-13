package br.edu.ufrgs.controller;

import br.edu.ufrgs.dao.csv.SalesCsvParser;
import br.edu.ufrgs.dto.PaginatedResultDto;
import br.edu.ufrgs.dto.UploadDto;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.repository.CommissionPolicyRepository;
import br.edu.ufrgs.repository.CommissionReportRepository;
import br.edu.ufrgs.service.CommissionProcessing;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class UploadController {

    private final CommissionPolicyRepository policyRepository;
    private final CommissionReportRepository reportRepository;

    public UploadController(
            CommissionPolicyRepository policyRepository,
            CommissionReportRepository reportRepository
    ) {
        this.policyRepository = policyRepository;
        this.reportRepository = reportRepository;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UploadDto> uploadSalesCsv(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String csvContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            List<Seller> sellers = new SalesCsvParser()
                    .getSellerListFromReader(new StringReader(csvContent));

            List<CommissionProcessing.Result> results =
                    new CommissionProcessing(policyRepository.getPolicy())
                            .processCommissions(sellers);

            String reportId = UUID.randomUUID().toString();
            CommissionReport report = new CommissionReport(
                    reportId,
                    file.getOriginalFilename(),
                    LocalDateTime.now(),
                    results
            );
            reportRepository.save(report);

            return ResponseEntity.ok(new UploadDto(
                    reportId,
                    file.getOriginalFilename(),
                    report.getUploadedAt().toString(),
                    file.getSize() / (1024.0 * 1024.0),
                    "Completed"
            ));

        } catch (Exception e) {
            throw new RuntimeException("Failed to process sales CSV: " + e.getMessage(), e);
        }
    }

    @GetMapping
    public PaginatedResultDto<UploadDto> listUploads(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        List<CommissionReport> all = reportRepository.findAll();
        int total = all.size();
        int totalPages = total == 0 ? 1 : (int) Math.ceil((double) total / pageSize);
        int fromIndex = Math.min((page - 1) * pageSize, total);
        int toIndex = Math.min(fromIndex + pageSize, total);

        List<UploadDto> dtos = all.subList(fromIndex, toIndex)
                .stream()
                .map(r -> new UploadDto(
                        r.getId(),
                        r.getFileName(),
                        r.getUploadedAt().toString(),
                        0.0,
                        "Completed"
                ))
                .toList();

        return new PaginatedResultDto<>(dtos, page, pageSize, total, totalPages);
    }
}
