package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class sliderListener implements ChangeListener {
    Game game;

    public sliderListener(Game gameReference) {
        game = gameReference;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            game.simulationSpeed = 2001 - source.getValue();
        }
    }
}
