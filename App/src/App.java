
public class App {
	private static int id = 1;

	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{		
		// create a Login object and add it to the applet
		editButton editButton = new editButton(id);
		//set applet size to 500 X 150
		editButton.setSize(700, 500); // set size of window
		editButton.setLocation(10,10);
		editButton.setVisible(true);
	}
}
