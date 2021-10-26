package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	static FileInputStream fis;
	static FileOutputStream fos;
	static XSSFWorkbook wb;
	static XSSFSheet ws;
	static XSSFRow row;
	static XSSFCell cell;

	public static int getRowCount(String file, String sheet) throws IOException{
		fis = new FileInputStream(file); 
		wb = new XSSFWorkbook(fis);
		ws = wb.getSheet(sheet);
		int rowCount = ws.getLastRowNum();
		wb.close();
		fis.close();
		return rowCount;
	}

	public static int getCellCount(String file, String sheet, int rowNum) throws IOException {
		fis = new FileInputStream(file);
		wb = new XSSFWorkbook(file);
		ws = wb.getSheet(sheet);
		int cellCount = ws.getRow(rowNum).getLastCellNum();
		wb.close();
		fis.close();
		return cellCount;
	}
	
	public static String getCellData(String file, String sheet, int rowNum, int colNum) {
		try {
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			ws = wb.getSheet(sheet);
			row = ws.getRow(rowNum);
			cell = row.getCell(colNum);
			DataFormatter formatter = new DataFormatter();
			String cellData = formatter.formatCellValue(cell);
			return cellData;
		}catch(IOException ie){
			ie.printStackTrace();
		}finally {
			try{
				wb.close();
				fis.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void setCellData(String file, String sheet, int rowNum, int colNum, String data) {
		try{
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			ws = wb.getSheet(sheet);
			row = ws.getRow(rowNum);
			cell = row.createCell(colNum);
			cell.setCellValue(data);
			fos = new FileOutputStream(file);
			wb.write(fos);
		}catch(IOException ie) {
			ie.printStackTrace();
		}finally {
			try {
				wb.close();
				fos.close();
				fis.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
