package safehome.device;
// The sensor's ID will be given sequentially when new allocation of sensor object.
// polling is done on isOpen() function.

/**
 * @author yhkim
 *
 */
public interface interfaceSensor  
{
	/**
	 * @return
	 */
	public int getID();			// start from 1 and increase by 1
	/**
	 * @return
	 */
	public boolean read();   	// return true when door is opened. false when closed.
	/**
	 * 
	 */
	public void enable();
	/**
	 * 
	 */
	public void disable();
	/**
	 * @return
	 */
	public boolean test();	// return whether enabled or not
}
