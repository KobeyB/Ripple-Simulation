import java.awt.BorderLayout;

import javax.swing.JFrame;

public class RippleWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3285619235236995244L;
	
	private static final double FRAME_RATE = 24;
	
	public static final int WIDTH = 1000;        // 4:3 aspect ratio
	public static final int HEIGHT = 750;
	
	
	public RippleWindow() {
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Ripple Simulator");
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		this.add(new MyPanel(FRAME_RATE), BorderLayout.CENTER);
		
		this.setVisible(true);
	}

}
