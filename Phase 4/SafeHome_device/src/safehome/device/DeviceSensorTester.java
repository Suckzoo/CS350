package safehome.device;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public abstract class DeviceSensorTester 
{
	static SafeHomeSensorTest safeHomeSensorTest;	// for a sensor test dialog
	static DeviceSensorTester head_WinDoorSensor;		// a head for linked list of whole sensors
	static DeviceSensorTester head_MotionDetector;		// a head for linked list of whole sensors
	DeviceSensorTester next;							// a link to next node of linked list
	
	abstract public void intrude();
	abstract public void release();
	
	int sensorID;				// sensor's ID : given sequentially when new operation.
	
	static int newIdSequence_WinDoorSensor = 0;
	static int newIdSequence_MotionDetector = 0;
}


class SafeHomeSensorTest extends JFrame implements ActionListener
{
	private JTextField inputSensorID_WinDoorSensor;
	private JTextField inputSensorID_MotionDetector;
	
	JTextField rangeSensorID_WinDoorSensor;
	JTextField rangeSensorID_MotionDetector;
	
	public SafeHomeSensorTest() // constructor
	{
		super("Sensor Test");
		setSize(355,170);
		getContentPane().setLayout(null);
		setResizable(false);
		getContentPane().setBackground(Color.ORANGE);

		/*addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						//System.exit(0);
					}
				}
		);*/
		
		rangeSensorID_WinDoorSensor = new JTextField();
		rangeSensorID_WinDoorSensor.setEditable(false);
		rangeSensorID_WinDoorSensor.setHorizontalAlignment(JTextField.CENTER);
		rangeSensorID_WinDoorSensor.setBackground(Color.WHITE);
		rangeSensorID_WinDoorSensor.setForeground(Color.BLACK);
		rangeSensorID_WinDoorSensor.setText("N/A");
		
		inputSensorID_WinDoorSensor = new JTextField();
		inputSensorID_WinDoorSensor.setEditable(true);
		inputSensorID_WinDoorSensor.setHorizontalAlignment(JTextField.CENTER);
		inputSensorID_WinDoorSensor.setBackground(Color.WHITE);
		inputSensorID_WinDoorSensor.setForeground(Color.BLACK);
		inputSensorID_WinDoorSensor.setText("");
		
		JButton buttonOpen = new JButton("open");
		buttonOpen.addActionListener(this);
		JButton buttonClose = new JButton("close");
		buttonClose.addActionListener(this);
		
		JPanel displayPanel = new JPanel(new GridLayout(4, 2));
		displayPanel.add(new Label("      WinDoor"));
		displayPanel.add(new Label(" Sensors"));
		displayPanel.add(new Label("   ID range"));
		displayPanel.add(new Label("   input ID"));
		displayPanel.add(rangeSensorID_WinDoorSensor);
		displayPanel.add(inputSensorID_WinDoorSensor);
		displayPanel.add(buttonOpen);
		displayPanel.add(buttonClose);
		displayPanel.setBounds(15, 15, 150, 100);
		getContentPane().add(displayPanel);
		
		rangeSensorID_MotionDetector = new JTextField();
		rangeSensorID_MotionDetector.setEditable(false);
		rangeSensorID_MotionDetector.setHorizontalAlignment(JTextField.CENTER);
		rangeSensorID_MotionDetector.setBackground(Color.WHITE);
		rangeSensorID_MotionDetector.setForeground(Color.BLACK);
		rangeSensorID_MotionDetector.setText("N/A");
		
		inputSensorID_MotionDetector = new JTextField();
		inputSensorID_MotionDetector.setEditable(true);
		inputSensorID_MotionDetector.setHorizontalAlignment(JTextField.CENTER);
		inputSensorID_MotionDetector.setBackground(Color.WHITE);
		inputSensorID_MotionDetector.setForeground(Color.BLACK);
		inputSensorID_MotionDetector.setText("");
		
		buttonOpen = new JButton("detect");
		buttonOpen.addActionListener(this);
		buttonClose = new JButton("clear");
		buttonClose.addActionListener(this);
		
		displayPanel = new JPanel(new GridLayout(4, 2));
		displayPanel.add(new Label("          Motion"));
		displayPanel.add(new Label("Sensors"));
		displayPanel.add(new Label("   ID range"));
		displayPanel.add(new Label("   input ID"));
		displayPanel.add(rangeSensorID_MotionDetector);
		displayPanel.add(inputSensorID_MotionDetector);
		displayPanel.add(buttonOpen);
		displayPanel.add(buttonClose);
		displayPanel.setBounds(180, 15, 150, 100);
		getContentPane().add(displayPanel);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("open")
				|| event.getActionCommand().equals("close"))
		{
			// get ID from the input field
			String inputNumber = inputSensorID_WinDoorSensor.getText();
			if(inputNumber.equals(""))
			{
				JOptionPane.showMessageDialog(this, "input the WinDoorSensor's ID");
				return;
			}
			
			for(int i=0; i<inputNumber.length(); i++)
			{
				if(!Character.isDigit(inputNumber.charAt(i)))
				{
					JOptionPane.showMessageDialog(this, "only digit allowed");
					return;
				}
			}
			
			int selectedID = Integer.parseInt(inputNumber);
			DeviceSensorTester scan = DeviceSensorTester.head_WinDoorSensor;
			
			// find correspond sensor ID
			while(scan != null && scan.sensorID != selectedID)
			{
				scan = scan.next;
			}
			
			if(scan == null)
			{
				JOptionPane.showMessageDialog(this, "ID "+selectedID+" not exist");
			}
			else
			{
				if(event.getActionCommand().equals("open"))
				{
					scan.intrude();
				}
				else //if(event.getActionCommand().equals("close"))
				{
					scan.release();
				}
			}
		}
		else// if(event.getActionCommand().equals("detect")
			//	|| event.getActionCommand().equals("clear"))
		{
			// get ID from the input field
			String inputNumber = inputSensorID_MotionDetector.getText();
			if(inputNumber.equals(""))
			{
				JOptionPane.showMessageDialog(this, "input the MotionDetector's ID");
				return;
			}
			
			for(int i=0; i<inputNumber.length(); i++)
			{
				if(!Character.isDigit(inputNumber.charAt(i)))
				{
					JOptionPane.showMessageDialog(this, "only digit allowed");
					return;
				}
			}
			
			int selectedID = Integer.parseInt(inputNumber);
			DeviceSensorTester scan = DeviceSensorTester.head_MotionDetector;
			
			// find correspond sensor ID
			while(scan != null && scan.sensorID != selectedID)
			{
				scan = scan.next;
			}
			
			if(scan == null)
			{
				JOptionPane.showMessageDialog(this, "ID "+selectedID+" not exist");
			}
			else
			{
				if(event.getActionCommand().equals("detect"))
				{
					scan.intrude();
				}
				else //if(event.getActionCommand().equals("clear"))
				{
					scan.release();
				}
			}
		}
	}
	
	static final long serialVersionUID = 0;
}
