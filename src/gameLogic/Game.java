package gameLogic;

import actionListeners.*;

import javax.swing.*;
import java.awt.*;

public class Game {
    private static Game uniqueInstance;
    static int gameWidth = 1000;
    static int gameHeight = 1000;

    static public int gridSizeX = 40;
    static public int gridSizeY = 40;

    public Boolean paused = false;
    public Boolean drawMode = false;

    public int simulationSpeed = 200;
    public Cell[][] cells;

    private JCheckBox drawModeCheckbox;

    private Game() {

    }

    public static Game getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    public void run() {
        cells = new Cell[gridSizeX][gridSizeY];

        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        JPanel user_interface = new JPanel();

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new pauseButtonListener());

        drawModeCheckbox = new JCheckBox("Draw Mode");

        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 1000, 200);
        JLabel sliderLabel = new JLabel("Simulation Speed", JLabel.CENTER);
        speedSlider.addChangeListener(new sliderListener(this));

        JButton saveStateButton = new JButton("Save State");
        saveStateButton.addActionListener(new saveStateButtonListener(this));

        JButton loadStateButton = new JButton("Load State");
        loadStateButton.addActionListener(new loadStateButtonListener(this));

        user_interface.add(pauseButton);
        user_interface.add(drawModeCheckbox);
        user_interface.add(sliderLabel);
        user_interface.add(speedSlider);
        user_interface.add(saveStateButton);
        user_interface.add(loadStateButton);

        gridPanel.setLayout(new GridLayout(gridSizeX,gridSizeY-1,1,1));


        // Generate Cells and Panels
        for (int i = 0; i < gridSizeX*gridSizeY; i++) {
            Cell currentCell = new Cell(false);
            currentCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            currentCell.setBackground(Color.LIGHT_GRAY);
            currentCell.addMouseListener(new cellMouseListener(this));
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
            Thread.sleep(1000);
            while (true) {
                Thread.sleep(simulationSpeed);
                checkCheckboxes();
                if (!paused) {
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

    private void checkCheckboxes() {
        if (drawModeCheckbox.isSelected()) {
            drawMode = true;
        }
        else {
            drawMode = false;
        }
    }
}
