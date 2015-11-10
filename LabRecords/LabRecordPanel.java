///////////////////////////////////////////////////////////////////////////////////////
// Name: LabRecordsPanel.java
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

public class LabRecordPanel extends JPanel
{
 
	private static final long serialVersionUID = 1L;
	
	/////////////////////////////////////////////////////////////////
	// JLABELS
	/////////////////////////////////////////////////////////////////
	
	private JLabel instructionsLabel1;
	private JLabel instructionsLabel2;
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
   	
  private JList<Records> recordList;
	private JList<Patient> patientList;
	private JList<Patient> searchList;
	private Vector<Patient> searchVector;
	private Vector<Records> recordVector;
	private Vector<Patient> patientVector;
	
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
	public LabRecordPanel() 
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
		
		/////////////////////////////////////////////////////////////////
		// Instantiate components
		/////////////////////////////////////////////////////////////////
		
		// Labels
		instructionsLabel1 = new JLabel("         To upload new lab records to the system, make sure that the first item of the list is selected. In case you want to update an existing Lab Record, just select the item in the list");
		instructionsLabel2 = new JLabel("  below, make the changes and click Save button."); // Labels for instructions of the use case
		firstNameLabel = new JLabel(" First name:"); // first name label
		lastNameLabel = new JLabel("Last name:"); // last name label
		dobLabel = new JLabel("Date of Birth: "); // DOB label
		labRecordLabel = new JLabel(" Lab Record: "); // lab record label
		dateLabel = new JLabel("Date: "); // date label
		successLabel = new JLabel(" "); // success message label
		recordListLabel = new JLabel("Lab Record List:"); // Lab Record list label
		patientListLabel = new JLabel("Patients:"); // Patient list label
		firstNameSearchLabel = new JLabel(" First name:"); // first name label used in the search patient frame
		lastNameSearchLabel = new JLabel("Last name:"); // last name label used in the search patient frame
	
    // Text fields
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
		recordVector = new Vector<Records>(); // Vector of Records objects
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
            	obj.setFirstName(rs.getString(2));
            	obj.setLastName(rs.getString(3));
            	obj.setDOB(rs.getString(4));
            	obj.setRecord(rs.getString(5));
            	obj.setDate(rs.getString(6));
            	
            	recordVector.add(obj);
            }
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
      System.err.println(e.getMessage());
		}
	
		recordList = new JList<Records>(recordVector); // creates a JList that show the content of the recordVector
		scrollList = new JScrollPane(recordList); // add scroll option to the list
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time
		recordList.addListSelectionListener(new ListListener()); // List listener
	
		//PATIENT LIST
		patientVector = new Vector<Patient>(); // Vector of Patient objects
		patientList = new JList<Patient>(patientVector); // creates a JList that show the content of the recordVector
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
            	obj.setFirstName(rs.getString(2));
            	obj.setLastName(rs.getString(3));
            	obj.setDOB(rs.getString(4));
            	
            	patientVector.add(obj);
            }
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
		}
		
		searchVector = new Vector<Patient>();
		
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
    patientInfoPanel.setLayout(new BorderLayout());
    patientInfoPanel.add(firstNamePanel, BorderLayout.WEST);
    patientInfoPanel.add(lastNamePanel, BorderLayout.CENTER);
    patientInfoPanel.add(dobPanel, BorderLayout.EAST);
		
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
	}
  
	// List listener
  	private class ListListener implements ListSelectionListener
	{
			Records labRec1 = new Records();
			public void valueChanged (ListSelectionEvent event)
			{
				labRec1 = recordList.getSelectedValue();

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
					if(firstNameSearchField.getText().matches(patientVector.elementAt(i).getFirstName()) && lastNameSearchField.getText().equals(patientVector.elementAt(i).getLastName()))
						searchVector.add(patientVector.elementAt(i));
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found! 1");
				else
				{
					searchList = new JList<Patient>(searchVector);
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
					if(firstNameSearchField.getText().matches(patientVector.elementAt(i).getFirstName()))
						searchVector.add(patientVector.elementAt(i));
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!2");
				else
				{
					searchList = new JList<Patient>(searchVector);
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
					if(lastNameSearchField.getText().matches(patientVector.elementAt(i).getLastName()))
						searchVector.add(patientVector.elementAt(i));
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!");
				else
				{
					searchList = new JList<Patient>(searchVector);
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

			String firstName = firstNameField.getText(); // first name of person
			String lastName = lastNameField.getText(); // last name of person
			String dob = dobField.getText(); // date of birth of person
			String date = dateField.getText(); // date of the submitted lab record
			String labRecord = labRecordArea.getText(); // lab record for the patient

			// fill the attributes of the Records class to save the complete object

			labRec.setFirstName(firstName);
			labRec.setLastName(lastName);
			labRec.setDOB(dob);
			labRec.setDate(date);
			labRec.setRecord(labRecord);

			if(recordList.getSelectedIndex() > 0)
			{
				int selectedIndex = recordList.getSelectedIndex();
				recordVector.remove(selectedIndex);
				recordVector.add(selectedIndex,labRec);
				
				try
				{
					statement = conn.createStatement();
					statement.executeUpdate("UPDATE labrecord SET " + "`fname`='" + firstName + "', `lname`='" + lastName + "', `dob`='" + dob + "', `labRecord`='" + labRecord + "', `date`='" + date + "' WHERE `recordID`='" + (selectedIndex) + "';");
				}
				catch (Exception e)
				{
					System.err.println("Got an exception! "); 
		            System.err.println(e.getMessage()); 
				}
				
				successLabel.setText("  The lab record was updated successfully.");
				successLabel.setForeground(Color.green);
			}
			else
			{
				recordVector.add(labRec);
				successLabel.setText("  The lab record is uploaded.");
				successLabel.setForeground(Color.green);
				
				try
				{
					statement = conn.createStatement();
					statement.executeUpdate("INSERT INTO labRecord (fname, lname, dob, labRecord, date)" + "VALUES ('" + firstName + "', '" + lastName + "', '" + dob + "', '" + labRecord + "', '" + date + "')");
				}
				catch (Exception e)
				{
					System.err.println("Got an exception! "); 
		            System.err.println(e.getMessage()); 
				}
				
				//clear the fields to add new lab record
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
			patient = patientList.getSelectedValue();  // gets the value from the complete set of patients
			if(patient == null) // in case of the patient is selected from specific search list
				patient = searchList.getSelectedValue(); // gets the value from the search list

			firstNameField.setText(patient.getFirstName());
			lastNameField.setText(patient.getLastName());
			dobField.setText(patient.getDOB());
			
			searchPatientFrame.dispose(); // close the window
		}
	}

}
