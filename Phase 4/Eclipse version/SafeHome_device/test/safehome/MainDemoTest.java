package safehome;

import java.lang.reflect.Field;

import javax.swing.JButton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 */
public class MainDemoTest {

    @Test
    public void testMain() throws Exception {
    	String[] str = new String[2];
    	//Main function constructs object
    	MainDemo.main(str);
    	//Then, every module created!
    	Assert.assertNotNull(MainDemo.safehomeConsole);
    	Assert.assertNotNull(MainDemo.controlPanel);
    	Assert.assertNotNull(MainDemo.user);
    	SafehomeConsole sc = MainDemo.safehomeConsole;
    	ControlPanel cp = MainDemo.controlPanel;
    	User u = MainDemo.user;
    	//if new main is called,
    	MainDemo.main(str);
    	//two objects are different! (because new object created!)
    	Assert.assertNotEquals(MainDemo.safehomeConsole.hashCode(), sc.hashCode());
    	Assert.assertNotEquals(MainDemo.controlPanel.hashCode(), cp.hashCode());
    	Assert.assertNotEquals(MainDemo.user.hashCode(), u.hashCode());    	
    }

    @Test
    public void testActionPerformed() throws Exception {
    	MainDemo md = new MainDemo();
    	//fake control panel button
    	JButton jb1 = new JButton("Control Panel");
    	jb1.addActionListener(md);
    	//fake sensor test button
    	JButton jb2 = new JButton("Sensor");
    	jb2.addActionListener(md);
    	//fake ui button
    	JButton jb3 = new JButton("User Interface");
    	jb3.addActionListener(md);
    	//dummy button
    	JButton jb_trash = new JButton("Camera");
    	jb_trash.addActionListener(md);
    	
    	Class klass = MainDemo.class;
    	Field login = klass.getDeclaredField("loginInterface");
    	Field sensor = klass.getDeclaredField("sensorTest");
    	login.setAccessible(true);
    	sensor.setAccessible(true);
    	LoginInterface lg = (LoginInterface) login.get(md);
    	SensorTest st = (SensorTest) sensor.get(md);
    	//control panel button press?
    	jb1.doClick();
    	//then control panel is now visible!
    	Assert.assertTrue(md.controlPanel.isVisible());
    	//sensor test button press?
    	jb2.doClick();
    	//then sensor test window is now visible!
    	Assert.assertTrue(st.isVisible());
    	//user interface button press?
    	jb3.doClick();
    	//login interface is now visible!
    	Assert.assertTrue(lg.mainFrame.isVisible());
    	//nothing happens when strange button is clicked! (and it should not happen!)
    	jb_trash.doClick();
    }
}