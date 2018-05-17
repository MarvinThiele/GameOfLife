import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class myMouseListener implements MouseListener {
    public void mouseClicked(MouseEvent event) {
        System.out.println("entered");
    }
    public void mouseEntered(MouseEvent event) {
        //System.out.println("entered");
    }
    public void mouseExited(MouseEvent event) {
        //System.out.println("exited");
    }
    public void mousePressed(MouseEvent event) {
        //System.out.println("pressed");
    }
    public void mouseReleased(MouseEvent event) {
        //System.out.println("released");
    }
} // inner class clickListener