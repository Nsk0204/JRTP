package in.cybage.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.cybage.request.SearchRequest;
import in.cybage.response.SearchResponse;
import in.cybage.service.ReportServiceImpl;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReportsRestController {

	@Autowired
	private ReportServiceImpl service;

	@GetMapping("/plans")
	public ResponseEntity<List<String>> uniqueNames() {
		List<String> planNames = service.getUniquePlanNames();
		return new ResponseEntity<>(planNames, HttpStatus.OK);
	}

	@GetMapping("/statuses")
	public ResponseEntity<List<String>> uniqueStatuses() {
		List<String> planStatus = service.getUniquePlanStatuses();
		return new ResponseEntity<>(planStatus, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) {
		List<SearchResponse> search = service.search(request);
		return new ResponseEntity<>(search, HttpStatus.OK);
	}

	@GetMapping("/excel")
	public void excel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=data" + ".xlsx";

		response.setHeader(headerKey, headerValue);

		service.generateExcel(response);

	}

	@GetMapping("/pdf")
	public void pdf(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=data.pdf";

		response.setHeader(headerKey, headerValue);

		service.generatePdf(response);

	}

}
