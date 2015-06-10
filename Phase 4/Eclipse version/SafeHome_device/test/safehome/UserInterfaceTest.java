package safehome;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by suckzoo on 2015-06-08.
 * Written by Seokju Hong on 2015-06-10
 */
public class UserInterfaceTest {
	@Test
	public void testWindoorCheckbox() throws Exception {
		String[] args = new String[2];
		MainDemo.main(args);
		UserInterface ui = new UserInterface();
		JCheckBox not_windoor = new JCheckBox("WindoorSensor 6");
		not_windoor.addActionListener(ui);
		//check checkbox
		ui.checkBoxList.get(0).doClick();
		//then windoor sensor 1 will run!
		Assert.assertTrue(MainDemo.safehomeConsole.stateWindoorSensor[0]);
		//check checkbox again...
		ui.checkBoxList.get(0).doClick();
		//then windoor sensor 1 will not run!
		Assert.assertFalse(MainDemo.safehomeConsole.stateWindoorSensor[0]);
		//what if invalid windoor checkbox clicked? (may not be happened... but...)
		not_windoor.doClick();
		//nothing happens! but no error is expected!
	}
	@Test
	public void testMotionCheckbox() throws Exception {
		String[] args = new String[2];
		MainDemo.main(args);
		UserInterface ui = new UserInterface();
		JCheckBox not_motion = new JCheckBox("MotionSensor 6");
		not_motion.addActionListener(ui);
		//check checkbox
		ui.checkBoxList.get(5).doClick();
		//then windoor sensor 1 will run!
		Assert.assertTrue(MainDemo.safehomeConsole.stateMotionDetector[2]);
		//check checkbox
		ui.checkBoxList.get(5).doClick();
		//then windoor sensor 1 will run!
		Assert.assertFalse(MainDemo.safehomeConsole.stateMotionDetector[2]);
		//what if invalid windoor checkbox clicked? (may not be happened... but...)
		not_motion.doClick();
		//nothing happens! but no error is expected!
	}
	@Test
	public void testSaveLoadZone() throws Exception {
		String[] args = new String[2];
		MainDemo.main(args);
		UserInterface ui = new UserInterface();
		JButton save = new JButton("Save security zone");
		save.addActionListener(ui);
		JButton load = new JButton("Load security zone");
		load.addActionListener(ui);
		//no saved zone!
		load.doClick();
		//then, no sensor is armed (with this case: no checkbox is checked!)
		int i;
		for(i=0;i<5;i++)
		{
			Assert.assertFalse(MainDemo.safehomeConsole.stateMotionDetector[i]);
			Assert.assertFalse(MainDemo.safehomeConsole.stateWindoorSensor[i]);
		}
		//check some sensors
		ui.checkBoxList.get(0).doClick();
		ui.checkBoxList.get(1).doClick();
		ui.checkBoxList.get(3).doClick();
		ui.checkBoxList.get(4).doClick();
		//save zone name is SaveZone1
		ui.optionKey = "SaveZone1";
		//and, save it!
		save.doClick();
		//and, uncheck the sensors (disarm!)
		ui.checkBoxList.get(0).doClick();
		ui.checkBoxList.get(1).doClick();
		ui.checkBoxList.get(3).doClick();
		ui.checkBoxList.get(4).doClick();
		//and, load!
		load.doClick();
		//check state is equal? (should be equal because we loaded them!)
		Assert.assertTrue(MainDemo.safehomeConsole.stateWindoorSensor[0]);
		Assert.assertTrue(MainDemo.safehomeConsole.stateWindoorSensor[2]);
		Assert.assertTrue(MainDemo.safehomeConsole.stateMotionDetector[0]);
		Assert.assertTrue(MainDemo.safehomeConsole.stateMotionDetector[1]);
		//what if wrong key detected?
		ui.optionKey = "SaveZoneError";
		load.doClick();
		//just no error is expected!
	}
	@Test
	public void testActivate() throws Exception {
		String[] args = new String[2];
		MainDemo.main(args);
		UserInterface ui = new UserInterface();
		JButton activate = new JButton("Activate");
		activate.addActionListener(ui);
		//check some sensors
		ui.checkBoxList.get(0).doClick();
		ui.checkBoxList.get(1).doClick();
		ui.checkBoxList.get(3).doClick();
		ui.checkBoxList.get(4).doClick();
		//and Arm it!
		activate.doClick();
		//has sensor armed?
		Assert.assertTrue(MainDemo.safehomeConsole.stateWindoorSensor[0]);
		Assert.assertTrue(MainDemo.safehomeConsole.stateWindoorSensor[2]);
		Assert.assertTrue(MainDemo.safehomeConsole.stateMotionDetector[0]);
		Assert.assertTrue(MainDemo.safehomeConsole.stateMotionDetector[1]);
		//what if we invoke panic?
		Class demo = MainDemo.class;
		Field sensor = demo.getDeclaredField("sensorTest");
		sensor.setAccessible(true);
		SensorTest st = (SensorTest) sensor.get(MainDemo.safeHome);
		//panic case!
		st.winDoorSensors[0].intrude();
		//small delay provided to console(thread issues)
		MainDemo.safehomeConsole.t.sleep(2000);
		Assert.assertTrue(MainDemo.safehomeConsole.nowPanic);
		st.winDoorSensors[0].release();
		//also, panic case!
		st.motionDetectors[0].intrude();
		//small delay provided to console(thread issues)
		MainDemo.safehomeConsole.t.sleep(2000);
		Assert.assertTrue(MainDemo.safehomeConsole.nowPanic);
		//every intrudes released!
		st.motionDetectors[0].release();
		//small delay provided to console(thread issues)
		MainDemo.safehomeConsole.t.sleep(2000);
		//panic resolved.
		Assert.assertFalse(MainDemo.safehomeConsole.nowPanic);
				
	}
	@Test
	public void testCameraWindow() throws Exception {
		UserInterface ui = new UserInterface();
		JButton cam1 = new JButton("Camera 1");
		cam1.addActionListener(ui);
		JButton cam2 = new JButton("Camera 2");
		cam2.addActionListener(ui);
		//cam1 pushed
		cam1.doClick();
		//cam1 panel will show up.
		Assert.assertTrue(ui.cameraControlPanel1.isVisible());
		//cam2 pushed
		cam2.doClick();
		//cam2 panel will show up.
		Assert.assertTrue(ui.cameraControlPanel2.isVisible());
	}
}