package safehome;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import safehome.device.DeviceCamera;
import safehome.device.DeviceControlPanelAbstract;
import safehome.device.DeviceMotionDetector;
import safehome.device.DeviceWinDoorSensor;


/*
 * CLASS NAME : MainDemo 
 * DESCRIPTION
 * 		A main dialog of whole demo program
 * 		It shows three buttons to enter three test cases 
 * 		 - Control panel test
 * 		 - Sensor test
 * 		 - Camera test
 * PRECONDITION : The program is executed initially
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click on the button
 * EXAMPLE : click the control panel test button to pop up control panel test dialog
 * POSTCONDITION : any one dialog of the test cases are pop up
 * NOTES : Click any button to proceed exact test case
 */  


/**
 * This class shows a main dialog of a whole demo program.
 * When a user runs <code>MainDemo</code> class, <code>MainDemo</code> shows three buttons, 
 * Control panel test/Sensor test/Camera test. A user can test each device by 
 * selecting a button.
 * @author cs350 TA
 *
 */
public class MainDemo extends JFrame implements ActionListener
{
	// instance of three test cases


	public static ControlPanel controlPanel;
	private UserInterface userInterface;
	private SensorTest sensorTest;
	private LoginInterface loginInterface;
	public static SafehomeConsole safehomeConsole;
	public static User user;
	static MainDemo safeHome;

	

	/**
	 * Runs a simple demo program. 
	 * @param args command line arguments are ignored.
	 */
	public static void main(String[] args)
	{
		safeHome = new MainDemo();
		safeHome.setVisible(true);
	}
	
	/*
	 * NAME : MainDemo (constructor) 
	 * DESCRIPTION
	 * 		make layout of main page of this demo program
	 * 		place three buttons - "Control Panel", "sensor", "Camera"
	 * PRECONDITION : The main instantiates MainDemo class
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The layout of this dialog is made
	 * NOTES : none
	 */  
	/**
	 * Constructs a <code>MainDemo</code> frame with three buttons,
	 * "Control Panel", "Sensor", "Camera". It is initially invisible.
	 * <p>
	 * Pre-condition: None 
	 * <p>
	 * Post-condition: The frame of <code>MainDemo</code> dialog is constructed.   
	 */
	public MainDemo() 
	{
		super("Hardware Device Demo");
		controlPanel = new ControlPanel();
		sensorTest = new SensorTest();
		loginInterface = new LoginInterface();
		user = new User();
		userInterface = new UserInterface();
		safehomeConsole = new SafehomeConsole(sensorTest);
		setSize(400, 215);
		getContentPane().setLayout(null);
		setResizable(false);

		// Terminate the program when the MainDemo dialog is closed
		addWindowListener
		(
				new WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						System.exit(0);
					}
				}
		);
		
		// make buttons
		JButton ControlPanel = new JButton("Control Panel");
		ControlPanel.addActionListener(this);
		
		JButton SensorTest = new JButton("Sensor");
		SensorTest.addActionListener(this);
		
		//JButton Camera = new JButton("Camera");
		//Camera.addActionListener(this);

		JButton UI = new JButton("User Interface");
		UI.addActionListener(this);

		// attach buttons to dialog
		JPanel displayPanel = new JPanel(new GridLayout(3, 1));
		displayPanel.add(ControlPanel);
		displayPanel.add(SensorTest);
		displayPanel.add(UI);
		//displayPanel.add(Camera);
		displayPanel.setBounds(15, 15, 360, 150);
		getContentPane().add(displayPanel);
	}

	/*
	 * NAME : actionPerformed
	 * DESCRIPTION
	 * 		This function is the action listener(call back function) of three buttons
	 * 		Each button pops up its test dialog
	 * PRECONDITION : User click on any button
	 * PARAMETER : event - ActionEvent that carrying what button was pressed
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE
	 * 		If Control Panel button is clicked by user, 
	 * 		the parameter event.getActionCommand() equals "Control Panel"
	 * POSTCONDITION : any one dialog of the test cases are pop up
	 * NOTES : none
	 */  
	/** 
	 * Invoked when an action occurs. When the "Control Panel" button is pressed, 
	 * the demo program shows control panel dialog. When the "Sensor" or "Camera" button is pressed, 
	 * the demo program shows sensor test dialog or camera test dialog accordingly. Otherwise, it does 
	 * no operation. 
	 * <p>
	 * Pre-condition: A user clicks on one of three buttons "Control Panel", "Sensor" and "Camera"
	 * <p>
	 * Post-condition: The corresponding dialog of the test cases is pop up.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		if((event.getActionCommand().equals("Control Panel")))
		{
			controlPanel.setVisible(true);
		}
			else if((event.getActionCommand().equals("Sensor"))) {
			sensorTest.setVisible(true);
		}
		else if((event.getActionCommand().equals("User Interface")))
		{
            loginInterface.requireLogin(user, userInterface);
			//if(loginInterface.isLoggedIn())
			//{
			//	userInterface.setVisible(true);
			//}
			//userInterface.setVisible(true);
		}
	}

	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */

	static final long serialVersionUID = 1876534;
}

