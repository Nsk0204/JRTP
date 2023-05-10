package in.cybage.service;

import java.io.IOException;
import java.util.List;

import com.lowagie.text.DocumentException;

import in.cybage.request.SearchRequest;
import in.cybage.response.SearchResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface ReportService {
	
	public List<String> getUniquePlanNames();

	public List<String> getUniquePlanStatuses();

	public List<SearchResponse> search(SearchRequest request);

//	public HttpServletResponse generateExcel();
	public void generateExcel(HttpServletResponse response) throws Exception;

	public void generatePdf(HttpServletResponse response) throws DocumentException,IOException;

}



