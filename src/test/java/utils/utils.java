package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class utils {
	public static FileInputStream fi;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static String excelPath;
	public static String sheetName;
	public static FileOutputStream fo;

	////constructor////

	public utils(String path){
		this.excelPath=path;
	}

	public static int getRowCount(String excelPath, String sheetName) throws IOException {

		fi = new FileInputStream(excelPath);//FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		int rowCount=sheet.getLastRowNum();
		System.out.println("No.of Rows:"+rowCount);
		workbook.close();
		fi.close();
		return rowCount;
	}

	public static int getCellCount(String excelPath, String sheetName, int rowNum) throws IOException{

		fi = new FileInputStream(excelPath);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int columnCount = row.getLastCellNum();
		workbook.close();
		fi.close();
		return columnCount;
	}


	public static String getCelldata(String excelPath, String sheetName,int rowNum,int columnNum) throws IOException {

		fi = new FileInputStream(excelPath);
		workbook = new XSSFWorkbook(fi);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(columnNum);

		DataFormatter formatter=new DataFormatter();
		String data;
		try {
			data=formatter.formatCellValue(cell);
		}catch(Exception e) {
			data = "";
		}
		workbook.close();
		fi.close();
		return data;
	}



	public static void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
		////If file not exists create one
		File excelfile=new File(excelPath);
		if(!excelfile.exists())
		{
			workbook=new XSSFWorkbook();
			fo=new FileOutputStream(excelPath);
			workbook.write(fo);
		}

		fi=new FileInputStream(excelPath);
		workbook=new XSSFWorkbook(fi);

		////If sheet not exists create one 
		if(workbook.getSheetIndex(sheetName)==-1) 
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);

		////If row not exists create one	
		if(sheet.getRow(rowNum)==null)
			sheet.createRow(rowNum);
		row=sheet.getRow(rowNum);


		cell = row.createCell(colNum);
		cell.setCellValue(data);
		fo=new FileOutputStream(excelPath);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();

	}

	public void writeToExcel(ArrayList<String> data , String sheetName,int rowNo) throws IOException {

		File excelfile=new File(excelPath);
		if(!excelfile.exists())
		{
			workbook=new XSSFWorkbook();
			fo=new FileOutputStream(excelPath);
			workbook.write(fo);
		}

		fi=new FileInputStream(excelPath);
		workbook=new XSSFWorkbook(fi);

		////If sheet not exists create one 
		if(workbook.getSheetIndex(sheetName)==-1) 
			workbook.createSheet(sheetName);
		sheet=workbook.getSheet(sheetName);
	//	int rowNum=0;
		////If row not exists create one	
		//		if(sheet.getRow(rowNum)==null)
		//			sheet.createRow(rowNum);
		//		row=sheet.getRow(rowNum);

		int colNum =0;
		//		for(Map.Entry entry: data.entrySet()) {
		//			XSSFRow row = sheet.createRow(rowNum++);
		//			colNum =0;
		//			row.createCell(colNum).setCellValue((String)entry.getKey());
		//			row.createCell(++colNum).setCellValue((String)entry.getValue());
		//		}
		System.out.println("out"+rowNo);

		if(sheet.getRow(rowNo)==null) 
		{
			sheet.createRow(rowNo);
			System.out.println("Created");
		}
		row=sheet.getRow(rowNo);

		System.out.println("just get "+sheet.getPhysicalNumberOfRows());
		//	XSSFRow row = sheet.createRow(rowNum++);
		for (int i = 0; i < data.size(); i++) {

			//	colNum =0;
			row.createCell(i).setCellValue(data.get(i));
			//row.createCell(++colNum).setCellValue(data.get());
			System.out.println("inside"+sheet.getLastRowNum());
		}
		fo=new FileOutputStream(excelPath);
		workbook.write(fo);
		workbook.close();
	}


}
