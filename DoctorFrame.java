import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DoctorFrame extends JPanel{
	JFrame frame;
	JFrame searchPatientFrame;
	
	JPanel mainPanel;
	JPanel epPanel;
	
	JButton labRecordsButton;
	JButton hcButton;
	JButton ePrescribeButton;
	private JButton cancel;
	JButton save;
	
	JTextField nameField;
	JTextField DOBField;
	JTextField prescriptionField;
	
	String presID;
	String patientID;
	String date;
	String presInfo;
	Writer writer;
	String filename;
	SaveEPButtonListener saveEP;
	int space;

	//Patient Search
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel lastNameSearchLabel;
	private JLabel prescriptionListLabel;
	private JLabel patientListLabel;
	private JLabel dateLabel;
	private JLabel dobLabel;
	private JLabel labRecordLabel;
	private JLabel instructionsLabel1;
	private JLabel instructionsLabel2;
	private JLabel successLabel;

	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField firstNameSearchLabel;
	private JTextField firstNameSearchField;
	private JTextField lastNameSearchField;
	private JTextField dobField;
	private JTextField dateField;

	private JButton searchPatientButton;
	private JButton selectPatientButton;
	private JButton searchButton;
	private JButton clearButton;
	private JButton saveButton;
	private JButton browseButton;

	private JList<Patient> patientList;
	private JList<Prescription> prescriptionList;
	private JList<Patient> searchList;

	private Vector<Patient> searchVector;
	private Vector<Prescription> prescriptionVector;
	private Vector<Patient> patientVector;

	private JScrollPane scrollPresciptionList;
	private JScrollPane scrollPatientList;
	private JScrollPane scrollSearchList;


	private JPanel firstNamePanel;
	private JPanel firstNameSearch;
	private JPanel lastNamePanel;
	private JPanel lastNameSearch;
	private JPanel datePanel;
	private JPanel dobPanel;
	private JPanel buttonPanel;
	private JPanel searchFields;
	private JPanel instructionsPanel;
	private JPanel northPanel;
	private JPanel centerNorthPanel;
	private JPanel centerSouthPanel;
	private JPanel centerPanel;
	private JPanel patientInfoPanel;
	private JPanel prescriptionListPanel;
	
	private JScrollPane scrollLabRecord;
	private JScrollPane prescriptionScrollList;
	private JScrollPane scrollList;


	private Container patientContainer;

	private Connection conn;
	private Statement statement;
	private ResultSet rs;

	public DoctorFrame() throws IOException
	{
		super();

		try
		{
			// "Load" the JDBC driver
			Class.forName("java.sql.Driver");

			// Establish the connection to the database
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url,"root","f00tball2015");
		}

		catch (Exception e)
		{
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}



		//Labels
		firstNameLabel = new JLabel(" First name:"); // first name label
		lastNameLabel = new JLabel("Last name:"); // last name label
		prescriptionListLabel = new JLabel("Prescription List:");
		patientListLabel = new JLabel("Patient List:");
		dateLabel = new JLabel("Date:");
		dobLabel = new JLabel("Date of birth:");

		//Text Fields
		firstNameField = new JTextField(10); // first name text field
		lastNameField = new JTextField(10); // last name text field
		firstNameSearchField = new JTextField(10); // first name field to search patient
		lastNameSearchField = new JTextField(10); // last name field to search patient
		dobField = new JTextField(10); // DOB field
		dateField = new JTextField(10); // date field

		//Buttons
		labRecordsButton = new JButton("Lab Records");
		hcButton = new JButton("View/Update Health Care Conditions");
		ePrescribeButton = new JButton("e-Prescribe");
		save = new JButton("Save");
		cancel = new JButton("Cancel");

		searchPatientButton = new JButton("Search Patient"); // search patient button
		searchPatientButton.addActionListener(new PatientSearchButtonListener()); // add listener

		selectPatientButton = new JButton("Select"); // select patient button
		selectPatientButton.addActionListener(new PatientSelectButtonListener()); // add listener

		searchButton = new JButton("Search"); // search button
		searchButton.addActionListener(new SearchButtonListener()); // add listener


		//Frames and Panels
		frame = new JFrame("Doctor");
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		frame.setSize(300,150); // set size of window


		mainPanel.add(labRecordsButton);
		mainPanel.add(hcButton);
		mainPanel.add(ePrescribeButton);

		hcButtonListener hcListener = new hcButtonListener();
		labButtonListener labListener = new labButtonListener();
		ePrescribeButtonListener ePrescribeListener = new ePrescribeButtonListener();
		saveEP = new SaveEPButtonListener();

		hcButton.addActionListener(hcListener);
		labRecordsButton.addActionListener(labListener);
		ePrescribeButton.addActionListener(ePrescribeListener);

		frame.add(mainPanel);
		frame.setVisible(true);

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

		prescriptionListPanel = new JPanel(); // Record List Panel
		prescriptionListPanel.setLayout(new BoxLayout(prescriptionListPanel, BoxLayout.Y_AXIS));
		prescriptionListPanel.add(searchPatientButton);
		prescriptionListPanel.add(Box.createRigidArea(new Dimension(0,10)));
		prescriptionListPanel.add(prescriptionListLabel);
		prescriptionListPanel.add(Box.createRigidArea(new Dimension(0,3)));
		prescriptionListPanel.add(scrollList);

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
		add(prescriptionListPanel, BorderLayout.EAST);

		//Prescription List

		prescriptionVector = new Vector<Prescription>(); // Vector of Records objects
		Prescription emptyObject = new Prescription(); // create a default object
		prescriptionVector.add(emptyObject); // add the object to the vector

		//Populates the record list with the values from the database
		try
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM prescription");

            while ( rs.next() )
            {
            	Prescription obj = new Prescription();
            	obj.setPrescriptionID(rs.getString(1));
            	obj.setPatientID(rs.getString(2));
            	obj.setPrescriptionInfo(rs.getString(3));
            	obj.setDate(rs.getString(4));
            	obj.setIsMedication(rs.getBoolean(5));
            	obj.setIsFilled(rs.getBoolean(6));

            	prescriptionVector.add(obj);
            }
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
     		System.err.println(e.getMessage());
		}

		prescriptionList = new JList<Prescription>(prescriptionVector); // creates a JList that show the content of the recordVector
		prescriptionScrollList = new JScrollPane(prescriptionList); // add scroll option to the list
		prescriptionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time
	/*	prescriptionList.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(LIstSelectionEvent evt) {
				if(evt.getValueIsAdjusting())
					return;
			}
		}; // List listener*/


		//Patient List

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
	}

	
	public class SaveEPButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				String name = nameField.getText();
				String DOB = DOBField.getText();
				String prescription = prescriptionField.getText();

				//filename parser: "lastname_firstname.txt"
				space = name.indexOf(' ');
				filename = name.substring(space+1) + " " + name.substring(0, space-1) + ".txt";
			try{
				writer = new BufferedWriter(new FileWriter(filename));
				writer.write(name+"\r\n");
				writer.write(DOB+"\r\n");
				writer.write(prescription);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			finally {
			try{
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			}

			frame.remove(epPanel);
			epPanel = null;
			frame.validate();
			frame.repaint();
		}
	}

	public class hcButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			UpdateHCFrame hcPanel = null;
			try {
				hcPanel = new UpdateHCFrame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frame.add(hcPanel);
			frame.validate();
			frame.repaint();
		}
	}
	public class labButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			LabRecords record = new LabRecords();
		}
	}
	public class ePrescribeButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
				epPanel = new JPanel();
				epPanel.add(nameField);
				epPanel.add(DOBField);
				epPanel.add(prescriptionField);
				epPanel.add(save);
				epPanel.add(cancel);
				save.addActionListener(saveEP);
				frame.add(epPanel);
				frame.validate();
				frame.repaint();
		}
	}

  	//Search Button Listener
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

	//PatientSearch Button Listener
	private class PatientSearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			searchPatientFrame.setVisible(true); // open the window
		}
	}

	//Select Patient Button Listener
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
