package br.edu.ufrgs.dto;

public record SellerDto(
        String id,
        String name,
        String initials,
        double totalSales,
        double commissionRate,
        double finalCommission
) {}