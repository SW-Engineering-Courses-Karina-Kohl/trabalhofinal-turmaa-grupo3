package br.edu.ufrgs.dto;

public record UploadConfirmationDto(
        String reportId,
        String fileName,
        String processedAt,
        int sellersProcessed,
        double totalCommissionPool,
        String status
) {}
