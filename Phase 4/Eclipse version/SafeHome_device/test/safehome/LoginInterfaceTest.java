package safehome;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 * Written by Seokju Hong on 2015-06-10
 */
public class LoginInterfaceTest {

    @Test
    public void testRequireLogin() throws Exception {
    	User u = new User();
    	UserInterface ui = new UserInterface();
    	LoginInterface li = new LoginInterface();
    	//requireLogin
    	li.requireLogin(u, ui);
    	//then, registeredUser and afterView will have reference to u, ui.
    	Assert.assertEquals(li.registeredUser.hashCode(), u.hashCode());
    	Assert.assertEquals(li.afterView.hashCode(), ui.hashCode());
    	//What if UI is already visible?
    	ui.setVisible(true);
    	//Let's make new user.
    	User new_u = new User();
    	//requireLogin again!
    	li.requireLogin(new_u, ui);
    	//but user should not be changed!
    	Assert.assertEquals(li.registeredUser.hashCode(), u.hashCode());
    }

    @Test
    public void testActionPerformed() throws Exception {
    	User u = new User();
    	UserInterface ui = new UserInterface();
    	LoginInterface li = new LoginInterface();
    	li.requireLogin(u, ui);
    	//login button
    	JButton loginBtn = new JButton("Login");
    	loginBtn.addActionListener(li);
    	//N/A button
    	JButton logoutBtn = new JButton("Logout");
    	logoutBtn.addActionListener(li);
    	//non-available button should not work! (just checking run-time errors with exceptions)
    	logoutBtn.doClick();
    	//Wrong ID/PW
    	li.usernameTxt.setText("team2");
    	li.passwordTxt.setText("you_cannot_login");
    	loginBtn.doClick();
    	//then, ui will not show up!
    	Assert.assertFalse(ui.isVisible());
    	//login window still remains!
    	Assert.assertTrue(li.mainFrame.isVisible());
    	//With correct ID/PW
    	li.usernameTxt.setText("team2");
    	li.passwordTxt.setText("12345678");
    	loginBtn.doClick();
    	//then, ui will now show up!
    	Assert.assertTrue(ui.isVisible());
    	//but, login window must go down!
    	Assert.assertFalse(li.mainFrame.isVisible());
    	
    }
}