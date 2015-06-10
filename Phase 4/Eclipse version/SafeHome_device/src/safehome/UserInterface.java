package safehome;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by suckzoo on 2015-06-07.
 */

public class UserInterface extends JFrame implements ActionListener
{
	ArrayList<JCheckBox> checkBoxList;
	CameraView cameraThumb1;
	CameraView cameraThumb2;
	CameraTest cameraControlPanel1, cameraControlPanel2;
	HashMap<String, ArrayList<JCheckBox>> securityZoneMapper;
	Vector<String> keys;
	String optionKey;
	public UserInterface()
	{

		securityZoneMapper = new HashMap<>();
		keys = new Vector<>();
		cameraControlPanel1 = new CameraTest(1);
		cameraControlPanel2 = new CameraTest(2);
		//tabbedPane.addTab("Security zone configuration", panelSecurityZone);
		checkBoxList = new ArrayList<>();
		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel panelSecurityZone = new JPanel();
		panelSecurityZone.setLayout(null);
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new GridLayout(5, 2));
		checkPanel.setSize(250, 320);
		checkPanel.setLocation(80, 45);
		for(int i=1;i<=5;i++) {
			checkBoxList.add(new JCheckBox("WindoorSensor " + Integer.toString(i)));
			checkBoxList.add(new JCheckBox("MotionSensor " + Integer.toString(i)));
			checkBoxList.get(2*i-2).addActionListener(this);
			checkBoxList.get(2*i-1).addActionListener(this);
			checkPanel.add(checkBoxList.get(2 * i - 2));
			checkPanel.add(checkBoxList.get(2 * i - 1));
		}
		panelSecurityZone.add(checkPanel);

        JButton saveButton = new JButton("Save security zone");
		saveButton.setSize(160, 60);
		saveButton.setLocation(400, 80);
		saveButton.addActionListener(this);

		JButton activateButton = new JButton("Activate");
		activateButton.setSize(160, 60);
		activateButton.setLocation(400, 160);
		activateButton.addActionListener(this);

		JButton loadButton = new JButton("Load security zone");
		loadButton.setSize(160, 60);
		loadButton.setLocation(400, 240);
		loadButton.addActionListener(this);

		panelSecurityZone.add(saveButton);
		panelSecurityZone.add(activateButton);
		panelSecurityZone.add(loadButton);

		tabbedPane.add("Security zone setting", panelSecurityZone);

		JPanel panelSurveillance = new JPanel();
		panelSurveillance.setLayout(null);
		cameraThumb1 = new CameraView(cameraControlPanel1.cameraView);
		cameraThumb1.setSize(270, 270);
		cameraThumb1.setLocation(20, 70);
		cameraThumb2 = new CameraView(cameraControlPanel2.cameraView);
		cameraThumb2.setSize(270, 270);
		cameraThumb2.setLocation(350, 70);
		JButton cameraDetail1 = new JButton("Camera 1");
		cameraDetail1.addActionListener(this);
		cameraDetail1.setLocation(20, 385);
		cameraDetail1.setSize(270,30);
		JButton cameraDetail2 = new JButton("Camera 2");
		cameraDetail2.addActionListener(this);
		cameraDetail2.setLocation(350, 385);
		cameraDetail2.setSize(270,30);

		panelSurveillance.add(cameraThumb1);
		panelSurveillance.add(cameraThumb2);
		panelSurveillance.add(cameraDetail1);
		panelSurveillance.add(cameraDetail2);

		tabbedPane.add("Surveillance", panelSurveillance);
		
		optionKey = null;

