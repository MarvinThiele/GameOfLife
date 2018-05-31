package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pauseButtonListener implements ActionListener {
    private Game game;
    private JButton pauseButton;

    public pauseButtonListener(Game gameReference, JButton pauseButton) {
        this.game = gameReference;
        this.pauseButton = pauseButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.paused = !game.paused;

        if (game.paused) {
            pauseButton.setText("Resume Simulation");
        }
        else {
            pauseButton.setText("Pause Simulation");
        }
    }
}
