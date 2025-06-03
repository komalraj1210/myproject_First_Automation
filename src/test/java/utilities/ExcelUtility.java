package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	public String path;

	public ExcelUtility(String path) {
		this.path=path;
	}

	// ✅ Get row count with checks
	public int getrowcount(String sheetName) throws IOException {
		if (!new File(path).exists()) return 0;

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowcount = (sheet != null) ? sheet.getLastRowNum() : 0;
		workbook.close();
		fi.close();
		return rowcount;
	}

	// ✅ Get cell count with sheet & row check
	public int getcellcount(String sheetName, int rownum) throws IOException {
		if (!new File(path).exists()) return 0;

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = (sheet != null) ? sheet.getRow(rownum) : null;
		int cellcount = (row != null) ? row.getLastCellNum() : 0;
		workbook.close();
		fi.close();
		return cellcount;
	}

	// ✅ Safe cell data retrieval
	public String getcelldata(String sheetName, int rownum, int colnum) throws IOException {
		if (!new File(path).exists()) return "";

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = (sheet != null) ? sheet.getRow(rownum) : null;
		cell = (row != null) ? row.getCell(colnum) : null;

		String data = "";
		try {
			DataFormatter formatter = new DataFormatter();
			data = formatter.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		workbook.close();
		fi.close();
		return data;
	}

	// ✅ Set cell data safely
	public void setcellData(String sheetName, int rownum, int colnum, String data) throws IOException {
		File file = new File(path);
		if (!file.exists()) return;

		fi = new FileInputStream(file);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = workbook.createSheet(sheetName);
		}

		row = sheet.getRow(rownum);
		if (row == null) row = sheet.createRow(rownum);

		cell = row.createCell(colnum);
		cell.setCellValue(data);

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}

	// ✅ Green cell coloring
	public void fillGreenColour(String sheetName, int rownum, int colnum) throws IOException {
		applyCellColor(sheetName, rownum, colnum, IndexedColors.GREEN);
	}

	// ✅ Red cell coloring
	public void fillREdColour(String sheetName, int rownum, int colnum) throws IOException {
		applyCellColor(sheetName, rownum, colnum, IndexedColors.RED);
	}

	// ✅ Yellow cell coloring
	public void fillYellowColour(String sheetName, int rownum, int colnum) throws IOException {
		applyCellColor(sheetName, rownum, colnum, IndexedColors.YELLOW);
	}

	// 🔁 Common method for cell color application with validations
	private void applyCellColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
		if (!new File(path).exists()) return;

		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			workbook.close();
			fi.close();
			return;
		}

		row = sheet.getRow(rownum);
		if (row == null) row = sheet.createRow(rownum);

		cell = row.getCell(colnum);
		if (cell == null) cell = row.createCell(colnum);

		style = workbook.createCellStyle();
		style.setFillForegroundColor(color.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);

		fo = new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}
}
