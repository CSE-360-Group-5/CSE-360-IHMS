///////////////////////////////////////////////////////////////////////////////////////
// Name: Login.java
// Authors: Rian Martins
// Description: Initialize the applet with the login panel
// Date: 11/7s/2015
///////////////////////////////////////////////////////////////////////////////////////

import javax.swing.JApplet;

public class Login extends JApplet
{

	private static final long serialVersionUID = 1L;

	public void init()
	{		
		// create a Login object and add it to the applet
		LoginPanel LoginPanel = new LoginPanel();
		getContentPane().add(LoginPanel);

		//set applet size to 500 X 150
		setSize(500, 150); // set size of window
		//setLocation(10,10);
	}
}
