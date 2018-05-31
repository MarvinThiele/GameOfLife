package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class setBoardSizeListener implements ActionListener {
    private Game game;
    private JTextField gameSizeTextField;
    private JButton pauseButton;

    public setBoardSizeListener(Game gameReference, JTextField gameSizeTextField, JButton pauseButton) {
        this.game = gameReference;
        this.gameSizeTextField = gameSizeTextField;
        this.pauseButton = pauseButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int size = Integer.parseInt(gameSizeTextField.getText());
            if (size > 1) {
                game.paused = true;
                game.deleteGameField();
                game.gameField = game.createGamePanel(size, size);
                game.frame.add(game.gameField);
                game.paused = false;
                pauseButton.setText("Pause Simulation");
            }
            else {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(game.frame, "Please specify a numerical value larger than 1!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
