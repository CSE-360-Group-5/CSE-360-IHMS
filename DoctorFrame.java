import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class DoctorFrame extends JPanel{
	JFrame frame;
	JPanel mainPanel;
	JPanel epPanel;
	JButton labRecordsButton;
	JButton hcButton;
	JButton ePrescribeButton;
	JButton cancel;
	JButton save;
	JTextField nameField;
	JTextField DOBField;
	JTextField prescriptionField;
	String name;
	String DOB;
	String prescription;
	String filename;
	Writer writer;
	SaveEPButtonListener saveEP;
	int space;
	
	
	public DoctorFrame() throws IOException{
		super();
		frame = new JFrame("Doctor");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		labRecordsButton = new JButton("Lab Records");
		hcButton = new JButton("View/Update Health Care Conditions");
		ePrescribeButton = new JButton("e-Prescribe");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		
		nameField = new JTextField(name, 20);
		DOBField = new JTextField(DOB,20);
		prescriptionField = new JTextField(prescription, 50);
		
		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		mainPanel.add(ePrescribeButton);
		
		hcButtonListener hcListener = new hcButtonListener();
		labButtonListener labListener = new labButtonListener();
		ePrescribeButtonListener ePrescribeListener = new ePrescribeButtonListener(); 
		saveEP = new SaveEPButtonListener();
		
		hcButton.addActionListener(hcListener);
		labRecordsButton.addActionListener(labListener);
		ePrescribeButton.addActionListener(ePrescribeListener);
		
		frame.add(mainPanel);
		frame.setVisible(true);
	}
	
	public class SaveEPButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				name = nameField.getText();
				DOB = DOBField.getText();
				prescription = prescriptionField.getText();
				
				//filename parser: "lastname_firstname.txt"
				space = name.indexOf(' ');
				filename = name.substring(space+1) + " " + name.substring(0, space-1) + ".txt";
			try{
				writer = new BufferedWriter(new FileWriter(filename));
				writer.write(name+"\r\n");
				writer.write(DOB+"\r\n");
				writer.write(prescription);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			finally {
			try{
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}
			
			frame.remove(epPanel);
			epPanel = null;
			frame.validate();
			frame.repaint();
		}
	}
		
	public class hcButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			UpdateHCFrame hcPanel = null;
			try {
				hcPanel = new UpdateHCFrame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.add(hcPanel);
			frame.validate();
			frame.repaint();
		}
	}
	public class labButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			LabRecords record = new LabRecords();
		}
	}
	public class ePrescribeButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				epPanel = new JPanel();
				epPanel.add(nameField);
				epPanel.add(DOBField);
				epPanel.add(prescriptionField);
				epPanel.add(save);
				epPanel.add(cancel);
				save.addActionListener(saveEP);
				frame.add(epPanel);
				frame.validate();
				frame.repaint();
		}
	}
	
}
