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
import javax.swing.table.DefaultTableModel;

public class Staff extends JFrame
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

	public Staff()
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

		pat.add("Test1");
		pat.add("Test2");
		pat.add("Test3");
		try{
			Statement sta = conn.createStatement();
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
			e.printStackTrace();
		}

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
				new FinalizeAppointment();
				System.out.println("Testf");
			}
		});
		upload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				new UploadMedicalReports();
				System.out.println("Testu");
			}
		});
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				new UpdateMedicalReports();
				System.out.println("Testud");
			}
		});
		epresc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				epres(presc);
			}
		});
		fappoin.setAlignmentX(Component.CENTER_ALIGNMENT);
		upload.setAlignmentX(Component.CENTER_ALIGNMENT);
		update.setAlignmentX(Component.CENTER_ALIGNMENT);
		epresc.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(staff);
		main.setResizable(false);
		main.setVisible(true);
	}
	public static void epres( ArrayList p)
	{
		final ArrayList temp=new ArrayList();
		final JFrame frame=new JFrame("E-Prescriptions");
		Container pane;
		JPanel panep = new JPanel(null);

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
				String name= (String) pat.get(patients.getSelectedIndex());
				int index=patients.getSelectedIndex();
				temp.clear();
				for(int i=0; i<presc.size();i++){
					if(((Prescription) presc.get(i)).getPat().equals(name)){
						temp.add(presc.get(i));
					}
				}
				for(int i=0;i<temp.size();i++){
					Prescription ta;
					for(int j=i-1;j>=0;j--){
						if(((Prescription) temp.get(i)).getYear()<((Prescription) temp.get(j)).getYear()){
							ta=(Prescription) temp.get(j);
							temp.set(j, temp.get(i));
							temp.set(i, ta);
						}
						else if(((Prescription) temp.get(i)).getYear()<((Prescription) temp.get(j)).getYear()){
							if(((Prescription) temp.get(i)).getMonth()<((Prescription) temp.get(j)).getMonth()){
								ta=(Prescription) temp.get(j);
								temp.set(j, temp.get(i));
								temp.set(i, ta);
							}
						}
						else if(((Prescription) temp.get(i)).getYear()<((Prescription) temp.get(j)).getYear()){
							if(((Prescription) temp.get(i)).getMonth()<((Prescription) temp.get(j)).getMonth()){
								if(((Prescription) temp.get(i)).getDate()<((Prescription) temp.get(j)).getDate()){
									ta=(Prescription) temp.get(j);
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
		patients.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(panep);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	static void epresl(final ArrayList p, int start,int index){
		final JFrame frame=new JFrame("E-Prescriptions");
		final int s=start;
		Container pane;
		final JPanel panep = new JPanel(null);
		final JPanel panep2= new JPanel(null);
		JButton next = new JButton(">>");
		JButton prev = new JButton("<<");
		JLabel [] pt = new JLabel[p.size()],pi=new JLabel[p.size()];

		for(int i=0;i<p.size();i++){
			pt[i]=new JLabel("Drug: "+((Prescription) p.get(i)).getDrug()+",  Doctor: "+((Prescription) p.get(i)).getDoc());
			pi[i]=new JLabel("\tDirections: "+((Prescription) p.get(i)).getDir()+",  Date: "+Integer.toString(((Prescription) p.get(i)).getMonth())+"/"+Integer.toString(((Prescription) p.get(i)).getDate())+"/"+Integer.toString(((Prescription) p.get(i)).getYear()));
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
		//JLabel pa = new JLabel("Choose the Patient:");
		panep.add(patients);
		panep.add(Box.createVerticalStrut(15));
		
		for(int st=start;st<5&&st<p.size();st++){
			panep.add(pt[st]);
			panep.add(Box.createVerticalStrut(5));
			panep.add(pi[st]);
			panep.add(Box.createVerticalStrut(20));
		}
		
		panep2.add(prev);
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
				String name= (String) pat.get(patients.getSelectedIndex());
				int ind=patients.getSelectedIndex();
				p.clear();
				for(int i=0; i<presc.size();i++){
					if(((Prescription) presc.get(i)).getPat().equals(name)){
						p.add(presc.get(i));
					}
				}
				for(int i=0;i<p.size();i++){
					Prescription t;
					for(int j=i-1;j>=0;j--){
						if(((Prescription) p.get(i)).getYear()<((Prescription) p.get(j)).getYear()){
							t=(Prescription) p.get(j);
							p.set(j, p.get(i));
							p.set(i, t);
						}
						else if(((Prescription) p.get(i)).getYear()<((Prescription) p.get(j)).getYear()){
							if(((Prescription) p.get(i)).getMonth()<((Prescription) p.get(j)).getMonth()){
								t=(Prescription) p.get(j);
								p.set(j, p.get(i));
								p.set(i, t);
							}
						}
						else if(((Prescription) p.get(i)).getYear()<((Prescription) p.get(j)).getYear()){
							if(((Prescription) p.get(i)).getMonth()<((Prescription) p.get(j)).getMonth()){
								if(((Prescription) p.get(i)).getDate()<((Prescription) p.get(j)).getDate()){
									t=(Prescription) p.get(j);
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
		panep.setAlignmentY(Component.LEFT_ALIGNMENT);
		frame.setResizable(false);
		frame.setVisible(true);

	}
}
