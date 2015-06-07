package safehome;

import javax.swing.*;
import java.io.*;
import sun.audio.*;

/*
** Created by suckzoo on 2015-06-07.
*/
public class SafehomeConsole implements Runnable {
    boolean[] stateWindoorSensor;
    boolean[] stateMotionDetector;
    private SensorTest sensorTest;
    static final int MAX_SENSOR = 5;
    boolean isOperating;
    Thread t;
    static final int RUNNING = 0;
    static final int SUSPENDED = 1;
    static final int STOPPED = 2;
    private int state;
    public SafehomeConsole(SensorTest sensorTestInstance)
    {
        t = null;
        sensorTest = sensorTestInstance;
        stateWindoorSensor = new boolean[5];
        stateMotionDetector = new boolean[5];
        int i;
        for(i=0;i<MAX_SENSOR;i++)
        {
            stateWindoorSensor[i] = false;
            stateMotionDetector[i] = false;
        }
    }
    public void armWindoorSensor(int id) {
        stateWindoorSensor[id] = true;
        sensorTest.armWindoorSensor(id);
    }
    public void disarmWindoorSensor(int id) {
        stateWindoorSensor[id] = false;
        sensorTest.disarmWindoorSensor(id);
    }
    public void armMotionDetector(int id) {
        stateMotionDetector[id] = true;
        sensorTest.armMotionDetector(id);
    }
    public void disarmMotionDetector(int id) {
        stateMotionDetector[id] = false;
        sensorTest.disarmMotionDetector(id);
    }
    public synchronized boolean armSystem()
    {
        if(isOperating) return true;
        int i;
        for(i=0;i<MAX_SENSOR;i++)
        {
            if(stateMotionDetector[i] == true && sensorTest.readMotionDetector(i))
            {
                JOptionPane.showMessageDialog(null, "Not ready: MotionDetector " + Integer.toString(i), "arm failed", JOptionPane.PLAIN_MESSAGE);
                return false;
                //failed
            }
            if(stateWindoorSensor[i] == true && sensorTest.readWindoorSensor(i))
            {
                JOptionPane.showMessageDialog(null, "Not ready: WindoorSensor " + Integer.toString(i), "arm failed", JOptionPane.PLAIN_MESSAGE);
                return false;
                //failed;
            }
        }
        isOperating = true;
        state = RUNNING;
        if(t == null)
        {
            t = new Thread(this);
            t.start();
        }
        return true;
    }
    public void disarmSystem() {
        if (!isOperating) return;
        isOperating = false;
    }
    public void panic() {
        try
        {
            // get the sound file as a resource out of my jar file;
            // the sound file must be in the same directory as this class file
            InputStream inputStream = getClass().getResourceAsStream("ALARM.WAV");
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
            System.out.println("panic!!!!!!");
        }
        catch (Exception e)
        {
        }
    }
    public void resolvePanic() {
        for (i = 0; i < MAX_SENSOR; i++) {
            if (stateWindoorSensor[i] == true && sensorTest.readWindoorSensor(i)) {
                disarmWindoorSensor(i);
            }
            if (stateMotionDetector[i] == true && sensorTest.readMotionDetector(i)) {
                disarmMotionDetector(i);
            }
        }
    }


    public synchronized boolean checkState() {
        while (state == SUSPENDED) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        return state == STOPPED;
    }
    public void run() {
         int i;
         while (true) {
             for (i = 0; i < MAX_SENSOR; i++) {
                 if (stateWindoorSensor[i] == true && sensorTest.readWindoorSensor(i)) {
                     panic();
                 }
                 if (stateMotionDetector[i] == true && sensorTest.readMotionDetector(i)) {
                     panic();
                 }
             }
             if(checkState())
             {
                 break;
             }
         }
     }
}
