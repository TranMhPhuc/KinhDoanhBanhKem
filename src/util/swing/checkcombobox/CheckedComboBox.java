package util.swing.checkcombobox;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.accessibility.Accessible;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.plaf.basic.ComboPopup;

public class CheckedComboBox<E extends CheckableItem> extends JComboBox<E> {

    private boolean keepOpen;
    private transient ActionListener listener;

    public CheckedComboBox() {
        super();
    }

    public CheckedComboBox(ComboBoxModel<E> model) {
        super(model);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 20);
    }

    @Override
    public void updateUI() {
        setRenderer(null);
        removeActionListener(listener);
        super.updateUI();
        listener = e -> {
            if ((e.getModifiers() & AWTEvent.MOUSE_EVENT_MASK) != 0) {
                updateItem(getSelectedIndex());
                keepOpen = true;
            }
        };
        JLabel label = new JLabel(" ");
        JCheckBox check = new JCheckBox(" ");
        setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            if (index < 0) {
                String txt = getCheckedItemString(list.getModel());
                label.setText(txt.isEmpty() ? " " : txt);
                return label;
            } else {
                check.setText(Objects.toString(value, ""));
                check.setSelected(value.isSelected());
                if (isSelected) {
                    check.setBackground(list.getSelectionBackground());
                    check.setForeground(list.getSelectionForeground());
                } else {
                    check.setBackground(list.getBackground());
                    check.setForeground(list.getForeground());
                }
                return check;
            }
        });
        addActionListener(listener);
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "checkbox-select");
        getActionMap().put("checkbox-select", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Accessible a = getAccessibleContext().getAccessibleChild(0);
                if (a instanceof ComboPopup) {
                    updateItem(((ComboPopup) a).getList().getSelectedIndex());
                }
            }
        });
    }

    private static <E extends CheckableItem> String getCheckedItemString(ListModel<E> model) {
        return IntStream.range(0, model.getSize())
                .mapToObj(model::getElementAt)
                .filter(CheckableItem::isSelected)
                .map(Objects::toString)
//                .sorted()
                .collect(Collectors.joining(", "));
    }

    protected void updateItem(int index) {
        if (isPopupVisible()) {
            E item = getItemAt(index);
            item.setSelected(!item.isSelected());
            setSelectedIndex(-1);
            setSelectedItem(item);
        }
    }

    @Override
    public void setPopupVisible(boolean v) {
        if (keepOpen) {
            keepOpen = false;
        } else {
            super.setPopupVisible(v);
        }
    }

}
