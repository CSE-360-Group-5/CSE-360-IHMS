import javax.swing.JApplet;

public class LoginApplet extends JApplet
{

	private static final long serialVersionUID = 1L;

	public void init()
	{
		// create a Login object and add it to the applet
		Login LoginPanel = new Login();
		getContentPane().add(LoginPanel);

		//set applet size to 500 X 150
		setSize(500, 150); // set size of window
	}
}
