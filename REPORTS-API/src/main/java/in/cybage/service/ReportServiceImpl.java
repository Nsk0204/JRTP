package in.cybage.service;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.cybage.entity.EligibilityDtls;
import in.cybage.repo.EligibilityDtlsRepo;
import in.cybage.request.SearchRequest;
import in.cybage.response.SearchResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private EligibilityDtlsRepo eligRepo;

	List<SearchResponse> response = new ArrayList<>();

	public List<String> getUniquePlanNames() {
		return eligRepo.findPlanNames();
	}

	public List<String> getUniquePlanStatuses() {
		return eligRepo.findPlanStatuses();
	}

	public List<SearchResponse> search(SearchRequest request) {

		EligibilityDtls queryBuilder = new EligibilityDtls();

		String planName = request.getPlanName();
		if (planName != null && !planName.equals("")) {
			queryBuilder.setPlanName(planName);
		}

		String planStatus = request.getPlanStatus();
		if (planStatus != null && !planStatus.equals("")) {
			queryBuilder.setPlanStatus(planStatus);
		}

		LocalDate planStartDate = request.getPlanStartDate();
		if (planStartDate != null) {
			queryBuilder.setPlanStartDate(planStartDate);
		}

		LocalDate planEndDate = request.getPlanEndDate();
		if (planEndDate != null) {
			queryBuilder.setPlanEndDate(planEndDate);
		}

		Example<EligibilityDtls> filter = Example.of(queryBuilder);

		List<EligibilityDtls> entities = eligRepo.findAll(filter);
		for (EligibilityDtls entity : entities) {
			SearchResponse sr = new SearchResponse();
			BeanUtils.copyProperties(entity, sr);
			response.add(sr);
		}

		return response;
	}

	public void generateExcel(HttpServletResponse response) throws Exception {
		List<EligibilityDtls> entities = eligRepo.findAll();

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sh = wb.createSheet();
		HSSFRow rw = sh.createRow(0);

		rw.createCell(0).setCellValue("Name");
		rw.createCell(1).setCellValue("Email");
		rw.createCell(2).setCellValue("Mobile");
		rw.createCell(3).setCellValue("Gender");
		rw.createCell(4).setCellValue("SSn");

		entities.forEach(entity -> {
			int i = 1;

			HSSFRow rw1 = sh.createRow(i);
			rw1.createCell(0).setCellValue(entity.getName());
			rw1.createCell(1).setCellValue(entity.getEmail());
			rw1.createCell(2).setCellValue(entity.getMobile());
			rw1.createCell(3).setCellValue(entity.getGender());
			rw1.createCell(4).setCellValue(entity.getSsn());

			i++;

		});

		ServletOutputStream outputstream = response.getOutputStream();
		wb.write(outputstream);
		wb.close();
		outputstream.close();

	}

	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {

		List<EligibilityDtls> entities = eligRepo.findAll();

		// Writing the line in the document
		Document document = new Document(PageSize.A4);
		// Granting write permission to pdf and adding to the response object
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		// Setting the font of the document
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		// Creating the paragraph
		Paragraph p = new Paragraph("Search Report", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);

		// Set the table for storing the data
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 1.5f, 3.5f, 3.0f, 3.0f, 1.5f });
		table.setSpacingBefore(10);

		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("E-mail", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Phone No", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Gender", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("SSN", font));
		table.addCell(cell);

		for (EligibilityDtls entity : entities) {
			table.addCell(entity.getName());
			table.addCell(entity.getEmail());
			table.addCell(String.valueOf(entity.getMobile()));
			table.addCell(String.valueOf(entity.getGender()));
			table.addCell(String.valueOf(entity.getSsn()));

		}
	}

}

