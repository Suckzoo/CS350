package safehome;

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
        sc.armWindoorSensor(9999);
    }

    @Test
    public void testDisarmWindoorSensor() throws Exception {

    }

    @Test
    public void testArmMotionDetector() throws Exception {

    }

    @Test
    public void testDisarmMotionDetector() throws Exception {

    }

    @Test
    public void testArmSystem() throws Exception {

    }

    @Test
    public void testDisarmSystem() throws Exception {

    }

    @Test
    public void testResolvePanic() throws Exception {

    }
}