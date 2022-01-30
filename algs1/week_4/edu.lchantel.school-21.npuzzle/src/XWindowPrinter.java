import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.*;

public class XWindowPrinter {
    public static void main(String[] args)
    {
        //create a frame
        JFrame frame = new JFrame();
        //set Window title
        frame.setTitle("Jframe title goes here");
        /*set proper close for window operation with close button
        * or window should be close manually. For example cmd + q
        * on MacOS*/
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        //prevent frame from being resized
        frame.setResizable(false);
        //set x-dimension, and y-dimension of our frame
        frame.setSize(420, 420);
        //make frame visible
        frame.setVisible(true);
        //set image icon for our frame
        ImageIcon icon = new ImageIcon("/Users/wildboargonnago/Pictures/tzeentch_icon.png");
        System.out.println("Image load status: " + icon.getImageLoadStatus());
        frame.setIconImage(icon.getImage());
        //change background color
        frame.getContentPane().setBackground(new Color(123, 50, 250));
    }
}
