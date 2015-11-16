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
	JPanel ePanel;
	JButton labRecordsButton;
	JButton hcButton;
	JButton ePrescribeButton;
	JButton cancel;
	JButton save;
	JButton data;
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
	LabRecords record;
	static JTextArea patients;
	JTextField alert;
	public DoctorFrame() throws IOException{
		super();
		frame = new JFrame("Doctor");
		frame.setSize(500, 200);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		labRecordsButton = new JButton("Lab Records");
		hcButton = new JButton("View/Update Health Care Conditions");
		ePrescribeButton = new JButton("e-Prescribe");
		data = new JButton("Patient Data");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		
		
		nameField = new JTextField(name, 20);
		DOBField = new JTextField(DOB,20);
		prescriptionField = new JTextField(prescription, 50);
		patients = new JTextArea(20, 20);
		patients.setVisible(false);
		
		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		mainPanel.add(ePrescribeButton);
		mainPanel.add(data);
		
		hcButtonListener hcListener = new hcButtonListener();
		labButtonListener labListener = new labButtonListener();
		ePrescribeButtonListener ePrescribeListener = new ePrescribeButtonListener(); 
		saveEP = new SaveEPButtonListener();
		
		hcButton.addActionListener(hcListener);
		labRecordsButton.addActionListener(labListener);
		ePrescribeButton.addActionListener(ePrescribeListener);
		
		frame.add(mainPanel);
		frame.setVisible(true);
		alert = new JTextField();
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
			
			//frame.remove(ePanel);
			//ePanel = null;
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
			JFrame hcFrame = new JFrame();
			hcFrame.add(hcPanel);
			hcFrame.setSize(500, 300);
			hcFrame.setVisible(true);
		}
	}
	public class labButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			record = new LabRecords();
		}
	}
	public class patientListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			patients.setVisible(true);
			mainPanel.add(patients);
			mainPanel.validate();
			mainPanel.repaint();
			for(int i = 0; i < record.patientVector.size(); i++){
				patients.append(record.patientVector.elementAt(i).getFirstName());
				patients.append(record.patientVector.elementAt(i).getLastName());
				patients.append(record.patientVector.elementAt(i).getDOB());
			}
		}
	}
	public class ePrescribeButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFrame scriptFrame = new JFrame();
			ePanel = new JPanel();
			ePanel.add(nameField);
			ePanel.add(DOBField);
			ePanel.add(prescriptionField);
			ePanel.add(save);
			ePanel.add(cancel);
			save.addActionListener(saveEP);
			scriptFrame.add(ePanel);
			scriptFrame.validate();
			scriptFrame.repaint();
			scriptFrame.setVisible(true);
		}
	}
	
}