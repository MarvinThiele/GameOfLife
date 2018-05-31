package actionListeners;

import gameLogic.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class clearBoardButtonListener implements ActionListener {
    private Game game;

    public clearBoardButtonListener(Game gameReference) {
        this.game = gameReference;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.epochCount = 0;
        for (int i = 0; i < game.gridSizeY; i++) {
            for (int j = 0; j < game.gridSizeX; j++) {
                game.cells[j][i].wasAlive = false;
                game.cells[j][i].setDead();
            }
        }
    }
}

