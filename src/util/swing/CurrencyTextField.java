/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.swing;

import java.awt.event.KeyAdapter;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Minh Tu
 */
public class CurrencyTextField extends JFormattedTextField {

    public CurrencyTextField() {
        NumberFormatter format = new NumberFormatter();
        format.setAllowsInvalid(false);
        this.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(format));
        
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    commitEdit();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //Plain text components do not fire these events
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                int len = getText().length();
                char keyChar = evt.getKeyChar();
                if ((keyChar == 8 || keyChar == 127) && (len == 1 || getText().equals(getSelectedText()))) {
                    setValue(null);
                }

            }
        });
    }

}
