package safehome;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 */
public class UserTest {

    @Test
    public void testLoginInterface() throws Exception {
        User u = new User();
        Assert.assertFalse(u.loginInterface("team1", "12345678"));
        Assert.assertFalse(u.loginInterface("team2", "12345679"));
        Assert.assertTrue(u.loginInterface("team2", "12345678"));
    }

    @Test
    public void testLoginControlPanel() throws Exception {
        User u = new User();
        Assert.assertFalse(u.loginControlPanel("1234"));
        Assert.assertFalse(u.loginControlPanel("0987654"));
        Assert.assertFalse(u.loginControlPanel("0987a"));
        Assert.assertTrue(u.loginControlPanel("0987"));
    }

    @Test
    public void testChangeInterfacePassword() throws Exception {
        User u = new User();
        //wrong original password
        Assert.assertFalse(u.changeInterfacePassword("asdf1234","87654321"));
        //wrong type of password(less than 8 letter)
        Assert.assertFalse(u.changeInterfacePassword("12345678", "clazzi"));
        //still login is possible with original password
        Assert.assertTrue(u.loginInterface("team2", "12345678"));
        //password change
        Assert.assertTrue(u.changeInterfacePassword("12345678", "clazziquai"));
        //original password fails
        Assert.assertFalse(u.loginInterface("team2", "12345678"));
        //new password success
        Assert.assertTrue(u.loginInterface("team2", "clazziquai"));
    }

    @Test
    public void testChangeControlPanelPassword() throws Exception {
        User u = new User();
        //wrong original password
        Assert.assertFalse(u.changeControlPanelPassword("1111","1234"));
        //wrong type of password(not 4 digit)
        Assert.assertFalse(u.changeControlPanelPassword("0987","12345"));
        //wrong type of password(not a number)
        Assert.assertFalse(u.changeControlPanelPassword("0987","a1b2"));
        //still login is possible with original password
        Assert.assertTrue(u.loginControlPanel("0987"));
        //password change
        Assert.assertTrue(u.changeControlPanelPassword("0987","1234"));
        //login success with changed password
        Assert.assertTrue(u.loginControlPanel("1234"));
        //login fails with old password
        Assert.assertFalse(u.loginControlPanel("0987"));
    }
}