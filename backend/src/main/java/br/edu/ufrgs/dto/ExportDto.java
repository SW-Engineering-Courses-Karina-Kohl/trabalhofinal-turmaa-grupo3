package br.edu.ufrgs.dto;

public record ExportDto(
        String batchId,
        String fileName,
        String exportedAt,
        double totalCommissionPool,
        String message
) {}