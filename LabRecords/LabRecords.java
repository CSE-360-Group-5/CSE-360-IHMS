//////////////////////////////////////////////////////////////////
// Name: LabRecords.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the upload/update lab records of the IPIMS
// Date: 10/23/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;

public class LabRecords {
  /////////////////////////////////////////////////////////////////
  // Main class
  /////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
   	/////////////////////////////////////////////////////////////////
	// Labels
	/////////////////////////////////////////////////////////////////

	JLabel instructionsLabel1 = new JLabel("         To upload new lab records to the system, make sure that the first item of the list is selected. In case you want to update an existing Lab Record, just select the item in the list");
	JLabel instructionsLabel2 = new JLabel("  below, make the changes and click Save button."); // Labels for instructions of the use case
	JLabel firstNameLabel = new JLabel("First name:"); // first name label
	JLabel lastNameLabel = new JLabel("Last name:"); // last name label
	JLabel dobLabel = new JLabel("Date of Birth: "); // DOB label
	JLabel labRecordLabel = new JLabel(" Lab Record: "); // lab record label
	JLabel dateLabel = new JLabel("Date: "); // date label
	JLabel successLabel = new JLabel(""); // success message label
	JLabel recordListLabel = new JLabel("Lab Record List:"); // Lab Record list label
	JLabel patientListLabel = new JLabel("Patients:"); // Patient list label

    	/////////////////////////////////////////////////////////////////
    	// Text fields
	/////////////////////////////////////////////////////////////////

    	JTextField firstNameField = new JTextField(10); // first name text field
    	JTextField lastNameField = new JTextField(10); // last name text field
    	JTextField dobField = new JTextField(10); // DOB field
    	JTextField dateField = new JTextField(10); // date field

	/////////////////////////////////////////////////////////////////
	// Text areas
	/////////////////////////////////////////////////////////////////

	JTextArea labRecordArea = new JTextArea(15, 20); // Text area for upload lab records
	JScrollPane scrollLabRecord = new JScrollPane(labRecordArea); // add scroll option to the text area

   	/////////////////////////////////////////////////////////////////
   	// Buttons
   	/////////////////////////////////////////////////////////////////

   	JButton saveButton = new JButton("Save"); // save button
   	JButton browseButton = new JButton("Upload File"); // browse button
   	JButton clearButton = new JButton("Clear"); // clear button
   	JButton searchPatientButton = new JButton("Search Patient"); // search patient button
   	JButton selectPatientButton = new JButton("Select"); // select patient button

   	/////////////////////////////////////////////////////////////////
	// Lists
	/////////////////////////////////////////////////////////////////

	// LAB RECORDS LIST
	Vector<Records> recordVector = new Vector<Records>(); // Vector of Records objects
	Records emptyObject = new Records(); // create a default object
	recordVector.add(emptyObject); // add the object to the vector

