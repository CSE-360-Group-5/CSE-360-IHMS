/*Contents of UpdateMedicalReports.class */

//Import packages
//Import packages
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class UpdateMedicalReports extends JFrame
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
	
	
	public UpdateMedicalReports()
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


			// Search for patient
			fUpdate1 = new JFrame("Update Medical Report");
			fUpdate1.setSize(330, 375);
			pane = fUpdate1.getContentPane();
			pane.setLayout(null);
			//fUpdate1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			pan1 = new JPanel(null);
			fLabel = new JLabel("First Name");
			lLabel = new JLabel("Last Name");
			search = new JButton("Search");
			idLabel = new JLabel("Patient ID");
			fName = new JTextField(10);
			lName = new JTextField(10);
			patID = new JTextField(10);

			pane.add(pan1);		
			pan1.add(fLabel);
			pan1.add(lLabel);
			pan1.add(fName);
			pan1.add(lName);
			pan1.add(search);
			pan1.add(patID);
			pan1.add(idLabel);


			fLabel.setBounds(10, 40, 200, 40);
			fName.setBounds(10, 75, 100, 30);
			lLabel.setBounds(170, 40, 200, 40);
			lName.setBounds(170, 75, 100, 30);
			search.setBounds(10, 250, 80, 40);
			idLabel.setBounds(10, 125, 200, 40);
			patID.setBounds(10, 160, 100, 30 );


			//Update reports
			fUpdate2 = new JFrame("Update Medical Report");
			fUpdate2.setSize(330, 375);
			pane2 = fUpdate2.getContentPane();
			pane2.setLayout(null);
			fUpdate2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			pan2 = new JPanel(null);
			revise = new JButton("Revise");
			delete = new JButton("delete");
			newSearch = new JButton("Search");


			//insert the medical report
			try 
			{ 
				statement = conn.createStatement(); 
				rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`='10001';");

				while(rs.next())
				{
					report2 = new JLabel("<html>" + rs.getString("medHistory"));
					report = new JTextArea(rs.getString("medHistory"));
				}

				rs.close();

			}
			catch (Exception e) 
			{ 
				System.err.println("Got an exception! ");  
				System.err.println(e.getMessage());  
			}



			pane2.add(pan2);
			pan2.add(report2);
			pan2.add(revise);
			pan2.add(newSearch);
			pan2.add(delete);

			report2.setBounds(7, 10, 300, 200);
			revise.setBounds(10, 290, 80, 40);
			newSearch.setBounds(115,290,80,40);
			delete.setBounds(220, 290, 80, 40);




			//add panel for revise 
			fRevise = new JFrame("Update Medical Report");
			fRevise.setSize(330, 375);
			pane3 = fRevise.getContentPane();
			pane3.setLayout(null);
			fRevise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			pan3 = new JPanel(null);
			//report = new JTextArea(temp.getReport());   moved up to try statement

			submit = new JButton("Submit");
			cancel = new JButton("Cancel");


			pane3.add(pan3);
			pan3.add(report);
			pan3.add(submit);
			pan3.add(cancel);

			report.setBounds(7, 10, 300, 200);
			submit.setBounds(10, 290, 80, 40);
			cancel.setBounds(240, 290, 80, 40);


			search.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					String fName1 = fName.getText();
					String lName1 = lName.getText();
					String patID1 = patID.getText();
					boolean found = true;


					try 
					{ 
						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient");

						//rs.next();

						while(rs.next())
						{
							if(rs.getString("fname").equalsIgnoreCase(fName1)&&rs.getString("lname").equalsIgnoreCase(lName1) && Integer.toString(rs.getInt("idpatient")).equals(patID1))
							{
								fUpdate2.setVisible(true);
								fUpdate1.setVisible(false);
								//rs.absolute(Integer.parseInt(patID1));
								while(rs.next())
									report2.setText("<html>" + rs.getString("medHistory"));

								found = true;
							}
							else
							{
								found = false;
								//error();
							}
						}
						if(found == false)
						{
							while(rs.previous())
							{
								if(rs.getString("fname").equalsIgnoreCase(fName1)&&rs.getString("lname").equalsIgnoreCase(lName1) && Integer.toString(rs.getInt("idpatient")).equals(patID1))
								{
									fUpdate2.setVisible(true);
									fUpdate1.setVisible(false);
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
								error();
							}
						}
						rs.close();
					}

					catch (Exception e) 
					{ 
						System.err.println("Got an exception! ");  
						System.err.println(e.getMessage());  
					}


				}
			});

			delete.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					String patID1 = patID.getText();
					try 
					{ 
						statement = conn.createStatement(); 
						String del = " ";
						statement.executeUpdate("UPDATE patient SET " + "`medHistory`='" + del + "' WHERE `idpatient`='" + (Integer.parseInt(patID1)) + "';"); 



						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`="+patID1);
						//rs.absolute(Integer.parseInt(patID1));

						while(rs.next())
						{
							report2.setText("<html>" + rs.getString("medHistory"));
							fUpdate2.setVisible(true);
							fUpdate1.setVisible(false);
						}

					}

					catch (Exception e) 
					{ 
						System.err.println("Got an exception! ");  
						System.err.println(e.getMessage());  
					}
				}
			});

			newSearch.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					fUpdate1.setVisible(true);
					fUpdate2.setVisible(false);

				}
			});

			revise.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					String patID1 = patID.getText();
					try 
					{ 
						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`=" + patID1);
						//rs.absolute(Integer.parseInt(patID1));

						while(rs.next())
						{
							report.setText(rs.getString("medHistory"));
							fRevise.setVisible(true);
							fUpdate2.setVisible(false);
							fUpdate1.setVisible(false);
						}

					}

					catch (Exception e) 
					{ 
						System.err.println("Got an exception! ");  
						System.err.println(e.getMessage());  
					}
				}
			});

			submit.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					try 
					{ 
						String editReport = report.getText();
						String patID1 = patID.getText();
						statement = conn.createStatement(); 
						statement.executeUpdate("UPDATE patient SET " + "`medHistory`='" + editReport + "' WHERE `idpatient`='" + (Integer.parseInt(patID1)) + "';"); 

						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`=" + patID1);
						//rs.absolute(Integer.parseInt(patID1));

						while(rs.next())
						{
							report2.setText("<html>" + rs.getString("medHistory"));
							fUpdate2.setVisible(true);
							fRevise.setVisible(false);
							fUpdate1.setVisible(false);
						}
					}

					catch (Exception e) 
					{ 
						System.err.println("Got an exception! ");  
						System.err.println(e.getMessage());  
					}

				}
			});

			cancel.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					fUpdate2.setVisible(true);
					fRevise.setVisible(false);
					fUpdate1.setVisible(false);

				}
			});

			pan1.setBounds(0, 0, 320, 335);
			pan2.setBounds(0, 0, 320, 335);
			pan3.setBounds(0,0,320,355);
			fUpdate1.setResizable(false);
			fUpdate1.setVisible(true);
			//fRevise.setResizable(false);
			//fRevise.setVisible(true);
		}

		public static void error()
		{
			fError = new JFrame("Error");
			fError.setSize(250, 250);
			pane4 = fError.getContentPane();
			pane4.setLayout(null);
			fError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			pan4 = new JPanel(null);
			error = new JLabel("Patient does not exist");
			ok = new JButton("OK");


			pane4.add(pan4);
			pan4.add(error);
			pan4.add(ok);

			error.setBounds(10, 10, 220, 40);
			ok.setBounds(10, 100, 80, 40);



			ok.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					fUpdate1.setVisible(true);
					fError.setVisible(false);

				}
			});

			pan4.setBounds(0,0,150,150);
			fError.setResizable(false);
			fError.setVisible(true);

		}
}

