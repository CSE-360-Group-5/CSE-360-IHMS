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

public class UploadMedicalReport extends JFrame
{
	private static final long serialVersionUID = 1L;
	static Container pane;
	static JButton fappoin, upload, update, epresc;
	static JPanel staff;
	static JFrame main;
	static ArrayList presc=new ArrayList();
	static ArrayList pat = new ArrayList();
	static Vector doctorsVector, patientVector, time, month, day, year;
	
	static JLabel entInfo, report2, error, fLabel, lLabel, idLabel;
	static JButton search, revise, delete, submit, cancel, newSearch, ok;
	static JFrame fUpdate1,fUpdate2,fRevise,fError;
	static Container pane1,pane2,pane3,pane4;
	static JPanel mainPanel, buttonPanel;
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
	
	
	public UploadMedicalReport(final int id)
	{
	// Create connection to database
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


			fc = new JFileChooser();

			//Upload reports
			setTitle("Upload Medical Report");
			setSize(330, 375);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			upload = new JButton("Browse");
			cancel = new JButton("Cancel");

			browse = new JLabel("Select Report file to Upload");
			
			buttonPanel = new JPanel();
			buttonPanel.add(upload);
			buttonPanel.add(cancel);
			
			//reports = new JComboBox(upReports);
			
			complete = new JLabel("");
			curPat = new JLabel("");

			browse.setBounds(10, 40, 200, 40);
			complete.setBounds(10,150,200,40 );
			upload.setBounds(10, 230, 80, 30);
			cancel.setBounds(200, 230, 80, 30);
			curPat.setBounds(10,10,200,30);
			
			mainPanel = new JPanel();
			mainPanel.add(browse);
			mainPanel.add(buttonPanel);
			mainPanel.add(curPat);
			mainPanel.add(complete);
			
			add(mainPanel);

			
			cancel.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					dispose();
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

							statement = conn.createStatement(); 
							statement.executeUpdate("UPDATE patient SET " + "`HCR`='" + addReport + "' WHERE `idpatient`='" + id + "';");
							
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

		}
}
