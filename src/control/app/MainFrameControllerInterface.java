package control.app;

import javax.swing.JFrame;
import view.profile.ProfilePanel;

public interface MainFrameControllerInterface {
    
    public void setMainFrameView(JFrame mainFrame);
    
    public void setProfilePanelView(ProfilePanel profilePanel);
    
    void requestUpdateProfile();
    
    void requestCancelEditProfile();
    
    void requestChangePassword();
    
    void checkPasswordUpdateInput();
    
    void requestSignOut();
    
    void requestCloseProgram();
    
    boolean canCloseProfilePanel();
    
}
