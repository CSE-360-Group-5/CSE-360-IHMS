import javax.swing.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
public class UpdateHCCMain {
	public static void main(String[] args) throws IOException{
	JFrame frame = new JFrame();
	UpdateHCFrame hcframe = new UpdateHCFrame();
	frame.setSize(500, 300);
	frame.add(hcframe);
	frame.setVisible(true);
	DoctorFrame dframe = new DoctorFrame();
	}
}