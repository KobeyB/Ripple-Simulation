import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MyPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426940946811133635L;
	
	private final int DOT_GAP = 20;
	private final int NUM_OF_DOTS = RippleWindow.WIDTH/DOT_GAP * RippleWindow.HEIGHT/DOT_GAP;
	private final Color BG_COLOR = new Color(0, 0, 75);

	public static final int RIPPLE_WIDTH = 18;
	static final int RIPPLE_DENSITY = 100;
	
	private final Font font = new Font("Avenir", Font.PLAIN, 14);
	
	private Timer timer;
	private double t;
	private Dot[] dots;
	private Point clickedPoint;
	private double radius;
	private boolean hasStarted = false;
	
	
	public MyPanel(double frameRate) {
		
		timer = new Timer((int) (1000/frameRate), this);
		timer.start();
		
		dots = new Dot[this.NUM_OF_DOTS];
		
		this.radius = 0;
		
		int x = 0;
		int y = 0;
		
		// Spreads the dots across the screen according to the window height, width,
		// and gap size between dots
		for (int i=0; i<NUM_OF_DOTS; i++) {
			
			if (x % RippleWindow.WIDTH == 0 && x != 0) {
				x = 0;
				y += this.DOT_GAP;
			}
			
			dots[i] = new Dot(x, y, frameRate);
			
			x+= this.DOT_GAP;
			
		}
		
		this.addMouseListener(new ClickListener());
		
	}
	
	// Paint methods
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.setBackground(BG_COLOR);
		
		Graphics2D g2d = (Graphics2D) g;
		
		for (int i=0; i<this.dots.length; i++) {
			Dot d = dots[i];
			d.render(g2d);
		}
		
		if (clickedPoint == null && !hasStarted)	
			showDirectionsMessage(g2d);

	
		g2d.setColor(new Color(150, 150, 150));
		g2d.setFont(font);
		g2d.drawString("Author: Kobey Buhr", 12, this.getHeight() - 12);
		
	}
	
	private void showDirectionsMessage(Graphics2D g2d) {
		
		g2d.setFont(new Font("Avenir", Font.PLAIN, 20));
		
		int stringWidth = g2d.getFontMetrics().stringWidth("Click anywhere to simulate a ripple");
		
		int rectWidth = stringWidth + 20;
		int rectHeight = g2d.getFontMetrics().getAscent() + 10;
		int rectX = (int) (this.getWidth()/2.0 - rectWidth/2.0);
		int rectY = (int) (this.getHeight()/2.0 - rectHeight/4.0);
		
		g2d.setColor(new Color(70, 70, 70, 200));
		g2d.fillRoundRect(rectX, rectY, rectWidth, rectHeight, 20, 20);
		
		g2d.setColor(new Color(200, 200, 200));
		
		g2d.drawString("Click anywhere to simulate a ripple",
				(int) (RippleWindow.WIDTH/2.0 - stringWidth/2.0), (int) (RippleWindow.HEIGHT/2.0));
		
	}

	public class ClickListener extends MouseAdapter {
		
		@Override
		public void mousePressed(MouseEvent e) {
			hasStarted = true;
			t = 0;
			radius = 0;
			clickedPoint = e.getPoint();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == timer) {
			
			t += 0.1;
			
			if (clickedPoint != null) {

				this.radius = RIPPLE_DENSITY * t;
				
				if (radius < RippleWindow.HEIGHT * 2) {
					for(int i=0; i<dots.length; i++) {
						Dot d = dots[i];
						
						double dSquared = Math.pow((d.getX() - clickedPoint.x), 2)
								+ Math.pow((d.getY() - clickedPoint.y), 2);
						
						// Find dots that are within a donut of width RIPPLE_WIDTH
						// Cause dots to start rippling if in donut
						if (dSquared <= Math.pow(radius + RIPPLE_WIDTH, 2)
								&& dSquared >= Math.pow(radius - RIPPLE_WIDTH, 2)) {
							
							d.setT(0);
							d.isRippling(true);
						}
					}
				}
				
				if (radius > Math.max(RippleWindow.WIDTH, RippleWindow.HEIGHT) + 200)
					clickedPoint = null;
				
			}
		}
		
		repaint();
	}

}
