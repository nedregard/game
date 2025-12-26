package graphics;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

public class GameWindow {

    private JFrame frame;

    public GameWindow(GamePanel panel) {
        frame = new JFrame("My Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        frame.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);

        GraphicsDevice device =
            GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        device.setFullScreenWindow(frame);

        frame.validate();
        frame.setVisible(true);

        panel.requestFocusInWindow();
    }
}