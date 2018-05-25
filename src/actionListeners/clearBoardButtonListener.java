package actionListeners;

import gameLogic.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class clearBoardButtonListener implements ActionListener {
    Game game;

    public clearBoardButtonListener(Game gameReference) {
        game = gameReference;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < game.gridSizeY; i++) {
            for (int j = 0; j < game.gridSizeX; j++) {
                game.cells[j][i].setDead();
            }
        }
    }
}

