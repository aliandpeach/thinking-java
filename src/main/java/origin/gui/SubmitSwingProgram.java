package origin.gui;//: gui/SubmitSwingProgram.java

import javax.swing.*;
import java.util.concurrent.*;

public class SubmitSwingProgram extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3361787694726416419L;
	JLabel label;

	public SubmitSwingProgram() throws Exception {
		super("Hello Swing");
		TimeUnit.SECONDS.sleep(1);
		label = new JLabel("A Label");
		add(label);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 100);
		setVisible(true);
	}

	static SubmitSwingProgram ssp;

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				try {
					ssp = new SubmitSwingProgram();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ssp.label.setText("Hey! This is Different!");
			}
		});
	}
} // /:~
