import javax.swing.*;
import java.awt.*;
public class UpdateHCCMain {
	public static void main(String[] args){
	JFrame frame = new JFrame();
	UpdateHCFrame hcframe = new UpdateHCFrame();
	frame.setSize(500, 300);
	frame.add(hcframe);
	frame.setVisible(true);
	}
}