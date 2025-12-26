import graphics.GamePanel;
import graphics.GameWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GamePanel panel = new GamePanel();
            new GameWindow(panel);
        });
    }
}