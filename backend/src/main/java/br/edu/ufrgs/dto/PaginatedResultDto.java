package br.edu.ufrgs.dto;

import java.util.List;

public record PaginatedResultDto<T>(
        List<T> data,
        int page,
        int pageSize,
        int total,
        int totalPages
) {}