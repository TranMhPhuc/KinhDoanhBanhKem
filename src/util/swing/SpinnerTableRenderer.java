package util.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class SpinnerTableRenderer extends JSpinner implements TableCellRenderer {

    private JTextField textField;

    public SpinnerTableRenderer() {
        super();
        textField = ((JSpinner.DefaultEditor) this.getEditor()).getTextField();
        textField.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        System.out.println("value: " + value + ", isSelected: " + isSelected + ", hasFocus: " + hasFocus + ", row: " + row + ", col: " + column);
        if (table.getSelectedRow() == row) {
            textField.setBackground(table.getSelectionBackground());
        } else {
            textField.setBackground(table.getBackground());
        }
        
        setValue(value);
        return this;
    }

}
