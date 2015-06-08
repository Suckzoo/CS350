package safehome;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 * Written by Young Seok Kim on 2015-06-08.
 */
public class ControlPanelTest {

    @Test
    public void testPowerOff() throws Exception {
        ControlPanel cp = new ControlPanel();
        cp.PowerOff();
        //Assert.assertEquals(sc.t.getState(), Thread.State.WAITING);
        //Assert.assertEquals(2, cnt);
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_OFF);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testPowerOn() throws Exception {
        ControlPanel cp = new ControlPanel();
        cp.PowerOn();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testReset() throws Exception {
        ControlPanel cp = new ControlPanel();
        cp.PowerOn();
        cp.Reset();
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
        //Assert.assertEquals(cp.led)
    }

    @Test
    public void testArm() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.Arm();
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.ARM);
    }

    @Test
    public void testDisarm() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.Disarm();
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.DISARM);
    }

    @Test
    public void testButton1() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.button1();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButton2() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button2();
        //Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.POWER_OFF);
    }

    @Test
    public void testButton3() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button3();
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.RESET);
    }

    @Test
    public void testButton4() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button4();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButton5() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button5();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButton6() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button6();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButton7() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button7();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.ARM);
    }

    @Test
    public void testButton8() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button8();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DISARM);
    }

    @Test
    public void testButton9() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button9();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButtonStar() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.buttonStar();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.ARM);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButton0() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.button0();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.POWER_ON);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }

    @Test
    public void testButtonSharp() throws Exception {
        MainDemo md = new MainDemo();
        ControlPanel cp = md.controlPanel;
        cp.PowerOn();
        cp.buttonSharp();
        Assert.assertEquals(cp.currentStatus, ControlPanel.STATUS.ARM);
        Assert.assertEquals(cp.nextStatus, ControlPanel.STATUS.DO_NOTHING);
    }
}