package br.edu.ufrgs.dao;

import br.edu.ufrgs.dto.PagedResponse;
import br.edu.ufrgs.model.CommissionReport;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class CommissionReportRepository {
    private static final String queryCount = "SELECT COUNT(r) FROM CommissionReport r";
    private static final String querySelectAll = "SELECT r FROM CommissionReport r ORDER BY r.id";
    private static final String querySelectOne = "SELECT r FROM CommissionReport r WHERE r.id = :id";

    @PersistenceContext(unitName = "salesopsPU")
    private EntityManager em;

    public PagedResponse<CommissionReport> findAllPaginated(int page, int size) {
        if(size <= 0) size = 10;

        // Count total
        Long count = em.createQuery(queryCount, Long.class)
                       .getSingleResult();

        // Get page
        List<CommissionReport> reports = em.createQuery(querySelectAll, CommissionReport.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();

        int totalPages = (int) Math.ceil((double) count / size);

        return PagedResponse.<CommissionReport>builder()
        	.data(reports)
        	.page(page)
        	.pageSize(size)
        	.total(count)
        	.totalPages(totalPages)
        	.last(page >= totalPages - 1)
        	.build();
    }

    public CommissionReport findOne(int id) {
        try {
            return em.createQuery(querySelectOne, CommissionReport.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CommissionReport save(CommissionReport report) {
        em.persist(report);
        em.flush();
        return report;
    }
}
