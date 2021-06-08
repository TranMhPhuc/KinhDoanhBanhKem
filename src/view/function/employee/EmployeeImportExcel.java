package view.function.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import model.employee.EmployeeModel;
import model.employee.EmployeeModelInterface;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import static org.apache.poi.ss.usermodel.CellType._NONE;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EmployeeImportExcel {

    public static ArrayList<EmployeeModelInterface> readExcel(String excelFilePath) throws IOException {
        ArrayList<EmployeeModelInterface> ret = new ArrayList<>();

        // Get file
        InputStream inputStream = new FileInputStream(new File(excelFilePath));

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();

            if (nextRow.getRowNum() == 0) {
                // Ignore header
                continue;
            }

            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();

            // Read cells and set value for book object
            EmployeeModelInterface employee = new EmployeeModel();
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case 0:
                        employee.setEmployeeID((String) getCellValue(cell));
                        break;
                    case 1:
                        employee.setName((String) getCellValue(cell));
                        break;
                    case 2:
                        employee.setPhoneNum((String) getCellValue(cell));
                        break;
                    case 3:
                        employee.setBirthday(Date.valueOf((String) ((Double) getCellValue(cell)).toString()));
                        break;
                    case 4:
                        employee.setEmail((String) getCellValue(cell));
                        break;
                    case 5:
                        employee.setPersonalID((String) getCellValue(cell));
                        break;
                    case 6:
                        employee.setGender(Boolean.valueOf((String) getCellValue(cell)));
                        break;
                    case 7:
                        employee.setStartDate(Date.valueOf((String) getCellValue(cell)));
                        break;
                    case 8:
//                        employee.setPosition((String) getCellValue(cell));
                        break;
                    case 9:
                        employee.setStatus(Boolean.valueOf((String) getCellValue(cell)));
                        break;
                    case 10:
                        employee.setEndDate(Date.valueOf((String) getCellValue(cell)));
                        break;
                    default:
                        break;
                }

            }
            ret.add(employee);
        }

        return ret;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
    
    public static void main(String[] args) throws IOException {
        ArrayList<EmployeeModelInterface> list = EmployeeImportExcel.readExcel("F:\\IT\\Java\\Demo.xlsx");
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
