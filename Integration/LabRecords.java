///////////////////////////////////////////////////////////////////////////////////////
// Name: LabRecords.java
// Authors: Rian Martins, Zarif Akhtab
// Description: Create content of GUI for the upload/update lab records of the IPIMS
// Date: 11/6/2015
///////////////////////////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class LabRecords extends JFrame{
 
	private static final long serialVersionUID = 1L;
	
	//////////////////////////////////////////////////////////////////
	// Menu
	//////////////////////////////////////////////////////////////////
	
	private JMenuBar menu;
	private JMenu menuOp1;
	private JMenu menuOp2;
	private JMenuItem menuItem1;
	
	/////////////////////////////////////////////////////////////////
	// JLABELS
	/////////////////////////////////////////////////////////////////
	
	private JLabel instructionsLabel1;
	private JLabel instructionsLabel2;
	private JLabel patientIDLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel dobLabel;
	private JLabel labRecordLabel;
	private JLabel dateLabel;
	private JLabel successLabel;
	private JLabel recordListLabel;
	private JLabel patientListLabel;
	private JLabel firstNameSearchLabel;
	private JLabel lastNameSearchLabel;
	
	/////////////////////////////////////////////////////////////////
	// TEXT_FIELDS
	/////////////////////////////////////////////////////////////////
	
	private JTextField patientIDField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField dobField;
	private JTextField dateField;
	private JTextField firstNameSearchField;
	private JTextField lastNameSearchField;
	
	/////////////////////////////////////////////////////////////////
	// TEXT_AREAS
	/////////////////////////////////////////////////////////////////
	
	private JTextArea labRecordArea;
	private JScrollPane scrollLabRecord;
	
	/////////////////////////////////////////////////////////////////
	// JBUTTONS 
	/////////////////////////////////////////////////////////////////
	
	private JButton saveButton;
   	private JButton browseButton;
   	private JButton clearButton;
   	private JButton searchPatientButton;
   	private JButton selectPatientButton;
   	private JButton searchButton;
   	
	/////////////////////////////////////////////////////////////////
   	// JLists and Vectors
	/////////////////////////////////////////////////////////////////
   	
   	private JList recordList;
	private JList patientList;
	private JList searchList;
	private Vector searchVector;
	private Vector recordVector;
	private Vector patientVector;
	
	/////////////////////////////////////////////////////////////////
	// FileChooser
	/////////////////////////////////////////////////////////////////
	
	private JFileChooser fc;
	
	/////////////////////////////////////////////////////////////////
	// Scroll Panes
	/////////////////////////////////////////////////////////////////
	
	private JScrollPane scrollPatientList;
	private JScrollPane scrollSearchList;
	private JScrollPane scrollList;

	/////////////////////////////////////////////////////////////////
   	// Frames, Panels and Containers
	/////////////////////////////////////////////////////////////////
	
	private JFrame searchPatientFrame;
	private JPanel instructionsPanel;
	private JPanel datePanel;
	private JPanel northPanel;
	private JPanel patientIDPanel;
	private JPanel firstNamePanel;
	private JPanel firstNameSearch;
	private JPanel lastNameSearch;
	private JPanel lastNamePanel;
	private JPanel dobPanel;
	private JPanel patientInfoPanel;
	private JPanel centerNorthPanel;
	private JPanel centerSouthPanel;
	private JPanel buttonPanel;
	private JPanel centerPanel;
	private JPanel recordListPanel;
	private JPanel searchFields;
	private Container patientContainer;
	
	/////////////////////////////////////////////////////////////////
	// Database components
	/////////////////////////////////////////////////////////////////
	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	// Constructor
	public LabRecords() 
	{
		
		
		/////////////////////////////////////////////////////////////////
		// Database connection
		/////////////////////////////////////////////////////////////////
		
		try
		{
			// "Load" the JDBC driver
			Class.forName("java.sql.Driver");
		
			// Establish the connection to the database
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url,"root","admin");
		}
		
		catch (Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
		// Instantiate components
		
		// Menu
		class ProfileAction implements MenuListener
		{
		    public void menuSelected(MenuEvent e) {
		    	dispose();
		    	Profile p1 = new Profile("staff", 1, "Lab Staff");
        		p1.setVisible(true);
        		p1.setAlwaysOnTop(true);
		    }
		    public void menuDeselected(MenuEvent e){}
		    public void menuCanceled(MenuEvent e){}
		}
		
		class LabRecAction extends AbstractAction {
			private static final long serialVersionUID = 1L;
			public LabRecAction() {
		        putValue(SHORT_DESCRIPTION, "Create new lab records");
		    }
		    public void actionPerformed(ActionEvent e) {
		    	dispose();
		    	LabRecords labRec = new LabRecords();
		    	labRec.setVisible(true);
		    	labRec.setAlwaysOnTop(true);
		    }
		}
		
		Action labAction = new LabRecAction();
		
		// MENU COMPONENTS
		menu = new JMenuBar();
		
		menuOp1 = new JMenu("Profile");
		menuOp1.addMenuListener(new ProfileAction());
		menuOp2 = new JMenu("Lab Records");
		
		menuItem1 = new JMenuItem(labAction);
		menuItem1.setText("Upload/Update Lab Records");
		menuOp2.add(menuItem1);
		
		menu.add(menuOp1);
		menu.add(menuOp2);
		setJMenuBar(menu);
		
		// Labels
		instructionsLabel1 = new JLabel("         To upload new lab records to the system, make sure that the first item of the list is selected. In case you want to update an existing Lab Record, just select the item in the list");
		instructionsLabel2 = new JLabel("  below, make the changes and click Save button."); // Labels for instructions of the use case
		patientIDLabel = new JLabel("Patient ID:"); // patient ID label
		firstNameLabel = new JLabel(" First name:"); // first name label
		lastNameLabel = new JLabel("Last name:"); // last name label
		dobLabel = new JLabel("Date of Birth: "); // DOB label
		labRecordLabel = new JLabel(" Lab Record: "); // lab record label
		dateLabel = new JLabel("Date: "); // date label
		successLabel = new JLabel("   "); // success message label
		recordListLabel = new JLabel("Lab Record List:"); // Lab Record list label
		patientListLabel = new JLabel("Patients:"); // Patient list label
		firstNameSearchLabel = new JLabel(" First name:"); // first name label used in the search patient frame
		lastNameSearchLabel = new JLabel("Last name:"); // last name label used in the search patient frame
	
	    // Text fields
		patientIDField = new JTextField(10); // patient ID field
	    firstNameField = new JTextField(10); // first name text field
	    lastNameField = new JTextField(10); // last name text field
	    dobField = new JTextField(10); // DOB field
	    dateField = new JTextField(10); // date field
	    firstNameSearchField = new JTextField(10); // first name field to search patient
	    lastNameSearchField = new JTextField(10); // last name field to search patient
	
		// Text areas
		labRecordArea = new JTextArea(20, 60); // Text area for upload lab records
		scrollLabRecord = new JScrollPane(labRecordArea); // add scroll option to the text area
	
	   	// Buttons
		saveButton = new JButton("Save"); // save button
	   	saveButton.addActionListener(new SaveButtonListener()); // add listener
	   	
	   	browseButton = new JButton("Upload File"); // browse button
	   	browseButton.addActionListener(new BrowseButtonListener()); // add listener
	   	
	   	clearButton = new JButton("Clear"); // clear button
	   	clearButton.addActionListener(new ClearButtonListener()); // add listener
	   	
	   	searchPatientButton = new JButton("Search Patient"); // search patient button
	   	searchPatientButton.addActionListener(new PatientSearchButtonListener()); // add listener
	   	
	   	selectPatientButton = new JButton("Select"); // select patient button
	   	selectPatientButton.addActionListener(new PatientSelectButtonListener()); // add listener
	   	
	   	searchButton = new JButton("Search"); // search button
	   	searchButton.addActionListener(new SearchButtonListener()); // add listener
	
	   	/////////////////////////////////////////////////////////////////
		// Lists
		/////////////////////////////////////////////////////////////////
	
		// LAB RECORDS LIST
		recordVector = new Vector(); // Vector of Records objects
		Records emptyObject = new Records(); // create a default object
		recordVector.add(emptyObject); // add the object to the vector
		
		//Populates the record list with the values from the database
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM labRecord");
            
            while ( rs.next() ) 
            {
            	Records obj = new Records();
            	obj.setPatientId(rs.getInt("patientID"));
            	obj.setFirstName(rs.getString("fname"));
            	obj.setLastName(rs.getString("lname"));
            	obj.setDOB(rs.getString("dob"));
            	obj.setRecord(rs.getString("labRecord"));
            	obj.setDate(rs.getString("date"));
            	
            	recordVector.add(obj);
            }
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
		}
	
		recordList = new JList(recordVector); // creates a JList that show the content of the recordVector
		scrollList = new JScrollPane(recordList); // add scroll option to the list
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time
		recordList.addListSelectionListener(new ListListener()); // List listener
	
		//PATIENT LIST
		patientVector = new Vector(); // Vector of Patient objects
		patientList = new JList(patientVector); // creates a JList that show the content of the recordVector
		scrollPatientList = new JScrollPane(patientList); // add scroll option to the list
		patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time
		
		// Populates the patient list with the patients from the database
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM patient ORDER BY fname");
            
            while ( rs.next() ) 
            {
            	Patient obj = new Patient();
            	obj.setPatientId(rs.getInt("idpatient"));
            	obj.setFirstName(rs.getString("fname"));
            	obj.setLastName(rs.getString("lname"));
            	obj.setDOB(rs.getString("dob"));
            	
            	patientVector.add(obj);
            }
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
		}
		
		searchVector = new Vector();
		
		// File chooser	
		fc = new JFileChooser();
	
		/////////////////////////////////////////////////////////////////
		// Frames and panels
		/////////////////////////////////////////////////////////////////
	
		searchPatientFrame = new JFrame("Search Patient");
	
		instructionsPanel = new JPanel(); // Instructions panel
		instructionsPanel.setLayout(new GridLayout(3,1));
		instructionsPanel.add(instructionsLabel1);
		instructionsPanel.add(instructionsLabel2);
		
		patientIDPanel = new JPanel(); // Patient ID panel
		patientIDPanel.add(patientIDLabel);
		patientIDPanel.add(patientIDField);
	
		datePanel = new JPanel(); // date panel
		datePanel.add(dateLabel);
		datePanel.add(dateField);
	
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(successLabel, BorderLayout.WEST);
	
		firstNamePanel = new JPanel(); // First name panel
		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(firstNameField);
		
		lastNamePanel = new JPanel(); // Last name panel
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameField);
	
		dobPanel = new JPanel(); // User DOB panel
		dobPanel.add(dobLabel);
	    dobPanel.add(dobField);
	
	    patientInfoPanel = new JPanel();
	    patientInfoPanel.setLayout(new BoxLayout(patientInfoPanel, BoxLayout.X_AXIS));
	    patientInfoPanel.add(patientIDPanel);
	    patientInfoPanel.add(firstNamePanel);
	    patientInfoPanel.add(lastNamePanel);
	    patientInfoPanel.add(dobPanel);
		
	    centerNorthPanel = new JPanel();
	    centerNorthPanel.setLayout(new BoxLayout(centerNorthPanel, BoxLayout.Y_AXIS));
	    centerNorthPanel.add(patientInfoPanel);
	    centerNorthPanel.add(Box.createRigidArea(new Dimension(0,5)));
	
		centerSouthPanel = new JPanel();
		centerSouthPanel.setLayout(new BorderLayout());
		centerSouthPanel.add(labRecordLabel, BorderLayout.WEST);
		centerSouthPanel.add(datePanel, BorderLayout.EAST);
		centerSouthPanel.add(scrollLabRecord, BorderLayout.SOUTH);
	
		buttonPanel = new JPanel(); // button panel
		buttonPanel.add(saveButton);
		buttonPanel.add(browseButton);
		buttonPanel.add(clearButton);
	
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(northPanel);
		centerPanel.add(centerNorthPanel);
		centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
		centerPanel.add(centerSouthPanel);
		centerPanel.add(buttonPanel);
	
		recordListPanel = new JPanel(); // Record List Panel
		recordListPanel.setLayout(new BoxLayout(recordListPanel, BoxLayout.Y_AXIS));
		recordListPanel.add(searchPatientButton);
		recordListPanel.add(Box.createRigidArea(new Dimension(0,10)));
		recordListPanel.add(recordListLabel);
		recordListPanel.add(Box.createRigidArea(new Dimension(0,3)));
		recordListPanel.add(scrollList);
		
		firstNameSearch = new JPanel();
		firstNameSearch.add(firstNameSearchLabel);
		firstNameSearch.add(firstNameSearchField);
		
		lastNameSearch = new JPanel();
		lastNameSearch.add(lastNameSearchLabel);
		lastNameSearch.add(lastNameSearchField);
		
		searchFields = new JPanel();
		searchFields.add(firstNameSearch);
		searchFields.add(lastNameSearch);
		searchFields.add(searchButton);
		
		// Add components to container of the Search Patient window
		patientContainer = searchPatientFrame.getContentPane();
		patientContainer.setLayout(new BoxLayout(patientContainer, BoxLayout.Y_AXIS));
		patientContainer.add(searchFields);
		patientContainer.add(patientListLabel);
		patientContainer.add(scrollPatientList);
		patientContainer.add(selectPatientButton);
		
		searchPatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the window on close
		searchPatientFrame.setSize(500,300); // set size of window
		
		// Add panels to the constructor
		setLayout(new BorderLayout());
		add(instructionsPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.WEST);
		add(recordListPanel, BorderLayout.EAST);
		
		setSize(1200,580); // set size of window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // set default close operation
		setTitle("Lab Records");
	}
  
	// List listener
	private class ListListener implements ListSelectionListener
	{
			Records labRec1 = new Records();
			public void valueChanged (ListSelectionEvent event)
			{
				labRec1 = (Records) recordList.getSelectedValue();

				patientIDField.setText(Integer.toString(labRec1.getPatientID()));
				firstNameField.setText(labRec1.getFirstName());
				lastNameField.setText(labRec1.getLastName());
				dobField.setText(labRec1.getDOB());
				dateField.setText(labRec1.getDate());
				labRecordArea.setText(labRec1.getRecord());
			}
	}

  	// Search Button Listener
	private class SearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// if both names are entered on the search
			if(!firstNameSearchField.getText().equals("") && !lastNameSearchField.getText().equals(""))
			{
				searchVector.clear();
				for(int i = 0; i < patientVector.size(); i++)
				{
					Patient p1 = (Patient) patientVector.elementAt(i);
					if(firstNameSearchField.getText().matches(p1.getFirstName()) && lastNameSearchField.getText().equals(p1.getLastName()))
						searchVector.add(patientVector.elementAt(i));
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!");
				else
				{
					searchList = new JList(searchVector);
					searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollSearchList = new JScrollPane(searchList);
					patientContainer.removeAll();
					patientContainer.add(searchFields);
					patientContainer.add(patientListLabel);
					patientContainer.add(scrollSearchList);
					patientContainer.add(selectPatientButton);
					patientContainer.repaint();
					patientContainer.revalidate();
				}
			}
			// if only the first name is entered on the search
			else if (lastNameSearchField.getText().equals("") && !firstNameSearchField.getText().equals(""))
			{
				searchVector.clear();
				for(int i = 0; i < patientVector.size(); i++)
				{
					Patient p1 = (Patient) patientVector.elementAt(i);
					if(firstNameSearchField.getText().matches(p1.getFirstName()))
						searchVector.add(patientVector.elementAt(i));
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!");
				else
				{
					searchList = new JList(searchVector);
					searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollSearchList = new JScrollPane(searchList);
					patientContainer.removeAll();
					patientContainer.add(searchFields);
					patientContainer.add(patientListLabel);
					patientContainer.add(scrollSearchList);
					patientContainer.add(selectPatientButton);
					patientContainer.repaint();
					patientContainer.revalidate();
				}
			}
			// if only the last name is entered on the search
			else if (firstNameSearchField.getText().equals("") && !lastNameSearchField.getText().equals(""))
			{
				searchVector.clear();
				for(int i = 0; i < patientVector.size(); i++)
				{
					Patient p1 = (Patient) patientVector.elementAt(i);
					if(lastNameSearchField.getText().matches(p1.getLastName()))
						searchVector.add(patientVector.elementAt(i));
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!");
				else
				{
					searchList = new JList(searchVector);
					searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollSearchList = new JScrollPane(searchList);
					patientContainer.removeAll();
					patientContainer.add(searchFields);
					patientContainer.add(patientListLabel);
					patientContainer.add(scrollSearchList);
					patientContainer.add(selectPatientButton);
					patientContainer.repaint();
					patientContainer.revalidate();
				}
			}
			// if the fields are empty, show the complete set of patient
			else
			{
				patientContainer.removeAll();
				patientContainer.add(searchFields);
				patientContainer.add(patientListLabel);
				patientContainer.add(scrollPatientList);
				patientContainer.add(selectPatientButton);
				patientContainer.repaint();
				patientContainer.revalidate();
			}
		}
	}

	// Save Button Listener
	private class SaveButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
					
			/////////////////////////////////////////////////////////////////
			// data variables for the lab record
			/////////////////////////////////////////////////////////////////
			Records labRec = new Records();
			int patientID;

			// check if patient ID is entered
			if(patientIDField.getText().equals(""))
				patientID = 0;
			else
				patientID = Integer.parseInt(patientIDField.getText()); // system id of the patient
			
			String firstName = firstNameField.getText(); // first name of person
			String lastName = lastNameField.getText(); // last name of person
			String dob = dobField.getText(); // date of birth of person
			String date = dateField.getText(); // date of the submitted lab record
			String labRecord = labRecordArea.getText(); // lab record for the patient

			// fill the attributes of the Records class to save the complete object
			labRec.setPatientId(patientID);
			labRec.setFirstName(firstName);
			labRec.setLastName(lastName);
			labRec.setDOB(dob);
			labRec.setDate(date);
			labRec.setRecord(labRecord);
			
			boolean isValidPatient = false;
			for(int i = 0; i < patientVector.size(); i++)
			{
				Patient p1 = (Patient) patientVector.get(i);
				if (p1.getpatientID() == patientID && p1.getFirstName().equals(firstName) && p1.getLastName().equals(lastName) && p1.getDOB().equals(dob))
				{
					isValidPatient = true;
				}
			}
			
			// check if the input isn't valid
			if(firstName.equals("") || lastName.equals("") || date.equals(""))
			{
				JOptionPane.showMessageDialog(labRecordArea, "Please fill all the required fields");
			}
			else if(patientID == 0)
			{
				JOptionPane.showMessageDialog(labRecordArea, "Please fill the patient ID, if you don't know it is always possible to search\nthe patient by using the 'Search Patient' button on the right.");
			}
			else if(!isValidPatient)
			{
				JOptionPane.showMessageDialog(labRecordArea, "Patient not registered");
			}
			

			// insert the record in the database
			else if(recordList.getSelectedIndex() > 0) // edit existing lab record
			{
				int selectedIndex = recordList.getSelectedIndex();
				recordVector.remove(selectedIndex);
				recordVector.add(selectedIndex,labRec);
				
				try
				{
					statement = conn.createStatement();
					statement.executeUpdate("UPDATE labrecord SET " + "`patientID`='" + patientID + "', `fname`='" + firstName + "', `lname`='" + lastName + "', `dob`='" + dob + "', `labRecord`='" + labRecord + "', `date`='" + date + "' WHERE `recordID`='" + (selectedIndex) + "';");
				}
				catch (Exception e)
				{
					System.err.println("Got an exception! "); 
		            System.err.println(e.getMessage()); 
				}
				
				successLabel.setText("  The lab record was updated successfully.");
				successLabel.setForeground(Color.green);
			}
			else // creating new lab record
			{
				recordVector.add(labRec);
				successLabel.setText("  The lab record is uploaded.");
				successLabel.setForeground(Color.green);
				int id=0;
				
				try
				{
					statement = conn.createStatement();
					rs = statement.executeQuery("SELECT * FROM labRecord");
		            
		            while ( rs.next() ) {
		                id = rs.getInt(1);
		            }
					
		            id++;
					statement = conn.createStatement();
					statement.executeUpdate("INSERT INTO labRecord (recordID, patientID, fname, lname, dob, labRecord, date)" + "VALUES (" + id + ", " + patientID + ", '" + firstName + "', '" + lastName + "', '" + dob + "', '" + labRecord + "', '" + date + "')");
				}
				catch (Exception e)
				{
					System.err.println("Got an exception! "); 
		            System.err.println(e.getMessage()); 
				}
				
				//clear the fields to add new lab record
				patientIDField.setText("");
				firstNameField.setText("");
				lastNameField.setText("");
				dateField.setText("");
				dobField.setText("");
				labRecordArea.setText("");
			}
			recordList.updateUI(); //update list
		}
	}

	// Clear Button Listener
	private class ClearButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			recordList.setSelectedIndex(0);
			successLabel.setText("");
			patientIDField.setText("");
			firstNameField.setText("");
			lastNameField.setText("");
			dateField.setText("");
			dobField.setText("");
			labRecordArea.setText("");
		}
	 }

	// Browse Button Listener
	private class BrowseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();

				String line;
				String filename = fc.getCurrentDirectory().toString() + "/" + file.getName();

				try
				{
					FileReader fr = new FileReader (filename);
					BufferedReader inFile = new BufferedReader (fr);

					line = inFile.readLine();
					while (line != null)
					{
						labRecordArea.append(line + "\n");
						line = inFile.readLine();
					}
					inFile.close();
				}
				catch (FileNotFoundException exception)
				{
					JOptionPane.showMessageDialog(labRecordLabel, "The file could not be opened.");
				}
				catch (IOException exception)
				{
					JOptionPane.showMessageDialog(labRecordLabel, "Sorry, an error occured :/");
				}
			}
		}
	}

	// PatientSearch Button Listener
	private class PatientSearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			searchPatientFrame.setVisible(true); // open the window
		}
	}

	// Select Patient Button Listener
	private class PatientSelectButtonListener implements ActionListener
	{
		Patient patient = new Patient();
		public void actionPerformed(ActionEvent event)
		{
			patient = (Patient) patientList.getSelectedValue();  // gets the value from the complete set of patients
			if(patientContainer.isAncestorOf(scrollSearchList)) // in case of the patient is selected from specific search list
				patient = (Patient) searchList.getSelectedValue(); // gets the value from the search list

			recordList.setSelectedIndex(0);
			patientIDField.setText(""+patient.getpatientID());
			firstNameField.setText(patient.getFirstName());
			lastNameField.setText(patient.getLastName());
			dobField.setText(patient.getDOB());
			labRecordArea.setText("");
			dateField.setText("");
			
			searchPatientFrame.dispose(); // close the window
		}
	}

}
