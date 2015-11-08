///////////////////////////////////////////////////////////////////////////////////////
// Name: LabRecords.java
// Authors: Rian Martins
// Description: Create the applet for the upload/update lab records of the IPIMS
// Date: 11/6/2015
///////////////////////////////////////////////////////////////////////////////////////


import javax.swing.*;

public class LabRecords extends JApplet
{
	private static final long serialVersionUID = 1L;

	public void init()
	{
		// create a LabRecordPanel object and add it to the applet
		LabRecordPanel labRecordPanel = new LabRecordPanel();
		getContentPane().add(labRecordPanel);

		//set applet size to 1100 X 500
		setSize(1100,500); // set size of window
	}
}

