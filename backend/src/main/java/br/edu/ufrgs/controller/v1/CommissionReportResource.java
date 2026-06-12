package br.edu.ufrgs.controller.v1;

import br.edu.ufrgs.dao.CommissionReportRepository;
import br.edu.ufrgs.dao.SellerRepository;
import br.edu.ufrgs.dao.csv.CommissionReportCsvWriter;
import br.edu.ufrgs.dao.export.CommissionReportPdfWriter;
import br.edu.ufrgs.dao.export.ExportFileContract;
import br.edu.ufrgs.dto.PagedResponse;
import br.edu.ufrgs.model.CommissionReport;
import br.edu.ufrgs.model.Seller;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


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
	@Path("/{id}")
	public Response findOne(
			@PathParam("id") int id
	) {
		CommissionReport report = commissionRepository.findOne(id);
		if (report == null) return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(report).build();
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

	@POST
	@Path("/{id}/export")
	public Response exportFile(
			@PathParam("id") int id,
			@QueryParam("doc_type") @DefaultValue("csv") String docType
	) {
		CommissionReport report = commissionRepository.findOne(id);
		if (report == null) return Response.status(Response.Status.NOT_FOUND).build();

		String tmpDir = System.getProperty("java.io.tmpdir");
		String filename = "report-" + id + "." + docType;
		java.nio.file.Path filePath = java.nio.file.Path.of(tmpDir, filename);

		List<Seller> sellers = sellerRepository.findAll(report.getId());
		ExportFileContract fileExporter;

		switch (docType){
			case "csv":
				fileExporter = new CommissionReportCsvWriter();
				break;
			case "pdf":
				fileExporter = new CommissionReportPdfWriter();
				break;
			default:
				return Response.status(Response.Status.BAD_REQUEST).build();
		}

		try {
			fileExporter.write(sellers, filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		// TODO Add notification via websocket to send download link through WS

		return Response.ok().build();
	}

	@GET
	@Path("/downloads/{filename}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response download(@PathParam("filename") String filename) {
		java.nio.file.Path filePath = java.nio.file.Path.of(System.getProperty("java.io.tmpdir"), filename);

		if (!Files.exists(filePath))
			return Response.status(Response.Status.NOT_FOUND).build();

		StreamingOutput stream = output -> {
			Files.copy(filePath, output);
		};

		return Response.ok(stream)
				.header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
				.build();
	}
}
