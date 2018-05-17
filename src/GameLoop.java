import javax.swing.*;
import java.awt.*;

public class GameLoop {

    static int gameWidth = 1200;
    static int gameHeight = 900;

    static int gridSizeX = 10;
    static int gridSizeY = 10;

    public static void main(String[] args) throws InterruptedException{
        JFrame frame = new JFrame("Conway's Game of Life");



        JButton btn2 = new JButton("First");
        JButton btn3 = new JButton("First");

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(gridSizeX,gridSizeY-1,1,1));

        JPanel[][] multi = new JPanel[gridSizeX][gridSizeY];

        for (int i = 0; i < gridSizeX*gridSizeY; i++) {
            JPanel jp1 = new JPanel();
            jp1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jp1.setBackground(Color.LIGHT_GRAY);
            gridPanel.add(jp1);

            int x_pos = i % gridSizeY;
            int y_pos = (int) i / gridSizeY;

            multi[x_pos][y_pos] = jp1;
        }

        multi[0][0].setBackground(Color.RED);
        multi[0][1].setBackground(Color.RED);
        multi[2][0].setBackground(Color.RED);
        multi[2][1].setBackground(Color.RED);

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
