//////////////////////////////////////////////////////////////////
// Name: Registration.java
// Authors: Rian Martins
// Description: Creates the applet for the registration of users to the IPIMS
// Date: 11/6/2015
/////////////////////////////////////////////////////////////////

import javax.swing.*;

public class Registration extends JApplet
{

	private static final long serialVersionUID = 1L;

	public void init()
	{
		// create a RegistrationPanel object and add it to the applet
		RegistrationPanel registPanel = new RegistrationPanel();
		getContentPane().add(registPanel);

		//set applet size to 750 X 400
		setSize (750, 400);
	}
}
