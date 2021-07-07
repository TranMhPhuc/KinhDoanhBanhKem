/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.swing;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import view.employee.EmployeePanel;

/**
 *
 * @author Minh Tu
 */
public class CustomizedMaskFormatter {

    public static void customizePhoneNumConstraint(JFormattedTextField textfPhoneNum, int numConstraint) {
        try {
            String format = "";
            for (int i = 0; i < numConstraint; i++) {
                format += "#";
            }
            MaskFormatter maskFormat = new MaskFormatter(format);
            textfPhoneNum.setFormatterFactory(new DefaultFormatterFactory(maskFormat));
        } catch (ParseException ex) {
            Logger.getLogger(EmployeePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
