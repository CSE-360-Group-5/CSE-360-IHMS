import javax.swing.*;
import java.awt.*;
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
	int space;
	
	
	public DoctorFrame() throws IOException{
		super();
		frame = new JFrame("Doctor");
		mainPanel = new JPanel;
		mainPanel.setLayout(BoxLayout.(mainPanel), BoxLayout.Y-AXIS);
		
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
		SaveEPButtonListener saveEP = new SaveEPButtonListener();
		
		hcButton.addActionListener(hcListener);
		labRecordsButton.addActionListener(labListener);
		ePrescribeButton.addActionListener(ePrescribeListener);
		
		frame.add(mainPanel);
		frame.setVisible(true);
	}
	
	public class SaveEPButtonListener implements ActionListener{
		void actionPerformed(ActionEvent e){
			try{
				name = nameField.getText();
				DOB = DOBField.getText();
				prescription = prescriptionField.getText();
				
				//filename parser: "lastname_firstname.txt"
				space = name.indexOf(“ “);
				fileName = name.substring(space+1) + “_“ + name.substring(0, space-1) + “.txt”;
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try{
				writer = new BufferedWriter(new FileWriter(filename));
				writer.write(name+"\r\n");
				writer.write(DOB+"\r\n");
				writer.write(prescription);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			frame.remove(epPanel);
			frame.validate();
			frame.repaint();
		}
	}
		
	public class hcButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			UpdateHCFrame hcPanel = new UpdateHCFrame();
			frame.add(hcPanel);
			frame.validate();
			frame.repaint();
		}
	}
	public class labButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			LabRecords.main(args);
		}
	}
	public class ePrescribeButtonListener implements ActionListener{
		void ActionPerformed(ActionEvent e){
			try{
				epPanel = new JPanel("e-prescribe");
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
	
}
