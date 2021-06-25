/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

/**
 *
 * @author Minh Tu
 */
public class PasswordListener implements MouseListener {

    JLabel iconPassword;
    JPasswordField passfPassword;
    boolean showPW;

    public PasswordListener(JLabel iconPassword, JPasswordField passfPassword, boolean showPW) {
        super();
        this.iconPassword = iconPassword;
        this.passfPassword = passfPassword;
        this.showPW = showPW;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if (showPW) {
            showPW = false;
            iconPassword.setIcon(new ImageIcon(getClass().getResource("/img/selected_hidePW_24px.png")));
            passfPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
        } else {
            showPW = true;
            iconPassword.setIcon(new ImageIcon(getClass().getResource("/img/selected_showPW_24px.png")));
            passfPassword.setEchoChar('\u0000');
        }
    }

    @Override
    public void mouseExited(MouseEvent evt) {
        if (showPW) {
            iconPassword.setIcon(new ImageIcon(getClass().getResource("/img/showPW_24px.png")));
        } else {
            iconPassword.setIcon(new ImageIcon(getClass().getResource("/img/hidePW_24px.png")));
        }
        iconPassword.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
        if (showPW) {
            iconPassword.setIcon(new ImageIcon(getClass().getResource("/img/selected_showPW_24px.png")));
        } else {
            iconPassword.setIcon(new ImageIcon(getClass().getResource("/img/selected_hidePW_24px.png")));
        }
        iconPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mousePressed(MouseEvent evt) {

    }

    @Override
    public void mouseReleased(MouseEvent evt) {

    }
}
