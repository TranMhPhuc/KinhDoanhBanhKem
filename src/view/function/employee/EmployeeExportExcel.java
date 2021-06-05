package view.function.employee;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import model.employee.EmployeeDataStorage;
import model.employee.EmployeeModelInterface;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.db.DataModelUpdateManager;

public class EmployeeExportExcel {

    private static CellStyle cellStyleFormatNumber = null;

    public static void writeExcel(String excelFilePath, String sheetName) throws IOException {
        // Create Workbook
        Workbook workbook = getWorkbook(excelFilePath);

        // Create sheet
        Sheet sheet = workbook.createSheet(sheetName);

        int rowIndex = 0;
        
        String[] headers = new String[] {
            "Mã NV",
            "Tên nhân viên",
            "SDT",
            "Ngày sinh",
            "Email",
            "CCCD",
            "Giới tính",
            "Ngay bat dau",
            "Ten chuc vu",
            "Trang thai",
            "Ngay nghi viec",
        };

        // Write header
        writeHeader(sheet, rowIndex, headers);

        // Write data
        rowIndex++;

        Iterator<EmployeeModelInterface> iterator = EmployeeDataStorage.getInstance().createIterator();
        System.out.println("size: " + EmployeeDataStorage.getInstance().getSize());
        while (iterator.hasNext()) {
            // Create row
            Row row = sheet.createRow(rowIndex);
            // Write data on row
            writeEmployee(iterator.next(), row);
            rowIndex++;
        }

        // Write footer
        writeFooter(sheet, rowIndex);

        // Auto resize column witdth
        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autosizeColumn(sheet, numberOfColumn);

        // Create file excel
        createOutputFile(workbook, excelFilePath);
        
        System.out.println("Done!!!");
    }

    private static Workbook getWorkbook(String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    private static void writeHeader(Sheet sheet, int rowIndex, String[] headers) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = null;
        for (int i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headers[i]);
        }
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    private static void writeEmployee(EmployeeModelInterface employee, Row row) {
        if (cellStyleFormatNumber == null) {
            // Format number
            short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setDataFormat(format);
        }

        Cell cell = null;
        int index = 0;

        cell = row.createCell(index++);
        cell.setCellValue(employee.getEmployeeID());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getEmployeeName());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getPhoneNum());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getBirthday());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getEmail());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getPersonalID());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getGender());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getStartDate());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getEmployeePositionName());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getStatus());
        cell = row.createCell(index++);
        cell.setCellValue(employee.getEndDate());
    }

    // Write footer
    private static void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
//        Row row = sheet.createRow(rowIndex);
//        Cell cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellFormula("SUM(E2:E6)");
    }

    // Auto resize column width
    private static void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }
    
    public static void main(String[] args) throws IOException {
        DataModelUpdateManager dataModelUpdateManager = DataModelUpdateManager.getInstance();
        dataModelUpdateManager.updateFromDB();
        EmployeeExportExcel.writeExcel("F:\\IT\\Java\\Demo.xlsx", "Demo");
    }
}