class LoginInterface extends JFrame implements ActionListener
{
	JFrame mainFrame;
	JTextField usernameTxt;
	JPasswordField passwordTxt;
	User registeredUser;
	JFrame afterView;
	public LoginInterface()
	{
        mainFrame = new JFrame("Login");
        mainFrame.setSize(400, 200);
		JPanel mainPanel = new JPanel();

        usernameTxt = new JTextField(25);
        passwordTxt = new JPasswordField(25);
        JLabel usernameLbl = new JLabel("Username: ");
        JLabel passwordLbl = new JLabel("Password: ");
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        mainPanel.add(usernameLbl);
        mainPanel.add(usernameTxt);
        mainPanel.add(passwordLbl);
        mainPanel.add(passwordTxt);
        mainPanel.add(loginButton);

        mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	public void requireLogin(User user, JFrame frame)
	{
		if(frame.isVisible()) return;
        registeredUser = user;
        afterView = frame;
        mainFrame.setVisible(true);
	}
    public void actionPerformed(ActionEvent ev)
    {
		if(ev.getActionCommand().equals("Login")) {
			String id = usernameTxt.getText();
			String pw = String.copyValueOf(passwordTxt.getPassword());
			if (registeredUser.loginInterface(id, pw)) {
				afterView.setVisible(true);
				mainFrame.setVisible(false);
				usernameTxt.setText("");
				passwordTxt.setText("");
			} else {
				JOptionPane.showMessageDialog(null, "invalid ID/PW", "login failed", JOptionPane.PLAIN_MESSAGE);
			}
		}
    }
}





/*
 * CLASS NAME : ControlPanel
 * DESCRIPTION
 * 		A simple implementation of the abstract class
 * 		All implemented abstract methods has small functionality to show it is working well
 * 		All APIs that provided from super class is tested by buttons
 * PRECONDITION : From main dialog, "Control Panel" button was chosen
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click the numeric buttons
 * EXAMPLE : click the button 1 to test its functionality
 * POSTCONDITION : none
 * NOTES : If possible, click all buttons to test all functionality
 */
/**
 * This class is a simple implementation of the abstract
 * class <code>{@link DeviceControlPanelAbstract}</code>. All abstract methods are
 * implemented simply to show that each button is working well.
 * <p>
 * The control panel consists of three parts. It looks like this
 * <p>
 * <img src = "doc-files/initial.PNG">
 * <p>
 * LCD on the left-up shows the current status, which button is pressed,
 * the system is in stay or away mode, and the system is ready or not.
 * Two LEDs on the left-down indicate the system is armed or not and
 * the system is power-on or off. The keypad on the right-side has 12 buttons,
 * 0 ~ 9, *, #. The corresponding action occurs when a user presses each button.
 *
 *
 * @author cs350 TA
 * @see safehome.device.DeviceControlPanelAbstract
 */
class ControlPanel extends DeviceControlPanelAbstract
{
	/*
	 * NAME : ControlPanel (constructor)
	 * DESCRIPTION
	 * 		Initiate the short messages
	 * PRECONDITION
	 * 		The main instantiates MainDemo class and MainDemo instantiates this class
	 * 		in consecutively
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready
	 * NOTES : none
	 */
	/**
	 * Constructs a control panel frame.
	 *  <p>
	 * Pre-condition: None.
	 * <p>
	 * Post-condition: The control panel frame is ready. It is initially invisible.
	 * @see safehome.device.DeviceControlPanelAbstract#DeviceControlPanelAbstract().
	 */
	int pwLength;
	int[] password;
	enum STATUS{
		DO_NOTHING,
		POWER_ON,
		POWER_OFF,
		RESET,
		ARM,
		DISARM
	};
	STATUS currentStatus, nextStatus;
	public ControlPanel()
	{
		super();
		pwLength = -1;
		nextStatus = STATUS.DO_NOTHING;
		currentStatus = STATUS.POWER_OFF;
		password = new int[4];
		setDisplayShortMessage1("        Starting system");
		setDisplayShortMessage2("        please wait...");
	}

	void PowerOff()
	{
		currentStatus = STATUS.POWER_OFF;
		setDisplayAway(false);
		setDisplayStay(false);
		setDisplayNotReady(false);
		setArmedLED(false);
		setPoweredLED(false);
		setDisplayShortMessage1("    ");
		setDisplayShortMessage2("    ");
		pwLength = -1;
		nextStatus = STATUS.DO_NOTHING;
	}

	void PowerOn() {
		if(currentStatus != STATUS.POWER_OFF) return;
		currentStatus = STATUS.POWER_ON;
		setSecurityZoneNumber(1);
		setPoweredLED(true);
		setDisplayShortMessage1("    Safehome is ready");
		setDisplayShortMessage2(" ");
	}

	void Reset()
	{
		setDisplayAway(false);
		setDisplayStay(false);
		setDisplayNotReady(false);
		setArmedLED(false);
		setPoweredLED(true);
		setDisplayShortMessage1("    Safehome is ready");
		setDisplayShortMessage2(" ");
		pwLength = -1;
		nextStatus = STATUS.DO_NOTHING;
		MainDemo.safehomeConsole.disarmSystem();
		MainDemo.safehomeConsole.resolvePanic();
	}

