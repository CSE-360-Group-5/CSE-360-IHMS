//////////////////////////////////////////////////////////////////
// Name: ViewLabRecords.java
// Authors: Rian Martins
// Description: GUI interface for the lab record viewer
// Date: 11/14/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ViewLabRecords extends JFrame {

	private static final long serialVersionUID = 1L;

	// ///////////////////////////////////////////////////////////////
	// JLabels
	// ///////////////////////////////////////////////////////////////
	
	private JLabel recordDateLabel;
	private JLabel patientNameLabel;
	private JLabel recordListLabel;
	
	private String patientName;
	
	// ///////////////////////////////////////////////////////////////
	// JTextFields
	// ///////////////////////////////////////////////////////////////
	
	private JTextField dateField;
	private JTextField nameField;
	
	// ///////////////////////////////////////////////////////////////
	// JPanel
	// ///////////////////////////////////////////////////////////////

	private JPanel recordPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel recordInfoPanel;
	private JPanel datePanel;
	private JPanel namePanel;

	// ///////////////////////////////////////////////////////////////
	// Vectors and JLists
	// ///////////////////////////////////////////////////////////////

	private Vector recordVector;
	
	private JList recordList;

	// ///////////////////////////////////////////////////////////////
	// JTextArea
	// ///////////////////////////////////////////////////////////////

	private JTextArea reportArea;
	private JScrollPane scrollReportArea;
	private JScrollPane scrollReportListArea;

	// ///////////////////////////////////////////////////////////////
	// Database components
	// ///////////////////////////////////////////////////////////////

	private Connection conn;
	private Statement statement;
	private ResultSet rs;

	public ViewLabRecords(int id) {
		// ///////////////////////////////////////////////////////////////
		// Database connection
		// ///////////////////////////////////////////////////////////////

		try
		{
			// "Load" the JDBC driver
			Class.forName("java.sql.Driver");

			// Establish the connection to the database
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url, "root", "admin");
		}

		catch (Exception e) 
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}

		// Instantiate components
		
		//Lists
		recordVector = new Vector();
		
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM labRecord WHERE `patientID`='" + id + "';");
			
            while ( rs.next() ) { // loads the records from the specific patient and add to the list
                Records record = new Records();
                
                record.setPatientId(rs.getInt("patientID"));
                record.setFirstName(rs.getString("fname"));
                record.setLastName(rs.getString("lname"));
                record.setDOB(rs.getString("dob"));
                record.setDate(rs.getString("date"));
                record.setRecord(rs.getString("labRecord"));
                
                patientName = rs.getString("fname") + " " + rs.getString("lname"); // sets patient name
                
                recordVector.add(record);
            }
			
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
            System.err.println(e); 
		}
		
		recordList = new JList(recordVector);
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		recordList.addListSelectionListener(new ListListener()); // List listener
		scrollReportListArea = new JScrollPane(recordList);
		
		//JLABELS
		patientNameLabel = new JLabel("Patient Name:");
		recordDateLabel = new JLabel("Date:");
		recordListLabel = new JLabel("Lab Records:");
		
		//JTEXTAREAS and JTEXTFIELD
		reportArea = new JTextArea(17,35);
		reportArea.setEditable(false);
		scrollReportArea = new JScrollPane(reportArea);
		
		dateField = new JTextField(10);
		dateField.setEditable(false);
		nameField = new JTextField(10);
		nameField.setEditable(false);
		nameField.setText(patientName);
		
		//PANELS
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(recordListLabel);
		westPanel.add(Box.createRigidArea(new Dimension(0,5)));
		westPanel.add(scrollReportListArea);
		
		datePanel = new JPanel();
		datePanel.add(recordDateLabel);
		datePanel.add(dateField);
		
		namePanel = new JPanel();
		namePanel.add(patientNameLabel);
		namePanel.add(nameField);
		
		recordInfoPanel = new JPanel();
		recordInfoPanel.setLayout(new BoxLayout(recordInfoPanel, BoxLayout.X_AXIS));
		recordInfoPanel.add(namePanel);
		recordInfoPanel.add(datePanel);
		
		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(recordInfoPanel);
		eastPanel.add(scrollReportArea);
		
		recordPanel = new JPanel();
		recordPanel.setLayout(new BorderLayout());
		recordPanel.add(westPanel, BorderLayout.WEST);
		recordPanel.add(eastPanel, BorderLayout.EAST);
		
		Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 10);
		recordPanel.setBorder(padding);

		add(recordPanel); // add main panel to the frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // set the default close operation
		setTitle("View Lab Record - " + patientName); // sets the title of the frame
		setSize(850, 400); // set size of window

	}
	
	// List listener
	private class ListListener implements ListSelectionListener
	{
		Records labRec1 = new Records();
		public void valueChanged (ListSelectionEvent event)
		{
			labRec1 = (Records) recordList.getSelectedValue();

			dateField.setText(labRec1.getDate());
			reportArea.setText(labRec1.getRecord());
		}
	}
}
