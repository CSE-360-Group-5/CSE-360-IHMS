//////////////////////////////////////////////////////////////////
// Name: Registration.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the upload Lab records of the IPIMS
// Date: 10/23/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
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

    JLabel firstNameLabel = new JLabel("First name:"); // first name label
    JLabel lastNameLabel = new JLabel("Last name:"); // last name label
    JLabel dobLabel = new JLabel("Date of Birth: "); // DOB label
    JLabel labRecordLabel = new JLabel("Lab Record: "); // lab record label
    JLabel dateLabel = new JLabel("Date: "); // date label
    JLabel successLabel = new JLabel("");

    /////////////////////////////////////////////////////////////////
    // Text fields
    /////////////////////////////////////////////////////////////////

    JTextField firstNameField = new JTextField(15); // first name text field
    JTextField lastNameField = new JTextField(15); // last name text field
    JTextField dobField = new JTextField(10); // DOB field
    JTextField dateField = new JTextField(10); // date field

    /////////////////////////////////////////////////////////////////
	// Text areas
    /////////////////////////////////////////////////////////////////

	JTextArea labRecordArea = new JTextArea(15, 25);
	JScrollPane scrollLabRecord = new JScrollPane(labRecordArea);

    /////////////////////////////////////////////////////////////////
    // Buttons
    /////////////////////////////////////////////////////////////////

    JButton saveButton = new JButton("Save"); // save button

    /////////////////////////////////////////////////////////////////
	// Lists
	/////////////////////////////////////////////////////////////////

	Vector<Records> recordVector = new Vector<Records>();
	Records emptyObject = new Records();
	recordVector.add(emptyObject);

	JList<Records> recordList = new JList<Records>(recordVector);
	recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	recordList.addListSelectionListener(new ListSelectionListener()
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

    /////////////////////////////////////////////////////////////////
    // Frames and panels
    /////////////////////////////////////////////////////////////////

    JFrame frame = new JFrame("Lab Records");

    JPanel firstNamePanel = new JPanel(); // First name panel
    firstNamePanel.add(firstNameLabel);
    firstNamePanel.add(firstNameField);

    JPanel lastNamePanel = new JPanel(); // Last name panel
    lastNamePanel.add(lastNameLabel);
    lastNamePanel.add(lastNameField);

    JPanel dobPanel = new JPanel(); // User DOB panel
    dobPanel.add(dobLabel);
    dobPanel.add(dobField);

    JPanel LabRecordPanel = new JPanel();
    LabRecordPanel.add(labRecordLabel);
    LabRecordPanel.add(dateLabel);
    LabRecordPanel.add(dateField);
    LabRecordPanel.add(scrollLabRecord);

    JPanel submitPanel = new JPanel(); // Submit button panel
    submitPanel.add(saveButton);

    JPanel recordListPanel = new JPanel();
    recordListPanel.add(recordList);

    // Add panels to container
    Container content = frame.getContentPane();
    content.setLayout(new GridLayout(5,2));
    content.add(successLabel);
    content.add(firstNamePanel);
    content.add(lastNamePanel);
    content.add(dobPanel);
    content.add(LabRecordPanel);
    content.add(submitPanel);
    content.add(recordListPanel);

    // Save button listener
    saveButton.addActionListener(new ActionListener()
    {
        public void actionPerformed(ActionEvent event)
        {
			/////////////////////////////////////////////////////////////////
			// data variables for the user
			/////////////////////////////////////////////////////////////////
			Records labRec = new Records();

			String firstName = firstNameField.getText(); // first name of person
			String lastName = lastNameField.getText(); // last name of person
			String dob = dobField.getText(); // date of birth of person
			String date = dateField.getText(); // date of the submitted lab record
			String labRecord = labRecordArea.getText(); // lab record for the patient

			/////////////////////////////////////////////////////////////////
			// Information will not be displayed back to the user but will be put into the database
       		// START TEST
       		/////////////////////////////////////////////////////////////////

			// Check when the user is a patient and display his information - Test Purpose

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
				successLabel.setText("The lab record was updated successfully");
				successLabel.setForeground(Color.green);
			}
			else
			{
				recordVector.add(labRec);
				successLabel.setText("The lab record is uploaded");
				successLabel.setForeground(Color.green);
			}
			recordList.updateUI();

       	// END TEST
        }
    });

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // quit program on close
    //frame.setSize(300,480); // set size of window
    frame.pack();
    frame.setVisible(true); // show the window
  }

}
