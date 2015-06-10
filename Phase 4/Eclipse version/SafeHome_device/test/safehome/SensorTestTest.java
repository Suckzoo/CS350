package safehome;

import java.awt.Color;

import javax.swing.JButton;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by suckzoo on 2015-06-08.
 */
public class SensorTestTest {

    @Test
    public void testArmWindoorSensor() throws Exception {
        SensorTest st = new SensorTest();
        //in-Range
        st.armWindoorSensor(0);
        st.armWindoorSensor(4);
        int cnt = 0;
        int i;
        for(i=0;i<5;i++)
        {
            if(st.winDoorSensors[i].test()) cnt++;
        }
        Assert.assertEquals(2, cnt);
        //new arm
        st.armWindoorSensor(3);
        //duplicated arming
        st.armWindoorSensor(4);
        //Range over
        st.armWindoorSensor(5);
        st.armWindoorSensor(6);
        cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.winDoorSensors[i].test()) cnt++;
        }
        Assert.assertEquals(3, cnt);
    }

    @Test
    public void testDisarmWindoorSensor() throws Exception {
        SensorTest st = new SensorTest();
        //in-Range
        st.armWindoorSensor(0);
        st.armWindoorSensor(4);
        int cnt = 0;
        int i;
        for(i=0;i<5;i++)
        {
            if(st.winDoorSensors[i].test()) cnt++;
        }
        Assert.assertEquals(2, cnt);
        //disarm before armed
        st.disarmWindoorSensor(4);
        //strange disarm
        st.disarmWindoorSensor(1);
        //Range over
        st.disarmWindoorSensor(5);
        st.disarmWindoorSensor(6);
        cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.winDoorSensors[i].test()) cnt++;
        }
        Assert.assertEquals(1, cnt);
    }

    @Test
    public void testArmMotionDetector() throws Exception {
        SensorTest st = new SensorTest();
        //in-Range
        st.armMotionDetector(0);
        st.armMotionDetector(4);
        int cnt = 0;
        int i;
        for(i=0;i<5;i++)
        {
            if(st.motionDetectors[i].test()) cnt++;
        }
        Assert.assertEquals(2, cnt);
        //new arm
        st.armMotionDetector(3);
        //duplicated arming
        st.armMotionDetector(4);
        //Range over
        st.armMotionDetector(5);
        st.armMotionDetector(6);
        cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.motionDetectors[i].test()) cnt++;
        }
        Assert.assertEquals(3, cnt);
    }

    @Test
    public void testDisarmMotionDetector() throws Exception {
        SensorTest st = new SensorTest();
        //in-Range
        st.armMotionDetector(0);
        st.armMotionDetector(4);
        int cnt = 0;
        int i;
        for(i=0;i<5;i++)
        {
            if(st.motionDetectors[i].test()) cnt++;
        }
        //armed sensor = 2
        Assert.assertEquals(2, cnt);
        //disarm before armed
        st.disarmMotionDetector(4);
        //strange disarm
        st.disarmMotionDetector(1);
        //Range over
        st.disarmMotionDetector(5);
        st.disarmMotionDetector(6);
        cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.motionDetectors[i].test()) cnt++;
        }
        //valid disarm = 1
        Assert.assertEquals(1, cnt);
    }

    @Test
    public void testReadWindoorSensor() throws Exception {
        SensorTest st = new SensorTest();
        //intrude
        st.winDoorSensors[0].intrude();
        //but not detected because it is not armed yet.
        Assert.assertFalse(st.readWindoorSensor(0));
        //so, let's arm it.
        st.armWindoorSensor(0);
        //now, we can read value.
        Assert.assertTrue(st.readWindoorSensor(0));
        //if we release it,
        st.winDoorSensors[0].release();
        //we cannot detect it.
        Assert.assertFalse(st.readWindoorSensor(0));
        //not-mentioned sensors?
        Assert.assertFalse(st.readWindoorSensor(1));
        //invalid sensors?
        Assert.assertFalse(st.readWindoorSensor(999));
    }

    @Test
    public void testReadMotionDetector() throws Exception {
        SensorTest st = new SensorTest();
        st.motionDetectors[0].intrude();
        Assert.assertFalse(st.readMotionDetector(0));
        st.armMotionDetector(0);
        Assert.assertTrue(st.readMotionDetector(0));
        st.motionDetectors[0].release();
        Assert.assertFalse(st.readMotionDetector(0));
        Assert.assertFalse(st.readMotionDetector(1));
        Assert.assertFalse(st.readMotionDetector(999));
    }
    
    @Test
    public void testRefresh() throws Exception {
    	SensorTest st = new SensorTest();
    	int i;
    	//test for default value
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		Assert.assertTrue(st.testM[i].getText().equals("disable"));
    		Assert.assertTrue(st.readM[i].getText().equals("clear"));
    		Assert.assertTrue(st.testM[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.readM[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.testW[i].getText().equals("disable"));
    		Assert.assertTrue(st.readW[i].getText().equals("close"));
    		Assert.assertTrue(st.testW[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.readW[i].getForeground()==Color.BLACK);
    	}
    	//enabling sensors...
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		st.motionDetectors[i].enable();
    		st.winDoorSensors[i].enable();
    	}
    	st.refresh();
    	//enabled but not detected
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		Assert.assertTrue(st.testM[i].getText().equals("enable"));
    		Assert.assertTrue(st.readM[i].getText().equals("clear"));
    		Assert.assertTrue(st.testM[i].getForeground()==Color.RED);
    		Assert.assertTrue(st.readM[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.testW[i].getText().equals("enable"));
    		Assert.assertTrue(st.readW[i].getText().equals("close"));
    		Assert.assertTrue(st.testW[i].getForeground()==Color.RED);
    		Assert.assertTrue(st.readW[i].getForeground()==Color.BLACK);
    	}
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		st.motionDetectors[i].intrude();
    		st.winDoorSensors[i].intrude();
    	}
    	st.refresh();
    	//enabled and detected
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		Assert.assertTrue(st.testM[i].getText().equals("enable"));
    		Assert.assertTrue(st.readM[i].getText().equals("detect"));
    		Assert.assertTrue(st.testM[i].getForeground()==Color.RED);
    		Assert.assertTrue(st.readM[i].getForeground()==Color.RED);
    		Assert.assertTrue(st.testW[i].getText().equals("enable"));
    		Assert.assertTrue(st.readW[i].getText().equals("open"));
    		Assert.assertTrue(st.testW[i].getForeground()==Color.RED);
    		Assert.assertTrue(st.readW[i].getForeground()==Color.RED);
    	}
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		st.motionDetectors[i].disable();
    		st.winDoorSensors[i].disable();
    	}
    	st.refresh();
    	//sensor disabled, so never detected.
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		Assert.assertTrue(st.testM[i].getText().equals("disable"));
    		Assert.assertTrue(st.readM[i].getText().equals("clear"));
    		Assert.assertTrue(st.testM[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.readM[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.testW[i].getText().equals("disable"));
    		Assert.assertTrue(st.readW[i].getText().equals("close"));
    		Assert.assertTrue(st.testW[i].getForeground()==Color.BLACK);
    		Assert.assertTrue(st.readW[i].getForeground()==Color.BLACK);
    	}
    }
    
    @Test
    public void testActionPerformed() throws Exception {
    	String[] args = new String[2];
    	MainDemo.main(args);
    	SensorTest st = new SensorTest();
    	JButton enable = new JButton("Enable All");
    	enable.addActionListener(st);
    	JButton disable = new JButton("Disable All");
    	disable.addActionListener(st);
    	JButton refresh = new JButton("refresh");
    	refresh.addActionListener(st);
    	JButton trash = new JButton("Breakdown every sensors");
    	trash.addActionListener(st);
    	//refresh button clicks
    	refresh.doClick();
    	//but nothing happens :( (used given code, seems redundant branch)
    	//enable button clicks
    	enable.doClick();
    	//every sensor enabled!
    	int i;
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		Assert.assertTrue(MainDemo.safehomeConsole.stateMotionDetector[i]);
    		Assert.assertTrue(MainDemo.safehomeConsole.stateWindoorSensor[i]);
    	}
    	//disable button clicks
    	disable.doClick();
    	//every sensor disabled!
    	for(i=0;i<st.MAX_SENSOR;i++)
    	{
    		Assert.assertFalse(MainDemo.safehomeConsole.stateMotionDetector[i]);
    		Assert.assertFalse(MainDemo.safehomeConsole.stateWindoorSensor[i]);
    	}
    	//trash button clicks
    	trash.doClick();
    	//nothing happens! (just run-time error check!)
    }
}