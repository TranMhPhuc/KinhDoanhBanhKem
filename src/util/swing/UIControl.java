package util.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

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
    }

    public static void setHorizontalAlignmentForColumn(JTable table, int columnNumber, int Alignment) {
        DefaultTableCellRenderer align = new DefaultTableCellRenderer();
        align.setHorizontalAlignment(Alignment);
        table.getColumnModel().getColumn(columnNumber).setCellRenderer(align);
        //JLabel.CENTER
    }

}
