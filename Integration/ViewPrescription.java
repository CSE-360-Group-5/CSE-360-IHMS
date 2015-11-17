//////////////////////////////////////////////////////////////////
// Name: ViewPrescription.java
// Authors: Nathan Chancellor
// Description: GUI interface for the pharmacist
// Date: 11/15/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ViewPrescription extends JFrame {

	private static final long serialVersionUID = 1L;

	// ///////////////////////////////////////////////////////////////
	// JLabels
	// ///////////////////////////////////////////////////////////////

	private JLabel prescriptionDateLabel;
	private JLabel patientIDLabel;
	private JLabel prescriptionsListLabel;

	private String patientName;

	// ///////////////////////////////////////////////////////////////
	// JTextFields
	// ///////////////////////////////////////////////////////////////

	private JTextField dateField;
	private JTextField nameField;

	// ///////////////////////////////////////////////////////////////
	// JPanel
	// ///////////////////////////////////////////////////////////////

	private JPanel prescriptPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private JPanel prescriptInfoPanel;
	private JPanel datePanel;
	private JPanel idPanel;

	// ///////////////////////////////////////////////////////////////
	// JButton
	// ///////////////////////////////////////////////////////////////

	private JButton filledButton;

	// ///////////////////////////////////////////////////////////////
	// Vectors and JLists
	// ///////////////////////////////////////////////////////////////

	private Vector prescriptionVector;

	private JList prescriptionsList;

	// ///////////////////////////////////////////////////////////////
	// JTextArea
	// ///////////////////////////////////////////////////////////////

	private JTextArea prescriptionsArea;
	private JScrollPane scrollPrescriptionArea;
	private JScrollPane scrollPrescriptionListArea;

	// ///////////////////////////////////////////////////////////////
	// Database components
	// ///////////////////////////////////////////////////////////////

	private Connection conn;
	private Statement statement;
	private ResultSet rs;

	public ViewPrescription(int id) {
		// ///////////////////////////////////////////////////////////////
		// Database connection
		// ///////////////////////////////////////////////////////////////

		try {
			// "Load" the JDBC driver
			Class.forName("java.sql.Driver");

			// Establish the connection to the database
			String url = "jdbc:mysql://localhost:3306/cse";
			conn = DriverManager.getConnection(url, "root", "admin");
		}

		catch (Exception e) {
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}

		// Instantiate components

		//Lists
		prescriptionVector = new Vector();

		try 
		{
			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`=" + id);

			while (rs.next()) // loads the prescriptions from the database and adds them to the list
			{
				patientName = rs.getString("fname") + " " + rs.getString("lname");
			}

			statement = conn.createStatement();
			rs = statement.executeQuery("SELECT * FROM prescription WHERE `patientID`=" + id);

			while (rs.next()) // loads the prescriptions from the database and adds them to the list
			{
				if(rs.getString("filled").equals("false") && rs.getString("medication").equals("true"))
				{
					Prescription prescript = new Prescription();

					prescript.setPatientID(rs.getInt("patientID"));
					prescript.setPrescriptInfo(rs.getString("prescriptionInfo"));
					prescript.setPrescriptID(rs.getInt("prescriptionID"));
					prescript.setMedication(rs.getString("medication"));
					prescript.setDate(rs.getString("date"));
					prescript.setFilled(rs.getString("filled"));

					prescriptionVector.add(prescript);
				}
			}

		} 
		catch (Exception e) 
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			System.err.println(e);
		}

		prescriptionsList = new JList(prescriptionVector);
		prescriptionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		prescriptionsList.addListSelectionListener(new ListListener()); // List listener
		scrollPrescriptionListArea = new JScrollPane(prescriptionsList);

		//JTEXTAREAS
		prescriptionsArea = new JTextArea(15,30);
		prescriptionsArea.setEditable(false);
		scrollPrescriptionArea = new JScrollPane(prescriptionsArea);

		//JLABELS
		patientIDLabel = new JLabel("Patient Name:");
		prescriptionDateLabel = new JLabel("Date:");
		prescriptionsListLabel = new JLabel("Prescriptions:");

		//JTEXTFIELDS
		dateField = new JTextField(10);
		dateField.setEditable(false);
		nameField = new JTextField(10);
		nameField.setEditable(false);
		nameField.setText(patientName);

		//Button
		filledButton = new JButton("Mark as filled");
		filledButton.addActionListener(new FilledButtonListener());

		//PANELS
		westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		westPanel.add(prescriptionsListLabel);
		westPanel.add(Box.createRigidArea(new Dimension(0,5)));
		westPanel.add(scrollPrescriptionListArea);

		datePanel = new JPanel();
		datePanel.add(prescriptionDateLabel);
		datePanel.add(dateField);

		idPanel = new JPanel();
		idPanel.add(patientIDLabel);
		idPanel.add(nameField);

		prescriptInfoPanel = new JPanel();
		prescriptInfoPanel.setLayout(new BoxLayout(prescriptInfoPanel, BoxLayout.X_AXIS));
		prescriptInfoPanel.add(idPanel);
		prescriptInfoPanel.add(datePanel);

		eastPanel = new JPanel();
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(prescriptInfoPanel);
		eastPanel.add(scrollPrescriptionArea);
		eastPanel.add(filledButton);

		prescriptPanel = new JPanel();
		prescriptPanel.setLayout(new BorderLayout());
		prescriptPanel.add(westPanel, BorderLayout.WEST);
		prescriptPanel.add(eastPanel, BorderLayout.EAST);

		Border padding = BorderFactory.createEmptyBorder(20, 20, 10, 10);
		prescriptPanel.setBorder(padding);

		add(prescriptPanel); // add main panel to the frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // set the default close operation
		setTitle("View Prescriptions"); // sets the title of the frame
		setSize(850, 400); // set size of window

	}

	// List listener
	private class ListListener implements ListSelectionListener {
		Prescription prescript1 = new Prescription();
		public void valueChanged (ListSelectionEvent event) 
		{
			prescript1 = (Prescription) prescriptionsList.getSelectedValue();

			dateField.setText(prescript1.getDate());
			prescriptionsArea.setText(prescript1.toStringArea());
		}
	}

	// Button listener
	private class FilledButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			Prescription prescript2 = (Prescription) prescriptionsList.getSelectedValue();

			try 
			{
				statement = conn.createStatement();
				statement.executeUpdate("UPDATE `cse`.`prescription` SET `filled`='true' WHERE `prescriptionID`=" + prescript2.getPrescriptID());

				prescriptionVector.remove(prescript2);
				prescriptionsList.updateUI();
				prescriptionsArea.setText("");
				dateField.setText("");

			} 
			catch (Exception e) 
			{
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
				System.err.println(e);
			}
		}
	}
}
