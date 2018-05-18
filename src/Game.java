import javax.swing.*;
import java.awt.*;

public class Game {
    private static Game uniqueInstance;
    static int gameWidth = 1000;
    static int gameHeight = 1000;

    static int gridSizeX = 100;
    static int gridSizeY = 100;

    public Boolean paused = false;

    private Game() {

    }

    public static Game getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    public void run() {
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setLayout(new BorderLayout());

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new pauseButtonListener());

        JPanel gridPanel = new JPanel();
        JPanel user_interface = new JPanel();

        user_interface.add(pauseButton);

        gridPanel.setLayout(new GridLayout(gridSizeX,gridSizeY-1,1,1));
        Cell[][] cells = new Cell[gridSizeX][gridSizeY];

        // Generate Cells and Panels
        for (int i = 0; i < gridSizeX*gridSizeY; i++) {
            Cell currentCell = new Cell(false);
            currentCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            currentCell.setBackground(Color.LIGHT_GRAY);
            currentCell.addMouseListener(new myMouseListener());
            gridPanel.add(currentCell);

            int x_pos = i % gridSizeY;
            int y_pos = (int) i / gridSizeY;

            cells[x_pos][y_pos] = currentCell;
        }

        // Give Neighbors
        for (int x = 0; x < gridSizeX; x++) {
            for (int y = 0; y < gridSizeY; y++) {
                //Left Border
                if (x == 0) {
                    // Top Left
                    if (y == 0) {
                        System.out.println(x);
                        System.out.println(y);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y+1]);
                        cells[x][y].fillNeighbors();
                    }
                    // Bottom Left
                    else if (y == gridSizeY-1) {
                        cells[x][y].neighbors.add(cells[x][y-1]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y-1]);
                        cells[x][y].fillNeighbors();
                    }
                    // Middle
                    else {
                        cells[x][y].neighbors.add(cells[x][y-1]);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].neighbors.add(cells[x+1][y-1]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y+1]);
                        cells[x][y].fillNeighbors();
                    }
                }
                //Right Border
                else if (x == gridSizeX-1) {
                    // Top Right
                    if (y == 0) {
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x-1][y+1]);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].fillNeighbors();
                    }
                    // Bottom Right
                    else if (y == gridSizeY-1) {
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x-1][y-1]);
                        cells[x][y].neighbors.add(cells[x][y-1]);
                        cells[x][y].fillNeighbors();
                    }
                    // Middle
                    else {
                        cells[x][y].neighbors.add(cells[x][y-1]);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].neighbors.add(cells[x-1][y-1]);
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x-1][y+1]);
                    }
                }
                //Middle
                else {
                    // Middle Top
                    if (y == 0) {
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x-1][y+1]);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].neighbors.add(cells[x+1][y+1]);
                    }
                    // Middle Bottom
                    else if (y == gridSizeY-1) {
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y-1]);
                        cells[x][y].neighbors.add(cells[x][y-1]);
                        cells[x][y].neighbors.add(cells[x-1][y-1]);
                    }
                    // Middle
                    else {
                        cells[x][y].neighbors.add(cells[x-1][y+1]);
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x-1][y-1]);
                        cells[x][y].neighbors.add(cells[x+1][y+1]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y-1]);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].neighbors.add(cells[x][y-1]);
                    }
                }

            }
        }

        cells[0][0].setAlive();
        cells[5][5].setAlive();
        cells[4][5].setAlive();
        cells[6][5].setAlive();

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(user_interface, BorderLayout.SOUTH);
        frame.setSize(gameWidth, gameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try {
            Thread.sleep(2000);
            while (true) {
                Thread.sleep(50);
                if (!paused) {
                    System.out.println("Step");
                    for (int x = 0; x < gridSizeX; x++) {
                        for (int y = 0; y < gridSizeY; y++) {
                            cells[x][y].nextState();
                        }
                    }
                    for (int x = 0; x < gridSizeX; x++) {
                        for (int y = 0; y < gridSizeY; y++) {
                            cells[x][y].step();
                            //cells[x][y].repaint();
                        }
                    }
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
