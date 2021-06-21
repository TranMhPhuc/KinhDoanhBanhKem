/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apitest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.messages.Messages;

public class ExcelExport {

    private static CellStyle createCellStyleForHeaderExcel(XSSFSheet sheet) {
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
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
                String notify = f.getName() + Messages.getInstance().OTHERS_REPLACE_EXCEL;
                if (f.exists() && getDialogType() == JFileChooser.CUSTOM_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(null, notify, "Save excel file", JOptionPane.YES_NO_OPTION);
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
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter("Excel Extensions (xls, xlsx, xlsm)", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(excelFilter);
        int choose = excelFileChooser.showDialog(null, "Export");
        // int choose = excelFileChooser.showSaveDialog(this);
        if (choose == JFileChooser.APPROVE_OPTION) {
            FileOutputStream excelFileOutput = null;
            BufferedOutputStream excelBuffered = null;
            XSSFWorkbook excelExporter = new XSSFWorkbook();
            XSSFSheet excelSheet = excelExporter.createSheet("Sheet1");

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
                    Object test = dtm.getValueAt(i, j);

                    if (dtm.getColumnClass(j).equals(Integer.class)) {
                        excelCell.setCellValue(Integer.parseInt(test.toString()));
                    } else if (dtm.getColumnClass(j).equals(String.class)) {
                        excelCell.setCellValue(test.toString());
                    } else if (dtm.getColumnClass(j).equals(Double.class)) {
                        excelCell.setCellValue(Double.parseDouble(test.toString()));
                    } else {
                        excelCell.setCellValue("Invalid type");
                    }
                    excelCell.setCellStyle(dataStyle);

                }
            }

            try {
                String absolutePath = excelFileChooser.getSelectedFile().getAbsolutePath();
                String filePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)) + File.separator;
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
}
