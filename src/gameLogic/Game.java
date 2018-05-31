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
    public JPanel gameField;
    public JFrame frame;

    public int epochCount = 0;

    public JCheckBox drawModeCheckbox;

    private Game() {

    }

    public static Game getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Game();
        }
        return uniqueInstance;
    }

    public void run() {


        frame = new JFrame("Conway's Game of Life");
        frame.setLayout(new BorderLayout());

        ImageIcon img = new ImageIcon("./resources/icon.png");
        frame.setIconImage(img.getImage());



        // User Interface
        JButton pauseButton = new JButton("Pause Simulation");
        pauseButton.addActionListener(new pauseButtonListener());

        drawModeCheckbox = new JCheckBox("Draw Mode");

        JLabel speedSliderLabel = new JLabel("Simulation Speed", JLabel.CENTER);
        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 0, 2000, 1500);
        speedSlider.addChangeListener(new sliderListener(this));

        JButton saveStateButton = new JButton("Save Board");
        saveStateButton.addActionListener(new saveStateButtonListener(this));

        JButton loadStateButton = new JButton("Load Board");
        loadStateButton.addActionListener(new loadStateButtonListener(this, pauseButton));

        JButton clearBoardButton = new JButton("Clear Board");
        clearBoardButton.addActionListener(new clearBoardButtonListener(this));

        JPanel user_interface = new JPanel();
        user_interface.add(pauseButton);
        user_interface.add(drawModeCheckbox);
        user_interface.add(speedSliderLabel);
        user_interface.add(speedSlider);
        user_interface.add(saveStateButton);
        user_interface.add(loadStateButton);
        user_interface.add(clearBoardButton);

        JPanel user_interface_container = new JPanel();
        user_interface_container.setLayout(new BorderLayout());
        user_interface_container.add(user_interface, BorderLayout.NORTH);

        // Statistics
        JPanel statistics_panel = new JPanel();
        JLabel epochCountLabel = new JLabel();
        epochCountLabel.setText("Generation 0");
        statistics_panel.add(epochCountLabel);

        // Settings
        JPanel settings_panel = new JPanel();
        JLabel gameSizeLabel = new JLabel("Game Size:");
        JTextField gameSizeTextField = new JTextField();
        gameSizeTextField.setPreferredSize(new Dimension(50, 24));
        JButton setBoardSizeButton = new JButton("Set Board Size");
        setBoardSizeButton.addActionListener(new setDimensionsListener(this, gameSizeTextField, pauseButton));

        settings_panel.add(gameSizeLabel);
        settings_panel.add(gameSizeTextField);
        settings_panel.add(setBoardSizeButton);

        user_interface_container.add(settings_panel, BorderLayout.SOUTH);

        this.gameField = createGamePanel(gridSizeX, gridSizeY);

        frame.add(statistics_panel, BorderLayout.NORTH);
        frame.add(gameField, BorderLayout.CENTER);
        frame.add(user_interface_container, BorderLayout.SOUTH);
        frame.setSize(gameWidth, gameHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //


        try {
            Thread.sleep(500);
            cells[49][50].setAlive();
            cells[49][49].setAlive();
            cells[49][48].setAlive();

            cells[51][50].setAlive();
            cells[51][49].setAlive();
            cells[51][48].setAlive();

            cells[50][50].setAlive();
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

    public void deleteGameField() {
        this.frame.remove(this.gameField);
    }

    public JPanel createGamePanel(int sizeX, int sizeY) {
        gridSizeX = sizeX;
        gridSizeY = sizeY;
        this.cells = new Cell[sizeX][sizeY];
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(sizeX,sizeY-1,0,0));

        // Generate Cells and Panels
        for (int i = 0; i < sizeX*sizeY; i++) {
            Cell currentCell = new Cell(false);
            currentCell.addMouseListener(new cellMouseListener(this));
            gridPanel.add(currentCell);

            int x_pos = i % sizeY;
            int y_pos = (int) i / sizeY;

            cells[x_pos][y_pos] = currentCell;
        }

        // Give Neighbors
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                //Left Border
                if (x == 0) {
                    // Top Left
                    if (y == 0) {
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].neighbors.add(cells[x+1][y]);
                        cells[x][y].neighbors.add(cells[x+1][y+1]);
                        cells[x][y].fillNeighbors();
                    }
                    // Bottom Left
                    else if (y == sizeY-1) {
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
                else if (x == sizeX-1) {
                    // Top Right
                    if (y == 0) {
                        cells[x][y].neighbors.add(cells[x-1][y]);
                        cells[x][y].neighbors.add(cells[x-1][y+1]);
                        cells[x][y].neighbors.add(cells[x][y+1]);
                        cells[x][y].fillNeighbors();
                    }
                    // Bottom Right
                    else if (y == sizeY-1) {
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
                    else if (y == sizeY-1) {
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
        return gridPanel;
    }
}
