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
    static final int NOT_INITIALIZED = 3;
    private int state;
    boolean overridePanic;

    public SafehomeConsole(SensorTest sensorTestInstance)
    {
        state = NOT_INITIALIZED;
        overridePanic = false;
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
        if(id>=MAX_SENSOR) return;
        stateWindoorSensor[id] = true;
        sensorTest.armWindoorSensor(id);
    }
    public void disarmWindoorSensor(int id) {
        if(id>=MAX_SENSOR) return;
        stateWindoorSensor[id] = false;
        sensorTest.disarmWindoorSensor(id);
    }
    public void armMotionDetector(int id) {
        if(id>=MAX_SENSOR) return;
        stateMotionDetector[id] = true;
        sensorTest.armMotionDetector(id);
    }
    public void disarmMotionDetector(int id) {
        if(id>=MAX_SENSOR) return;
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
                JOptionPane.showMessageDialog(null, "Not ready: MotionDetector " + Integer.toString(i+1), "arm failed", JOptionPane.PLAIN_MESSAGE);
                return false;
                //failed
            }
            if(stateWindoorSensor[i] == true && sensorTest.readWindoorSensor(i))
            {
                JOptionPane.showMessageDialog(null, "Not ready: WindoorSensor " + Integer.toString(i+1), "arm failed", JOptionPane.PLAIN_MESSAGE);
                return false;
                //failed;
            }
        }
        isOperating = true;
        if(state == RUNNING) return true;
        state = RUNNING;
        if(t == null)
        {
            t = new Thread(this);
            t.start();
        } else
        {
            notify();
        }
        return true;
    }
    public void disarmSystem() {
        if (!isOperating) return;
        isOperating = false;
        overridePanic = false;
        state = SUSPENDED;
    }
    public synchronized void manualPanic()
    {
        overridePanic = true;
        isOperating = true;
        if(state == RUNNING) return;
        state = RUNNING;
        if(t == null)
        {
            t = new Thread(this);
            t.start();
        } else
        {
            notify();
        }
    }
    public void panic() {
        try
        {
            // get the sound file as a resource out of my jar file;
            // the sound file must be in the same directory as this class file
            InputStream inputStream = getClass().getResourceAsStream("ALARM.WAV");
            AudioStream audioStream = new AudioStream(inputStream);
            AudioPlayer.player.start(audioStream);
            //System.out.println("panic!!!!!!");
        }
        catch (Exception e)
        {
        }
    }
    public void resolvePanic() {
        overridePanic = false;
        for (int i = 0; i < MAX_SENSOR; i++) {
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
         int cnt = 0;
         try {
             while (true) {
                 //System.out.print("Thread operating..." + Integer.toString(cnt++));
                 for (i = 0; i < MAX_SENSOR; i++) {
                     if(overridePanic) {
                         panic();
                     }
                     else if (stateWindoorSensor[i] == true && sensorTest.readWindoorSensor(i)) {
                         panic();
                     }
                     else if (stateMotionDetector[i] == true && sensorTest.readMotionDetector(i)) {
                         panic();
                     }
                 }
                 if (checkState()) {
                     break;
                 }
                 Thread.sleep(1000);
             }
         } catch(InterruptedException e) {
         }
     }
}
