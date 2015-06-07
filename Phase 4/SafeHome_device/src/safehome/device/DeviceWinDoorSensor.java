package safehome.device;
public class DeviceWinDoorSensor extends DeviceSensorTester implements interfaceSensor
{
	private boolean opened;				// the window/door is open or closed
	private boolean enabled;
	
	
	public DeviceWinDoorSensor()
	{
		if(safeHomeSensorTest == null)
		{
			safeHomeSensorTest = new SafeHomeSensorTest();
			safeHomeSensorTest.setVisible(true);
		}		
		
		sensorID = ++newIdSequence_WinDoorSensor;
		opened = false;
		enabled = false;
		
		next = head_WinDoorSensor;
		head_WinDoorSensor = this;
		
		safeHomeSensorTest.rangeSensorID_WinDoorSensor.setText("1 ~ "+newIdSequence_WinDoorSensor);
	}
	
	// SensorTester use these functions
	public void intrude() { opened = true; }
	public void release() { opened = false; }
	
	// from interfaceSensor
	public int getID() { return sensorID; }
	public boolean read() { if(enabled) return opened; else return false; }
	public void enable() { enabled = true; }
	public void disable() { enabled = false; }
	public boolean test() { return enabled; }
}
