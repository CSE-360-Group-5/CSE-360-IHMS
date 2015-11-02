import java.awt.Component; 
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	static JLabel entInfo, report2, error;
	static JButton search, revise, delete, submit, cancel, newSearch, ok;
	static JFrame fUpdate1,fUpdate2,fRevise,fError;
	static Container pane1,pane2,pane3,pane4;
	static JTextField patient;
	static JPanel pan1,pan2,pan3,pan4;
	static JTextArea report;
	
	static JLabel patID, patName, patDOB, viewRecord, browse, error1, complete;
	//static JButton submit, upload, search, ok;
	static JFrame fUpload1, fUpload2; //fError;
	//static Container pane1,pane2,pane3;
	static JTextField inputID, inputName, inputDOB;
	//static JPanel pan1,pan2,pan3;
	static JComboBox reports;
	static String[] upReports = {"Report1", "Report2", "Report3"};
	
	static JRadioButton doc1, doc2, doc3;
	static JButton enter;
	static JScrollPane scal; 				//The scrollpane
	static JPanel pcal;
	static JTable Cal;
	//static Container pane;
	static DefaultTableModel mcal; 			//Table model
	static JFrame fmain, docFrame, pFrame;
	static String docSelected, pSelected;
	static String t,d,m,y;

	
	public static void main (String[] args){
		pat.add("Test1");
		pat.add("Test2");
		pat.add("Test3");
		Prescription hope = new Prescription("drug","Test1","Doctor","Direction",23,24,10,2015, "Report", 12059, 19011995);
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
			Prescription temp = new Prescription("drug","Joshua Liddell","Doctor","Direction",23,24,10,2015,"Joshua Liddell Medical Report",123456789,27021985);
			presc.add(temp);
			
			// Search for patient
			fUpdate1 = new JFrame("Update Medical Report");
			fUpdate1.setSize(330, 375);
			pane = fUpdate1.getContentPane();
			pane.setLayout(null);
			//fUpdate1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			pan1 = new JPanel(null);
			entInfo = new JLabel("Enter first and last name of patient");
			search = new JButton("Search");
			patient = new JTextField();
			
			pane.add(pan1);
			pan1.add(entInfo);
			pan1.add(patient);
			pan1.add(search);
			
			search.setBounds(10, 190, 80, 40);
			entInfo.setBounds(10, 40, 300, 40);
			patient.setBounds(10, 80, 200, 30);
			
			//Update reports
			fUpdate2 = new JFrame("Update Medical Report");
			fUpdate2.setSize(330, 375);
			pane2 = fUpdate2.getContentPane();
			pane2.setLayout(null);
			//fUpdate2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		
			pan2 = new JPanel(null);
			revise = new JButton("Revise");
			delete = new JButton("Delete");
			newSearch = new JButton("Search");
			report2 = new JLabel(temp.getReport());
			
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
			//fRevise.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			pan3 = new JPanel(null);
			report = new JTextArea(temp.getReport());
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
					String patName = patient.getText();
					if(presc.get(0).getPat().equals(patName))
					{
						fUpdate2.setVisible(true);
						fUpdate1.setVisible(false);
					}
					else
						error1();
					
					
				}
			});
			
			delete.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					
					//report2.setText("");
					presc.get(0).setReport("");//does not work
					report2.setText(presc.get(0).getReport());
					fUpdate2.setVisible(true);
					fUpdate1.setVisible(false);
					
					
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
					report.setText(presc.get(0).getReport());
					fRevise.setVisible(true);
					fUpdate2.setVisible(false);
					fUpdate1.setVisible(false);
					
					
					
				}
			});
			
			submit.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent event)
				{
					String editReport = report.getText();
					presc.get(0).setReport(editReport);//does not work
					report2.setText(presc.get(0).getReport());
					//report2.setText(editReport);
					fUpdate2.setVisible(true);
					fRevise.setVisible(false);
					fUpdate1.setVisible(false);
					
					
					
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

		public static void error1()
		{
			fError = new JFrame("Error");
			fError.setSize(250, 250);
			pane4 = fError.getContentPane();
			pane4.setLayout(null);
			//fError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
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
		Prescription temp=new Prescription("drug","Joshua Liddell","Doctor","Direction",23,24,10,2015,"Joshua Liddell Medical Report",123456789,27021985);
		presc.add(temp);
		
		fUpload1 = new JFrame("Upload Medical Report");
		fUpload1.setSize(330, 375);
		pane1 = fUpload1.getContentPane();
		pane1.setLayout(null);
		//fUpload1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		pan1 = new JPanel(null);
		patID = new JLabel("Enter patient ID");
		patName = new JLabel("Enter patient name");
		patDOB = new JLabel("Enter date of birth(ddmmyyyy)");
		viewRecord = new JLabel("View patient");
		submit = new JButton("Submit");
		inputID = new JTextField();
		inputName = new JTextField();
		inputDOB = new JTextField();
		
		pane1.add(pan1);
		pan1.add(patID);
		pan1.add(patName);
		pan1.add(inputID);
		pan1.add(inputName);
		pan1.add(patDOB);
		pan1.add(inputDOB);
		pan1.add(submit);
		
		
		patID.setBounds(10, 10, 200, 40);
		patName.setBounds(115, 10,200, 40);
		inputID.setBounds(10, 45, 100, 30);
		inputName.setBounds(115, 45, 200, 30);
		patDOB.setBounds(10, 90, 250, 40);
		inputDOB.setBounds(10, 120, 100, 30);
		submit.setBounds(10, 230, 80, 30);
		
		//Upload reports
		fUpload2 = new JFrame("Upload Medical Report");
		fUpload2.setSize(330, 375);
		pane2 = fUpload2.getContentPane();
		pane2.setLayout(null);
		//fUpload2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
		pan2 = new JPanel(null);
		browse = new JLabel("Browse for record");
		reports = new JComboBox(upReports);
		upload = new JButton("Upload");
		search = new JButton("Search");
		complete = new JLabel("");
		
		
		pane2.add(pan2);
		pan2.add(browse);
		pan2.add(reports);
		pan2.add(complete);
		pan2.add(upload);
		pan2.add(search);
		
		browse.setBounds(10, 40, 200, 40);
		reports.setBounds(10, 75, 200, 40);
		complete.setBounds(10,150,200,40 );
		upload.setBounds(10, 230, 80, 30);
		search.setBounds(200, 230, 80, 30);
		
		submit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event)
			{
				String patName = inputName.getText();
				String patID = inputID.getText();
				String patDOB = inputDOB.getText();
						
				if(presc.get(0).getPat().equals(patName)&&presc.get(0).getID().equals(patID)&&presc.get(0).getDOB().equals(patDOB))
				{
					fUpload2.setVisible(true);
					fUpload1.setVisible(false);
				}
				else
					error();
				
				
			}
		});
		
		upload.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent event)
			{
				//function to upload to database here
				complete.setText("Submission Complete");
				fUpload2.setVisible(true);
				fUpload1.setVisible(false);
				
				
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
		
		
		pan1.setBounds(0, 0, 320, 335);
		pan2.setBounds(0, 0, 320, 335);
		fUpload1.setResizable(false);
		fUpload1.setVisible(true);
	}
	public static void error()
	{
		fError = new JFrame("Error");
		fError.setSize(250, 250);
		pane3 = fError.getContentPane();
		pane3.setLayout(null);
		//fError.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
		
		final String[] doctors = {"Doctor 1", "Doctor 2", "Doctor 3", "Doctor 4", "Doctor 5", "..."};
		final JList doctorBox = new JList(doctors);
	
		enter = new JButton("Enter");
				
	    doctorBox.setBounds(85,80, 150, 100);
	    enter.setBounds(15,285, 300, 65);
	    fmain.add(doctorBox);
		fmain.add(enter);
		
		fmain.setResizable(false);
		fmain.setVisible(true);
		
		enter.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent event) 
	           {
	        	   
	        	   if(doctorBox.getSelectedValue() == doctors[0])
	       			{
	        		   docSelected = doctors[0];
	       			}
	       			else if(doctorBox.getSelectedValue() == doctors[1])
	       			{
	       				docSelected = doctors[1];
	       			}
	       			else if(doctorBox.getSelectedValue() == doctors[2])
	       			{
	       				docSelected = doctors[2];
	       			}
	       			else if(doctorBox.getSelectedValue() == doctors[3])
	       			{
	       				docSelected = doctors[3];
	       			}
	       			else
	       			{
	       				docSelected = doctors[4];
	       			}
	        	   
	        	   	docFrame = new JFrame (docSelected); 			//Create and name frame
	        	   	docFrame.setSize(330, 375); 					//Set size to 400x400 pixels
	       			pane = docFrame.getContentPane();
	       			pane.setLayout(null);							//Apply null layout
	       				   
	       			System.out.println("what");
	       			
					/*for(int i = 0; i < person.size(); i++)
	       			{
						System.out.println("hi");
	       				if(person.get(i).getDoctor() == docSelected)
	       				{
	       					 t = person.get(i).getTime().toString();
	       					 d = person.get(i).getDate();
	       					 m = person.get(i).getMonth();
	       					 y = person.get(i).getYear();
	       					 System.out.println("hey");
	       				}	 
	       			}*/
					
					final String[] patients = {"Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient 5", "..."};
					final JList patientBox = new JList(patients);
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
				        	  if(patientBox.getSelectedValue() == patients[0])
				       			{
				        		  pSelected = patients[0];
				       			}
				       			else if(patientBox.getSelectedValue() == patients[1])
				       			{
				       				pSelected = patients[1];
				       			}
				       			else if(patientBox.getSelectedValue() == patients[2])
				       			{
				       				pSelected = patients[2];
				       			}
				       			else if(patientBox.getSelectedValue() == patients[3])
				       			{
				       				pSelected = patients[3];
				       			}
				       			else
				       			{
				       				pSelected = patients[4];
				       			}
				        	  	pFrame = new JFrame (pSelected); 			//Create and name frame
				        	   	pFrame.setSize(330, 375); 					//Set size to 400x400 pixels
				       			pane = pFrame.getContentPane();
				       			pane.setLayout(null);							//Apply null layout
				       			pane.setLayout(new GridLayout(5,2,20,50));
				       			
				        	  	JLabel pTime = new JLabel(t);
				        	  	JLabel pMonth = new JLabel(m);
				       	  		JLabel pDay = new JLabel(d);
				       			JLabel pYear= new JLabel(y);
				        	  			
				       			JLabel time = new JLabel("   Appointment Time:");
				       			JLabel t = new JLabel("12:00 PM");
				       			JLabel month = new JLabel("   Month:");
				       			JLabel m = new JLabel("November");
				       			JLabel day = new JLabel("   Day:");
				       			JLabel  d = new JLabel("10");
				       			JLabel year = new JLabel("   Year:");
				       			JLabel y = new JLabel("2015");
				       			JButton confirm = new JButton("Confirm");
				       			JButton cancel = new JButton("Deny");
	       		
				       			pFrame.add(time);
				       			pFrame.add(t);
				       			//pFrame.add(pTime);
				       			pFrame.add(month);
				       			pFrame.add(m);
				       			//pFrame.add(pMonth);
				       			pFrame.add(day);
				       			pFrame.add(d);
				       			//pFrame.add(pDay);
				       			pFrame.add(year);
				       			pFrame.add(y);
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
				       					docFrame.setVisible(true);	
				       					pFrame.setVisible(false);
				       				}
				       			});
				           }
					});
	           }
	           
	           });

	}
}

