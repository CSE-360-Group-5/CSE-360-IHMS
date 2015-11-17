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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class FinalizeAppointment extends JFrame
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
	static JTextField fName, lName, patID;
	static JPanel pan1,pan2,pan3,pan4;
	static JTextArea report;

	static JLabel patID2, patName, patDOB, viewRecord, browse, error1, complete, curPat;
	static JLabel docListLabel, appListLabel;
	static JFrame fUpload1, fUpload2; //fError;
	static JTextField inputID, inputName, inputDOB;
	static JPanel buttonPanel;
	static JPanel panel;
	static JScrollPane scrollList, scrollApp;
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
	public static JFrame docFrame, pFrame;
	static String docSelected, pSelected, test;
	static String t,d,m,y;
	static String[] p;

	//Database
	static Connection conn;
	static Statement statement;
	static ResultSet rs; // Gets the result from SELECT commands 

	public FinalizeAppointment(final String type, final String name)
	{
		try
		{
			// Step 1: "Load" the JDBC driver
			Class.forName("java.sql.Driver"); 

			// Step 2: Establish the connection to the database 
			String url = "jdbc:mysql://localhost:3306/cse"; 
			conn = DriverManager.getConnection(url,"root","admin");  
			System.out.println("connected");
		}

		catch (Exception e)
		{
			System.err.println("D'oh! Got an exception!"); 
			System.err.println(e.getMessage()); 
		} 

		setAlwaysOnTop(false);
		setSize(325, 300);	//Set size to 325x300 pixels

		doctorsVector = new Vector();
		if(!type.equals("Doctor"))
		{
			try
			{
				statement = conn.createStatement();
				ResultSet rs = statement.executeQuery("SELECT docName FROM Appointments");
	
				while(rs.next())
				{
					if(!doctorsVector.contains(rs.getString(1)))
					{
						doctorsVector.add(rs.getString(1));
					}	
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
			doctorsVector.add(name);
		
		docListLabel = new JLabel("List of Doctors");

		final JList doctorBox = new JList(doctorsVector);
		scrollList = new JScrollPane(doctorBox);
		scrollList.setSize(70, 50);

		enter = new JButton("Enter");
		JButton back = new JButton("Back");

		doctorBox.setBounds(85,80, 150, 100);
		enter.setBounds(10,285, 150, 65);
		back.setBounds(170,285,150,65);

		buttonPanel = new JPanel();
		buttonPanel.add(enter);
		buttonPanel.add(back);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(docListLabel);
		panel.add(scrollList);
		panel.add(buttonPanel);
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		panel.setBorder(padding);

		add(panel);

		setResizable(false);

		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) 
			{	
				setVisible(true);	
				docFrame.setVisible(false);
			}
		});

		enter.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event) 
			{
				if(type.equals("Doctor"))
					docSelected = name;
				else
					docSelected = (String) doctorBox.getSelectedValue();

				docFrame = new JFrame (docSelected); 			//Create and name frame
				docFrame.setSize(330, 325); 					//Set size to 400x400 pixels
				pane = docFrame.getContentPane();
				pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));							//Apply null layout
				
				try 
				{
					statement = conn.createStatement();
					ResultSet rs = statement.executeQuery("SELECT * FROM Appointments WHERE `docName`='" + docSelected + "';");

					patientVector = new Vector();

					while(rs.next())
					{
						if(rs.getString("status").equals("pending"))
							patientVector.add(Integer.toString(rs.getInt(1)));
					}
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}

				final JList patientBox = new JList(patientVector);

				appListLabel = new JLabel("Pending Appointments: ");
				
				JButton pEnter = new JButton("Enter");
				JButton back = new JButton("Back");
				
				JPanel bPanel = new JPanel();
				bPanel.add(pEnter);
				bPanel.add(back);
				
				patientBox.setBounds(85,80, 150, 100);
				pEnter.setBounds(10,285, 150, 65);
				back.setBounds(170,285,150,65);
				
				scrollApp = new JScrollPane(patientBox);
				
				docFrame.add(appListLabel);
				docFrame.add(scrollApp);
				docFrame.add(bPanel);

				docFrame.setResizable(false);
				docFrame.setVisible(true);
				docFrame.setAlwaysOnTop(true);

				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) 
					{	
						setVisible(true);	
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

						try 
						{
							statement = conn.createStatement();
							ResultSet rs = statement.executeQuery("SELECT time FROM Appointments WHERE `appointmentsId`='" + pSelected + "';");

							time = new Vector();

							while(rs.next())
							{
								time.add(rs.getString(1));
							}
						} 
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
						try 
						{
							statement = conn.createStatement();
							ResultSet rs = statement.executeQuery("SELECT month FROM Appointments WHERE `appointmentsId`='" + pSelected + "';");

							month = new Vector();

							while(rs.next())
							{
								month.add(rs.getString(1));
							}
						} 
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
						try 
						{
							statement = conn.createStatement();
							ResultSet rs = statement.executeQuery("SELECT day FROM Appointments WHERE `appointmentsId`='" + pSelected + "';");

							day = new Vector();

							while(rs.next())
							{
								day.add(rs.getString(1));
							}
						} 
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
						try {
							statement = conn.createStatement();
							ResultSet rs = statement.executeQuery("SELECT year FROM Appointments WHERE `appointmentsId`='" + pSelected + "';");

							year = new Vector();

							while(rs.next())
							{
								year.add(rs.getString(1));
							}
						} 
						catch (SQLException e) 
						{
							e.printStackTrace();
						}

						JLabel pTime = new JLabel(time.toString());
						JLabel pMonth = new JLabel(month.toString());
						JLabel pDay = new JLabel(day.toString());
						JLabel pYear= new JLabel(year.toString());

						JLabel appTime = new JLabel("   Appointment Time");
						JLabel appMonth = new JLabel("   Month:");
						JLabel appDay = new JLabel("   Day:");
						JLabel appYear = new JLabel("   Year:");
						JButton confirm = new JButton("Confirm");
						JButton cancel = new JButton("Deny");

						pFrame.add(appTime);
						pFrame.add(pTime);
						pFrame.add(appMonth);
						pFrame.add(pMonth);
						pFrame.add(appDay);
						pFrame.add(pDay);
						pFrame.add(appYear);
						pFrame.add(pYear);
						pFrame.add(confirm);
						pFrame.add(cancel);

						setVisible(false);
						pFrame.setResizable(false);
						pFrame.setVisible(true);
						pFrame.setAlwaysOnTop(true);

						confirm.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) 
							{
								try
								{
									Statement stmt = conn.createStatement();
									String sql = "UPDATE `cse`.`appointments` SET `status`='approved' WHERE `appointmentsID`='" + pSelected + "';";
									stmt.executeUpdate(sql);
									patientVector.remove(pSelected);
									patientBox.updateUI();

									setVisible(false);
									docFrame.setVisible(true);
									pFrame.setVisible(false);

								}
								catch(Exception e){
									e.printStackTrace();
								}
							}
						});
						cancel.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event)
							{
								int reply = JOptionPane.showConfirmDialog(pFrame, "Are you sure?", "Confirm cancellation", JOptionPane.YES_NO_OPTION);
								if (reply == JOptionPane.YES_OPTION) {
									try
									{
										Statement stmt = conn.createStatement();
										String sql = "UPDATE `cse`.`appointments` SET `status`='denied' WHERE `appointmentsID`='" + pSelected + "';";
										stmt.executeUpdate(sql);
										patientVector.remove(pSelected);
										patientBox.updateUI();

										setVisible(true);	
										docFrame.setVisible(false);
										pFrame.setVisible(false);

									}
									catch(Exception e){
										e.printStackTrace();
									}

								}

							}	
						});

					}
				});
			}

		});
	}
}
