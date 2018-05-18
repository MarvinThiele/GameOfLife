import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class pauseButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        Game g = Game.getInstance();
        g.paused = !g.paused;
    }
}
