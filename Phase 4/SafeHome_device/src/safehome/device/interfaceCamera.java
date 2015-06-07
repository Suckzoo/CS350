package safehome.device;
import java.awt.image.BufferedImage;

// Assume that a String as a picture from the real camera.
// The string has the information - pan, zoom, time.
// For example, "time = 00, zoom x5, center"
// boolean returning shows whether the operation success or not(limitation reached).
// The time information increased every one second.


/**
 * @author yhkim
 *
 */
public interface interfaceCamera 
{
	/**
	 * @param id id is just id merong
	 */
	public void setID(int id);
	/**
	 * @return
	 */
	public int getID();
	/**
	 * @return
	 */
	public BufferedImage getView();
	/**
	 * @return
	 */
	public boolean panRight();
	/**
	 * @return
	 */
	public boolean panLeft();
	/**
	 * @return
	 */
	public boolean zoomIn();
	/**
	 * @return
	 */
	public boolean zoomOut();
}
