//////////////////////////////////////////////////////////////////
// Name: ViewRegisteredPatients.java
// Authors: Rian Martins
// Description: GUI inferface for the patient viewer
// Date: 11/15/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ViewRegisteredPatients extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static boolean hasNew;

	// ///////////////////////////////////////////////////////////////
	// JLabels
	// ///////////////////////////////////////////////////////////////
	
	private JLabel patientListLabel;
	
	// ///////////////////////////////////////////////////////////////
	// JPanel
	// ///////////////////////////////////////////////////////////////

	private JPanel patientListPanel;

	// ///////////////////////////////////////////////////////////////
	// Vectors and JLists
	// ///////////////////////////////////////////////////////////////

	private Vector patientVector;
	private Vector newPatientVector;
	
	private JList patientList;
	private JList newPatientList;
	
	// ///////////////////////////////////////////////////////////////
	// JButton
	// ///////////////////////////////////////////////////////////////

	private JButton okButton;
	
	// ///////////////////////////////////////////////////////////////
	// JScrollPane
	// ///////////////////////////////////////////////////////////////
	
	private JScrollPane scrollPatientList;
	private JScrollPane scrollNewPatientList;

	// ///////////////////////////////////////////////////////////////
	// Database components
	// ///////////////////////////////////////////////////////////////

	private Connection conn;
	private Statement statement;
	private ResultSet rs;

	public ViewRegisteredPatients(String set) {
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
		hasNew = false;
		
		//ALL PATIENTS
		patientListLabel = new JLabel("Patient List:");
		
		patientVector = new Vector();
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM patient");
			
            while ( rs.next() ) { // loads the records from the specific patient and add to the list
                Patient patient = new Patient();
                
                patient.setPatientId(rs.getInt("idpatient"));
                patient.setFirstName(rs.getString("fname"));
                patient.setLastName(rs.getString("lname"));
                patient.setDOB(rs.getString("dob"));
                
                patientVector.add(patient);
            }
			
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
		}
		
		patientList = new JList(patientVector);
		patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPatientList = new JScrollPane(patientList);
		
		//NEW PATIENTS
		newPatientVector = new Vector();
		if(set.equals("new"))
		{
			int i=0;
			while(i < LoginPanel.registeredPatientList.size())
			{
				Patient registeredP = (Patient) LoginPanel.registeredPatientList.get(i);
				if(registeredP.getIsNew().equals("true"))
				{
					newPatientVector.add(registeredP);
					hasNew = true;
					try
					{
						statement = conn.createStatement();
						statement.executeUpdate("UPDATE patient SET " + "`isNew`='false' WHERE `idpatient`='" + registeredP.getpatientID() + "';");
					}
					catch (Exception e)
					{
						System.err.println("Got an exception! "); 
			            System.err.println(e.getMessage()); 
					}
					registeredP.setIsNew("false");
					LoginPanel.registeredPatientList.remove(i);
					LoginPanel.registeredPatientList.add(i,registeredP);
				}
				i++;
			}
		}
		
		newPatientList = new JList(newPatientVector);
		newPatientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollNewPatientList = new JScrollPane(newPatientList);
		
		//JButton
		okButton = new JButton("Ok");
		okButton.addActionListener(new okButtonListener());
		
		//JPanel
		patientListPanel = new JPanel();
		patientListPanel.setLayout(new BoxLayout(patientListPanel, BoxLayout.Y_AXIS));
		patientListPanel.add(patientListLabel);
		if(set.equals("all"))	
			patientListPanel.add(scrollPatientList);
		else
			patientListPanel.add(scrollNewPatientList);
		patientListPanel.add(okButton);
		
		Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 10);
		patientListPanel.setBorder(padding);

		add(patientListPanel); // add main panel to the frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // set the default close operation
		setTitle("View Patient List"); // sets the title of the frame
		setSize(400, 300); // set size of window

	}
	
	// PatientSearch Button Listener
	private class okButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			dispose(); // close the window
		}
	}
}
