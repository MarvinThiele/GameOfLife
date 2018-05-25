package gameLogic;

import actionListeners.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Game {
    private static Game uniqueInstance;
    static int gameWidth = 1000;
    static int gameHeight = 1000;

    static public int gridSizeX = 100;
    static public int gridSizeY = 100;

    public Boolean paused = false;
    public Boolean drawMode = false;

    public int simulationSpeed = 200;
    public Cell[][] cells;

    public int epochCount = 0;

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

        ImageIcon img = new ImageIcon("./resources/icon.png");
        frame.setIconImage(img.getImage());

        JPanel gridPanel = new JPanel();
        JPanel user_interface = new JPanel();

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new pauseButtonListener());

        drawModeCheckbox = new JCheckBox("Draw Mode");

        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 200);
        JLabel sliderLabel = new JLabel("Simulation Speed", JLabel.CENTER);
        speedSlider.addChangeListener(new sliderListener(this));

        JButton saveStateButton = new JButton("Save State");
        saveStateButton.addActionListener(new saveStateButtonListener(this));

        JButton loadStateButton = new JButton("Load State");
        loadStateButton.addActionListener(new loadStateButtonListener(this));

        JButton clearBoardButton = new JButton("Clear Board");
        clearBoardButton.addActionListener(new clearBoardButtonListener(this));

        JPanel user_interface_container = new JPanel();
        user_interface_container.setLayout(new BorderLayout());

        user_interface.add(pauseButton);
        user_interface.add(drawModeCheckbox);
        user_interface.add(sliderLabel);
        user_interface.add(speedSlider);
        user_interface.add(saveStateButton);
        user_interface.add(loadStateButton);
        user_interface.add(clearBoardButton);
        user_interface_container.add(user_interface, BorderLayout.NORTH);

        JPanel statistics_panel = new JPanel();
        JLabel epochCountLabel = new JLabel();
        epochCountLabel.setText("Generation : 0");

        statistics_panel.add(epochCountLabel);
        frame.add(statistics_panel, BorderLayout.NORTH);

        JPanel settings_panel = new JPanel();
        JLabel heightLabel = new JLabel("Height:");
        JTextField heightTextField = new JTextField();
        heightTextField.setPreferredSize(new Dimension(50, 24));
        JLabel widthLabel = new JLabel("Width:");
        JTextField widthTextField = new JTextField();
        widthTextField.setPreferredSize(new Dimension(50, 24));

        settings_panel.add(heightLabel);
        settings_panel.add(heightTextField);
        settings_panel.add(widthLabel);
        settings_panel.add(widthTextField);

        user_interface_container.add(settings_panel, BorderLayout.SOUTH);

        gridPanel.setLayout(new GridLayout(gridSizeX,gridSizeY-1,0,0));


        // Generate Cells and Panels
        for (int i = 0; i < gridSizeX*gridSizeY; i++) {
            Cell currentCell = new Cell(false);
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

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(user_interface_container, BorderLayout.SOUTH);
        frame.setSize(gameWidth, gameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            Thread.sleep(2000);
            while (true) {
                Thread.sleep(simulationSpeed);
                checkCheckboxes();
                if (!paused) {
                    epochCount++;
                    epochCountLabel.setText("Generation "+Integer.toString(epochCount)+":");
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
