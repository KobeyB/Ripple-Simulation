import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Dot implements ActionListener{
	
	private static final double DAMPING_SPEED = 7.0;
	private static final double RIPPLE_SPEED = 4.0;
	
	private static int initialDotSize = 13;
	private static int initialGValue = 80;
	private static int initialBValue = 200;
	private static Color initialColor = new Color(0, initialGValue, initialBValue);
	
	private Timer timer;
	private double t;
	
	private int size;
	private Color color;
	
	private int x;
	private int y;
	
	public boolean isRippling;
	
	private double frameRate;

	public Dot(int x, int y, double frameRate) {
		
		this.frameRate = frameRate;
		
		this.timer = new Timer((int) (1000/this.frameRate), this);
		
		this.x = x;
		this.y = y;
		
		this.size = initialDotSize;
		this.color = initialColor;
	}
	
	
	// paint methods
	
	public void render(Graphics2D g2d) { 
		g2d.setColor(color);
		g2d.fillOval(x, y, size, size);
		
		if (isRippling) {
			ripple();
		}
		else {
			timer.stop();
			t = 0;
		}
	}
	
	
	// other methods
	
	public void ripple() {
		
		if (!timer.isRunning()) {
			this.t = 0;
			timer.start();
		}
		
		/*
		 * 
		 *  Ripple equation: y = Ae^-bt cos(wt + Ø)
		 *  A and Ø are constants that only affect the bounds of the values
		 *  e is Euler's number
		 *  b is the dampening coefficient; it is a measure of the amount of dampening force
		 *  t is time
		 *  w is the natural (angular) frequency
		 *  
		 *  Credit for equation: Diganta Bhaskar, M.Sc Theoretical Physics and Mathematics (found on Quora)
		 *  https://www.quora.com/What-is-the-equation-describing-a-ripple-in-water?share=1
		 *  
		 */

		this.size = (int) Math.round(
				10 * Math.pow(Math.E, -(t/DAMPING_SPEED))*Math.cos(RIPPLE_SPEED*t + 0.8) + initialDotSize);
		
		int gValue = (int) Math.round(
				70 * Math.pow(Math.E, -(t/DAMPING_SPEED))*Math.cos(RIPPLE_SPEED*t + 0.8) + initialGValue);
		
		int bValue = (int) Math.round(
				60 * Math.pow(Math.E, -(t/DAMPING_SPEED))*Math.cos(RIPPLE_SPEED*t + 0.8) + initialBValue);
		
		
		this.color = new Color(0, gValue, bValue);
		
	}
	
	public void isRippling(boolean b) {
		isRippling = b;
	}
	
	
	// getters and setters

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setT(int t) {
		this.t = t;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == timer) {
			
			this.t += 0.1;
		}
		
	}
	
}
