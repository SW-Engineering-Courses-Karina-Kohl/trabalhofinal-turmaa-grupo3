package br.edu.ufrgs.repository;

import br.edu.ufrgs.model.CommissionReport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CommissionReportRepository {

    private final Map<String, CommissionReport> store = new LinkedHashMap<>();

    public void save(CommissionReport report) {
        store.put(report.getId(), report);
    }

    public Optional<CommissionReport> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<CommissionReport> findAll() {
        return new ArrayList<>(store.values());
    }
}
