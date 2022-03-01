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

	public XWindowPanel () {
		stoneFrame = new ImageIcon("/Users/wildboargonnago/wildboar-princeton-algs/algs1/week_4/edu.lchantel.school-21.npuzzle/resources/sandstone_frame.png")
			.getImage();
		transform = new AffineTransform();
		transform.setTransform(0.1, 0, 0, 0.1, 100, 100);
		timer = new Timer(1000, this);

		setPreferredSize(new Dimension(600, 600));
	}

	@Override
	public void	paint(Graphics g) {

		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(stoneFrame, transform, null);

		g2D.setFont(new Font("DooM", Font.PLAIN, 30));
		g2D.setColor(new Color(0xd21404));
		g2D.drawString("5", 138, 162);
	}

	@Override
	public void			actionPerformed(ActionEvent e) {

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
