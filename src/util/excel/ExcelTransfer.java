package util.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.messages.Messages;

public class ExcelTransfer {

    private ExcelTransfer() {
    }

    private static CellStyle createCellStyleForHeaderExcel(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private static CellStyle createCellStyleForDataExecel(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 13); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color
        font.setBold(false);

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }

    public static void exportTableToExcel(JTable tableExample) {
        JFileChooser excelFileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File f = getSelectedFile();

                if (f.exists() && getDialogType() == JFileChooser.CUSTOM_DIALOG) {
                    String notify = f.getName() + Messages.getInstance().OTHERS_REPLACE_EXCEL;
                    int result = JOptionPane.showConfirmDialog(null, notify,
                            "BakeryMS", JOptionPane.YES_NO_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };
        excelFileChooser.setDialogTitle("Export table to excel file");
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter(
                "Excel Extensions (xls, xlsx, xlsm)", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(excelFilter);
        int choose = excelFileChooser.showDialog(null, "Export");
        if (choose == JFileChooser.APPROVE_OPTION) {
            FileOutputStream excelFileOutput = null;
            BufferedOutputStream excelBuffered = null;
            XSSFWorkbook excelExporter = new XSSFWorkbook();
            XSSFSheet excelSheet = excelExporter.createSheet("Your sheet name");

            XSSFRow headerRow = excelSheet.createRow(0);
            excelSheet.setColumnWidth(3, 2850);

            CellStyle headerStyle = createCellStyleForHeaderExcel(excelSheet);
            CellStyle dataStyle = createCellStyleForDataExecel(excelSheet);

            //add Header Name to excel file
            DefaultTableModel dtm = (DefaultTableModel) tableExample.getModel();
            for (int i = 0; i < dtm.getColumnCount(); i++) {
                XSSFCell excelCell = headerRow.createCell(i);
                excelCell.setCellValue(tableExample.getColumnName(i));
                excelCell.setCellStyle(headerStyle);
            }
            //add Data to excel file
            for (int i = 0; i < dtm.getRowCount(); i++) {
                XSSFRow dataRow = excelSheet.createRow(i + 1);
                for (int j = 0; j < dtm.getColumnCount(); j++) {
                    XSSFCell excelCell = dataRow.createCell(j);
                    Object object = dtm.getValueAt(i, j);
                    if (object == null) {
                        continue;
                    }
                    if (dtm.getColumnClass(j).equals(Integer.class)) {
                        excelCell.setCellValue(Integer.parseInt(object.toString()));
                    } else if (dtm.getColumnClass(j).equals(String.class)) {
                        excelCell.setCellValue(object.toString());
                    } else if (dtm.getColumnClass(j).equals(Double.class)) {
                        excelCell.setCellValue(Double.parseDouble(object.toString()));
                    } else if (dtm.getColumnClass(j).equals(Long.class)) {
                        excelCell.setCellValue(Long.parseLong(object.toString()));
                    } else if (dtm.getColumnClass(j).equals(Float.class)) {
                        excelCell.setCellValue(Float.parseFloat(object.toString()));
                    } else if (dtm.getColumnClass(j).equals(Boolean.class)) {
                        excelCell.setCellValue(Boolean.parseBoolean(object.toString()));
                    } else {
                        excelCell.setCellValue("Invalid type");
                    }
                    excelCell.setCellStyle(dataStyle);
                }
            }

            // Auto resize column witdth
            int numberOfColumn = excelSheet.getRow(0).getPhysicalNumberOfCells();
            for (int columnIndex = 0; columnIndex < numberOfColumn; columnIndex++) {
                excelSheet.autoSizeColumn(columnIndex);
            }

            try {
                String absolutePath = excelFileChooser.getSelectedFile()
                        .getAbsolutePath();
                String filePath = absolutePath.substring(0, absolutePath
                        .lastIndexOf(File.separator)) + File.separator;
                String fileName = FilenameUtils.removeExtension(excelFileChooser
                        .getSelectedFile().getName());
                String result = filePath + fileName;
                excelFileOutput = new FileOutputStream(result + ".xlsx");
                excelBuffered = new BufferedOutputStream(excelFileOutput);
                excelExporter.write(excelBuffered);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBuffered != null) {
                        excelBuffered.close();
                    }
                    if (excelFileOutput != null) {
                        excelFileOutput.close();
                    }
                    if (excelExporter != null) {
                        excelExporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public static void createExcelFileTemplate(JTable tableExample) {
        JFileChooser excelFileChooser = new JFileChooser() {
            @Override
            public void approveSelection() {
                File f = getSelectedFile();
                String notify = f.getName() + "already exists.\nDo you want to replace it?";
                if (f.exists() && getDialogType() == JFileChooser.CUSTOM_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(null, notify,
                            "Confirm Override", JOptionPane.YES_NO_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };

        excelFileChooser.setDialogTitle("Create Excel File Template");

        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter(
                "Excel Extensions (xls, xlsx, xlsm)", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(excelFilter);
        int choose = excelFileChooser.showDialog(null, "Create");

        if (choose == JFileChooser.APPROVE_OPTION) {
            FileOutputStream excelFileOutput = null;
            BufferedOutputStream excelBuffered = null;
            XSSFWorkbook excelExporter = new XSSFWorkbook();
            XSSFSheet excelSheet = excelExporter.createSheet("Your sheet name");

            XSSFRow headerRow = excelSheet.createRow(0);
            excelSheet.setColumnWidth(3, 2850);

            CellStyle headerStyle = createCellStyleForHeaderExcel(excelSheet);

            //add Header Name to excel file
            DefaultTableModel dtm = (DefaultTableModel) tableExample.getModel();
            for (int i = 0; i < dtm.getColumnCount(); i++) {
                XSSFCell excelCell = headerRow.createCell(i);
                excelCell.setCellValue(dtm.getColumnName(i));
                excelCell.setCellStyle(headerStyle);

            }
            try {
                String absolutePath = excelFileChooser.getSelectedFile().getAbsolutePath();
                String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator))
                        + File.separator;
                String fileName = FilenameUtils.removeExtension(excelFileChooser.getSelectedFile().getName());
                String result = filePath + fileName;
                excelFileOutput = new FileOutputStream(result + ".xlsx");
                excelBuffered = new BufferedOutputStream(excelFileOutput);
                excelExporter.write(excelBuffered);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBuffered != null) {
                        excelBuffered.close();
                    }
                    if (excelFileOutput != null) {
                        excelFileOutput.close();
                    }
                    if (excelExporter != null) {
                        excelExporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public static void importExcelFileToTable(JTable jTable) {
        DefaultTableModel dtm = (DefaultTableModel) jTable.getModel();

        File excelFile;
        JFileChooser excelFileChooser = new JFileChooser();
        excelFileChooser.setDialogTitle("Choose Excel File To Import");
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter(
                "Excel Extensions (xls, xlsx, xlsm)", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(excelFilter);

        int choose = excelFileChooser.showDialog(null, "Choose");
        if (choose == JFileChooser.APPROVE_OPTION) {
            dtm.setRowCount(0);

            FileInputStream excelFileInput = null;
            BufferedInputStream excelBuffered = null;
            XSSFWorkbook excelImporter = null;
            try {
                boolean passed = true;
                excelFile = excelFileChooser.getSelectedFile();
                excelFileInput = new FileInputStream(excelFile);
                excelBuffered = new BufferedInputStream(excelFileInput);

                excelImporter = new XSSFWorkbook(excelBuffered);
                XSSFSheet excelSheet = excelImporter.getSheetAt(0);

                //tổng row đếm từ 0, tổng cell đếm từ 1
                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
                    Vector rowData = new Vector();
                    for (int column = 0; column < excelRow.getLastCellNum(); column++) {
                        XSSFCell excelCell = excelRow.getCell(column);
                        if (dtm.getColumnClass(column).equals(Integer.class)) {
                            if (excelCell.getCellType() != CellType.NUMERIC) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                break;
                            }
                            try {
                                double value = excelCell.getNumericCellValue();
                                rowData.add((int) Math.floor(value));
                            } catch (NumberFormatException ex ) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                ex.printStackTrace();
                                break;
                            }
                        } else if (dtm.getColumnClass(column).equals(Long.class)) {
                            if (excelCell.getCellType() != CellType.NUMERIC) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                break;
                            }
                            try {
                                double value  = excelCell.getNumericCellValue();
                                rowData.add(((Double) value).longValue());
                            } catch (NumberFormatException ex) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                ex.printStackTrace();
                                break;
                            }
                        } else if (dtm.getColumnClass(column).equals(Float.class)) {
                            if (excelCell.getCellType() != CellType.NUMERIC) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                break;
                            }
                            try {
                                double value  = excelCell.getNumericCellValue();
                                rowData.add(((Double) value).floatValue());
                            } catch (NumberFormatException ex) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                ex.printStackTrace();
                                break;
                            }
                        } else if (dtm.getColumnClass(column).equals(Boolean.class)) {
                            if (excelCell.getCellType() != CellType.BOOLEAN) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                break;
                            }
                            try {
                                rowData.add(Boolean.parseBoolean(excelCell.getStringCellValue()));
                            } catch (NumberFormatException ex) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                ex.printStackTrace();
                                break;
                            }
                        } else if (dtm.getColumnClass(column).equals(String.class)) {
                            if (excelCell.getCellType() != CellType.STRING) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                break;
                            }
                            rowData.add(excelCell.getStringCellValue());
                        } else if (dtm.getColumnClass(column).equals(Double.class)) {
                            if (excelCell.getCellType() != CellType.NUMERIC) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                break;
                            }
                            try {
                                rowData.add(excelCell.getNumericCellValue());
                            } catch (NumberFormatException ex) {
                                passed = false;
                                showErrorMessage(row, column + 1);
                                ex.printStackTrace();
                                break;
                            }
                        }
                    }
                    if (passed) {
                        dtm.addRow(rowData);
                    } else {
                        dtm.setRowCount(0);
                        break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (excelBuffered != null) {
                        excelBuffered.close();
                    }
                    if (excelFileInput != null) {
                        excelFileInput.close();
                    }
                    if (excelImporter != null) {
                        excelImporter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        }

    }

    private static void showErrorMessage(int row, int column) {
        JOptionPane.showMessageDialog(null, "Row " + row + " Column " + column
                + " is invalid, stopped reading file.", "Bakery Management System",
                JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon("img/error.png"));
    }

}
