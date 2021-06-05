package util.excel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelTransfer {

    public static void importExcel() {

    }

    public void exportToXLS(String sheetName) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        if (sheetName.isEmpty()) {
            sheetName = "Sheet";
        }
        XSSFSheet sheet = workbook.createSheet(sheetName);
    }
    
    private static void writeHeaderLine(XSSFSheet sheet, String[] headerString) {
        XSSFRow headerRow = sheet.createRow(0);
        
        
        for (int i = 0; i < headerString.length; i++) {
            XSSFCell headerCell = headerRow.createCell(i);
//            headerCell.setCellValue(headerString[i]);
        }
    }

}
