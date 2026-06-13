package br.edu.ufrgs.dto;

import java.util.List;

public record CommissionBatchDto(
        String id,
        String fileName,
        String batchNumber,
        double totalCommissionPool,
        int activeSellers,
        double averagePayout,
        boolean validationPassed,
        List<SellerDto> sellers,
        int totalSellersCount
) {}