package editortrees;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * A wrapper class for binary trees that can display the wrapped tree in a window.
 * 
 * @author Curt Clifton. Created Jan 24, 2008.
 */
public class DisplayableBinaryTree extends JComponent {
	// a stormy gray background to be easy on the eyes at night, and set a stormy mood.
	private static final Color BACKGROUND_COLOR = Color.DARK_GRAY;
	// a light blue color, keeping in line with the stormy color scheme
	private static final Color FOWARD_ARROW_COLOR = new Color(0x3399FF);
	private static final Color PARENT_ARROW_COLOR = new Color(0x77619A);
	private static final String FONT_NAME = "Comic Sans MS"; // comics sans for the win
	// private static final String FONT_NAME = "ESSTIXFifteen"; // change if you don't want to make it look cool
	// private static final String FONT_NAME = "ESSTIXThirteen"; // change if you don't want to make it look cool
	// private static final String FONT_NAME = "Jokerman"; // change if you don't want to make it look cool

	private int width;
	private int height;
	private EditTree tree;
	private JFrame frame;
	private double xDistance;
	private double circleRadius;
	private double yDistance;
	private double nodeX;
	private double nodeY;
	private double angle;
	private boolean angular;

	/**
	 * Constructs a new displayable binary tree, set to default to the given window size for display..
	 * 
	 * @param tree
	 * @param windowWidth
	 *            in pixels
	 * @param windowHeight
	 *            in pixels
	 */
	public DisplayableBinaryTree(EditTree tree, int windowWidth, int windowHeight) {
		this.angle = 0;
		this.width = windowWidth;
		this.height = windowHeight;
		this.tree = tree;
		this.angular = Math.random() < 0.1;
		Runnable repainter = new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(10);
						repaint();
					}
				} catch (InterruptedException exception) {
					// Reports interrupt
				}
			}
		};
		new Thread(repainter).start();
	}

	/**
	 * Creates a new window, using the current size information stored in this, and renders the current state of the
	 * tree wrapped by this.
	 */
	public void display() {
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setMinimumSize(new Dimension(this.tree.size() * 20 + 18, this.tree.height() * 20 + 45));
		this.frame.setSize(new Dimension(this.width, this.height));
		// set the background color to a stormy gray
		this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
		// add the tree to the frame
		this.frame.add(this);
		this.frame.setVisible(true);
		// this.frame.add(new DisplayableNode(this.tree.getRoot()));
	}

	/**
	 * Creates a new window, using the current size information stored in this, and renders the current state of the
	 * tree wrapped by this.
	 */
	public void display(boolean visable) {
		this.frame = new JFrame();
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setMinimumSize(new Dimension(this.tree.size() * 20 + 18, this.tree.height() * 20 + 45));
		this.frame.setSize(new Dimension(this.width, this.height));
		// set the background color to a stormy gray
		this.frame.getContentPane().setBackground(BACKGROUND_COLOR);
		// add the tree to the frame
		this.frame.add(this);
		this.frame.setVisible(visable);
		// this.frame.add(new DisplayableNode(this.tree.getRoot()));
	}

	public void close() {
		this.frame.dispose();
		// this.frame.setVisible(false);
	}

	/**
	 * Sets the default size for the next window displayed.
	 * 
	 * @param windowWidth
	 *            in pixels
	 * @param windowHeight
	 *            in pixels
	 */
	public void setSize(int windowWidth, int windowHeight) {
		this.width = windowWidth;
		this.height = windowHeight;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// anti aliasing makes everything better
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.width = this.frame.getWidth() - 18; // adjust for margins
		this.height = this.frame.getHeight() - 45; // adjust for the margins

		int treeHeight = this.tree.height();
		int treeSize = this.tree.size();
		if (treeSize < 1) {
			return;
		}

		this.xDistance = this.width / ((double) (treeSize)); // make the constant
		this.circleRadius = this.xDistance / 2.0; // sets the circle diameter to the delta x distance
		// System.out.println(treeSize);
		// System.out.println(treeHeight);
		this.circleRadius *= 1.25;
		if (this.angular) {
			this.angle += 0.0001;
			// look up foyier siries
			this.circleRadius += 10 * Math.sin(3 * this.angle) + 2 * Math.cos(15 * this.angle);
		}
		this.xDistance = (this.width - this.circleRadius * 2) / ((double) (treeSize - 1));
		// calculates the delta y distance by equally dividing up the height minus the circle diameter
		this.yDistance = (this.height - 2 * circleRadius) / ((double) (treeHeight));

		// start at the upper left corner
		this.nodeX = this.circleRadius;
		this.nodeY = this.circleRadius;

		int size = 0;
		// loops throught font sizes, to get the right font size
		while (true) {
			// System.out.println(size);
			FontMetrics metric = g2.getFontMetrics(new Font(FONT_NAME, Font.CENTER_BASELINE, size));
			int height = metric.getHeight();
			int width = metric.getMaxAdvance();
			// times 4 works out nice
			// if the diagonal is 4 or 2.5 times the radius stop making it bigger
			double multiplyer = FONT_NAME.equals("Comic Sans MS") ? 1.5 : 2.5;
			if (Math.sqrt(height * height + width * width) > multiplyer * this.circleRadius) {
				g2.setFont(new Font(FONT_NAME, Font.PLAIN, --size));
				// System.out.println(g2.getFont().getSize());
				break; // done
			}
			size++;
		}
		// RAISE THE BAR VVVVV
		g2.setColor(Color.blue); // blue looks so much better
		g2.fill(new Rectangle2D.Double(this.width - 5, 50, 10, 5));
		g2.fill(new Rectangle2D.Double(this.width - 10, 60, 20, 5));
		g2.fill(new Rectangle2D.Double(this.width - 15, 70, 30, 5));
		g2.fill(new Rectangle2D.Double(this.width - 20, 80, 40, 5));
		g2.fill(new Rectangle2D.Double(this.width - 25, 90, 50, 5));
		// // RAISE THE BAR ^^^^^
		Node current = this.tree.getRoot();
		// CURRENT.POINT = THE CENTER POINT, NOT THE UPPER LEFT CORNER
		this.paintHelper(g2, current, this.nodeY);
		this.lineHelper(g2, current);
		// System.out.println("DONE");
	}

	/**
	 * helper method to paint nodes
	 * 
	 * @param g2
	 * @param current
	 * @param nodeY
	 */
	private void paintHelper(Graphics2D g2, Node current, double nodeY) {
		if (current.getLeft() != Node.NULL_NODE) {
			this.paintHelper(g2, current.getLeft(), nodeY + this.yDistance); // recurse
		}
		// set up the node
		current.setPoint(this.nodeX, nodeY);
		current.setCircleRadius(this.circleRadius);
		current.displayNode(g2); // display the node by passing the graphics2D
		this.nodeX += this.xDistance;
		if (current.getRight() != Node.NULL_NODE) {
			this.paintHelper(g2, current.getRight(), nodeY + this.yDistance); // recurse
		}
	}

	/**
	 * 
	 * @param g2
	 * @param current
	 */
	private void lineHelper(Graphics2D g2, Node current) {
		if (current.parent != Node.NULL_NODE) {
			this.drawParentArrow(g2, current.getPoint(), current.parent.getPoint());
		}
		// only if has left child
		if (current.getLeft() != Node.NULL_NODE) {
			// draw line arrow
			this.drawFowardArrow(g2, current.getPoint(), current.getLeft().getPoint());
			this.lineHelper(g2, current.getLeft()); // recurse
		}
		// only if has right child
		if (current.getRight() != Node.NULL_NODE) {
			// draw line arrow
			this.drawFowardArrow(g2, current.getPoint(), current.getRight().getPoint());
			this.lineHelper(g2, current.getRight()); // recurse
		}
	}

	/**
	 * makes the frame take an arrow to the knee
	 * 
	 * @param g2
	 *            graphics
	 * @param start
	 *            center point of the parent
	 * @param end
	 *            center point of the child
	 */
	private void drawParentArrow(Graphics2D g2, Point2D.Double start, Point2D.Double end) {
		g2.setColor(PARENT_ARROW_COLOR);
		double SIZE_MULTIPLIER = 1.5;
		AffineTransform transform = g2.getTransform(); // save graphics state to restore later
		double angle = 0;
		try {
			angle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
		} catch (NullPointerException e) {
			return;
		}
		g2.translate(end.getX(), end.getY()); // move the center of the child node
		g2.rotate(angle + Math.PI / 2.0); // rotate
		g2.translate(0, this.circleRadius); // move the edge of the circle
		double arrowLength = start.distance(end) - 2 * this.circleRadius; // distance is from edge to edge
		 Line2D.Double line = new Line2D.Double(0, 0, 0, arrowLength);
		 g2.draw(line);

		Path2D.Double arrowHead = new Path2D.Double(); // paths are cool
		double arrowLengthSqrt = Math.sqrt(arrowLength); // scales better with the sqrt
		// draws the arrow head
		arrowHead.moveTo(0, 0);
		arrowHead.lineTo(-arrowLengthSqrt / SIZE_MULTIPLIER, 2 * arrowLengthSqrt / SIZE_MULTIPLIER);
		arrowHead.lineTo(arrowLengthSqrt / SIZE_MULTIPLIER, 2 * arrowLengthSqrt / SIZE_MULTIPLIER);
		arrowHead.closePath();

		g2.fill(arrowHead);
		g2.setTransform(transform); // restores the graphics state
	}

	/**
	 * makes the frame take an arrow to the knee
	 * 
	 * @param g2
	 *            graphics
	 * @param start
	 *            center point of the parent
	 * @param end
	 *            center point of the child
	 */
	private void drawFowardArrow(Graphics2D g2, Point2D.Double start, Point2D.Double end) {
		g2.setColor(FOWARD_ARROW_COLOR);
		AffineTransform transform = g2.getTransform(); // save graphics state to restore later
		// get the correct rotation angle
		if (end ==null|| start == null) {
			//System.out.println("NULL ANGLE");
			return;
		}
		double angle = 0;
		try {
			angle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
		} catch (NullPointerException e) {
			// silently ignore that shit
			return;
		}
		g2.translate(end.getX(), end.getY()); // move the center of the child node
		g2.rotate(angle + Math.PI / 2.0); // rotate
		g2.translate(0, this.circleRadius); // move the edge of the circle
		double arrowLength = start.distance(end) - 2 * this.circleRadius; // distance is from edge to edge
		Line2D.Double line = new Line2D.Double(0, 0, 0, arrowLength);
		g2.draw(line);

		Path2D.Double arrowHead = new Path2D.Double(); // paths are cool
		double arrowLengthSqrt = Math.sqrt(arrowLength); // scales better with the sqrt
		// draws the arrow head
		arrowHead.moveTo(0, 0);
		arrowHead.lineTo(-arrowLengthSqrt, arrowLengthSqrt * 2);
		arrowHead.lineTo(arrowLengthSqrt, arrowLengthSqrt * 2);
		arrowHead.closePath();

		g2.fill(arrowHead);
		g2.setTransform(transform); // restores the graphics state
	}

	/**
	 * returns a string that gives the given time difference in easily read time units
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeUnits(long time) {
		double newTime = time;
		if (time < 1000) {
			return String.format("%d NanoSeconds", time);
		} else {
			newTime = time / 1000.0;
			if (newTime < 1000) {
				return String.format("%f MicroSeconds", newTime);
			} else {
				newTime /= 1000.0;
				if (newTime < 1000) {
					return String.format("%f MiliSeconds", newTime);
				} else {
					newTime /= 1000.0;
					if (newTime < 300) {
						return String.format("%f Seconds", newTime);
					} else {
						newTime /= 60.0;
						if (newTime < 180) {
							return String.format("%f Minutes", newTime);
						} else {
							return String.format("%f Hours", newTime / 60.0);
						}
					}
				}
			}
		}
	}

}
