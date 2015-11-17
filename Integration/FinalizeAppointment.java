import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class FinalizeAppointment extends JFrame
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
	
	public FinalizeAppointment()
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
	        	  	fmain.setVisible(true);	
 					docFrame.setVisible(false);
	           }
		});
		
		enter.addActionListener(new ActionListener() 
		{
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
	




	
