package actionListeners;

import gameLogic.Cell;
import gameLogic.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class cellMouseListener implements MouseListener {

    Game game;

    public cellMouseListener(Game gameReference) {
        game = gameReference;
    }

    public void mouseClicked(MouseEvent event) {

    }
    public void mouseEntered(MouseEvent event) {
        if (game.drawMode) {
            Cell selectedCell = (Cell) event.getSource();
            reverseCellState(selectedCell);
        }
    }
    public void mouseExited(MouseEvent event) {

    }
    public void mousePressed(MouseEvent event) {
        Cell selectedCell = (Cell) event.getSource();
        reverseCellState(selectedCell);
    }
    public void mouseReleased(MouseEvent event) {

    }

    private void reverseCellState(Cell cell) {
        if (cell.alive) {
            cell.setDead();
        }
        else {
            cell.setAlive();
        }
    }
} // inner class clickListener