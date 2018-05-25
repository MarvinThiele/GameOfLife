package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class saveStateButtonListener implements ActionListener{
    Game game;

    public saveStateButtonListener(Game gameReference) {
        game = gameReference;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser c = new JFileChooser();
        // Demonstrate "Save" dialog:
        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Chosen File");
            File file = c.getSelectedFile();
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.append(Integer.toString(game.gridSizeX));
                writer.newLine();
                writer.append(Integer.toString(game.gridSizeY));
                writer.newLine();

                for (int i = 0; i < game.gridSizeY; i++) {
                    for (int j = 0; j < game.gridSizeX; j++) {
                        if (game.cells[j][i].alive) {
                            writer.write("1 ");
                        }
                        else {
                            writer.write("0 ");
                        }
                    }
                    writer.newLine();
                }
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }
        if (rVal == JFileChooser.CANCEL_OPTION) {
            System.out.println("Canceld File Opening");
        }
    }
}