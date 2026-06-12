package br.edu.ufrgs.dao;

import br.edu.ufrgs.dto.PagedResponse;
import br.edu.ufrgs.model.Seller;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class SellerRepository {
    private static final String queryCount = "SELECT COUNT(s) FROM Seller s WHERE s.commissionReportId = :id";
    private static final String querySelectAll = "SELECT s FROM Seller s WHERE s.commissionReportId = %s ORDER BY s.id";

    @PersistenceContext(unitName = "salesopsPU")
    private EntityManager em;

    public PagedResponse<Seller> findAllPaginated(int id, int page, int size) {
        if(size <= 0) size = 10;

        // Count total
        Long count = em.createQuery(queryCount, Long.class)
                .setParameter("id", id)
                .getSingleResult();

        // Get page
        List<Seller> sellers = em.createQuery(querySelectAll, Seller.class)
                .setParameter("id", id)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        int totalPages = (int) Math.ceil((double) count / size);

        return PagedResponse.<Seller>builder()
        	.content(sellers)
        	.page(page)
        	.size(size)
        	.totalElements(count)
        	.totalPages(totalPages)
        	.last(page >= totalPages - 1)
        	.build();
    }

    public Seller save(Seller report) {
        em.persist(report);
        em.flush();
        return report;
    }
}
