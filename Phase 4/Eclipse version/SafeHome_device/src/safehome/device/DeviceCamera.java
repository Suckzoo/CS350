package safehome.device;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


/**
 * 
 * @author cs350
 *
 */
public class DeviceCamera extends Thread implements interfaceCamera
{
	private int cameraId = 0;
	private int time = 0;	// 00~99
	private int pan = 0;	// -5(left5) ~ 0(center) ~ 5(right5)
	private int zoom = 2;	// 1~9
	
	BufferedImage imgSource;
	Font font;
	
	int centerWidth;
	int centerHeight;
	static final int RETURN_SIZE = 500;
	static final int SOURCE_SIZE = 200;
	
	/**
	 * 
	 */
	public DeviceCamera()
	{
		font = new Font("system", Font.PLAIN, 25);
		this.start();
	}
	
	/** (non-Javadoc)
	 * @see safehome.device.interfaceCamera#setID(int)
	 */
	public synchronized void setID(int id) 
	{ 
		cameraId = id;
		String fileName = "camera"+id+".jpg";
		
	    try 
	    {
	    	imgSource = ImageIO.read(new File(fileName));
	    	centerWidth = imgSource.getWidth(null)/2;
	    	centerHeight = imgSource.getHeight(null)/2;
	        // when applet
	        //URL url = new URL(getCodeBase(), "strawberry.jpg");
            //img = ImageIO.read(url);
	    } 
	    catch (IOException e) 
	    {
	    	imgSource = null;
	    	JOptionPane.showMessageDialog(null, fileName+" file open error");
	    	return;
	    }
	}
	
	/* (non-Javadoc)
	 * @see safehome.device.interfaceCamera#getID()
	 */
	public int getID() { return cameraId; }
	
	/* (non-Javadoc)
	 * @see safehome.device.interfaceCamera#getView()
	 */
	public synchronized BufferedImage getView()
	{
		String view = "Time = ";
		if(time < 10) view += "0";
		view += time+", zoom x"+zoom+", ";
		
		if(pan > 0)
			view += "right "+pan;
		else if(pan == 0)
			view += "center";
		else //if(pan < 0)
			view += "left "+(-pan);
		
		// making real view
		BufferedImage imgView = new BufferedImage(RETURN_SIZE, RETURN_SIZE, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = imgView.createGraphics();
		
		if(imgSource != null)
		{
			int zoomed = ((SOURCE_SIZE*(10-zoom))/10);
			int panned = (pan*SOURCE_SIZE)/5;
			g.drawImage(imgSource, 
                    0, 0, imgView.getWidth(null), imgView.getHeight(null),
                    centerWidth+panned-zoomed, centerHeight-zoomed, centerWidth+panned+zoomed, centerHeight+zoomed,
                    null);  
		}
		else
		{
			g.setColor(Color.black);
	        g.fillRect(0, 0, imgView.getWidth(null), imgView.getHeight(null));
		}
		
		g.setFont(font);
        FontRenderContext frc = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(view, frc);

        int wText = (int)bounds.getWidth();
        int hText = (int)bounds.getHeight();

        int rX = 0;//(size.width-wText)/2;
        int rY = 0;//(size.height-hText)/2;
        g.setColor(Color.gray);
        g.fillRoundRect(rX, rY, wText, hText, hText/2, hText/2);

        /* Draw text positioned in the rectangle.
         * Since the rectangle is sized based on the bounds of
         * the String we can position it using those bounds.
         */
        int xText = rX-(int)bounds.getX();
        int yText = rY-(int)bounds.getY();
        g.setColor(Color.cyan);
        g.setFont(font);
        g.drawString(view, xText, yText);
		
		return imgView;
	}
	
	/* (non-Javadoc)
	 * @see safehome.device.interfaceCamera#panRight()
	 */
	public synchronized boolean panRight()
	{
		if(++pan > 5)
		{
			pan--;
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see safehome.device.interfaceCamera#panLeft()
	 */
	public synchronized boolean panLeft()
	{
		if(--pan < -5)
		{
			pan++;
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see safehome.device.interfaceCamera#zoomIn()
	 */
	public synchronized boolean zoomIn()
	{
		if(++zoom > 9)
		{
			zoom--;
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/* (non-Javadoc)
	 * @see safehome.device.interfaceCamera#zoomOut()
	 */
	public synchronized boolean zoomOut()
	{
		if(--zoom < 1)
		{
			zoom++;
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 
	 */
	private synchronized void tick()
	{
		if(++time >= 100) 
			time = 0;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
			}
			
			tick();
		}
	}
}
