import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UpdateHCFrame extends JPanel{
	JTextArea conditions;
	JButton edit;
	JButton save;
	JTextField nameField;
	JTextField DOBField;
	String filename;
	Writer writer;
	
	public UpdateHCFrame () throws IOException{
		super();
		nameField=new JTextField("Patient Name",20);
		DOBField=new JTextField("Date of Birth: mm/dd/yyyy",20);
		conditions = new JTextArea("Health Care Conditions",20, 20);
		JButton edit = new JButton("Edit");
		JButton save = new JButton("Save");
		this.add(nameField);
		this.add(DOBField);
		this.add(conditions);
		this.add(edit);
		this.add(save);
		SaveButtonListener saver = new SaveButtonListener();
		save.addActionListener(saver);
		filename = "test.txt";
		
		//FileReader hc = new FileReader(getPatientConditions());
	}
	
	public class ButtonListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e){
			
		}
	}
	
	public class SaveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				writer = new BufferedWriter(new FileWriter(filename));
				
				//conditions.write(writer);
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				writer.write(conditions.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{
			try {
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		}
	}

	
	public class HCListener implements DocumentListener{
		public void changedUpdate(DocumentEvent e){
			
		}
		
		public void insertUpdate(DocumentEvent e){
			
		}
		
		public void removeUpdate(DocumentEvent e){
			
		}
	}
	
	public File getPatientConditions(){
		return new File("/Users/timothymillea/Documents/CSE360/TestPatient");
		
	}
}
