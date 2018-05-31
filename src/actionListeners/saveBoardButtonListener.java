package actionListeners;

import gameLogic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class saveBoardButtonListener implements ActionListener{
    private Game game;

    public saveBoardButtonListener(Game gameReference) {
        this.game = gameReference;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser c = new JFileChooser();

        int rVal = c.showSaveDialog(null);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("Chosen File");
            File file = c.getSelectedFile();
            BufferedWriter writer;
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
    }
}
