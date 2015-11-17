import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SearchPatient extends JFrame{

	private static final long serialVersionUID = 1L;

	private JMenuBar menu;
	private JMenu menuOp1;
	private JMenu menuOp2;
	private JMenu menuOp3;
	private JMenu menuOp4;
	private JMenu menuOp5;
	private JMenu menuOp6;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;

	private JButton searchButton;
	private JButton selectButton;
	private JButton cancelButton;

	private JTextField firstNameField;
	private JTextField lastNameField;

	static int patientID;

	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel patientListLabel;

	private JLabel optionUpdateHCC;
	private JLabel optionPrescription;
	private JLabel optionViewPrescription;
	private JLabel optionLabRecord;
	private JLabel optionHCR;
	private JLabel optionUpdateHCR;

	private JList patientList;
	private JList searchList;

	private Vector searchVector;
	private Vector patientVector;

	private JScrollPane scrollPatientList;
	private JScrollPane scrollSearchList;

	private JPanel firstNamePanel;
	private JPanel lastNamePanel;
	private JPanel patientInfoPanel;
	private JPanel buttonPanel;
	private JPanel searchPanel;

	private JFrame optionsFrame;
	private JPanel optionsContainer;

	private Connection conn;
	private Statement statement;
	private ResultSet rs;

	public SearchPatient(final String type, final int docID)
	{

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

		//Menu
		//MENU ACTIONS

		// Action to view new patient registered
		class NewPatientAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public NewPatientAction() {
				putValue(SHORT_DESCRIPTION, "View list of new patients");
			}
			public void actionPerformed(ActionEvent e) {
				ViewRegisteredPatients vp = new ViewRegisteredPatients("new");
				vp.setVisible(true);
				ViewRegisteredPatients.hasNew = false;
			}
		}
		Action newPatientAction = new NewPatientAction();

		// Action to view all patient registered
		class AllPatientAction extends AbstractAction
		{ 
			private static final long serialVersionUID = 1L;
			public AllPatientAction() {
				putValue(SHORT_DESCRIPTION, "View list of all patients");
			}
			public void actionPerformed(ActionEvent e) {
				ViewRegisteredPatients vp = new ViewRegisteredPatients("all");
				vp.setVisible(true);
			}
		}
		Action allPatientAction = new AllPatientAction();

		// Action to open Statistical Report
		class StatsReportAction implements MenuListener
		{
			public void menuSelected(MenuEvent e) {
				StatsReport report = new StatsReport();
				setAlwaysOnTop(false);
				report.setVisible(true);
				report.setAlwaysOnTop(true);
			}
			public void menuDeselected(MenuEvent e){}
			public void menuCanceled(MenuEvent e){}
		}

		// Action to open Statistical Report
		class ProfileAction implements MenuListener
		{
			public void menuSelected(MenuEvent e) {
				Profile profilePage = new Profile("staff", docID, type);
				profilePage.setVisible(true);
				dispose();
			}
			public void menuDeselected(MenuEvent e){}
			public void menuCanceled(MenuEvent e){}
		}

		// MENU COMPONENTS
		menu = new JMenuBar();

		menuOp1 = new JMenu();
		menuOp2 = new JMenu();
		menuOp3 = new JMenu();
		menuOp4 = new JMenu();
		menuOp5 = new JMenu();

		menuOp1.setText("Profile");
		menuOp1.addMenuListener(new ProfileAction());


		optionsFrame = new JFrame("Options");

		optionsContainer = new JPanel();
		optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.Y_AXIS));
		optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));

		if (type.equals("Doctor")) //if a doctor is logging in
		{
			menuOp2.setText("Patients");
			menuOp3.setText("Appointments Request");
			menuOp4.setText("View Medical Alerts");

			menuItem1 = new JMenuItem("Search Patient");

			menuOp2.add(menuItem1);

			menu.add(menuOp1);
			menu.add(menuOp2);
			menu.add(menuOp3);
			menu.add(menuOp4);

			//optionsFrame
			optionUpdateHCC = new JLabel("Update Healthcare Condition");
			optionUpdateHCC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionUpdateHCC.addMouseListener(new MouseUpdateHCCListener());

			optionPrescription = new JLabel("e-Prescription");
			optionPrescription.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			//optionPrescription.addMouseListener(new MousePrescriptionListener());

			optionLabRecord = new JLabel("View Lab Records");
			optionLabRecord.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionLabRecord.addMouseListener(new MouseLabRecordListener());

			optionUpdateHCR = new JLabel("Update Healthcare Records");
			optionUpdateHCR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionUpdateHCR.addMouseListener(new MouseUpdateHCRListener());

			//add option to container
			optionsContainer.add(optionUpdateHCC);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionPrescription);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionLabRecord);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionLabRecord);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionUpdateHCR);
		}
		else if (type.equals("HSP")) //if the HSP is logging in
		{
			menuOp2.setText("Patients");
			menuOp3.setText("Appointment Request");
			menuOp4.setText("View Medical Alerts");
			menuOp5.setText("Generate Statistical Report");
			menuOp5.addMenuListener(new StatsReportAction());

			menuItem1 = new JMenuItem("Search Patient");
			menuOp6 = new JMenu("List of Registered Patient");
			menuOp6.setMnemonic(KeyEvent.VK_S);

			menuItem2 = new JMenuItem(newPatientAction);
			menuItem2.setText("List of New Registered Patient");
			menuItem3 = new JMenuItem(allPatientAction);
			menuItem3.setText("List of All Registered Patient");

			menuOp2.add(menuItem1);
			menuOp2.add(menuOp6);
			menuOp6.add(menuItem2);
			menuOp6.add(menuItem3);

			menu.add(menuOp1);
			menu.add(menuOp2);
			menu.add(menuOp3);
			menu.add(menuOp4);
			menu.add(menuOp5);

			//optionsFrame
			optionUpdateHCC = new JLabel("Update Healthcare Condition");
			optionUpdateHCC.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionUpdateHCC.addMouseListener(new MouseUpdateHCCListener());

			optionLabRecord = new JLabel("View Lab Records");
			optionLabRecord.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionLabRecord.addMouseListener(new MouseLabRecordListener());

			optionHCR = new JLabel("Upload Healthcare Records");
			optionHCR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionHCR.addMouseListener(new MouseUploadHCRListener());

			optionUpdateHCR = new JLabel("Update Healthcare Records");
			optionUpdateHCR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionUpdateHCR.addMouseListener(new MouseUpdateHCRListener());

			//add option to container
			optionsContainer.add(optionUpdateHCC);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionLabRecord);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionLabRecord);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionHCR);
			optionsContainer.add(Box.createRigidArea(new Dimension(20,5)));
			optionsContainer.add(optionUpdateHCR);
		}
		else if (type.equals("Pharmacist")) //if the Pharmacist is logging in
		{
			menuOp2.setText("Patients");

			menuItem1 = new JMenuItem("Search Patients");

			menuOp2.add(menuItem1);

			menu.add(menuOp1);
			menu.add(menuOp2);

			//optionsFrame
			optionViewPrescription = new JLabel("View e-Prescription");
			optionViewPrescription.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionViewPrescription.addMouseListener(new MouseViewPrescriptionListener());

			//add option to container
			optionsContainer.add(optionViewPrescription);

		}
		else if (type.equals("Nurse")) //if the nurse is logging in
		{
			menuOp2.setText("Patients");

			menuItem1 = new JMenuItem("Search Patients");

			menuOp2.add(menuItem1);

			menu.add(menuOp1);
			menu.add(menuOp2);

			//optionsFrame
			optionUpdateHCR = new JLabel("Update Healthcare Records");
			optionUpdateHCR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			optionUpdateHCR.addMouseListener(new MouseUpdateHCRListener());

			//add option to container
			optionsContainer.add(optionUpdateHCR);
		}

		//Labels
		firstNameLabel = new JLabel(" First name:"); // first name label
		lastNameLabel = new JLabel("Last name:"); // last name label
		patientListLabel = new JLabel("Patient List:"); // patient list label

		//Text Fields
		firstNameField = new JTextField(10); // first name text field
		lastNameField = new JTextField(10); // last name text field

		//Buttons
		searchButton = new JButton("Search"); // search button
		searchButton.addActionListener(new SearchButtonListener()); // add listener

		selectButton = new JButton("Select");
		selectButton.addActionListener(new SelectButtonListener()); // add listener

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelButtonListener()); // add listener

		//JPanels
		firstNamePanel = new JPanel(); // First name panel
		firstNamePanel.add(firstNameLabel);
		firstNamePanel.add(firstNameField);

		lastNamePanel = new JPanel(); // Last name panel
		lastNamePanel.add(lastNameLabel);
		lastNamePanel.add(lastNameField);

		patientInfoPanel = new JPanel();
		patientInfoPanel.setLayout(new BoxLayout(patientInfoPanel, BoxLayout.X_AXIS));
		patientInfoPanel.add(firstNamePanel);
		patientInfoPanel.add(lastNamePanel);
		patientInfoPanel.add(searchButton);

		buttonPanel = new JPanel(); // button panel
		buttonPanel.add(selectButton);
		buttonPanel.add(cancelButton);

		//Patient List
		patientVector = new Vector(); // Vector of Patient objects
		patientList = new JList(patientVector); // creates a JList that show the content of the recordVector

		scrollPatientList = new JScrollPane(patientList); // add scroll option to the list
		patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow the selection of only one item at a time

		String[] patients = new String[1000000];

		// Populates the patient list with the patients from the database
		if(type.equals("Doctor"))
		{
			int i=0;
			try
			{
				//checks if the patient is a patient of the doctor logged
				statement = conn.createStatement();
				rs = statement.executeQuery("SELECT * FROM appointments WHERE `docID`='" + docID + "' ORDER BY `patientID`");

				while ( rs.next() )
				{
					int patientID = rs.getInt("patientID");
					patients[i] = String.valueOf(patientID);

					i++;
				}

				String[] patientSet = (String[]) new HashSet(Arrays.asList(patients)).toArray(new String[0]);

				//gets information from the specific set of patients
				for(int j = 0; j < patientSet.length; j++)
				{
					statement = conn.createStatement();
					rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`='" + patientSet[j] + "';");

					while ( rs.next() )
					{
						Patient obj = new Patient();
						obj.setFirstName(rs.getString("fname"));
						obj.setLastName(rs.getString("lname"));
						obj.setDOB(rs.getString("dob"));
						obj.setPatientId(Integer.parseInt(patientSet[j]));

						patientVector.add(obj);
					}
				}

			}
			catch (Exception e)
			{
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
				System.err.println(e);
			}
		}
		else
		{
			try
			{
				statement = conn.createStatement();
				rs = statement.executeQuery("SELECT * FROM patient ORDER BY fname");

				while ( rs.next() )
				{
					Patient obj = new Patient();
					obj.setFirstName(rs.getString("fname"));
					obj.setLastName(rs.getString("lname"));
					obj.setDOB(rs.getString("dob"));
					obj.setPatientId(rs.getInt("idpatient"));

					patientVector.add(obj);
				}
			}
			catch (Exception e)
			{
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
			}
		}

		searchVector = new Vector();

		// set options frame
		optionsFrame.add(optionsContainer);
		optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the window on close
		optionsFrame.setSize(300,150); // set size of window
		optionsFrame.setLocation(600, 280);
		optionsFrame.setVisible(false);

		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		searchPanel.add(patientInfoPanel);
		searchPanel.add(patientListLabel);
		searchPanel.add(scrollPatientList);
		searchPanel.add(buttonPanel);

		Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 10);
		searchPanel.setBorder(padding);

		setJMenuBar(menu);
		add(searchPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,580);

	}

	//Search Button Listener
	private class SearchButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			// if both names are entered on the search
			if(!firstNameField.getText().equals("") && !lastNameField.getText().equals(""))
			{
				searchVector.clear();
				for(int i = 0; i < patientVector.size(); i++)
				{
					Patient p1 = (Patient) patientVector.elementAt(i);
					if(firstNameField.getText().matches(p1.getFirstName()) && lastNameField.getText().equals(p1.getLastName()))
						searchVector.add(p1);
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found! 1");
				else
				{
					searchList = new JList(searchVector);
					searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollSearchList = new JScrollPane(searchList);
					searchPanel.removeAll();
					searchPanel.add(patientInfoPanel);
					searchPanel.add(patientListLabel);
					searchPanel.add(scrollSearchList);
					searchPanel.add(buttonPanel);
					repaint();
					revalidate();
				}
			}

			// if only the first name is entered on the search
			else if (lastNameField.getText().equals("") && !firstNameField.getText().equals(""))
			{
				searchVector.clear();
				System.out.println("dasdas");
				for(int i = 0; i < patientVector.size(); i++)
				{
					System.out.println("dasdas" + i);
					Patient p1 = (Patient) patientVector.elementAt(i);
					if(firstNameField.getText().matches(p1.getFirstName()))
						searchVector.add(p1);
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!2");
				else
				{
					searchList = new JList(searchVector);
					searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollSearchList = new JScrollPane(searchList);
					searchPanel.removeAll();
					searchPanel.add(patientInfoPanel);
					searchPanel.add(patientListLabel);
					searchPanel.add(scrollSearchList);
					searchPanel.add(buttonPanel);
					repaint();
					revalidate();
				}
			}
			// if only the last name is entered on the search
			else if (firstNameField.getText().equals("") && !lastNameField.getText().equals(""))
			{
				searchVector.clear();
				for(int i = 0; i < patientVector.size(); i++)
				{
					Patient p1 = (Patient) patientVector.elementAt(i);
					if(lastNameField.getText().matches(p1.getLastName()))
						searchVector.add(p1);
				}
				if(searchVector.size() == 0)
					JOptionPane.showMessageDialog(patientListLabel, "Patient not found!");
				else
				{
					searchList = new JList(searchVector);
					searchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollSearchList = new JScrollPane(searchList);
					searchPanel.removeAll();
					searchPanel.add(patientInfoPanel);
					searchPanel.add(patientListLabel);
					searchPanel.add(scrollSearchList);
					searchPanel.add(buttonPanel);
					repaint();
					revalidate();
				}
			}
			// if the fields are empty, show the complete set of patient
			else
			{
				searchPanel.removeAll();
				searchPanel.add(patientInfoPanel);
				searchPanel.add(patientListLabel);
				searchPanel.add(scrollPatientList);
				searchPanel.add(buttonPanel);
				repaint();
				revalidate();
			} 
		}
	}

	//Select Patient Button Listener
	private class SelectButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			optionsFrame.setVisible(true);
			Patient p1 = (Patient) patientList.getSelectedValue();
			patientID = p1.getpatientID();
		}
	}

	//Select Patient Button Listener
	private class CancelButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			dispose();
		}
	}

	private class MouseUpdateHCCListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent e)
		{
			ViewUpdateHCC hcc = new ViewUpdateHCC(patientID);
			hcc.setVisible(true);
		}
		public void mouseEntered(MouseEvent arg0){}
		public void mouseExited(MouseEvent arg0){}
		public void mousePressed(MouseEvent arg0){}
		public void mouseReleased(MouseEvent arg0){}
	}


	private class MouseViewPrescriptionListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent e)
		{
			ViewPrescription vp = new ViewPrescription(patientID);
			vp.setVisible(true);
		}
		public void mouseEntered(MouseEvent arg0){}
		public void mouseExited(MouseEvent arg0){}
		public void mousePressed(MouseEvent arg0){}
		public void mouseReleased(MouseEvent arg0){}
	}


	private class MouseLabRecordListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent e)
		{
			ViewLabRecords rec = new ViewLabRecords(patientID);
			rec.setVisible(true);
		}
		public void mouseEntered(MouseEvent arg0){}
		public void mouseExited(MouseEvent arg0){}
		public void mousePressed(MouseEvent arg0){}
		public void mouseReleased(MouseEvent arg0){}
	}

	private class MouseUploadHCRListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent e)
		{
			UploadMedicalReport medRep = new UploadMedicalReport(patientID);
			medRep.setVisible(true);
		}
		public void mouseEntered(MouseEvent arg0){}
		public void mouseExited(MouseEvent arg0){}
		public void mousePressed(MouseEvent arg0){}
		public void mouseReleased(MouseEvent arg0){}
	}

	private class MouseUpdateHCRListener implements MouseListener 
	{
		public void mouseClicked(MouseEvent e)
		{
			UpdateMedicalReport medRep = new UpdateMedicalReport(patientID);
			medRep.setVisible(true);
		}
		public void mouseEntered(MouseEvent arg0){}
		public void mouseExited(MouseEvent arg0){}
		public void mousePressed(MouseEvent arg0){}
		public void mouseReleased(MouseEvent arg0){}
	}
}
