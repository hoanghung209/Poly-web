
/*
 * @author: ngoctv
 */

package admin.web.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
 

public class ExcelReportHeper {
	private static Logger log = Logger.getLogger("LoginController.class");
	public static void addColumnHeaders(List<Map<String, String>> listMapDataXLS, HSSFSheet sheet, HSSFCellStyle styleHeader) {

		HSSFRow rowHeader = sheet.createRow(0);
		
		addColumnValue(rowHeader, "STT", 0);
		addColumnValue(rowHeader, "Nội dung thống kê", 1);
		addColumnValue(rowHeader, "Tổng", 2);
		
		int k = 3;
		for (Map<String, String> map : listMapDataXLS) {
			String name = map.get("datetime");
			HSSFCell cell = rowHeader.createCell(k);
			String value = name == null ? "" : name;

			cell.setCellValue(new HSSFRichTextString(value));
			cell.setCellStyle(styleHeader);
			k++;
		}
	}

	public static void addColumnValue(HSSFRow rowHeader, String component, int index) {
		HSSFCell cell = rowHeader.createCell(index);
		String value = component == null ? "" : component;

		cell.setCellValue(new HSSFRichTextString(value));

	}
	
	public static void writeExcelToResponse(HttpServletResponse response,
		HSSFWorkbook generatedExcel, String filename) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filename + ".xls");

		generatedExcel.write(response.getOutputStream());
//			response.flushBuffer();
	}
	//#region "Function is used by VIEW layer"
	
	public static void postProcessXLS(List<Map<String, String>> listMapDataXLS, List<Map<String, String>> listMapDataTotal) {
		try {
			
			String filename = "data";
			FacesContext facesContext = FacesContext.getCurrentInstance();

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFCellStyle styleHeader = (HSSFCellStyle) wb.createCellStyle();
			HSSFFont fontHeader = (HSSFFont) wb.createFont();
			fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleHeader.setFont(fontHeader);

			addColumnHeaders(listMapDataXLS, sheet, styleHeader);
			
			for (int i = 0; i < listMapDataTotal.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				
				addColumnValue(row, listMapDataTotal.get(i).get("stt"), 0);
				addColumnValue(row, listMapDataTotal.get(i).get("name"), 1);
				addColumnValue(row, listMapDataTotal.get(i).get("total"), 2);

				int k = 3;
				for (Map<String, String> map : listMapDataXLS) {

					String value = map.get(listMapDataTotal.get(i).get("label"));
					addColumnValue(row, value, k);
					k++;
				}
			}
			writeExcelToResponse(((HttpServletResponse) facesContext.getExternalContext().getResponse()), wb, filename);
			facesContext.responseComplete();
		} catch (Exception e) {
			log.error(e);
		}
	}
}