	public void Arm()
	{
		pwLength = -1;
		nextStatus = STATUS.DO_NOTHING;
		if(!MainDemo.safehomeConsole.armSystem())
		{
			currentStatus = STATUS.POWER_ON;
			setDisplayNotReady(true);
			setDisplayStay(false);
			setDisplayAway(false);
			setArmedLED(false);
			setDisplayShortMessage1("   Not ready");
			setDisplayShortMessage2("   Check sensor status");
			return;
		}
		setDisplayShortMessage1("    Away mode activated");
		setDisplayShortMessage2("    Sensor/Detector armed");
		currentStatus = STATUS.ARM;
		setDisplayStay(false);
		setDisplayNotReady(false);
		setDisplayAway(true);
		setArmedLED(true);
		//if success:
		//else
		//not ready;
	}

	void Disarm()
	{
		pwLength = -1;
		nextStatus = STATUS.DO_NOTHING;
		setDisplayShortMessage1("    Stay mode activated");
		setDisplayShortMessage2("    Sensor/Detector disarmed");
		if(currentStatus == STATUS.DISARM) return;
		currentStatus = STATUS.DISARM;
		setDisplayAway(false);
		setDisplayStay(true);
		setDisplayNotReady(false);
		setArmedLED(false);
		MainDemo.safehomeConsole.disarmSystem();
		MainDemo.safehomeConsole.resolvePanic();
	}

	/*
	 * NAME : button1
	 * DESCRIPTION
	 * 		set display security zone number to 1
	 * 		turn on the power LED
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/**
	 * Specifies the action which occurs when button 1 is pressed.
	 * It sets security zone number to 1, turns on the power LED and changes the message to
	 * <p>
	 * Key 1 pressed<br>On Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 1, power LED is turned on and
	 * the message is changed to
	 * <p>
	 * Key 1 pressed<br>On Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button1()
	 */
	public void button1()
	{
		// example running (key is active)
		if(currentStatus == STATUS.POWER_OFF) {
			PowerOn();
		} else {
			if(pwLength != -1) {
                password[pwLength++] = 1;
                if(pwLength == 4)
                {
                    pwLength = -1;
                    String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
                    if(MainDemo.user.loginControlPanel(pw))
                    {
                        switch(nextStatus) {
                            case DO_NOTHING:
                                break;
                            case POWER_OFF:
                                PowerOff();
                                break;
							case RESET:
								Reset();
								break;
							case ARM:
								Arm();
								break;
							case DISARM:
								Disarm();
								break;
                        }
                    }
                    else
                    {
                        setDisplayShortMessage1("   Failed");
                        setDisplayShortMessage2("   Wrong password");
                    }
                }
            }
		}
		// To do
		//
	}

