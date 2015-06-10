package safehome;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 */
public class SafehomeConsoleTest {

    @Test
    public void testArmWindoorSensor() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        //normal arming
        sc.armWindoorSensor(1);
        sc.armWindoorSensor(4);
        //duplicated arming
        sc.armWindoorSensor(1);
        //invalid arming
        sc.armWindoorSensor(9999);
        int i;
        int cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.winDoorSensors[i].test()) cnt++;
        }
        Assert.assertEquals(2, cnt);
    }

    @Test
    public void testDisarmWindoorSensor() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        //normal arming
        sc.armWindoorSensor(1);
        sc.armWindoorSensor(4);
        //normal disarming
        sc.disarmWindoorSensor(1);
        //invalid disarming
        sc.disarmWindoorSensor(9999);
        //disarming non-armed device
        sc.disarmWindoorSensor(2);
        int i;
        int cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.winDoorSensors[i].test()) cnt++;
        }
        Assert.assertEquals(1, cnt);
    }

    @Test
    public void testArmMotionDetector() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        //normal arming
        sc.armMotionDetector(1);
        sc.armMotionDetector(4);
        //duplicated arming
        sc.armMotionDetector(1);
        //invalid arming
        sc.armMotionDetector(9999);
        int i;
        int cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.motionDetectors[i].test()) cnt++;
        }
        Assert.assertEquals(2, cnt);
    }

    @Test
    public void testDisarmMotionDetector() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        //normal arming
        sc.armMotionDetector(1);
        sc.armMotionDetector(4);
        //normal disarming
        sc.disarmMotionDetector(1);
        //disarming invalid device
        sc.armMotionDetector(9999);
        //disarming non-armed device;
        sc.disarmMotionDetector(2);
        int i;
        int cnt = 0;
        for(i=0;i<5;i++)
        {
            if(st.motionDetectors[i].test()) cnt++;
        }
        Assert.assertEquals(1, cnt);
    }

    @Test
    public void testArmSystem() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        //intrude motiondetector before arming
        st.motionDetectors[0].intrude();
        sc.armMotionDetector(0);
        Assert.assertFalse(sc.armSystem());
        st.motionDetectors[0].release();
        sc.disarmMotionDetector(0);
        //intrude windoorsensor before arming
        st.winDoorSensors[0].intrude();
        sc.armWindoorSensor(0);
        Assert.assertFalse(sc.armSystem());
        sc.disarmSystem();
        sc.disarmWindoorSensor(0);
        st.winDoorSensors[0].release();
        //intrude motiondetector but not enable it
        st.motionDetectors[0].intrude();
        Assert.assertTrue(sc.armSystem());
        sc.disarmSystem();
        sc.disarmSystem();
        st.motionDetectors[0].release();
        //intrude windoorsensor but not enable it
        st.winDoorSensors[0].intrude();
        Assert.assertTrue(sc.armSystem());
        sc.disarmSystem();
        st.winDoorSensors[0].release();
        int i;
        //enable every sensor
        for(i=0;i<5;i++)
        {
            sc.armWindoorSensor(i);
            sc.armMotionDetector(i);
        }
        Assert.assertTrue(sc.armSystem());
        sc.disarmSystem();
    }

    @Test
    public void testDisarmSystem() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        Assert.assertTrue(sc.armSystem());
        sc.disarmSystem();
        //Thread disarming message may go slow.
        Thread.currentThread().sleep(2000);
        int i;
        for(i=0;i<5;i++)
        {
            sc.armWindoorSensor(i);
            sc.armMotionDetector(i);
        }
        st.winDoorSensors[0].intrude();
        Assert.assertEquals(sc.t.getState(), Thread.State.WAITING);
    }

    @Test
    public void testResolvePanic() throws Exception {
        SensorTest st = new SensorTest();
        SafehomeConsole sc = new SafehomeConsole(st);
        int i;
        for(i=0;i<5;i++)
        {
            sc.armMotionDetector(i);
            sc.armWindoorSensor(i);
        }
        sc.manualPanic();
        //small delay for thread running
        Assert.assertEquals(sc.t.getState(), Thread.State.RUNNABLE);
        Thread.currentThread().sleep(3000); //panic beep rocks
        sc.disarmSystem();
        sc.resolvePanic();
        //small delay for thread running
        Thread.currentThread().sleep(1000);
        Assert.assertEquals(sc.t.getState(), Thread.State.WAITING);
        for(i=0;i<5;i++)
        {
            Assert.assertFalse(st.motionDetectors[i].test());
            Assert.assertFalse(st.winDoorSensors[i].test());
        }
    }
}