package safehome;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by suckzoo on 2015-06-07.
 */
public class UserInterface extends JFrame implements ActionListener
{
	ArrayList<JCheckBox> checkBoxList;
	public UserInterface()
	{

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
			checkPanel.add(checkBoxList.get(2 * i - 2));
			checkPanel.add(checkBoxList.get(2 * i - 1));
		}
		panelSecurityZone.add(checkPanel);

        JButton saveButton = new JButton("Save security zone");
		saveButton.setSize(160, 60);
		saveButton.setLocation(400,120);

		JButton activateButton = new JButton("Activate");
		activateButton.setSize(160,60);
		activateButton.setLocation(400, 200);

		panelSecurityZone.add(saveButton);
		panelSecurityZone.add(activateButton);

		tabbedPane.add("First", panelSecurityZone);

		add(tabbedPane);
		setSize(640, 480);
		setResizable(false);
		setTitle("Configuration interface");
		//@TODO: for debug!
		//setVisible(true);
	}
	public void actionPerformed(ActionEvent ev)
	{

	}
}
