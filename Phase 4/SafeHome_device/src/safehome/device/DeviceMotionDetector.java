package safehome.device;
public class DeviceMotionDetector extends DeviceSensorTester implements interfaceSensor 
{
	private boolean detected;				// the window/door is open or closed
	private boolean enabled;
	
	public DeviceMotionDetector()
	{
		if(safeHomeSensorTest == null)
		{
			safeHomeSensorTest = new SafeHomeSensorTest();
			safeHomeSensorTest.setVisible(true);
		}		
		
		sensorID = ++newIdSequence_MotionDetector;
		detected = false;
		enabled = false;
		
		next = head_MotionDetector;
		head_MotionDetector = this;
		
		safeHomeSensorTest.rangeSensorID_MotionDetector.setText("1 ~ "+newIdSequence_MotionDetector);
	}
	
	// SensorTester use these functions
	public void intrude() { detected = true; }
	public void release() { detected = false; }
	
	// from interfaceSensor
	public int getID() { return sensorID; }
	public boolean read() { if(enabled) return detected; else return false; }
	public void enable() { enabled = true; }
	public void disable() { enabled = false; }
	public boolean test() { return enabled; }
}
