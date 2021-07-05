package util.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class UIControl {

    public static void setLocationCenterForDialog(JDialog d) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - d.getWidth()) / 2;
        int y = (screenSize.height - d.getHeight()) / 2;
        d.setLocation(x, y);
    }

    public static void setDefaultTableHeader(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer;
        table.setRowHeight(30);
        defaultTableCellRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(0);
        table.getTableHeader().setFont(new Font("Segoe UI", 1, 15));

        setHorizontalAlignmentForColumn(table, 0, JLabel.CENTER);
    }

    public static void setDefaultTableHeader2(JTable table) {
        DefaultTableCellRenderer defaultTableCellRenderer;
        table.setRowHeight(30);
        defaultTableCellRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(0);
        table.getTableHeader().setFont(new Font("Segoe UI", 1, 15));
    }

    public static void setHorizontalAlignmentForColumn(JTable table, int columnNumber, int Alignment) {
        DefaultTableCellRenderer align = new DefaultTableCellRenderer();
        align.setHorizontalAlignment(Alignment);
        table.getColumnModel().getColumn(columnNumber).setCellRenderer(align);
        //JLabel.CENTER
    }

    public static void setCurrencyCellRenderer(JTable table, int[] columns) {
        TableColumnModel m = table.getColumnModel();
        for (int i = 0; i < columns.length; i++) {
            m.getColumn(columns[i]).setCellRenderer(NumberRenderer.getCurrencyRenderer());
        }
    }

    public static void setNumberCellRenderer(JTable table, int[] columns) {
        TableColumnModel m = table.getColumnModel();
        for (int i = 0; i < columns.length; i++) {
            m.getColumn(columns[i]).setCellRenderer(NumberRenderer.getNumberRenderer());
        }
    }

    public static void setColumnWidth(JTable table,int column, int width) {
        table.getColumnModel().getColumn(column).setMinWidth(width);
        table.getColumnModel().getColumn(column).setMaxWidth(width);
    }
}
