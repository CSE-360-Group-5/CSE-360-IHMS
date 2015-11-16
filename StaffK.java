import java.awt.Component; 
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*; 
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class StaffK {
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

	public static void main (String[] args)
	{
		try
	    {

	      // Step 1: "Load" the JDBC driver

	      Class.forName("java.sql.Driver"); 

	      // Step 2: Establish the connection to the database 

	      String url = "jdbc:mysql://localhost:3306/healthcare"; 

	      conn = DriverManager.getConnection(url,"root","gillie31");  

	      System.out.println("connected");

	    }

	    catch (Exception e)

	    {

	      System.err.println("D'oh! Got an exception!"); 

	      System.err.println(e.getMessage()); 

	    } 
		
		pat.add("Test1");
		pat.add("Test2");
		pat.add("Test3");
		try{
			Statement sta= conn.createStatement();
			ResultSet rs = sta.executeQuery("SELECT * FROM Prescription");
			while(rs.next()){
				presc.add(new Prescription(rs.getString("Drug"),rs.getString("Patient"),rs.getString("Doctor"),rs.getString("Directions"),rs.getInt("Amount"),rs.getInt("Date"),rs.getInt("Month"),rs.getInt("Year"),rs.getString("Report"),rs.getInt("PatID"),rs.getInt("PatDOB")));
			}
			/*if(rs.getRow()!=0){
				rs.previous();
				id=rs.getInt("AppointmentID");
			}*/
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this was used to test but add rows in database instead
		/*Prescription hope = new Prescription("drug","Test1","Doctor","Direction",23,24,10,2015, "Report", 12059, 19011995);
		presc.add(hope);
		System.out.println(presc.get(0).getPat());
		presc.add(new Prescription("drug2","Test2","Doctor","Direction",23,24,10,2015, "Report", 12059, 27082004));
		System.out.println(presc.get(1).getPat());
		presc.add(new Prescription("drug2","Test2","Doctor","Direction",23,24,10,2015, "Report", 12059, 18061998));
		System.out.println(presc.get(2).getPat());
		presc.add(new Prescription("drug2","Test2","Doctor","Direction",23,24,10,2015, "Report", 12059, 13121994));
		System.out.println(presc.get(3).getPat());
		presc.add(new Prescription("drug2","Test2","Doctor","Direction",23,24,10,2015, "Report", 12059, 06062007));
		System.out.println(presc.get(4).getPat());
		presc.add(new Prescription("drug2","Test2","Doctor","Direction",23,24,10,2015, "Report", 12059, 19011990));
		System.out.println(presc.get(5).getPat());
		presc.add(new Prescription("drug2","Test3","Doctor","Direction",23,24,10,2015, "Report", 12059, 12121992));
		Prescription test=presc.get(0);
		System.out.println(test.getPat());
		*/
		main=new JFrame ("Staff Functions");
		main.setSize(400,400); //Set size to 400x400 pixels
		pane = main.getContentPane();
		pane.setLayout(null); //Apply null layout
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
		fappoin=new JButton("Finalize Appointment");
		upload=new JButton("Upload Medical Records");
		update=new JButton("Update Medical Records");
		epresc=new JButton("Access E-Prescriptions");
		staff=new JPanel();
		staff.setSize(400,400);
		staff.setLayout(new BoxLayout(staff, BoxLayout.Y_AXIS));
		staff.add(Box.createVerticalStrut(50));
		staff.add(fappoin);
		staff.add(Box.createVerticalStrut(50));
		staff.add(upload);
		staff.add(Box.createVerticalStrut(50));
		staff.add(update);
		staff.add(Box.createVerticalStrut(50));
		staff.add(epresc);
		fappoin.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) 
	        {
	        	FinalizeAppointment();
	        	System.out.println("Testf");
	        }
		});
	    upload.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event){
	        	UploadMedicalReports();
	        	System.out.println("Testu");
	        }
	    });
	    update.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event){
	        	UpdateMedicalReports();
	        	System.out.println("Testud");
	        }
	    });
	    epresc.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event){
	        	epres(presc);
	        }
	    });
		fappoin.setAlignmentX(staff.CENTER_ALIGNMENT);
		upload.setAlignmentX(staff.CENTER_ALIGNMENT);
		update.setAlignmentX(staff.CENTER_ALIGNMENT);
		epresc.setAlignmentX(staff.CENTER_ALIGNMENT);
		pane.add(staff);
		main.setResizable(false);
		main.setVisible(true);
	}
	public static void epres( ArrayList<Prescription> p){
		final ArrayList<Prescription> temp=new ArrayList();
		final JFrame frame=new JFrame("E-Prescriptions");
		Container pane;
		JPanel panep = new JPanel(null);
		JButton next=new JButton(">>");
		JButton prev=new JButton("<<");
		final JComboBox patients=new JComboBox(pat.toArray());
		frame.setSize(400,400);
		pane=frame.getContentPane();
		pane.setLayout(null); //Apply null layout
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panep.setSize(400,400);
		panep.setLayout(new BoxLayout(panep, BoxLayout.Y_AXIS));
		JLabel pa=new JLabel("Choose the Patient:");
		panep.add(Box.createVerticalStrut(50));
		panep.add(pa);
		panep.add(Box.createVerticalStrut(10));
		panep.add(patients);
		panep.add(Box.createVerticalStrut(300));
		patients.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				String name= pat.get(patients.getSelectedIndex());
				int index=patients.getSelectedIndex();
				temp.clear();
				for(int i=0; i<presc.size();i++){
					if(presc.get(i).getPat().equals(name)){
						temp.add(presc.get(i));
					}
				}
				for(int i=0;i<temp.size();i++){
					Prescription ta;
					for(int j=i-1;j>=0;j--){
						if(temp.get(i).getYear()<temp.get(j).getYear()){
							ta=temp.get(j);
							temp.set(j, temp.get(i));
							temp.set(i, ta);
						}
						else if(temp.get(i).getYear()<temp.get(j).getYear()){
							if(temp.get(i).getMonth()<temp.get(j).getMonth()){
								ta=temp.get(j);
								temp.set(j, temp.get(i));
								temp.set(i, ta);
							}
						}
						else if(temp.get(i).getYear()<temp.get(j).getYear()){
							if(temp.get(i).getMonth()<temp.get(j).getMonth()){
								if(temp.get(i).getDate()<temp.get(j).getDate()){
									ta=temp.get(j);
									temp.set(j, temp.get(i));
									temp.set(i, ta);
								}
							}
						}
					}
				}
				frame.setVisible(false);
				frame.dispose();
				epresl(temp,0,index);
			}
		});
		patients.setAlignmentX(panep.CENTER_ALIGNMENT);
		pane.add(panep);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	static void epresl(final ArrayList<Prescription> p, int start,int index){
		final JFrame frame=new JFrame("E-Prescriptions");
		final int s=start;
		Container pane;
		final JPanel panep = new JPanel(null);
		final JPanel panep2= new JPanel(null);
		JPanel panep3=new JPanel(null);
		JButton next=new JButton(">>");
		JButton prev=new JButton("<<");
		JLabel [] pt=new JLabel[p.size()],pi=new JLabel[p.size()];
		
		for(int i=0;i<p.size();i++){
			pt[i]=new JLabel("Drug: "+p.get(i).getDrug()+",  Doctor: "+p.get(i).getDoc());
			pi[i]=new JLabel("\tDirections: "+p.get(i).getDir()+",  Date: "+Integer.toString(p.get(i).getMonth())+"/"+Integer.toString(p.get(i).getDate())+"/"+Integer.toString(p.get(i).getYear()));
		}
		final JComboBox patients=new JComboBox(pat.toArray());
		patients.setSelectedIndex(index);
		frame.setSize(400,400);
		pane=frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS)); //Apply null layout
		main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		panep.setMaximumSize(new Dimension(1600,400));
		panep2.setMaximumSize(new Dimension(450,25));
		panep2.setLayout(new BoxLayout(panep2, BoxLayout.X_AXIS));
		panep.setLayout(new BoxLayout(panep, BoxLayout.Y_AXIS));
		JLabel pa=new JLabel("Choose the Patient:");
		panep.add(patients);
		panep.add(Box.createVerticalStrut(15));
		for(int st=start;st<5&&st<p.size();st++){
			panep.add(pt[st]);
			panep.add(Box.createVerticalStrut(5));
			panep.add(pi[st]);
			panep.add(Box.createVerticalStrut(20));
		}
		panep2.add(prev);
		//panep2.add(Box.createHorizontalStrut(300));
		panep2.add(next);
		patients.setMaximumSize(new Dimension(250, 25));
		next.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event){
	        	if(s+5>=p.size()){
	        		JOptionPane.showMessageDialog (null, "Error there are no more Prescriptions", "Error", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        }
	    });
		prev.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event){
	        	//frame.setVisible(true);
	        }
	    });
		patients.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				String name= pat.get(patients.getSelectedIndex());
				int ind=patients.getSelectedIndex();
				p.clear();
				for(int i=0; i<presc.size();i++){
					if(presc.get(i).getPat().equals(name)){
						p.add(presc.get(i));
					}
				}
				for(int i=0;i<p.size();i++){
					Prescription t;
					for(int j=i-1;j>=0;j--){
						if(p.get(i).getYear()<p.get(j).getYear()){
							t=p.get(j);
							p.set(j, p.get(i));
							p.set(i, t);
						}
						else if(p.get(i).getYear()<p.get(j).getYear()){
							if(p.get(i).getMonth()<p.get(j).getMonth()){
								t=p.get(j);
								p.set(j, p.get(i));
								p.set(i, t);
							}
						}
						else if(p.get(i).getYear()<p.get(j).getYear()){
							if(p.get(i).getMonth()<p.get(j).getMonth()){
								if(p.get(i).getDate()<p.get(j).getDate()){
									t=p.get(j);
									p.set(j, p.get(i));
									p.set(i, t);
								}
							}
						}
					}
				}
				frame.setVisible(false);
				frame.dispose();
				epresl(p,0,ind);
			}
		});
		pane.add(panep);
		pane.add(panep2);
		panep.setAlignmentY(pane.LEFT_ALIGNMENT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		
		{
			
		}
	}
	
	public static void UpdateMedicalReports()
	{
		// Create connection to database
		try 
		{ 
		// "Load" the JDBC driver 
			Class.forName("java.sql.Driver"); 
		
		 
		// Establish the connection to the database 
			String url = "jdbc:mysql://localhost:3306/healthcare"; 
			conn = DriverManager.getConnection(url,"root","gillie31"); 
			System.out.println("conected");
		} 
 
		 
		catch (Exception e) 
		{ 
			System.err.println("Got an exception!"); 
			System.err.println(e.getMessage()); 
		} 
		
		
		// create patient in database for testing
		try 
		{ 
			statement = conn.createStatement(); 
			rs = statement.executeQuery("SELECT * FROM patient"); 
		 			             
			
				statement = conn.createStatement(); 
				//statement.executeUpdate("INSERT INTO patient (idpatient, fname, lname, dob, email, password, sex, medHistory, mstatus, education, race)" + "VALUES ('" + 1 + "', '" + "Joshua" + "', '" + "Liddell" + "', '" + "27021985" + "', '" + "email" + "', '" + "password" + "', '" + "male" + "', '" + "medical report" + "', '" + "mstatus" + "', '" + "elvl" + "', '" + "prace" + "')"); 
		 
			
				} 
			catch (Exception e) 
			{ 
				System.err.println("Got an exception! ");  
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
		fName = new JTextField();
		lName = new JTextField();
		patID = new JTextField();
		
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
			rs = statement.executeQuery("SELECT * FROM patient");
			rs.next();
			
			
			report2 = new JLabel("<html>" + rs.getString("medHistory"));
			report = new JTextArea(rs.getString("medHistory"));
			//report.setLineWrap(true);
			
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
				Boolean found = true;

				
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
							rs.absolute(Integer.parseInt(patID1));
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
					rs = statement.executeQuery("SELECT * FROM patient");
					rs.absolute(Integer.parseInt(patID1));
				
					
					report2.setText("<html>" + rs.getString("medHistory"));
					fUpdate2.setVisible(true);
					fUpdate1.setVisible(false);
				
				
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
					rs = statement.executeQuery("SELECT * FROM patient");
					rs.absolute(Integer.parseInt(patID1));
					
				
				report.setText(rs.getString("medHistory"));
				fRevise.setVisible(true);
				fUpdate2.setVisible(false);
				fUpdate1.setVisible(false);
				
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
					rs = statement.executeQuery("SELECT * FROM patient");
					rs.absolute(Integer.parseInt(patID1));
				
		
					report2.setText("<html>" + rs.getString("medHistory"));
					fUpdate2.setVisible(true);
					fRevise.setVisible(false);
					fUpdate1.setVisible(false);
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
	public static void UploadMedicalReports()
	{
		// Create connection to database
		try 
		{ 
		// "Load" the JDBC driver 
			Class.forName("java.sql.Driver"); 
		
		 
		// Establish the connection to the database 
			String url = "jdbc:mysql://localhost:3306/healthcare"; 
			conn = DriverManager.getConnection(url,"root","gillie31"); 
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



// create patients in database for testing
try 
{ 
	statement = conn.createStatement(); 
	rs = statement.executeQuery("SELECT * FROM patient"); 
 	rs.next();		             
	
		statement = conn.createStatement(); 
		//statement.executeUpdate("INSERT INTO patient (idpatient, fname, lname, dob, email, password, sex, medHistory, mstatus, education, race)" + "VALUES ('" + 1 + "', '" + "Joshua" + "', '" + "Liddell" + "', '" + "27021985" + "', '" + "email" + "', '" + "password" + "', '" + "male" + "', '" + "medical report" + "', '" + "mstatus" + "', '" + "elvl" + "', '" + "prace" + "')");
		rs.next();	
		statement = conn.createStatement();
		//statement.executeUpdate("INSERT INTO patient (idpatient, fname, lname, dob, email, password, sex, medHistory, mstatus, education, race)" + "VALUES ('" + 2 + "', '" + "J" + "', '" + "L" + "', '" + "2" + "', '" + "email" + "', '" + "password" + "', '" + "male" + "', '" + "medical report" + "', '" + "mstatus" + "', '" + "elvl" + "', '" + "prace" + "')");
	
		} 
	catch (Exception e) 
	{ 
		System.err.println("Got an exception! ");  
		System.err.println(e.getMessage());  
	} 



submit.addActionListener(new ActionListener() 
{
	public void actionPerformed(ActionEvent event)
	{
		String fName1 = fName.getText();
		String lName1 = lName.getText();
		String patID = inputID.getText();
		String patDOB = inputDOB.getText();
		Boolean found = true;
		
		
		try 
		{ 
			statement = conn.createStatement(); 
			rs = statement.executeQuery("SELECT * FROM patient");
			rs.next();
		
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
					//error();
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
				
				statement = conn.createStatement(); 
				rs = statement.executeQuery("SELECT * FROM patient"); 
				rs.next();
			
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
	public static void FinalizeAppointment()
	{
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Standard preparation for a frame
		fmain = new JFrame ("Select Doctor"); 						//Create and name frame
		fmain.setSize(330, 375); 									//Set size to 400x400 pixels
		pane = fmain.getContentPane();
		pane.setLayout(null);										//Apply null layout
		//fmain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		//Close when X is clicked
		
		//pane.setLayout(new GridLayout(4,2,20,50)); 
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("SELECT doctor FROM Appointment");
			
			doctorsVector = new Vector<String>();
			
			while(rs.next())
			{
				if(!doctorsVector.contains(rs.getString(1)))
				{
					doctorsVector.add(rs.getString(1));
				}
				System.out.println(doctorsVector.lastElement());
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final JList doctorBox = new JList(doctorsVector);
	
		enter = new JButton("Enter");
		JButton back = new JButton("Back");
				
	    doctorBox.setBounds(85,80, 150, 100);
	    enter.setBounds(10,285, 150, 65);
	    back.setBounds(170,285,150,65);
	    
	    fmain.add(doctorBox);
		fmain.add(enter);
		fmain.add(back);
		
		fmain.setResizable(false);
		fmain.setVisible(true);
		
		back.addActionListener(new ActionListener() {
	          public void actionPerformed(ActionEvent event) 
	           {	
	        	  	main.setVisible(true);	
 					fmain.setVisible(false);
	           }
		});
		
		enter.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent event) 
	           {
	        	   docSelected = (String) doctorBox.getSelectedValue();
	        	   
	        	   	docFrame = new JFrame (docSelected); 			//Create and name frame
	        	   	docFrame.setSize(330, 375); 					//Set size to 400x400 pixels
	       			pane = docFrame.getContentPane();
	       			pane.setLayout(null);							//Apply null layout
	       				   
	       			System.out.println("what");
	       			
	       			try {
	       				statement = conn.createStatement();
	       				ResultSet rs = statement.executeQuery("SELECT appointmentID FROM Appointment WHERE `doctor`='" + docSelected + "';");
	       				
	       				patientVector = new Vector<String>();
	       				
	       				while(rs.next())
	       				{
	       					patientVector.add(rs.getString(1));
	       					System.out.println(patientVector.lastElement());
	       					
	       				}
	       			} catch (SQLException e) {
	       				// TODO Auto-generated catch block
	       				e.printStackTrace();
	       			}
	       			
					final JList patientBox = new JList(patientVector);
					
					JButton pEnter = new JButton("Enter");
					JButton back = new JButton("Back");
							
				    patientBox.setBounds(85,80, 150, 100);
				    pEnter.setBounds(10,285, 150, 65);
				    back.setBounds(170,285,150,65);
				    
				    docFrame.add(patientBox);
					docFrame.add(pEnter);
					docFrame.add(back);
					
					docFrame.setResizable(false);
					docFrame.setVisible(true);
				
					back.addActionListener(new ActionListener() {
				          public void actionPerformed(ActionEvent event) 
				           {	
				        	  	fmain.setVisible(true);	
		       					docFrame.setVisible(false);
				           }
					});
					
					pEnter.addActionListener(new ActionListener() {
				          public void actionPerformed(ActionEvent event) 
				           {	
				        	  	
				  				pSelected = (String) patientBox.getSelectedValue();; 
				  				
				        	  	pFrame = new JFrame (pSelected); 			//Create and name frame
				        	   	pFrame.setSize(330, 375); 					//Set size to 400x400 pixels
				       			pane = pFrame.getContentPane();
				       			pane.setLayout(null);							//Apply null layout
				       			pane.setLayout(new GridLayout(5,2,20,50));
				       			
				       			try {
				       				statement = conn.createStatement();
				       				ResultSet rs = statement.executeQuery("SELECT time FROM Appointment WHERE `appointmentId`='" + pSelected + "';");
				       				
				       				time = new Vector<String>();
				       				
				       				while(rs.next())
				       				{
				       					time.add(rs.getString(1));
				       					System.out.println(time.lastElement());
				       					
				       				}
				       			} catch (SQLException e) {
				       				// TODO Auto-generated catch block
				       				e.printStackTrace();
				       			}
				       			try {
				       				statement = conn.createStatement();
				       				ResultSet rs = statement.executeQuery("SELECT month FROM Appointment WHERE `appointmentId`='" + pSelected + "';");
				       				
				       				month = new Vector<String>();
				       				
				       				while(rs.next())
				       				{
				       					month.add(rs.getString(1));
				       					System.out.println(month.lastElement());
				       					
				       				}
				       			} catch (SQLException e) {
				       				// TODO Auto-generated catch block
				       				e.printStackTrace();
				       			}try {
				       				statement = conn.createStatement();
				       				ResultSet rs = statement.executeQuery("SELECT date FROM Appointment WHERE `appointmentId`='" + pSelected + "';");
				       				
				       				day = new Vector<String>();
				       				
				       				while(rs.next())
				       				{
				       					day.add(rs.getString(1));
				       					System.out.println(day.lastElement());
				       					
				       				}
				       			} catch (SQLException e) {
				       				// TODO Auto-generated catch block
				       				e.printStackTrace();
				       			}try {
				       				statement = conn.createStatement();
				       				ResultSet rs = statement.executeQuery("SELECT year FROM Appointment WHERE `appointmentId`='" + pSelected + "';");
				       				
				       				year = new Vector<String>();
				       				
				       				while(rs.next())
				       				{
				       					year.add(rs.getString(1));
				       					System.out.println(year.lastElement());
				       					
				       				}
				       			} catch (SQLException e) {
				       				// TODO Auto-generated catch block
				       				e.printStackTrace();
				       			}
				       			
				        	  	JLabel pTime = new JLabel(time.toString());
				        	  	JLabel pMonth = new JLabel(month.toString());
				       	  		JLabel pDay = new JLabel(day.toString());
				       			JLabel pYear= new JLabel(year.toString());
				        	  			
				       			JLabel appTime = new JLabel("Appointment Time");
				       			JLabel t = new JLabel("10:00 AM");
				       			JLabel appMonth = new JLabel("   Month:");
				       			JLabel m = new JLabel("November");
				       			JLabel appDay = new JLabel("   Day:");
				       			JLabel  d = new JLabel("18");
				       			JLabel appYear = new JLabel("   Year:");
				       			JLabel y = new JLabel("2015");
				       			JButton confirm = new JButton("Confirm");
				       			JButton cancel = new JButton("Deny");
	       		
				       			pFrame.add(appTime);
				       			pFrame.add(pTime);
				       			//pFrame.add(pTime);
				       			pFrame.add(appMonth);
				       			pFrame.add(pMonth);
				       			//pFrame.add(pMonth);
				       			pFrame.add(appDay);
				       			pFrame.add(pDay);
				       			//pFrame.add(pDay);
				       			pFrame.add(appYear);
				       			pFrame.add(pYear);
				       			//pFrame.add(pYear);
				       			pFrame.add(confirm);
				       			pFrame.add(cancel);
	       			
				       			pFrame.setResizable(false);
				       			pFrame.setVisible(true);
	       			
				       			confirm.addActionListener(new ActionListener() {
				       				public void actionPerformed(ActionEvent event) 
				       				{
				       					docFrame.setVisible(true);	
				       					pFrame.setVisible(false);
				       				}
				       			});
				       			cancel.addActionListener(new ActionListener() {
				       				public void actionPerformed(ActionEvent event)
				       				{
				       					try{
				       						Statement stmt = conn.createStatement();
				       					    String sql = "DELETE FROM appointment " +"WHERE appointmentId="+pSelected;
				       					    stmt.executeUpdate(sql);
				       					}
				       					catch(Exception e){
				       						e.printStackTrace();
				       					}
				       				
				       						fmain.setVisible(true);	
				       						docFrame.setVisible(false);
				       						pFrame.setVisible(false);
				       					
				       				}	
				       			});
				   
				           }
					});
	           }
	           
	           });

	}
}

