import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class myMouseListener implements MouseListener {
    public void mouseClicked(MouseEvent event) {

    }
    public void mouseEntered(MouseEvent event) {
        Cell a = (Cell) event.getSource();
        a.setAlive();
    }
    public void mouseExited(MouseEvent event) {
        //System.out.println("exited");
    }
    public void mousePressed(MouseEvent event) {
        // Somehow this is registers clicks a lot more accurately than mouseClicked()
        Cell a = (Cell) event.getSource();
        a.setAlive();
    }
    public void mouseReleased(MouseEvent event) {
        //System.out.println("released");
    }
} // inner class clickListener