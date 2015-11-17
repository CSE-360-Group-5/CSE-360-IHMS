/*Contents of UploadMedicalReports.class */

//Import packages
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UploadMedicalReports extends JFrame
{
	private static final long serialVersionUID = 1L;
	static Container pane;
	static JButton fappoin, upload, update, epresc;
	static JPanel staff;
	static JFrame main;
	static ArrayList<Prescription> presc=new ArrayList<Prescription>();
	static ArrayList<String> pat = new ArrayList();
	static Vector<String> doctorsVector, patientVector, time, month, day, year;
	
	static JLabel entInfo, report2, error, fLabel, lLabel, idLabel;
	static JButton search, revise, delete, submit, cancel, newSearch, ok;
	static JFrame fUpdate1,fUpdate2,fRevise,fError;
	static Container pane1,pane2,pane3,pane4;
	static JTextField fName, lName, patID;
	static JPanel pan1,pan2,pan3,pan4;
	static JTextArea report;
	
	static JLabel patID2, patName, patDOB, viewRecord, browse, error1, complete, curPat;
	//static JButton submit, upload, search, ok;
	static JFrame fUpload1, fUpload2; //fError;
	//static Container pane1,pane2,pane3;
	static JTextField inputID, inputName, inputDOB;
	//static JPanel pan1,pan2,pan3;
	static JComboBox reports;
	static String[] upReports = {"Report1", "Report2", "Report3"};
	static JFileChooser fc;
	
	static JRadioButton doc1, doc2, doc3;
	static JButton enter;
	static JScrollPane scal; 				//The scrollpane
	static JPanel pcal;
	static JTable Cal;
	
	//Static Container pane;
	static DefaultTableModel mcal; 			//Table model
	static JFrame fmain, docFrame, pFrame;
	static String docSelected, pSelected, test;
	static String t,d,m,y;
	static String[] p;
	
	//Database
	static Connection conn;
	static Statement statement;
	static ResultSet rs; // Gets the result from SELECT commands 
	
	
	public UploadMedicalReports()
	{
	// Create connection to database
			try 
			{ 
				// "Load" the JDBC driver 
				Class.forName("java.sql.Driver"); 


				// Establish the connection to the database 
				String url = "jdbc:mysql://localhost:3306/cse"; 
				conn = DriverManager.getConnection(url,"root","admin"); 
				System.out.println("conected");
			} 


			catch (Exception e) 
			{ 
				System.err.println("Got an exception!"); 
				System.err.println(e.getMessage()); 
			} 


			fUpload1 = new JFrame("Upload Medical Report");
			fUpload1.setSize(330, 375);
			pane1 = fUpload1.getContentPane();
			pane1.setLayout(null);
			//fUpload1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			pan1 = new JPanel(null);
			patID2 = new JLabel("Patient ID");
			patDOB = new JLabel("DOB(ddmmyyyy)");
			viewRecord = new JLabel("View patient");
			submit = new JButton("Submit");
			inputID = new JTextField();
			inputName = new JTextField();
			inputDOB = new JTextField();

			fName = new JTextField();
			lName = new JTextField();
			fLabel = new JLabel("First Name");
			lLabel = new JLabel("Last Name");

			fc = new JFileChooser();

			pane1.add(pan1);
			pan1.add(fLabel);
			pan1.add(lLabel);
			pan1.add(fName);
			pan1.add(lName);
			pan1.add(patDOB);
			pan1.add(patID2);
			pan1.add(inputID);
			pan1.add(inputDOB);
			pan1.add(submit);


			fLabel.setBounds(10, 10, 200, 40);
			fName.setBounds(10, 45, 100, 30);
			lLabel.setBounds(170, 10, 200, 40);
			lName.setBounds(170, 45, 100, 30);
			patDOB.setBounds(10, 105, 250, 40);
			inputDOB.setBounds(10, 135, 100, 30);
			patID2.setBounds(170, 105, 200, 40);
			inputID.setBounds(170, 135, 100, 30);
			submit.setBounds(10, 230, 80, 30);

			//Upload reports
			fUpload2 = new JFrame("Upload Medical Report");
			fUpload2.setSize(330, 375);
			pane2 = fUpload2.getContentPane();
			pane2.setLayout(null);
			fUpload2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			pan2 = new JPanel(null);
			browse = new JLabel("Select Report to Upload");
			reports = new JComboBox(upReports);
			upload = new JButton("Browse");
			search = new JButton("Search");
			complete = new JLabel("");
			curPat = new JLabel("");


			pane2.add(pan2);
			pan2.add(browse);
			pan2.add(reports);
			pan2.add(complete);
			pan2.add(upload);
			pan2.add(search);
			pan2.add(curPat);

			browse.setBounds(10, 40, 200, 40);
			reports.setBounds(10, 75, 200, 40);
			complete.setBounds(10,150,200,40 );
			upload.setBounds(10, 230, 80, 30);
			search.setBounds(200, 230, 80, 30);
			curPat.setBounds(10,10,200,30);


			submit.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					String fName1 = fName.getText();
					String lName1 = lName.getText();
					String patID = inputID.getText();
					String patDOB = inputDOB.getText();
					boolean found = true;


					try 
					{ 
						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient");

						while(rs.next())
						{
							if(rs.getString("fname").equalsIgnoreCase(fName1) && rs.getString("lname").equalsIgnoreCase(lName1) && Integer.toString(rs.getInt("idpatient")).equals(patID)&&rs.getString("dob").equals(patDOB))
							{
								fUpload2.setVisible(true);
								fUpload1.setVisible(false);
								found = true;


							}
							else
							{
								found = false;
							}
						}

						if(found == false)
						{
							while(rs.previous())
							{
								if(rs.getString("fname").equalsIgnoreCase(fName1) && rs.getString("lname").equalsIgnoreCase(lName1) && Integer.toString(rs.getInt("idpatient")).equals(patID)&&rs.getString("dob").equals(patDOB))
								{
									fUpload2.setVisible(true);
									fUpload1.setVisible(false);
									found = true;

								}
								else
								{
									found = false;
									//error();
								}
							}

							if (found == false)
							{
								error2();
							}
						}
						rs.close();
					}
					catch (Exception e) 
					{ 
						System.err.println("Got an exception! ");  
						System.err.println(e.getMessage());  
					} 
					curPat.setText("Patient: " + fName1 + " " + lName1);
				}
			});


			search.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					complete.setText("");
					fUpload1.setVisible(true);
					fUpload2.setVisible(false);

				}
			});


			//Browse button
			upload.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					String addReport = "";

					int returnVal = fc.showOpenDialog(null);

					if (returnVal == JFileChooser.CANCEL_OPTION)
					{
						complete.setText("Canceled");
					}

					if (returnVal == JFileChooser.APPROVE_OPTION)
					{
						File file = fc.getSelectedFile();

						String line;
						
						//make a string to concat the input line

						String filename = fc.getCurrentDirectory().toString() + "/" + file.getName();

						try
						{
							FileReader fr = new FileReader (filename);
							BufferedReader inFile = new BufferedReader (fr);

							line = inFile.readLine();
							while (line != null)
							{

								addReport = addReport + line + "\n";
								line = inFile.readLine();
							}
							inFile.close();
						}
						catch (FileNotFoundException exception)
						{

							complete.setText("The file could not be opened.");
						}
						catch (IOException exception)
						{

							complete.setText("Sorry, an error occured");
						}
						try 
						{ 
							//Current patient
							String patID = inputID.getText();

							statement = conn.createStatement(); 
							statement.executeUpdate("UPDATE patient SET " + "`medHistory`='" + addReport + "' WHERE `idpatient`='" + (Integer.parseInt(patID)) + "';");
							
							complete.setText("Upload Complete");
						}
						catch (Exception e) 
						{ 
							System.err.println("Got an exception! ");  
							System.err.println(e.getMessage());  
						}
					}


				}
			});


			pan1.setBounds(0, 0, 320, 335);
			pan2.setBounds(0, 0, 320, 335);
			fUpload1.setResizable(false);
			fUpload1.setVisible(true);
		}


		public static void error2()
		{
			fError = new JFrame("Error");
			fError.setSize(250, 250);
			pane3 = fError.getContentPane();
			pane3.setLayout(null);
			fError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			pan3 = new JPanel(null);
			error = new JLabel("Patient does not exist");
			ok = new JButton("OK");


			pane3.add(pan3);
			pan3.add(error);
			pan3.add(ok);

			error.setBounds(10, 10, 220, 40);
			ok.setBounds(10, 100, 80, 40);



			ok.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					fUpload1.setVisible(true);
					fError.setVisible(false);

				}
			});

			pan3.setBounds(0,0,150,150);
			fError.setResizable(false);
			fError.setVisible(true);
		}
}
