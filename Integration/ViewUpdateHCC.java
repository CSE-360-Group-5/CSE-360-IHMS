
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewUpdateHCC extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JTextField patientNameField;
	private JTextField dateField;
	private JTextField idField;
	private String fullName;
	
	private JLabel patientNameLabel;
	private JLabel dateLabel;
	
	private JTextArea conditions;

	private JButton saveButton;
	private JButton cancelButton;
	
	private JScrollPane scrollConditions;
	
	private JPanel datePanel;
	private JPanel namePanel;
	private JPanel patientInfoPanel;
	private JPanel buttonPanel;
	private JPanel hccPanel;
	
	//Database
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	//Constructor
	
	public ViewUpdateHCC (int id) 
	{
		try
		{
			Class.forName("java.sql.Driver");
			
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url,"root","admin");
		}
		
		catch(Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
		fullName = "";
		idField = new JTextField(10);
		patientNameField = new JTextField(15);
		patientNameField.setEditable(false);
		dateField = new JTextField(10);
		
		patientNameLabel = new JLabel("Patient Name: ");
		dateLabel = new JLabel("Date: ");
		
		conditions = new JTextArea(20, 50);
		scrollConditions = new JScrollPane(conditions);
		
		saveButton = new JButton("Save");
		SaveButtonListener saver = new SaveButtonListener();
		saveButton.addActionListener(saver);
		
		cancelButton = new JButton("Cancel");
		CancelButtonListener cancel = new CancelButtonListener();
		cancelButton.addActionListener(cancel);
		
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM hcc WHERE `patientID`='" + id + "';");
			
			while(rs.next())
			{
				conditions.setText(rs.getString("hcc"));
				dateField.setText(rs.getString("date"));
				idField.setText(Integer.toString(id));
				fullName = rs.getString("fname") + " " + rs.getString("lname");
			}
		}
		
		catch (Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
			System.err.println(e.getStackTrace());
		}
		
		patientNameField.setText(fullName);
		
		//JPanels
		datePanel = new JPanel();
		datePanel.add(dateLabel);
		datePanel.add(dateField);
		
		namePanel = new JPanel();
		namePanel.add(patientNameLabel);
		namePanel.add(patientNameField);
		
		patientInfoPanel = new JPanel();
		patientInfoPanel.setLayout(new BoxLayout(patientInfoPanel, BoxLayout.X_AXIS));
		patientInfoPanel.add(datePanel);
		patientInfoPanel.add(namePanel);
		
		buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		hccPanel = new JPanel();
		hccPanel.setLayout(new BoxLayout(hccPanel, BoxLayout.Y_AXIS));
		hccPanel.add(patientInfoPanel);
		hccPanel.add(scrollConditions);
		hccPanel.add(buttonPanel);
		
		Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 10);
		hccPanel.setBorder(padding);
		
		add(hccPanel);
		setSize(500,400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // set default close operation
		
	}
	
	public class CancelButtonListener implements ActionListener{ 
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	}
	
	public class SaveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{			
			String _conditions = conditions.getText();
			String _date = dateField.getText();
			String id = idField.getText();			
			
			try
			{
				statement = conn.createStatement();
				statement.executeUpdate("UPDATE hcc SET `hcc`='" + _conditions + "', `date`='" + _date + "' WHERE `patientID`='" + id + "';");
				JOptionPane.showMessageDialog(scrollConditions, "Healthcare condition updated.");
				dispose();
			}
			
			catch (Exception e1)
			{
				System.err.println("Got an exception!");
				System.err.println(e1.getMessage());
			}
			
		}
	}
}
