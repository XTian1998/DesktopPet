import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter {
    private int offsetX, offsetY;
    public void mouseDragged(MouseEvent e){
        SwingUtilities.getRoot((Component)e.getSource()).setLocation(e.getXOnScreen()-offsetX, e.getYOnScreen()-offsetY);
    }
    public void mousePressed(MouseEvent e){
        offsetX = e.getX();
        offsetY = e.getY();
    }
}
