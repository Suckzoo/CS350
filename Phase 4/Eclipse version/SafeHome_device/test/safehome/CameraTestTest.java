package safehome;

import java.lang.reflect.Field;

import javax.swing.JButton;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by suckzoo on 2015-06-08.
 * Written by Seokju Hong on 2015-06-10
 */
public class CameraTestTest {

    @Test
    public void testActionPerformed() throws Exception {
    	CameraTest ct = new CameraTest(1);
    	//pan left
    	JButton pl = new JButton("PanLeft");
    	pl.addActionListener(ct);
    	//pan right
    	JButton pr = new JButton("PanRight");
    	pr.addActionListener(ct);
    	//zoom in
    	JButton zi = new JButton("ZoomIn");
    	zi.addActionListener(ct);
    	//zoom out
    	JButton zo = new JButton("ZoomOut");
    	zo.addActionListener(ct);
    	//trash button
    	JButton zb = new JButton("ZoomBreak");
    	zb.addActionListener(ct);
    	//accessing private attributes
    	Class cam = ct.cameraView.camera.getClass();
    	Field p = cam.getDeclaredField("pan");
    	Field z = cam.getDeclaredField("zoom");
    	p.setAccessible(true);
    	z.setAccessible(true);
    	//functional check
    	int pan,zoom;
    	//pan left
    	pl.doClick();
    	pan = (int)p.get(ct.cameraView.camera);
    	Assert.assertEquals(pan,-1);
    	//zoom in
    	zi.doClick();
    	zoom = (int)z.get(ct.cameraView.camera);
    	Assert.assertEquals(zoom,3);
    	//zoom out
    	zo.doClick();
    	zoom = (int)z.get(ct.cameraView.camera);
    	Assert.assertEquals(zoom,2);
    	//pan right
    	pr.doClick();
    	pan = (int)p.get(ct.cameraView.camera);
    	Assert.assertEquals(pan,0);
    	
    	//trash button click
    	zb.doClick();
    	//nothing happens! (just checking run-time error happens or not!)
    	int i;
    	
    	//max pan-left
    	for(i=0;i<15;i++)
    	{
    		pl.doClick();
    	}
    	pan = (int)p.get(ct.cameraView.camera);
    	Assert.assertEquals(pan, -5);
    	//max pan-right
    	for(i=0;i<15;i++)
    	{
    		pr.doClick();
    	}
    	pan = (int)p.get(ct.cameraView.camera);
    	Assert.assertEquals(pan, 5);
    	//max zoom-in
    	for(i=0;i<15;i++)
    	{
    		zi.doClick();
    	}
    	zoom = (int)z.getInt(ct.cameraView.camera);
    	Assert.assertEquals(zoom, 9);
    	//max zoom-out
    	for(i=0;i<15;i++)
    	{
    		zo.doClick();
    	}
    	zoom = (int)z.getInt(ct.cameraView.camera);
    	Assert.assertEquals(zoom, 1);
    	
    }
}