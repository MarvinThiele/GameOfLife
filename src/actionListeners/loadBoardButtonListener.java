package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class loadBoardButtonListener implements ActionListener {
    Game game;
    JButton pauseButton;

    public loadBoardButtonListener(Game gameReference, JButton pauseButton) {
        this.game = gameReference;
        this.pauseButton = pauseButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        game.epochCount = 0;
        JFileChooser c = new JFileChooser();
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(game.frame);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            File file = c.getSelectedFile();
            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader(file));
                int gridSizeX = Integer.parseInt(br.readLine());
                int gridSizeY = Integer.parseInt(br.readLine());

                game.paused = true;
                game.deleteGameField();
                game.gameField = game.createGamePanel(gridSizeX, gridSizeY);
                game.frame.add(game.gameField);

                for (int i = 0; i < game.gridSizeY; i++) {
                    String states[] = br.readLine().split(" ");
                    for (int j = 0; j < game.gridSizeX; j++) {
                        if (states[j].equals("0")) {
                            game.cells[j][i].wasAlive = false;
                            game.cells[j][i].setDead();
                        }
                        else {
                            game.cells[j][i].setAlive();
                        }
                    }
                }
                br.close();
                game.drawModeCheckbox.setSelected(false);
                pauseButton.setText("Resume Simulation");


            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            System.out.println("Canceld File Loading");
        }
    }
}
