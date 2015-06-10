package safehome;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 * Written by Seokju Hong on 2015-06-10
 */
public class CameraViewTest {
	
    @Test
    public void testPaint() throws Exception {
    
    }

    @Test
    public void testRun() throws Exception {
    	//is thread running after constructing cameraview object?
    	CameraView cv1 = new CameraView(1);
    	Assert.assertEquals(cv1.th.getState(),Thread.State.RUNNABLE);
    	//is thread running after constructing cameraview object? (same to 2nd cam?)
    	CameraView cv2 = new CameraView(2);
    	Assert.assertEquals(cv2.th.getState(),Thread.State.RUNNABLE);
    }
}