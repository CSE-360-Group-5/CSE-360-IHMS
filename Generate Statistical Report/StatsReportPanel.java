//////////////////////////////////////////////////////////////////
// Name: statsReport.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the generation of the statistical report
// Date: 10/9/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.*;
//import java.awt.*;
import java.awt.event.*;

public class StatsReportPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	/////////////////////////////////////////////////////////////////
	// JButton
	/////////////////////////////////////////////////////////////////

	private JButton submitButton;
	
	/////////////////////////////////////////////////////////////////
	// JPanel
	/////////////////////////////////////////////////////////////////
	
	private JPanel submitPanel;
	
	/////////////////////////////////////////////////////////////////
	// Arrays
	/////////////////////////////////////////////////////////////////
	
	private String[] sex; // Sex array (Male, Female)
	private String[] maritalStatus; // Marital status array (Single, Married)
	private String[] education; // Education array (No Degree, High School or Equivalent, Some College, Bachelors, Masters, Doctorate)
	private String[] race; // Race array (Asian, Latino, Black/African-American, Pacific Islander, Caucasian, Other
	private String [] patientType; // Patient (Pediatric, Vascular, Neurologic, Gynocology, Orthopedic, Other)
	
	/////////////////////////////////////////////////////////////////
	// JTextArea
	/////////////////////////////////////////////////////////////////
	
	private JTextArea reportArea;
	
	/////////////////////////////////////////////////////////////////
	// Database components
	/////////////////////////////////////////////////////////////////
	
	private Connection conn;
	private Statement statement;
	private ResultSet rs;
	
	public StatsReportPanel() 
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
		
		// GUI elements and construction
	    submitButton = new JButton("Generate");
	    submitButton.addActionListener(new SubmitListener());
	
	    submitPanel = new JPanel();
	    reportArea = new JTextArea(60,60);
	    reportArea.setEditable(false);

	    // Fill arrays with information from the database
	    
	    submitPanel.setLayout(new BoxLayout(submitPanel, BoxLayout.Y_AXIS));
	    submitPanel.add(submitButton);
	    submitPanel.add(reportArea);
	
	    add(submitPanel);
	
	}
    
	// Functional logic
    private class  SubmitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
        	
        	int count = 0;
        	int count2 = 0;
        	try
    		{
    			statement = conn.createStatement();
    			rs = statement.executeQuery("SELECT * FROM patient");
                
                while ( rs.next() ) 
                {
                	sex[count] = rs.getString(7);
                	System.out.println(count + " sex: " + rs.getString(7));
                	maritalStatus[count] = rs.getString(9);
                	education[count] = rs.getString(10);
                	race[count] = rs.getString(11);
                	count++;
                }
                
                statement = conn.createStatement();
                rs = statement.executeQuery("SELECT * FROM staff");
                while ( rs.next() ) 
                {
                	sex[count] = rs.getString(8);
                	maritalStatus[count] = rs.getString(9);
                	education[count] = rs.getString(10);
                	race[count] = rs.getString(11);
                	count++;
                }
                
                
                statement = conn.createStatement();
                rs = statement.executeQuery("SELECT * FROM appointments");
                while ( rs.next() ) 
                {
                	patientType[count2] = rs.getString(3);
                	count2++;
                }
                
    		}
    		catch (Exception e)
    		{
    			System.err.println("Got an exception! "); 
                System.err.println(e.getMessage());
                System.err.println(e);
    		}
        	
        	
        	
          // initialize all count variables to zero
          int numOfMales = 0;
          int numOfFemales = 0;
          int numOfSing = 0;
          int numOfMar = 0;
          int numOfND = 0;
          int numOfHS = 0;
          int numOfSC = 0;
          int numOfBach = 0;
          int numOfMast = 0;
          int numOfDoc = 0;
          int numOfAsian = 0;
          int numOfLat = 0;
          int numOfBAA = 0;
          int numOfPacIs = 0;
          int numOfCauc = 0;
          int numOfOtherRace = 0;
          int numOfVasc = 0;
          int numOfGyno = 0;
          int numOfPed = 0;
          int numOfOrtho = 0;
          int numOfNeuro = 0;
          int numOfOtherType = 0;

          // gender counts
          for (int i = 0; i < count; i++) {
            if (sex[i].equalsIgnoreCase("Male"))
              numOfMales++;
            else
              numOfFemales++;
          }

          // marital status counts
          for (int i = 0; i < count; i++) {
            if (maritalStatus[i].equalsIgnoreCase("Single"))
              numOfSing++;
            else
              numOfMar++;
          }

          // education counts
          for (int i = 0; i < count; i++) {
            if (education[i].equalsIgnoreCase("No Degree"))
              numOfND++;
            else if (education[i].equalsIgnoreCase("High School or Equivalent"))
              numOfHS++;
            else if (education[i].equalsIgnoreCase("Some College"))
              numOfSC++;
            else if (education[i].equalsIgnoreCase("Bachelors"))
              numOfBach++;
            else if (education[i].equalsIgnoreCase("Masters"))
              numOfMast++;
            else
              numOfDoc++;
          }

          // race counts
          for(int i = 0; i < count; i++) {
            if (race[i].equalsIgnoreCase("Asian"))
              numOfAsian++;
            else if (race[i].equalsIgnoreCase("Latino"))
              numOfLat++;
            else if (race[i].equalsIgnoreCase("Black/African-American"))
              numOfBAA++;
            else if (race[i].equalsIgnoreCase("Caucasian"))
              numOfCauc++;
            else if (race[i].equalsIgnoreCase("Pacific Islander"))
              numOfPacIs++;
            else
              numOfOtherRace++;
          }

          // patient type counts
          for(int i = 0; i < count2; i++) {
            if (patientType[i].equalsIgnoreCase("Vascular"))
              numOfVasc++;
            else if (patientType[i].equalsIgnoreCase("Pediatric"))
              numOfPed++;
            else if (patientType[i].equalsIgnoreCase("Neurologic"))
              numOfNeuro++;
            else if (patientType[i].equalsIgnoreCase("Gynocology"))
              numOfGyno++;
            else if (patientType[i].equalsIgnoreCase("Orthopedic"))
              numOfOrtho++;
            else
              numOfOtherType++;
          }

          // generate the report
          String statsReport =
          "Total number of patients in time span: " + count + "\n" +
          "-----------------------------------------\n" +
          "Gender statistics: \n" +
          "Number of males: " + numOfMales + " (" + numOfMales / (double)count * 100 + "%)\n" +
          "Number of females: " + numOfFemales + " (" + numOfFemales / (double)count * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Marital status statistics: \n" +
          "Number of singles: " + numOfSing + " (" + numOfSing / (double)count * 100 + "%)\n" +
          "Number of married: " + numOfMar + " (" + numOfMar / (double)count * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Education statistics: \n" +
          "Number of no degrees: " + numOfND + " (" + numOfND / (double)count * 100 + "%)\n" +
          "Number of high school diplomas: " + numOfHS + " (" + numOfHS / (double)count * 100 + "%)\n" +
          "Number of some college experience: " + numOfSC + " (" + numOfSC / (double)count * 100 + "%)\n" +
          "Number of Bachelors: " + numOfBach + " (" + numOfBach / (double)count * 100 + "%)\n" +
          "Number of Masters: " + numOfMast + " (" + numOfMast / (double)count * 100 + "%)\n" +
          "Number of Doctorate: " + numOfDoc + " (" + numOfDoc / (double)count * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Race statistics: \n" +
          "Number of Caucasians: " + numOfCauc + " (" + numOfCauc / (double)count * 100 + "%)\n" +
          "Number of Latinos: " + numOfLat + " (" + numOfLat / (double)count * 100 + "%)\n" +
          "Number of Asians: " + numOfAsian + " (" + numOfAsian / (double)count * 100 + "%)\n" +
          "Number of Black/African-Americans: " + numOfBAA + " (" + numOfBAA / (double)count * 100 + "%)\n" +
          "Number of Pacific Islanders: " + numOfPacIs + " (" + numOfPacIs / (double)count * 100 + "%)\n" +
          "Number of Others: " + numOfOtherRace + " (" + numOfOtherRace / (double)count * 100 + "%)\n" +
          "-----------------------------------------\n" +
          "Patient type statistics: \n" +
          "Number of vascular patients: " + numOfVasc + " (" + numOfVasc / (double)count2 * 100 + "%)\n" +
          "Number of pediatric patients: " + numOfPed + " (" + numOfPed / (double)count2 * 100 + "%)\n" +
          "Number of neurologic patients: " + numOfNeuro + " (" + numOfNeuro / (double)count2 * 100 + "%)\n" +
          "Number of gynocology patients: " + numOfGyno + " (" + numOfGyno / (double)count2 * 100 + "%)\n" +
          "Number of orthopedic patients: " + numOfOrtho + " (" + numOfOrtho / (double)count2 * 100 + "%)\n" +
          "Number of other patients: " + numOfOtherType + " (" + numOfOtherType / (double)count2 * 100 + "%)\n" +
          "-----------------------------------------";

          reportArea.setText(statsReport);
        }
    }

  
}
