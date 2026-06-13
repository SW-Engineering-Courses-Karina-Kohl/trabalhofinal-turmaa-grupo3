package br.edu.ufrgs.model;

import br.edu.ufrgs.service.CommissionProcessing;
import java.time.LocalDateTime;
import java.util.List;

public class CommissionReport {

    private final String id;
    private final String fileName;
    private final LocalDateTime uploadedAt;
    private final List<CommissionProcessing.Result> results;

    public CommissionReport(
                            String id,
                            String fileName,
                            LocalDateTime uploadedAt,
                            List<CommissionProcessing.Result> results
                            ) {
        this.id = id;
        this.fileName = fileName;
        this.uploadedAt = uploadedAt;
        this.results = results;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public List<CommissionProcessing.Result> getResults() {
        return results;
    }
}
