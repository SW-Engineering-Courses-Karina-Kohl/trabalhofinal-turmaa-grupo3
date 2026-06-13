package br.edu.ufrgs.controller;

import br.edu.ufrgs.dao.CommissionReportRepository;
import br.edu.ufrgs.dao.SellerRepository;
import br.edu.ufrgs.dto.CommissionPolicy;
import br.edu.ufrgs.dto.PagedResponse;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import br.edu.ufrgs.service.SalesReportProcessing;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.EntityPart;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


@Path("/uploads")
@Produces(MediaType.APPLICATION_JSON)
public class UploadResource {

	@Inject
	private CommissionReportRepository commissionRepository;

	@Inject
	private SellerRepository sellerRepository;

	@Inject
	private CommissionPolicy commissionPolicy;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadSaleReport(@FormParam("file") EntityPart filePart) throws IOException {
		String filename = filePart.getFileName().orElse("unknown.csv");
		Reader reader = new InputStreamReader(filePart.getContent());

		SalesReportProcessing processing = new SalesReportProcessing(commissionPolicy);
		processing.process(filename, reader);

		CommissionReport report = processing.getCommissionReport();
		commissionRepository.save(report);

		List<Seller> sellers = processing.getSellers();
		sellers.forEach(seller -> sellerRepository.save(seller));

		return Response.status(Response.Status.CREATED)
				.entity(report)
				.build();
	}
}
