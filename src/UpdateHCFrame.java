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
	JButton cancel;
	JButton save;
	JTextField nameField;
	JTextField DOBField;
	String name;
	String DOB;
	String filename;
	String HC;
	Writer writer;
	
	
	public UpdateHCFrame () throws IOException{
		super();
		filename="test.txt";
		getPatientConditions();
		nameField=new JTextField(name,20);
		DOBField=new JTextField(DOB,20);
		conditions = new JTextArea(HC,20, 20);
		JButton cancel = new JButton("Cancel");
		JButton save = new JButton("Save");
		this.add(nameField);
		this.add(DOBField);
		this.add(conditions);
		this.add(save);
		this.add(cancel);
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
				name = nameField.getText();
				HC=conditions.getText();
				DOB=DOBField.getText();
				//conditions.write(writer);
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				writer.write(name+"\r\n");
				writer.write(DOB+"\r\n");
				writer.write(HC);
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
	
	public void getPatientConditions(){
		//return new File("/Users/timothymillea/Documents/CSE360/TestPatient");
		//Read from file
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			name = br.readLine();
			DOB = br.readLine();
			HC = br.readLine();
			br.close();
		}

		catch(FileNotFoundException e)
		{
			System.out.println("Unable to open file" + filename + "'");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}
}
