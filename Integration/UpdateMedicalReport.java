import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UpdateMedicalReport extends JFrame
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
	static JButton cancel1, cancel2;
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
	static JPanel buttonPanel, mainPanel;
	static JPanel revisePanel, reviseButtonPanel;
	static JTextArea mRecord;
	static JComboBox reports;
	static String[] upReports = {"Report1", "Report2", "Report3"};
	static JFileChooser fc;
	
	static JRadioButton doc1, doc2, doc3;
	static JButton enter;
	static JScrollPane scrollRecord, scrollRevise; 				//The scrollpane
	static JPanel pcal;
	static JTable Cal;
	
	//Static Container pane;
	static DefaultTableModel mcal; 			//Table model
	static JFrame fmain, docFrame, pFrame;
	static String docSelected, pSelected, test;
	static String t,d,m,y;
	static String[] p;
	static int patID1;
	
	//Database
	static Connection conn;
	static Statement statement;
	static ResultSet rs; // Gets the result from SELECT commands 
	
	
	public UpdateMedicalReport(int id)
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
			
			patID1 = id;

			//Update reports
			setTitle("Update Medical Report");
			setSize(330, 375);
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			revise = new JButton("Revise");
			delete = new JButton("Delete");
			cancel1 = new JButton("Cancel");
			
			buttonPanel = new JPanel();
			buttonPanel.add(revise);
			buttonPanel.add(delete);
			buttonPanel.add(cancel1);
			
			//newSearch = new JButton("Search");
			

			//insert the medical report
			try 
			{ 
				statement = conn.createStatement(); 
				rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`="+patID1);

				while(rs.next())
				{
					mRecord = new JTextArea(rs.getString("HCR"));
					mRecord.setEditable(false);
					mRecord.setSize(50, 50);
				}
				scrollRecord = new JScrollPane(mRecord);

				rs.close();

			}
			catch (Exception e) 
			{ 
				System.err.println("Got an exception! ");  
				System.err.println(e.getMessage());  
			}

			mainPanel = new JPanel();
			mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
			mainPanel.add(scrollRecord);
			mainPanel.add(buttonPanel);
			//mainPanel.add(newSearch);

			//report2.setBounds(7, 10, 300, 200);
			revise.setBounds(10, 290, 80, 40);
			//newSearch.setBounds(115,290,80,40);
			delete.setBounds(220, 290, 80, 40);

			add(mainPanel);


			//add panel for revise 
			fRevise = new JFrame("Update Medical Report");
			fRevise.setSize(330, 375);
			
			fRevise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			report = new JTextArea(mRecord.getText());
			report.setSize(50, 50);

			submit = new JButton("Submit");
			cancel = new JButton("Cancel");
			
			reviseButtonPanel = new JPanel();
			reviseButtonPanel.add(submit);
			reviseButtonPanel.add(cancel);
			
			scrollRevise = new JScrollPane(report);

			revisePanel = new JPanel();
			revisePanel.setLayout(new BoxLayout(revisePanel, BoxLayout.Y_AXIS));
			revisePanel.add(scrollRevise);
			revisePanel.add(reviseButtonPanel);
			
			fRevise.add(revisePanel);

			//report.setBounds(7, 10, 300, 200);
			submit.setBounds(10, 290, 80, 40);
			cancel.setBounds(240, 290, 80, 40);


			delete.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					try 
					{ 
						statement = conn.createStatement(); 
						String del = "empty";
						statement.executeUpdate("UPDATE patient SET " + "`HCR`='" + del + "' WHERE `idpatient`='" + patID1 + "';"); 



						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`="+patID1);
						//rs.absolute(Integer.parseInt(patID1));

						while(rs.next())
						{
							mRecord.setText("");
						}

					}

					catch (Exception e) 
					{ 
						System.err.println("Got an exception! ");  
						System.err.println(e.getMessage());  
					}
				}
			});

			cancel1.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					dispose();
				}
			});

			revise.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					try 
					{ 
						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`=" + patID1);

						while(rs.next())
						{
							report.setText(rs.getString("HCR"));
						}
						fRevise.setVisible(true);
						setVisible(false);

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
						
						statement = conn.createStatement(); 
						statement.executeUpdate("UPDATE patient SET " + "`HCR`='" + editReport + "' WHERE `idpatient`='" + patID1 + "';"); 
						
						JOptionPane.showMessageDialog(revisePanel, "Healthcare Report Successfully updated.");
						
						statement = conn.createStatement(); 
						rs = statement.executeQuery("SELECT * FROM patient WHERE `idpatient`=" + patID1);

						while(rs.next())
						{
							mRecord.setText(rs.getString("HCR"));
							
						}
						setVisible(true);
						fRevise.setVisible(false);
						//fUpdate1.setVisible(false);
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
					fRevise.setVisible(false);
					setVisible(true);
				}
			});

			/*
			pan1.setBounds(0, 0, 320, 335);
			pan2.setBounds(0, 0, 320, 335);
			pan3.setBounds(0,0,320,355);
			fUpdate1.setResizable(false);
			fUpdate1.setVisible(true);
			//fRevise.setResizable(false);
			//fRevise.setVisible(true);
			*/
		}

	/*
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

		} */
}
