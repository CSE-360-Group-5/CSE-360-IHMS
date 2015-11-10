//////////////////////////////////////////////////////////////////
// Name: statsReport.java
// Authors: Nathan Chancellor, Zarif Akhtab, Rian Martins
// Description: GUI inferface for the generation of the statistical report
// Date: 10/9/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;

public class StatsReport extends JApplet
{

	private static final long serialVersionUID = 1L;

	public void init()
	{
		// create a Statistical Report object and add it to the applet
		StatsReportPanel statsReportPanel = new StatsReportPanel();
		getContentPane().add(statsReportPanel);

		//set applet size to 800 X 600
		setSize(800,600); // set size of window
	}
}
