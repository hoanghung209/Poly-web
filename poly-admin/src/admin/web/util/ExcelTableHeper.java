
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
 

public class ExcelTableHeper {
	private static Logger log = Logger.getLogger("LoginController.class");
	public static void addColumnHeaders(List<String> columns, HSSFSheet sheet, HSSFCellStyle styleHeader) {

		HSSFRow rowHeader = sheet.createRow(0);
		
		List<String> columsName = columns;
		int k = 0;
		for (String name : columsName) {
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
	
	public static void postProcessXLS(List<Map<String, String>> listMapDataXLS, List<String> listColumnNames) {
		try {
			
			String filename = "data";
			FacesContext facesContext = FacesContext.getCurrentInstance();

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFCellStyle styleHeader = (HSSFCellStyle) wb.createCellStyle();
			HSSFFont fontHeader = (HSSFFont) wb.createFont();
			fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleHeader.setFont(fontHeader);
			
			List<String> columsName = listColumnNames;
			List<Map<String, String>> listDataXLS = listMapDataXLS;

			addColumnHeaders(columsName, sheet, styleHeader);

			for (int i = 0; i < listDataXLS.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);

				Map<String, String> rowData = listDataXLS.get(i);

				int k = 0;
				for (String name : columsName) {

					String value = rowData.get(name);
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
	
	public static void postProcessXLS(List<String> listColumnNameHeader, List<Map<String, String>> listMapDataXLS, List<String> listColumnNames) {
		try {
			
			String filename = "data";
			FacesContext facesContext = FacesContext.getCurrentInstance();

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			HSSFCellStyle styleHeader = (HSSFCellStyle) wb.createCellStyle();
			HSSFFont fontHeader = (HSSFFont) wb.createFont();
			fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			styleHeader.setFont(fontHeader);
			
			List<String> columsName = listColumnNames;
			List<Map<String, String>> listDataXLS = listMapDataXLS;

			addColumnHeaders(listColumnNameHeader, sheet, styleHeader);

			for (int i = 0; i < listDataXLS.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);

				Map<String, String> rowData = listDataXLS.get(i);

				int k = 0;
				for (String name : columsName) {

					String value = rowData.get(name);
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