		add(tabbedPane);
		setSize(640, 480);
		setResizable(false);
		setTitle("Configuration interface");
		//@TODO: for debug!
		//setVisible(true);
	}
	String getMessage()
	{
		if(optionKey != null) return optionKey;
		return JOptionPane.showInputDialog(this, "Input security zone name.");
	}
	String getListMessage()
	{
		if(optionKey != null) return optionKey;
		return (String)JOptionPane.showInputDialog(this, "Select security zone", "Load security zone", JOptionPane.QUESTION_MESSAGE, null, keys.toArray(), keys.get(0));
	}
	public void actionPerformed(ActionEvent ev)
	{
		if(ev.getActionCommand().contains("Windoor"))
		{
			int sensorNumber = Integer.parseInt(ev.getActionCommand().substring(14));
			if(sensorNumber>MainDemo.safehomeConsole.MAX_SENSOR) return;
			if(checkBoxList.get(2 * sensorNumber - 2).isSelected()) {
				MainDemo.safehomeConsole.armWindoorSensor(sensorNumber-1);
			} else {
				MainDemo.safehomeConsole.disarmWindoorSensor(sensorNumber-1);
			}
		}
		if(ev.getActionCommand().contains("Motion"))
		{
			int sensorNumber = Integer.parseInt(ev.getActionCommand().substring(13));
			if(sensorNumber>MainDemo.safehomeConsole.MAX_SENSOR) return;
			if(checkBoxList.get(2 * sensorNumber - 1).isSelected()) {
				MainDemo.safehomeConsole.armMotionDetector(sensorNumber - 1);
			} else {
				MainDemo.safehomeConsole.disarmMotionDetector(sensorNumber - 1);
			}
		}
		if(ev.getActionCommand().equals("Save security zone"))
		{
			while(true) {
				optionKey = getMessage();
				if (keys.contains(optionKey)) {
					JOptionPane.showMessageDialog(this, "Security zone name duplicated.");
				} else break;
			}
			ArrayList<JCheckBox> checkboxes = new ArrayList<>();
			keys.add(optionKey);
			for(int i=0;i<10;i++) {
				if(checkBoxList.get(i).isSelected())
				{
					checkboxes.add(checkBoxList.get(i));
				}
			}
			securityZoneMapper.put(optionKey, checkboxes);
		}
		if(ev.getActionCommand().equals("Load security zone"))
		{
			if(keys.size()==0) {
				JOptionPane.showMessageDialog(this, "No saved security zone");
			} else {
				String optionKey = getListMessage();
				if(securityZoneMapper.containsKey(optionKey)) {
					for(int i=0;i<5;i++)
					{
						checkBoxList.get(2*i).setSelected(false);
						checkBoxList.get(2*i+1).setSelected(false);
						MainDemo.safehomeConsole.disarmWindoorSensor(i);
						MainDemo.safehomeConsole.disarmMotionDetector(i);
					}
					ArrayList<JCheckBox> checkboxes = securityZoneMapper.get(optionKey);
					for(int i=0;i<checkboxes.size();i++)
					{
						checkboxes.get(i).setSelected(true);
						//System.out.println(checkboxes.get(i).getActionCommand());
						if(checkboxes.get(i).getActionCommand().contains("Windoor"))
						{
                            int sensorNumber = Integer.parseInt(checkboxes.get(i).getActionCommand().substring(14));
                            MainDemo.safehomeConsole.armWindoorSensor(sensorNumber-1);
						}
						else
						{
                            int sensorNumber = Integer.parseInt(checkboxes.get(i).getActionCommand().substring(13));
                            MainDemo.safehomeConsole.armMotionDetector(sensorNumber-1);
						}
					}
				}
			}
		}
		if(ev.getActionCommand().equals("Activate"))
		{
			for(int i=1;i<=5;i++) {
				if (checkBoxList.get(2 * i - 2).isSelected()) {
					MainDemo.safehomeConsole.armWindoorSensor(i - 1);
				} else {
					MainDemo.safehomeConsole.disarmWindoorSensor(i - 1);
				}
				if (checkBoxList.get(2 * i - 1).isSelected()) {
					MainDemo.safehomeConsole.armMotionDetector(i - 1);
				} else {
					MainDemo.safehomeConsole.disarmMotionDetector(i - 1);
				}
			}
			MainDemo.controlPanel.PowerOn();
			MainDemo.controlPanel.Arm();
			//MainDemo.safehomeConsole.armSystem();
		}
		if(ev.getActionCommand().equals("Camera 1"))
		{
			cameraControlPanel1.setVisible(true);
		}
		if(ev.getActionCommand().equals("Camera 2"))
		{
			cameraControlPanel2.setVisible(true);
		}
	}
}
