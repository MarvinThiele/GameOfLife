package gameLogic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell extends JPanel {
    public Boolean alive;
    public ArrayList<Cell> neighbors = new ArrayList<Cell>();
    Boolean nextState;
    public Boolean wasAlive = false;

    public Cell(Boolean alive) {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.GREEN);
        this.alive = alive;
    }

    public void setAlive() {
        alive = true;
        wasAlive = true;
        this.setBackground(Color.GREEN);
    }

    public void setDead() {
        alive = false;
        if (!wasAlive) {
            this.setBackground(Color.BLACK);
        }
        else {
            this.setBackground(Color.DARK_GRAY);
        }

    }

    public void nextState() {
        // Calculate Neighbors
        int neighbors_alive = 0;
        for (Cell neighbor : neighbors) {
            if (neighbor.alive) {
                neighbors_alive++;
            }
        }

        // Check Game of Life Rules
        if (neighbors_alive < 2) {
            nextState = false;
        }
        else if (neighbors_alive > 3) {
            nextState = false;
        }
        else if (!alive && neighbors_alive == 3) {
            nextState = true;
        }
        else if (alive) {
            nextState = true;
        }
        else {
            nextState = false;
        }
    }

    public void step() {
        // Set the cell into the next state
        alive = nextState;

        if (alive) {
            this.setAlive();
        }
        else {
            this.setDead();
        }
    }

    public void fillNeighbors() {
        // Fill the neighbors array so that the cell has 9 neighbors
        // This makes iterating over the neighbors more convenient
        while (neighbors.size() != 8) {
            neighbors.add(new Cell(false));
        }
    }
}
