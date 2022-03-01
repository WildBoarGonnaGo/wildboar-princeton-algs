import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class XWindowPanel extends JPanel implements ActionListener {
	private final Image 			stoneFrame;
	private final AffineTransform	transform;
	private final Timer				timer;
	private int						xV, yV, xCur, xEnd, yCur, yEnd;

	public XWindowPanel () {
		stoneFrame = new ImageIcon("/home/zavelskiymm/wildboar-princeton-algs/algs1/week_4/edu.lchantel.school-21.npuzzle/resources/sandstone_frame.png")
			.getImage();
		transform = new AffineTransform();
		transform.setTransform(0.2, 0, 0, 0.2, 100, 100);
		timer = new Timer(500, this);
		xV = 4;
		xCur = 100; xEnd = 200;
		yCur = 100; yEnd = 100;

		setPreferredSize(new Dimension(600, 600));
		timer.start();
	}

	@Override
	public void	paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(stoneFrame, transform, null);

		g2D.setFont(new Font("DooM", Font.PLAIN, 60));
		g2D.setColor(new Color(0xd21404));
		g2D.drawString("5", xCur + 76, yCur + 124);
	}

	@Override
	public void			actionPerformed(ActionEvent e) {
		xCur += xV;
		if (xCur >= xEnd) timer.stop();
		else if (xEnd - xCur <= 13) timer.setDelay(750);
		else if (xEnd - xCur <= 25) timer.setDelay(600);
		transform.setTransform(0.2, 0, 0, 0.2, xCur, yCur);
		repaint();
	}

	public static void	main(String[] args) {

		JFrame 			frame = new JFrame();
		XWindowPanel	panel = new XWindowPanel();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
