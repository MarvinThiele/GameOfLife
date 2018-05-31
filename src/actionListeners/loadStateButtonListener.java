package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class loadStateButtonListener implements ActionListener {
    Game game;

    public loadStateButtonListener(Game gameReference) {
        game = gameReference;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        game.epochCount = 0;
        JFileChooser c = new JFileChooser();
        // Demonstrate "Open" dialog:
        int rVal = c.showOpenDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            File file = c.getSelectedFile();
            BufferedReader br;

            try {
                br = new BufferedReader(new FileReader(file));
                int gridSizeX = Integer.parseInt(br.readLine());
                int gridSizeY = Integer.parseInt(br.readLine());

                if (gridSizeX == game.gridSizeX && gridSizeY == game.gridSizeY) {
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
                }
                else {
                    System.out.println("ERROR: Game Dimensions not fitting to the state file!");
                }
                br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            System.out.println("Canceld File Loading");
        }
    }
}
