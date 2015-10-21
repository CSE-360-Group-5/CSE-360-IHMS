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
	String filename;
	BufferedWriter writer;
	BufferedReader reader;
	JTextField alert;
	public UpdateHCFrame () throws IOException{
		super();
		conditions = new JTextArea(20, 20);
		//JButton edit = new JButton("Edit");
		JButton save = new JButton("Save");
		this.add(conditions);
		//this.add(edit);
		this.add(save);
		SaveButtonListener saver = new SaveButtonListener();
		save.addActionListener(saver);
		filename = "test.txt";
		reader = new BufferedReader(new FileReader(filename));
		alert = new JTextField();
		this.add(alert);
		alert.setVisible(false);
		while(reader.ready()){
			conditions.append(reader.readLine()); //Reads file to text area
			conditions.append("\n");
		}
		//FileReader hc = new FileReader(getPatientConditions());
	}
	
	//public class ButtonListener implements ActionListener{ 
	//	public void actionPerformed(ActionEvent e){
			
	//	}
	//}
	
	public class SaveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try {
				writer = new BufferedWriter(new FileWriter(filename)); //Saves the edits
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				writer.write(conditions.getText());
				JOptionPane.showMessageDialog(alert,"Patient conditions updated.");
				
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
