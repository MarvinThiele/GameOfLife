import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell extends JPanel {
    Boolean alive;
    ArrayList<Cell> neighbors = new ArrayList<Cell>();
    Boolean nextState;

    public Cell(Boolean Alive) {
        alive = Alive;
    }

    public void setAlive() {
        alive = true;
        this.setBackground(Color.GREEN);
    }

    public void nextState() {
        // Calculate Neighbors
        int neighbors_alive = 0;
        for (Cell neighbor : neighbors) {
            if (neighbor.alive) {
                neighbors_alive++;
            }
        }

        // Check Rules
        if (neighbors_alive < 2) {
            nextState = false;
        }
        else if (neighbors_alive > 3) {
            nextState = false;
        }
        else if (!alive && neighbors_alive == 3){
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
        alive = nextState;

        if (alive) {
            this.setBackground(Color.GREEN);
        }
        else {
            this.setBackground(Color.LIGHT_GRAY);
        }
        //this.revalidate();
    }

    public void fillNeighbors() {
        while (neighbors.size() != 8) {
            neighbors.add(new Cell(false));
        }
    }
}
