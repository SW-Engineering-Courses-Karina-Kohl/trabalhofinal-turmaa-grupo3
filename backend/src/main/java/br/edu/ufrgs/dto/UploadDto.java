package br.edu.ufrgs.dto;

public record UploadDto(
        String id,
        String fileName,
        String uploadedAt,
        double sizeMb,
        String status
) {}