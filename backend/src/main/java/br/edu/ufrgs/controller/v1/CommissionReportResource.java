package br.edu.ufrgs.controller.v1;

import br.edu.ufrgs.dao.CommissionReportRepository;
import br.edu.ufrgs.dao.SellerRepository;
import br.edu.ufrgs.dto.PagedResponse;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/v1/commissions")
@Produces(MediaType.APPLICATION_JSON)
public class CommissionReportResource {

	@Inject
	private CommissionReportRepository commissionRepository;


	@Inject
	private SellerRepository sellerRepository;

	@GET
	public Response findAll(
			@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("10") int size
	) {
		PagedResponse<CommissionReport> result = commissionRepository.findAllPaginated(page, size);
		return Response.ok(result).build();
	}

	@GET
	@Path("/{id}/sellers")
	public Response findAllSellers(
			@PathParam("id") int id,
			@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("10") int size
	) {
		PagedResponse<Seller> result = sellerRepository.findAllPaginated(id, page, size);
		return Response.ok(result).build();
	}
}