	JList<Records> recordList = new JList<Records>(recordVector); // creates a JList that show the content of the recordVector
	JScrollPane scrollList = new JScrollPane(recordList); // add scroll option to the list
	recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time
	recordList.addListSelectionListener(new ListSelectionListener() // List listener
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
	});

	//PATIENT LIST
	Vector<Patient> patientVector = new Vector<Patient>(); // Vector of Records objects
	Patient patient1 = new Patient("Patient", "Test1", "01/01/1970"); // create Patient object
	Patient patient2 = new Patient("Patient", "Test2", "01/01/1971"); // create Patient object
	Patient patient3 = new Patient("Patient", "Test3", "01/01/1972"); // create Patient object
	patientVector.add(patient1); // add the object to the vector
	patientVector.add(patient2); // add the object to the vector
	patientVector.add(patient3); // add the object to the vector

	JList<Patient> patientList = new JList<Patient>(patientVector); // creates a JList that show the content of the recordVector
	JScrollPane scrollPatientList = new JScrollPane(patientList); // add scroll option to the list
	patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time

	/////////////////////////////////////////////////////////////////
	// File chooser
	/////////////////////////////////////////////////////////////////

	final JFileChooser fc = new JFileChooser();

	/////////////////////////////////////////////////////////////////
	// Frames and panels
	/////////////////////////////////////////////////////////////////

	JFrame mainFrame = new JFrame("Lab Records");
	JFrame searchPatientFrame = new JFrame("Search Patient");

	JPanel instructionsPanel = new JPanel(); // Instructions panel
	instructionsPanel.setLayout(new GridLayout(3,1));
	instructionsPanel.add(instructionsLabel1);
	instructionsPanel.add(instructionsLabel2);

	JPanel datePanel = new JPanel(); // date panel
	datePanel.add(dateLabel);
	dateField.addKeyListener(new KeyAdapter() { // add listener to dateField
    		public void keyPressed(KeyEvent e) {
       			JTextField textField = (JTextField) e.getSource();
        		String date = textField.getText();
        		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){} // if backspace is pressed do nothing
        		else if(Character.isLetter(e.getKeyCode()) && date.length() == 1){
        			dateField.setText("");
				}
        		else if(date.length() == 2 || date.length() == 5){ //add the "/" after day and month
        			date += "/";
        			textField.setText(date);
			}
			else if(date.length() >= 10) // allow insertion up to ten characters
	        		textField.setText(textField.getText().substring(0, 9));
    		}
    	});
	datePanel.add(dateField);

	JPanel northPanel = new JPanel();
	northPanel.setLayout(new BorderLayout());
	northPanel.add(successLabel, BorderLayout.WEST);

	JPanel firstNamePanel = new JPanel(); // First name panel
	firstNamePanel.add(firstNameLabel);
	firstNamePanel.add(firstNameField);

	JPanel lastNamePanel = new JPanel(); // Last name panel
	lastNamePanel.add(lastNameLabel);
	lastNamePanel.add(lastNameField);

	JPanel dobPanel = new JPanel(); // User DOB panel
	dobPanel.add(dobLabel);
	dobField.addKeyListener(new KeyAdapter() { // add listener to dobField
    		public void keyPressed(KeyEvent e) {
       			JTextField textField = (JTextField) e.getSource();
        		String date = textField.getText();
        		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){} // if backspace is pressed do nothing
        		else if(Character.isLetter(e.getKeyCode()) && date.length() == 1){
        			dobField.setText("");
				}
        		else if(Character.isLetter(e.getKeyCode()) && date.length() > 1){
        			date = date.substring(0,date.length()-1);
        			textField.setText(date);
				}
        		else if(date.length() == 2 || date.length() == 5){ //add the "/" after day and month
        			date += "/";
        			textField.setText(date);
			}
			else if(date.length() >= 10) // allow insertion up to ten characters
	        		textField.setText(textField.getText().substring(0, 9));
    		}
    	});
    	dobPanel.add(dobField);

    	JPanel patientInfoPanel = new JPanel();
    	patientInfoPanel.setLayout(new GridLayout(1,3));
    	patientInfoPanel.add(firstNamePanel);
    	patientInfoPanel.add(lastNamePanel);
    	patientInfoPanel.add(dobPanel);
	
    	JPanel centerNorthPanel = new JPanel();
    	centerNorthPanel.setLayout(new BoxLayout(centerNorthPanel, BoxLayout.Y_AXIS));
    	centerNorthPanel.add(patientInfoPanel);
    	centerNorthPanel.add(Box.createRigidArea(new Dimension(0,5)));

	JPanel centerSouthPanel = new JPanel();
	centerSouthPanel.setLayout(new BorderLayout());
	centerSouthPanel.add(labRecordLabel, BorderLayout.WEST);
	centerSouthPanel.add(datePanel, BorderLayout.EAST);
	centerSouthPanel.add(scrollLabRecord, BorderLayout.SOUTH);

	JPanel buttonPanel = new JPanel(); // button panel
	buttonPanel.add(saveButton);
	buttonPanel.add(browseButton);
	buttonPanel.add(clearButton);

	JPanel centerPanel = new JPanel();
	centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	centerPanel.add(northPanel);
	centerPanel.add(centerNorthPanel);
	centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
	centerPanel.add(centerSouthPanel);
	centerPanel.add(buttonPanel);

	JPanel recordListPanel = new JPanel(); // Record List Panel
	recordListPanel.setLayout(new BoxLayout(recordListPanel, BoxLayout.Y_AXIS));
	recordListPanel.add(searchPatientButton);
	recordListPanel.add(Box.createRigidArea(new Dimension(0,10)));
	recordListPanel.add(recordListLabel);
	recordListPanel.add(Box.createRigidArea(new Dimension(0,3)));
	recordListPanel.add(scrollList);

	// Add panels to container
	Container content = mainFrame.getContentPane();
	content.setLayout(new BorderLayout());
	content.add(instructionsPanel, BorderLayout.NORTH);
	content.add(centerPanel, BorderLayout.WEST);
	content.add(recordListPanel, BorderLayout.EAST);

	JButton searchButton = new JButton("Search");
	Vector<Patient> searchVector = new Vector<Patient>();
	JScrollPane searchScroll = new JScrollPane(searchVector);
	// Add components to container
	Container patientContainer = searchPatientFrame.getContentPane();
	patientContainer.setLayout(new BoxLayout(patientContainer, BoxLayout.Y_AXIS));
	patientContainer.add(patientListLabel);
	patientContainer.add(searchScroll);
	patientContainer.add(selectPatientButton);
	patientContainer.add(firstNamePanel);
	patientContainer.add(lastNamePanel);
	
	searchButton.addActionListener(new ActionListener()
	{
		public void actionPerfomed(ActionEvent event)
		{
			for(int i = 0; i < patientVector.length; i++){
				if(firstNameField.getText.equals(patientVector.elementAt(i).getFirstName()) && lastNameField.getText.equals(patientVector.elementAt(i).getLastName)){
					searchVector.add(patientVector.elementAt(i));
				}
			}
		}
	});

	// Save button listener
	saveButton.addActionListener(new ActionListener()
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

			// fill the atributes of the Records class to save the complete object

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
				successLabel.setText("  The lab record was updated successfully.");
				successLabel.setForeground(Color.green);
			}
			else
			{
				recordVector.add(labRec);
				successLabel.setText("  The lab record is uploaded.");
				successLabel.setForeground(Color.green);
			}
			recordList.updateUI(); //update list

			//clear the fields to add new lab record
			firstNameField.setText("");
			lastNameField.setText("");
			dateField.setText("");
			dobField.setText("");
			labRecordArea.setText("");
		}
    });

    // Clear button listener
	clearButton.addActionListener(new ActionListener()
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
    });

    // browse button listener
	browseButton.addActionListener(new ActionListener()
	{
	        public void actionPerformed(ActionEvent event)
	        {
				int returnVal = fc.showOpenDialog(mainFrame);

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
						labRecordArea.setText("The file " + file.getName() + " could not be found");
					}
					catch (IOException exception)
					{
						labRecordArea.setText(exception.toString());
					}
				}
				else
				{
					labRecordArea.setText("Open command cancelled by user.");
				}
	        }
    });

    // search patient button listener
	searchPatientButton.addActionListener(new ActionListener()
	{
	        public void actionPerformed(ActionEvent event)
	        {
				searchPatientFrame.setVisible(true); // show the window
	        }
    });

    // select patient button listener
	selectPatientButton.addActionListener(new ActionListener()
	{
		Patient patient = new Patient();
	        public void actionPerformed(ActionEvent event)
	        {
				patient = patientList.getSelectedValue();

				firstNameField.setText(patient.getFirstName());
				lastNameField.setText(patient.getLastName());
				dobField.setText(patient.getDOB());

				searchPatientFrame.dispose();
	        }
    });

	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quit program on close
	mainFrame.setSize(1100,480); // set size of window
	mainFrame.setVisible(true); // show the window

	searchPatientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // quit program on close
	searchPatientFrame.setSize(400,400); // set size of window
	}

}
