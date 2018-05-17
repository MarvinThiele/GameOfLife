import javax.swing.*;
import java.awt.*;

public class GameLoop {

    static int gameWidth = 1200;
    static int gameHeight = 900;

    static int gridSizeX = 90;
    static int gridSizeY = 90;

    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Conway's Game of Life");



        JButton btn2 = new JButton("First");
        JButton btn3 = new JButton("First");

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(gridSizeX,gridSizeY));

        for (int i = 0; i < gridSizeX*gridSizeY; i++) {
            JPanel jp1 = new JPanel();
            jp1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (i % 2 == 0) {
                jp1.setBackground(Color.LIGHT_GRAY);
            }
            else {
                jp1.setBackground(Color.DARK_GRAY);
            }
            gridPanel.add(jp1);
        }

        gridPanel.add(btn2);
        gridPanel.add(btn3);

        frame.add(gridPanel);
        frame.setSize(gameWidth, gameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread.sleep(2000);
        while (true) {
            Thread.sleep(10);
        }
    }
}