	/*
	 * NAME : button2
	 * DESCRIPTION
	 * 		set display security zone number to 2
	 * 		turn off all LED and toggle messages
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/**
	 * Specifies the action which occurs when button 2 is pressed.
	 * It sets security zone number to 2, turns off all the LEDs, clears
	 * status display(Away, Stay, Not Ready) and changes the message to
	 * <p>
	 * Key 2 pressed<br>Off Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 2, all the LEDs are turned off,
	 * the status display is clear and the message is changed to
	 * <p>
	 * Key 2 pressed<br>Off Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button2()
	 */
	public void button2()
	{
		// example running (key is active)
		if(currentStatus == STATUS.POWER_OFF) return;
		if(currentStatus == STATUS.ARM) return;
		if(pwLength == -1) {
			setDisplayShortMessage1("    Shutdown system");
			setDisplayShortMessage2("    Input password");
			pwLength = 0;
			nextStatus = STATUS.POWER_OFF;
		} else {
			password[pwLength++] = 2;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
		// To do
		//
	}

	/*
	 * NAME : button3
	 * DESCRIPTION
	 * 		set display security zone number to 3
	 * 		turn off the armed LED and toggle messages
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/**
	 * Specifies the action which occurs when button 3 is pressed.
	 * It sets security zone number to 3, turns off the 'armed' LED, clears
	 * status display(Away, Stay, Not Ready) and changes the message to
	 * <p>
	 * Key 3 pressed<br>Reset Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 3, the 'armed' LED is turned off,
	 * the status display is clear and the message is changed to
	 * <p>
	 * Key 3 pressed<br>Reset Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button3()
	 */
	public void button3()
	{
		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength == -1) {
			setDisplayShortMessage1("    Reset system");
			setDisplayShortMessage2("    Input password");
			pwLength = 0;
			nextStatus = STATUS.RESET;
		} else {
			password[pwLength++] = 3;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
		//
	}

	/*
	 * NAME : button4
	 * DESCRIPTION
	 * 		set display security zone number to 4
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button4()
	 */
	/**
	 * Specifies the action which occurs when button 4 is pressed.
	 * It sets security zone number to 4 and changes the message to
	 * <p>
	 * Key 4 pressed<br>
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 4 and
	 * the message is changed to
	 * <p>
	 * Key 4 pressed<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button4()
	 */
	public void button4()
	{
		// example running (key is active)
		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength != -1) {
			password[pwLength++] = 4;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : button5
	 * DESCRIPTION
	 * 		set display security zone number to 5
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button5()
	 */
	/**
	 * Specifies the action which occurs when button 5 is pressed.
	 * It sets security zone number to 5 and changes the message to
	 * <p>
	 * Key 5 pressed<br>
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 5 and
	 * the message is changed to
	 * <p>
	 * Key 5 pressed<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button5()
	 */
	public void button5()
	{
		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength != -1) {
			password[pwLength++] = 5;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : button6
	 * DESCRIPTION
	 * 		set display security zone number to 6
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button6()
	 */
	/**
	 * Specifies the action which occurs when button 6 is pressed.
	 * It sets security zone number to 6 and changes the message to
	 * <p>
	 * Key 6 pressed<br>
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 6 and
	 * the message is changed to
	 * <p>
	 * Key 6 pressed<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button6()
	 */
	public void button6()
	{
		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength != -1) {
			password[pwLength++] = 6;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : button7
	 * DESCRIPTION
	 * 		set display security zone number to 7
	 * 		turn on armed LED and away
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button7()
	 */
	/**
	 * Specifies the action which occurs when button 7 is pressed.
	 * It sets security zone number to 7 , turns on the 'Away' LED, shows
	 * 'Away' status on the display and changes the message to
	 * <p>
	 * Key 7 pressed<br>
	 * Away Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 7, 'armed' LED is turned on
	 * 'Away' status is shown on the display and the message is changed to
	 * <p>
	 * Key 7 pressed<br>
	 * Away Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button7()
	 */
	public void button7()
	{

		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength == -1) {
			setDisplayShortMessage1("    Away mode: Arming system");
			setDisplayShortMessage2("    Input password");
			pwLength = 0;
			nextStatus = STATUS.ARM;
		} else {
			password[pwLength++] = 7;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : button8
	 * DESCRIPTION
	 * 		set display security zone number to 8
	 * 		turn on armed LED and stay
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button8()
	 */
	/**
	 * Specifies the action which occurs when button 8 is pressed.
	 * It sets security zone number to 8 , turns on the 'Stay' LED, shows
	 * 'Stay' status on the display and changes the message to
	 * <p>
	 * Key 8 pressed<br>
	 * Stay Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 8, 'armed' LED is turned on
	 * 'Stay' status is shown on the display and the message is changed to
	 * <p>
	 * Key 8 pressed<br>
	 * Stay Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button8()
	 */
	public void button8()
	{
		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength == -1) {
			setDisplayShortMessage1("    Stay mode: Disarming system");
			setDisplayShortMessage2("    Input password");
			pwLength = 0;
			nextStatus = STATUS.DISARM;
		} else {
			password[pwLength++] = 8;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : button9
	 * DESCRIPTION
	 * 		set display security zone number to 9
	 * 		turn on not ready
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button9()
	 */
	/**
	 * Specifies the action which occurs when button 9 is pressed.
	 * It sets security zone number to 9, shows 'Stay' status on
	 * the display and changes the message to
	 * <p>
	 * Key 9 pressed<br>
	 * Code Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 9,
	 * 'Not Ready' status is shown on the display and the message is changed to
	 * <p>
	 * Key 9 pressed<br>
	 * Code Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button9()
	 */
	public void button9()
	{
		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength != -1) {
			password[pwLength++] = 9;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : buttonStar
	 * DESCRIPTION
	 * 		set display security zone number to 10
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#buttonStar()
	 */
	/**
	 * Specifies the action which occurs when button * is pressed.
	 * It sets security zone number to 10 and changes the message to
	 * <p>
	 * Key * pressed<br>
	 * Panic Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 10
	 * and the message is changed to
	 * <p>
	 * Key * pressed<br>
	 * Panic Button<p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#buttonStar()
	 */
	public void buttonStar()
	{
		// example running (key is active)
		setSecurityZoneNumber(10);
		setDisplayShortMessage1("    key * pressed");
		setDisplayShortMessage2("    Panic Button");
		// To d
		currentStatus = STATUS.ARM;
		setDisplayStay(false);
		setDisplayNotReady(false);
		setDisplayAway(true);
		setArmedLED(true);
		pwLength = -1;
		MainDemo.safehomeConsole.manualPanic();
		//
	}

	/*
	 * NAME : button0
	 * DESCRIPTION
	 * 		set display security zone number to 11
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#button0()
	 */
	/**
	 * Specifies the action which occurs when button 0 is pressed.
	 * It sets security zone number to 11 and changes the message to
	 * <p>
	 * Key 0 pressed<br>
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 11
	 * and the message is changed to
	 * <p>
	 * Key 0 pressed<br>
	 * <p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#button0()
	 */
	public void button0()
	{

		if(currentStatus == STATUS.POWER_OFF) return;
		if(pwLength != -1) {
			password[pwLength++] = 0;
			if(pwLength == 4)
			{
				pwLength = -1;
				String pw = Integer.toString(password[0]) + Integer.toString(password[1]) + Integer.toString(password[2]) + Integer.toString(password[3]);
				if(MainDemo.user.loginControlPanel(pw))
				{
					switch(nextStatus) {
						case DO_NOTHING:
							break;
						case POWER_OFF:
							PowerOff();
							break;
						case RESET:
							Reset();
							break;
						case ARM:
							Arm();
							break;
						case DISARM:
							Disarm();
							break;
					}
				}
				else
				{
					setDisplayShortMessage1("   Failed");
					setDisplayShortMessage2("   Wrong password");
				}
				nextStatus = STATUS.DO_NOTHING;
			}
		}
	}

	/*
	 * NAME : buttonSharp
	 * DESCRIPTION
	 * 		set display security zone number to 12
	 * 		display short message of what key is pressed
	 * PRECONDITION : the control panel is ready and poped up to user
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The control panel is ready and slightly changed in view
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see safehome.device.DeviceControlPanelAbstract#buttonSharp()
	 */
	/**
	 * Specifies the action which occurs when button # is pressed.
	 * It sets security zone number to 12 and changes the message to
	 * <p>
	 * Key # pressed<br>
	 * Panic Button
	 * <p>
	 * Pre-condition: The control panel is ready and visible to the user.
	 * <p>
	 * Post-condition: Security zone number is changed to 12
	 * and the message is changed to
	 * <p>
	 * Key # pressed<br>
	 * Panic Button
	 * <p>
	 *
	 * @see safehome.device.DeviceControlPanelAbstract#buttonSharp()
	 */
	public void buttonSharp()
	{
		// example running (key is active)
		setSecurityZoneNumber(12);
		setDisplayShortMessage1("    key # pressed");
		setDisplayShortMessage2("    Panic Button");
		currentStatus = STATUS.ARM;
		setDisplayStay(false);
		setDisplayNotReady(false);
		setDisplayAway(true);
		setArmedLED(true);
		MainDemo.safehomeConsole.manualPanic();
		pwLength = -1;
	}

	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 1943342;
}



/*
 * CLASS NAME : SensorTest 
 * DESCRIPTION
 * 		A dialog to test a set of sensors interactively 
 * 		This class maintains 5 winDoorSensors and 5 motionDetectors
 * 		The dialog shows status of sensors and provide enable/disable buttons for sensors
 * 		If the sensor state is changed asynchronously, 
 * 		the refresh button is needed to update sensor status table
 * PRECONDITION : From main dialog, "Sensor" button was chosen
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click a button among "refresh", "Enable All", "Disable All" buttons
 * EXAMPLE : click refresh button to update sensor status table
 * POSTCONDITION : none
 * NOTES : Need Sensor Test dialog to change sensor's read() value
 */ 
/**
 * <code>SensorTest</code> class is a simple dialog to test a set of sensors interactively. 
 * This class maintains 5 door sensors and 5 motion detectors. The dialog shows status of sensors and provides
 * enable/disable buttons for sensors. If the sensor state is changed asynchronously, 
 * the new state is not updated before the refresh button is pressed.
 * <p>
 * The dialog of <code>SensorTest</code> looks like this:<p>
 * <img src="doc-files/sensors.PNG">
 * <p>
 * 
 * @author cs350 TA
 *
 */
class SensorTest extends JFrame implements ActionListener
{	
	DeviceWinDoorSensor[] winDoorSensors;
	JTextField[] testW;	// used to display result of DeviceWinDoorSensor.test()
	JTextField[] readW;	// used to display result of DeviceWinDoorSensor.read()
		
	DeviceMotionDetector[] motionDetectors;
	JTextField[] testM;	// used to display result of DeviceMotionDetector.test()
	JTextField[] readM;	// used to display result of DeviceMotionDetector.read()
	
	static final int MAX_SENSOR = 5;	// define max number of sensors
	
	/*
	 * NAME : SensorTest (constructor)
	 * DESCRIPTION
	 * 		Initiate sensors instances
	 * 		and make layout of sensor status table and buttons
	 * PRECONDITION
	 * 		The main instantiates MainDemo class and MainDemo instantiates this class
	 * 		in consecutively
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The layout of this dialog is made
	 * NOTES : none
	 */ 
	/**
	 * Constructs the sensor status dialog. Constructor initiates sensor 
	 * instances and make layout of sensor status table and buttons. 
	 * 
	 * <p>
	 * Pre-condition: None
	 * <p>
	 * Post-condition: The sensor status frame is ready. It is initially invisible.
	 * <p>
	 */
	public SensorTest()
	{
		super("Sensor Status");
		setSize(455, 570);
		getContentPane().setLayout(null);
		setResizable(false);
		
		// instanciate sensor and text field for status
		winDoorSensors = new DeviceWinDoorSensor[MAX_SENSOR];
		testW = new JTextField[MAX_SENSOR];
		readW = new JTextField[MAX_SENSOR];
		
		motionDetectors = new DeviceMotionDetector[MAX_SENSOR];
		testM = new JTextField[MAX_SENSOR];
		readM = new JTextField[MAX_SENSOR];
		
		// Loop is needed to instantiate array
		for(int i=0; i<MAX_SENSOR; i++)
		{
			winDoorSensors[i] = new DeviceWinDoorSensor();
			testW[i] = new JTextField();
			readW[i] = new JTextField();
			
			testW[i].setEditable(false);
			readW[i].setEditable(false);
			
			motionDetectors[i] = new DeviceMotionDetector();
			testM[i] = new JTextField();
			readM[i] = new JTextField();
			
			testM[i].setEditable(false);
			readM[i].setEditable(false);
		}
		
		// make buttons
		JButton buttonrefresh = new JButton("refresh");
		buttonrefresh.addActionListener(this);
		JButton buttonEnable = new JButton("Enable All");
		buttonEnable.addActionListener(this);
		JButton buttonDisable = new JButton("Disable All");
		buttonDisable.addActionListener(this);
		
		// attach text field to dialog
		JPanel displayPanel = new JPanel(new GridLayout(13, 3));
		displayPanel.add(new Label("Type and ID"));
		displayPanel.add(new Label("Test"));
		displayPanel.add(new Label("Read"));
		
		// attach test fields of array to dialog
		for(int i=0; i<MAX_SENSOR; i++)
		{
			displayPanel.add(new Label("WinDoor "+winDoorSensors[i].getID()));
			displayPanel.add(testW[i]);
			displayPanel.add(readW[i]);
		}
		for(int i=0; i<MAX_SENSOR; i++)
		{
			displayPanel.add(new Label("Motion "+motionDetectors[i].getID()));
			displayPanel.add(testM[i]);
			displayPanel.add(readM[i]);
		}
		
		displayPanel.add(new Label(""));
		displayPanel.add(new Label(""));
		displayPanel.add(new Label(""));
		
		// attach buttons to dialog
		displayPanel.add(buttonrefresh);
		displayPanel.add(buttonEnable);
		displayPanel.add(buttonDisable);
		
		displayPanel.setBounds(25, 25, 400, 400);
		getContentPane().add(displayPanel);
		
		// initially one time update is necessary to fill up sensor status table
		refresh();
	}
	
	/*
	 * NAME : actionPerformed
	 * DESCRIPTION
	 * 		This function is the action listener(call back function) of three buttons
	 * 		refresh - simply update status information
	 * 		Enable All - call enable() of all sensors
	 * 		Disable All - call disable() of all sensors
	 * PRECONDITION : User click on any button
	 * PARAMETER : event - ActionEvent that carrying what button was pressed
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE
	 * 		If refresh button is clicked by user, 
	 * 		the information in sensor status table will be updated
	 * POSTCONDITION
	 * 		Maybe sensor status be changed and
	 * 		sensor status table is updated
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/** 
	 * Invoked when an action occurs. When the "Refresh" button is pressed, 
	 * All the states of sensors are updated. When the "Enable All" button 
	 * or "Disable All" button is pressed, All the sensors are enabled or disabled.  
	 * After all the actions, the status is refreshed 
	 * <p>
	 * Pre-condition: A user clicks on one of three buttons "Refresh", "Enable All" and "Disable All"
	 * <p>
	 * Post-condition: The sensor status table is updated. Sensors are enabled or disabled according 
	 * to the button pressed.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("refresh"))
		{
		}
		else if(event.getActionCommand().equals("Enable All"))
		{
			for(int i=0; i<MAX_SENSOR; i++)
			{
				MainDemo.safehomeConsole.armMotionDetector(i);
				MainDemo.safehomeConsole.armWindoorSensor(i);
			}
		}
		else if(event.getActionCommand().equals("Disable All"))
		{
			for(int i=0; i<MAX_SENSOR; i++)
			{
				MainDemo.safehomeConsole.disarmMotionDetector(i);
				MainDemo.safehomeConsole.disarmWindoorSensor(i);
			}
		}

		refresh();
	}
	
	/*
	 * NAME : refresh
	 * DESCRIPTION
	 * 		This function updates the sensors status information
	 * 		call and get result from all of read() and test() methods of sensors
	 * 		display enable in red, disable in black
	 * PRECONDITION : this dialog and sensors are initiated
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : sensor status table is updated
	 * NOTES : none
	 */
	/**
	 * Updates the sensors status information. <code>refresh()</code> calls <code>read()</code>
	 * and <code>test()</code> methods from which all sensors and get results. If a sensor is enabled 
	 * the corresponding table entry displays 'enabled' with red color. If a sensor detects an opened window
	 * or motion, the corresponding table entry displays 'open' or 'detect' with red color.
	 * <p>
	 * Pre-condition: Sensor Status dialog and sensors are initiated
	 * <p>
	 * Post-condition: The sensor status table is updated 
	 */
	void refresh()
	{
		// loop all sensors with calling read(), test() method of sensors
		for(int i=0; i<MAX_SENSOR; i++)
		{
			if(winDoorSensors[i].test())
			{
				testW[i].setForeground(Color.RED);
				testW[i].setText("enable");
			}
			else
			{
				testW[i].setForeground(Color.BLACK);
				testW[i].setText("disable");
			}
				
			if(winDoorSensors[i].read())
			{
				readW[i].setForeground(Color.RED);
				readW[i].setText("open");
			}
			else
			{
				readW[i].setForeground(Color.BLACK);
				readW[i].setText("close");
			}

			
			if(motionDetectors[i].test())
			{
				testM[i].setForeground(Color.RED);
				testM[i].setText("enable");
			}
			else
			{
				testM[i].setForeground(Color.BLACK);
				testM[i].setText("disable");
			}
				
			if(motionDetectors[i].read())
			{
				readM[i].setForeground(Color.RED);
				readM[i].setText("detect");
			}
			else
			{
				readM[i].setForeground(Color.BLACK);
				readM[i].setText("clear");
			}
		}
	}

	public void armWindoorSensor(int id)
	{
		if(id>=MAX_SENSOR) return;
		winDoorSensors[id].enable();
	}
	public void disarmWindoorSensor(int id)
	{
		if(id>=MAX_SENSOR) return;
		winDoorSensors[id].disable();
	}
	public void armMotionDetector(int id)
	{
		if(id>=MAX_SENSOR) return;
		motionDetectors[id].enable();
	}
	public void disarmMotionDetector(int id)
	{
		if(id>=MAX_SENSOR) return;
		motionDetectors[id].disable();
	}
	public boolean readWindoorSensor(int id)
	{
		if(id>=MAX_SENSOR) return false;
		return winDoorSensors[id].read();
	}
	public boolean readMotionDetector(int id)
	{
		if(id>=MAX_SENSOR) return false;
		return motionDetectors[id].read();
	}
	
	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 465564;
}



/*
 * CLASS NAME : CameraTest 
 * DESCRIPTION
 * 		A dialog to test camera's functions
 * 		It has four buttons to control camera's view (pan left/right, zoom in/out)
 * 		and one big screen of camera view
 * PRECONDITION : From main dialog, "Camera" button was chosen
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : click the buttons to pan or zoom the camera
 * EXAMPLE : click zoom in button to zoom in the camera's view
 * POSTCONDITION : none
 * NOTES : If possible, click all buttons to test all functionality
 */ 
/**
 * This shows a dialog to test a camera's functions. The dialog has four buttons to control 
 * camera's view(pan left/right, zoom in/out) and one big screen of camera view. It looks like this:
 * <p>
 * <img src="doc-files/camera.PNG">
 * <p>
 * 
 * @author yhkim
 *
 */
class CameraTest extends JFrame implements ActionListener
{
	CameraView cameraView;
	
	/*
	 * NAME : CameraTest (constructor)
	 * DESCRIPTION
	 * 		Initiate sensors instances
	 * 		and place buttons(pan left/right, zoom in/out) and camera screen
	 * PRECONDITION
	 * 		The main instantiates MainDemo class and MainDemo instantiates this class
	 * 		in consecutively
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION : The layout of this dialog is made
	 * NOTES : none
	 */ 
	/**
	 * Constructs a camera frame. The camera frame consists of a camera screen and four buttons. 
	 * 
	 * <p>
	 * Pre-condition: <code>MainDemo</code> instance is initiated.
	 * <p>
	 * Post-condition: The layout of Camera test frame is made.
	 */
	public CameraTest(int id)
	{
		super("Camera Example");
		cameraView = new CameraView(id);
		//cameraView = view;
		setSize(455, 570);
		getContentPane().setLayout(null);
		setResizable(false);
		
		// make buttons
		JButton buttonPanLeft = new JButton("PanLeft");
		buttonPanLeft.addActionListener(this);
		JButton buttonPanRight = new JButton("PanRight");
		buttonPanRight.addActionListener(this);
		JButton buttonZoomIn = new JButton("ZoomIn");
		buttonZoomIn.addActionListener(this);
		JButton buttonZoomOut = new JButton("ZoomOut");
		buttonZoomOut.addActionListener(this);
		
		// attach buttons to dialog
		JPanel displayPanel = new JPanel(new GridLayout(2, 2));
		displayPanel.add(buttonPanLeft);
		displayPanel.add(buttonPanRight);
		displayPanel.add(buttonZoomIn);
		displayPanel.add(buttonZoomOut);
		displayPanel.setBounds(25, 25, 400, 60);
		getContentPane().add(displayPanel);
		
		// attach camera view to dialog
		displayPanel = new JPanel(new GridLayout(1, 1));
		displayPanel.add(cameraView);
		displayPanel.setBounds(25, 110, 400, 400);
		getContentPane().add(displayPanel);
	}
	
	/*
	 * NAME : actionPerformed
	 * DESCRIPTION
	 * 		This function is the action listener(call back function) of four buttons
	 * 		PanLeft, PanRight, ZoomIn, ZoomOut to control camera view
	 * PRECONDITION : User click on any button
	 * PARAMETER : event - ActionEvent that carrying what button was pressed
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE
	 * 		If PanLeft button is clicked by user, 
	 * 		the view of camera is panned left
	 * POSTCONDITION : View of camera is changed and display is repainted.
	 * NOTES : none
	 */
	/* (non-Javadoc)
	 * @param event ActionEvent that carrying what button was pressed
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/** 
	 * Invoked when an action occurs. When the "PanLeft" or "PanRight" button is pressed,
	 * the camera view pans left or right accordingly. When the "ZoonIn" or "ZoomOut" button is pressed
	 * the camera view zooms in  or out accordingly. The change of camera view is updated immediately
	 *  
	 * <p>
	 * Pre-condition: A user clicks on one of four buttons "ZoomIn", "ZoomOut", "PanLeft" and "PanRight"
	 * <p>
	 * Post-condition: View of camera is changed and display is repainted
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("PanLeft"))
		{
			cameraView.camera.panLeft();
		}
		else if(event.getActionCommand().equals("PanRight"))
		{
			cameraView.camera.panRight();
		}
		else if(event.getActionCommand().equals("ZoomIn"))
		{
			cameraView.camera.zoomIn();	
		}
		else if(event.getActionCommand().equals("ZoomOut"))
		{
			cameraView.camera.zoomOut();	
		}
		
		cameraView.repaint();
	}
	
	/*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 99767;
}


/*
 * CLASS NAME : CameraView 
 * DESCRIPTION
 * 		A simple java component that hold the BufferedImage from DeviceCamera
 *		and it has own drawing function 
 * PRECONDITION
 * 		This component was attached to any JFrame
 * 		There exist camera1.jpg file
 * PARAMETER : none
 * RETURN VALUE : none
 * USAGE : call repaint() method to refresh the camera view
 * EXAMPLE : none
 * POSTCONDITION : An image from a camera is displaying by this component
 * NOTES
 * 		In constructor, changing integer parameter of camera.setID(int)
 * 		can change loading image file - camera<int>.jpg  
 */ 
/**
 * <code>CameraView</code> class is a simple java component 
 * which has <code>{@link BufferedImage}</code> from <code>DeviceCamera</code>. 
 * This java component can be attached to any <code>{@link JFrame}</code>.
 * This class creates a <code>DeviceCamera</code> instance. 
 * The camera instance reads camera image from the file 
 * camera<int>.jpg and provides the image as an<code>BufferedImage</code> instance. 
 * 
 * 
 * 
 * @author cs350TA
 *
 */
class CameraView extends Component implements Runnable
{
	// Real instance of the Camera
	DeviceCamera camera = new DeviceCamera();
	Thread th;

	/*
	 * NAME : CameraView (constructor)
	 * DESCRIPTION
	 * 		Load image file to DeviceCamera
	 * 		and draw image to attached dialog
	 * 		and initiate Thread to periodic update of camera view
	 * PRECONDITION
	 * 		The main instantiates MainDemo class and MainDemo instantiates CameraTest class
	 * 		and CameraTest instantiates this class in consecutively
	 * PARAMETER : none
	 * RETURN VALUE : none
	 * USAGE : none
	 * EXAMPLE : none
	 * POSTCONDITION
	 * 		JPG image file is loaded to memory and ready to draw
	 * 		and thread is running to update camera view
	 * NOTES : none
	 */ 
	/**
	 * Constructs camera view component. Constructor set the id of the camera instance and 
	 * start a thread to repaint the updated camera image periodically. <code>DeviceCamera</code> 
	 * reads an image from the file camera<int>.jpg in the current working director. 
	 * If the file cannot be opened, <code>DeviceCamera</code> shows an error message.  
	 * <p>
	 * Pre-condition: The main instantiates MainDemo class and MainDemo instantiates CameraTest class
	 * and CameraTest instantiates this class in consecutively
	 * <p>
	 * Post-condition: jpg image file is loaded to memory and the camera instance is ready to draw. 
	 * Thread is running to update camera view periodically. 
	 *   
	 */

	public CameraView(int cameraID)
	{
		camera.setID(cameraID);

		th = new Thread(this);
		th.start();
	}

	public CameraView(CameraView another) {
		this.camera = another.camera;

		th = new Thread(this);
		th.start();
	}
	
	/*
	 * NAME : paint
	 * DESCRIPTION
	 * 		This function get new img from camera device and re-draw image to attached dialog
	 * PRECONDITION
	 * 		JPG image file is loaded to camera device's memory and ready to draw
	 * PARAMETER : g - Graphics to draw image to display
	 * RETURN VALUE : none
	 * USAGE : To draw image, should know about usage of Graphic class 
	 * EXAMPLE : none
	 * POSTCONDITION : User can see the image from the camera
	 * NOTES : none
	 */ 
    /* (non-Javadoc)
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
	
    /** 
     * Paints this component. This method gets new image from camera device and re-draw image to the component.
     * <p>
     * Pre-condition: JPG image file is loaded to camera device's memory and ready to draw
     * <p>
     * Post-condition: A user can see the image from the camera component.
     * @param g the graphics context to use for painting
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) 
    {
        // Draw the image stretched to exactly cover the size of the drawing area.
        Dimension size = getSize();
        BufferedImage img = camera.getView();
        
        g.drawImage(img,
                    0, 0, size.width, size.height,
                    0, 0, img.getWidth(null), img.getHeight(null),
                    null);
    }
     
    /*
	 * NAME : run
	 * DESCRIPTION
	 * 		This function executed by thread concurrently
	 *		and updates view of the camera at periodic interval
	 * PRECONDITION
	 * 		JPG image file is loaded to camera device's memory and ready to draw
	 * PARAMETER : g - Graphics to draw image to display
	 * RETURN VALUE : none
	 * USAGE : none 
	 * EXAMPLE : none
	 * POSTCONDITION : User can see the image from the camera
	 * NOTES
	 * 		Sleep interval should be less than 1000
	 * 		This function should have infinte loop
	 */ 
	/** 
	 * When an object implementing interface <code>Runnable</code> is used to create a thread, 
	 * starting the thread causes the object's run method to be called in that separately executing thread. 
	 * 
	 * This thread updates view of the camera in periodic interval 100ms.
	 * <p> 
	 * Pre-condition: JPG image file is loaded to camera device's memory and ready to draw.
	 * <p>
	 * Post-condition: A user can see the updated image from the camera 
	 * <p> 
	 * @see java.lang.Runnable#run()
	 */
    
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
			}
			repaint();
		}
	}
    
    /*
	 * meaningless member variable in this application
	 * prevent java compiler warning message
	 */
	static final long serialVersionUID = 45634;
}


