/*Contents of CalendarProgran.class */

//Import packages
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CalendarProgram{
	static int ryear, rmonth, rday, currentYear, currentMonth;
	static JLabel lmonth, lyear;
	static JButton prev, next, canc;
	static JScrollPane scal; //The scrollpane
	static JPanel pcal;
	static JTable Cal;
	static Container pane;
	static DefaultTableModel mcal; //Table model
	static JComboBox cyear, docName, appTime;
	static JFrame fmain;
	
	public static void main (String args[]){
		//style that is necessary
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Standard preparation for a frame
		fmain = new JFrame ("Schedule Appointments"); //Create and name frame
		fmain.setSize(330, 375); //Set size to 400x400 pixels
		pane = fmain.getContentPane();
		pane.setLayout(null); //Apply null layout
		fmain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked

		//controls and portions of Calendar
		lmonth = new JLabel ("January");
		lyear = new JLabel ("Change year:");
		cyear = new JComboBox();
		prev = new JButton ("<<");
		next = new JButton (">>");
		canc= new JButton("Cancel");
		mcal = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		Cal = new JTable(mcal);
		scal = new JScrollPane(Cal);
		pcal = new JPanel(null);
 
		canc.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent event) {
	               System.exit(0);
	          }
	       });
		
		//action listeners for buttons and the like
		prev.addActionListener(new btnPrev_Action());
		next.addActionListener(new btnNext_Action());
		cyear.addActionListener(new cmbYear_Action());
		Cal.addMouseListener(new mouseCont());
		
		//Adding the elements to the pane
		pane.add(pcal);
		pcal.add(lmonth);
		pcal.add(cyear);
		pcal.add(prev);
		pcal.add(next);
		pcal.add(canc);
		pcal.add(scal);
		
		//Setting where the elements are on the pane
		pcal.setBounds(0, 0, 320, 335);
		lmonth.setBounds(160-lmonth.getPreferredSize().width/2, 25, 100, 25);
		canc.setBounds(10, 305, 80, 20);
		cyear.setBounds(215, 305, 100, 20);
		prev.setBounds(10, 25, 50, 25);
		next.setBounds(260, 25, 50, 25);
		scal.setBounds(10, 50, 300, 250);
		
		//Make frame visible
		fmain.setResizable(false);
		fmain.setVisible(true);
		
		//Inner workings for the day mechanism
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		rday = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		rmonth = cal.get(GregorianCalendar.MONTH); //Get month
		ryear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = rmonth; //Match month and year
		currentYear = ryear;
				
		//Add days
		String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All of the days
		for (int i=0; i<7; i++){
			mcal.addColumn(days[i]);
		}
		
		Cal.getParent().setBackground(Cal.getBackground()); //Set background

		//No resize/reorder
		Cal.getTableHeader().setResizingAllowed(false);
		Cal.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		Cal.setColumnSelectionAllowed(true);
		Cal.setRowSelectionAllowed(true);
		Cal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		Cal.setRowHeight(38);
		mcal.setColumnCount(7);
		mcal.setRowCount(6);
		
		//Placing the dates in the cells
		for (int i=ryear-100; i<=ryear+100; i++){
			cyear.addItem(String.valueOf(i));
		}
		
		//Refresh calendar
		refreshCalendar (rmonth, ryear); //Refresh calendar
		
	}
	
	public static void refreshCalendar(int month, int year){
		//instantiation
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int numoday, startom; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		prev.setEnabled(true);
		next.setEnabled(true);
		if (month == 0 && year <= ryear){prev.setEnabled(false);} //Cannot set an appointment back in time
		if (month == 11 && year >= ryear+50){next.setEnabled(false);} //Too early to set an appointment
		lmonth.setText(months[month]); //Refresh the month label (at the top)
		lmonth.setBounds(160-lmonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		cyear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//deletes current table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mcal.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		numoday = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		startom = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//Create calendar
		for (int i=1; i<=numoday; i++){
			int row = new Integer((i+startom-2)/7);
			int column  =  (i+startom-2)%7;
			mcal.setValueAt(i, row, column);
		}

		//Apply renderers
		Cal.setDefaultRenderer(Cal.getColumnClass(0), new tblCalendarRenderer());
	}
	//adds a bit of color style to claendar
	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == rday && currentMonth == rmonth && currentYear == ryear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class mouseCont extends MouseAdapter{//use this to access get another frame pulled up to note down appointment time and what not
		public void mouseClicked(MouseEvent e){
			int row=Cal.getSelectedRow();//this will give you the row number
			int col=Cal.getSelectedColumn();//this will give you the column number
			if(row!=-1 && col!=-1 && Cal.getValueAt(row, col)!=null){//this now will only activate when there is an actual date associated with that day
				input(row,col,currentYear);
			}
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cyear.getSelectedItem() != null){
				String b = cyear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}

	static String[] input(int d, int m, int y){//input frame for schedule
		String[] out=new String[4];//appointment info storage
		
		final String[] doctors = {"Dr. Whitehead", "Dr. Namie", "Dr. Liddell"};
		
		final JFrame input=new JFrame("Make Appointment");
		Container pane1;
		JPanel pan=new JPanel(null);
		JLabel doctor=new JLabel("Choose your Doctor:");
		JLabel da=new JLabel("Date:");
		/*JLabel day=new JLabel(Integer.toString(d));
		JLabel mo=new JLabel("Month:");
		JLabel month=new JLabel(Integer.toString(m));
		JLabel ye=new JLabel("Year:");
		JLabel year=new JLabel(Integer.toString(y));*/
		JLabel time=new JLabel("Appointment Time:");
		JButton submit=new JButton("Submit");
		docName = new JComboBox(doctors);
		
		JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "hh:mm a");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setValue(new Date());
				
		JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "MMM/dd/yyyy");
		dateSpinner.setEditor(dateEditor);
		dateSpinner.setValue(new Date());
		
		input.setSize(330, 375); //Set size to 400x400 pixels
		pane1 = input.getContentPane();
		pane1.setLayout(null); //Apply null layout
		pan.setLayout(new GridLayout(4,2,20,50));
		pane1.setSize((int)(input.getHeight()/2),(int)(input.getWidth()/2));
		pane1.setLocation(input.getHeight()-((int)(input.getHeight()/2)),input.getWidth()-((int)(input.getWidth()/2)));//trying to center failed
		input.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close when X is clicked
		pane1.add(pan);
		//pan.add(ye);
		//pan.add(year);
		pan.add(da);
		pan.add(dateSpinner);
		pan.add(time);
		pan.add(timeSpinner);
		pan.add(doctor);
		pan.add(docName);
		pan.add(submit);
		
		 submit.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent event) {
	        	   fmain.setVisible(true);	
	        	   input.setVisible(false);
	        	   }
	       });
		 
		pan.setBounds(0, 0, 320, 335);
		input.setResizable(false);
		input.setVisible(true);
		return out;
	}
}


