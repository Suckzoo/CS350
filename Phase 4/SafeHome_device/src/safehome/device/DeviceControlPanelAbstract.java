package safehome.device;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

abstract public class DeviceControlPanelAbstract extends JFrame implements ActionListener 
{
	private JTextField displayNumber;
	private JTextField displayAway;
	private JTextField displayStay;
	private JTextField displayNotReady;
	private JTextArea displayText;
	
	private JButton ledPower;
	private JButton ledArmed;
	
	private String shortMessage1;
	private String shortMessage2;

	public DeviceControlPanelAbstract()	// constructor
	{	
		super("Control Panel");
		setSize(505,300);
		getContentPane().setLayout(null);
		setResizable(false);

		/*addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				}
		);*/
		
		int xStart = 15;
		int yStart = 15;
		int xW1 = 100;
		int xW2 = 40;
		int xW3 = 80;
		int yH1 = 90;
		int yH2 = 70;
		
		// adding "Security Zone" message
		JTextField displayDummy = new JTextField();
		displayDummy.setEditable(false);
		displayDummy.setHorizontalAlignment(JTextField.CENTER);
		displayDummy.setBackground(Color.WHITE);
		displayDummy.setForeground(Color.BLACK);
		displayDummy.setText("Security Zone");
		
		JPanel displayPanel = new JPanel(new GridLayout(1, 1));
		displayPanel.add(displayDummy);
		displayPanel.setBounds(xStart, yStart, xW1, yH1);
		getContentPane().add(displayPanel);
		
		// adding the securty zone Number
		displayNumber = new JTextField();
		displayNumber.setEditable(false);
		displayNumber.setHorizontalAlignment(JTextField.CENTER);
		displayNumber.setBackground(Color.WHITE);
		displayNumber.setForeground(Color.BLACK);
		displayNumber.setText("1");
		
		displayPanel = new JPanel(new GridLayout(1, 1));
		displayPanel.add(displayNumber);
		displayPanel.setBounds(xStart + xW1, yStart, xW2, yH1);
		getContentPane().add(displayPanel);
		
		// adding "away", "stay", "not ready"
		displayAway = new JTextField();
		displayAway.setEditable(false);
		displayAway.setHorizontalAlignment(JTextField.CENTER);
		displayAway.setBackground(Color.WHITE);
		displayAway.setForeground(Color.LIGHT_GRAY);
		displayAway.setText("away");
				
		displayStay = new JTextField();
		displayStay.setEditable(false);
		displayStay.setHorizontalAlignment(JTextField.CENTER);
		displayStay.setBackground(Color.WHITE);
		displayStay.setForeground(Color.LIGHT_GRAY);
		displayStay.setText("stay");		
		
		displayNotReady = new JTextField();
		displayNotReady.setEditable(false);
		displayNotReady.setHorizontalAlignment(JTextField.CENTER);
		displayNotReady.setBackground(Color.WHITE);
		displayNotReady.setForeground(Color.LIGHT_GRAY);
		displayNotReady.setText("not ready");
		
		displayPanel = new JPanel(new GridLayout(3, 1));
		displayPanel.add(displayAway);
		displayPanel.add(displayStay);
		displayPanel.add(displayNotReady);
		displayPanel.setBounds(xStart + xW1 + xW2, yStart, xW3, yH1);
		getContentPane().add(displayPanel);
		
		// adding the Short Message
		displayText = new JTextArea();
		displayText.setEditable(false);
		//displayText.setAlignmentY(JTextArea.BOTTOM_ALIGNMENT);
		displayText.setBackground(Color.WHITE);
		displayText.setForeground(Color.BLACK);
		displayText.setText("\n"+shortMessage1+"\n"+shortMessage2);
		
		displayPanel = new JPanel(new GridLayout(1, 1));
		displayPanel.add(displayText);
		displayPanel.setBounds(xStart, yStart + yH1, xW1 + xW2 + xW3, yH2);
		getContentPane().add(displayPanel);

		// create each button
		JButton number1 = new JButton("1");
		number1.addActionListener(this);
		number1.setBackground(Color.WHITE);
		number1.setForeground(Color.BLACK);

		JButton number2 = new JButton("2");
		number2.addActionListener(this);
		number2.setBackground(Color.WHITE);
		number2.setForeground(Color.BLACK);

		JButton number3 = new JButton("3");
		number3.addActionListener(this);
		number3.setBackground(Color.WHITE);
		number3.setForeground(Color.BLACK);

		JButton number4 = new JButton("4");
		number4.addActionListener(this);
		number4.setBackground(Color.WHITE);
		number4.setForeground(Color.BLACK);

		JButton number5 = new JButton("5");
		number5.addActionListener(this);
		number5.setBackground(Color.WHITE);
		number5.setForeground(Color.BLACK);

		JButton number6 = new JButton("6");
		number6.addActionListener(this);
		number6.setBackground(Color.WHITE);
		number6.setForeground(Color.BLACK);

		JButton number7 = new JButton("7");
		number7.addActionListener(this);
		number7.setBackground(Color.WHITE);
		number7.setForeground(Color.BLACK);

		JButton number8 = new JButton("8");
		number8.addActionListener(this);
		number8.setBackground(Color.WHITE);
		number8.setForeground(Color.BLACK);

		JButton number9 = new JButton("9");
		number9.addActionListener(this);
		number9.setBackground(Color.WHITE);
		number9.setForeground(Color.BLACK);

		JButton number0 = new JButton("0");
		number0.addActionListener(this);
		number0.setBackground(Color.WHITE);
		number0.setForeground(Color.BLACK);

		JButton numberStar = new JButton("*");
		numberStar.addActionListener(this);
		numberStar.setBackground(Color.WHITE);
		numberStar.setForeground(Color.BLACK);

		JButton numberSharp = new JButton("#");
		numberSharp.addActionListener(this);
		numberSharp.setBackground(Color.WHITE);
		numberSharp.setForeground(Color.BLACK);

		// attach buttons to one panel
		JPanel buttonPanel = new JPanel(new GridLayout(9, 5));
		buttonPanel.add(new JLabel("     on"));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("    off"));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("  reset"));
		buttonPanel.add(number1);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number2);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number3);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number4);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number5);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number6);
		buttonPanel.add(new JLabel("  away"));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("   stay"));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("  code"));
		buttonPanel.add(number7);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number8);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number9);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(numberStar);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(number0);
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(numberSharp);
		buttonPanel.add(new JLabel("(panic)"));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel(""));
		buttonPanel.add(new JLabel("(panic)"));
	
		buttonPanel.setBounds(280, 0, 205, 250);
		getContentPane().add(buttonPanel);
		
		// adding led, power and armed
		ledPower = new JButton();
		ledPower.setEnabled(false);//ledPower.addActionListener(this);
		ledPower.setBackground(Color.LIGHT_GRAY);
		
		ledArmed = new JButton();
		ledArmed.setEnabled(false);
		ledArmed.setBackground(Color.LIGHT_GRAY);
		
		JPanel ledPanel = new JPanel(new GridLayout(2, 3));
		ledPanel.add(new Label("armed"));
		ledPanel.add(new Label(""));
		ledPanel.add(new Label("power"));
		ledPanel.add(ledArmed);
		ledPanel.add(new Label(""));
		ledPanel.add(ledPower);
		
		ledPanel.setBounds(100, 185, 120, 60);
		getContentPane().add(ledPanel);
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if((event.getActionCommand().equals("1")))
		{
			button1();
		}
		else if((event.getActionCommand().equals("2")))
		{
			button2();
		}
		else if((event.getActionCommand().equals("3")))
		{
			button3();
		}
		else if((event.getActionCommand().equals("4")))
		{
			button4();
		}
		else if((event.getActionCommand().equals("5")))
		{
			button5();
		}
		else if((event.getActionCommand().equals("6")))
		{
			button6();
		}
		else if((event.getActionCommand().equals("7")))
		{
			button7();
		}
		else if((event.getActionCommand().equals("8")))
		{
			button8();
		}
		else if((event.getActionCommand().equals("9")))
		{
			button9();
		}
		else if((event.getActionCommand().equals("*")))
		{
			buttonStar();
		}
		else if((event.getActionCommand().equals("0")))
		{
			button0();
		}
		else if((event.getActionCommand().equals("#")))
		{
			buttonSharp();
		}
	} 
	
	public void setSecurityZoneNumber(int num)
	{
		displayNumber.setText("" + num);
	}

	public void setDisplayAway(boolean on)
	{
		if(on) displayAway.setForeground(Color.BLACK);
		else displayAway.setForeground(Color.LIGHT_GRAY);
	}

	public void setDisplayStay(boolean on)
	{
		if(on) displayStay.setForeground(Color.BLACK);
		else displayStay.setForeground(Color.LIGHT_GRAY);
	}

	public void setDisplayNotReady(boolean on)
	{
		if(on) displayNotReady.setForeground(Color.BLACK);
		else displayNotReady.setForeground(Color.LIGHT_GRAY);
	} 
	
	public void setDisplayShortMessage1(String message)
	{
		shortMessage1 = message;
		displayText.setText("\n"+shortMessage1+"\n"+shortMessage2);
	}
	
	public void setDisplayShortMessage2(String message)
	{
		shortMessage2 = message;
		displayText.setText("\n"+shortMessage1+"\n"+shortMessage2);
	}
	
	public void setArmedLED(boolean on)
	{
		if(on) ledArmed.setBackground(Color.RED);
		else ledArmed.setBackground(Color.LIGHT_GRAY);
	}
	
	public void setPoweredLED(boolean on)
	{
		if(on) ledPower.setBackground(Color.GREEN);
		else ledPower.setBackground(Color.LIGHT_GRAY);
	}
	
	public void showSensorTester()
	{
		if(DeviceSensorTester.safeHomeSensorTest == null)
			DeviceSensorTester.safeHomeSensorTest = new SafeHomeSensorTest();
			
		DeviceSensorTester.safeHomeSensorTest.setVisible(true);
	}
	
	abstract public void button1();
	abstract public void button2();
	abstract public void button3();
	abstract public void button4();
	abstract public void button5();
	abstract public void button6();
	abstract public void button7();
	abstract public void button8();
	abstract public void button9();
	abstract public void buttonStar();
	abstract public void button0();
	abstract public void buttonSharp();
}

