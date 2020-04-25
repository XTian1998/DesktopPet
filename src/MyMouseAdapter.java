import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter {
    private int offsetX, offsetY;
    private changingImgThread ct;
    public void mouseDragged(MouseEvent e){
        SwingUtilities.getRoot((Component)e.getSource()).setLocation(e.getXOnScreen()-offsetX, e.getYOnScreen()-offsetY);
    }
    public void mouseReleased(MouseEvent e){
        ct.interrupt();
        TestBody.cgJLabelImg(TestBody.imgLabel, "kabi_default.png");
    }
    public void mousePressed(MouseEvent e){
        offsetX = e.getX();
        offsetY = e.getY();
        ct = new changingImgThread(1);
        ct.start();
    }
    public void mouseEntered(MouseEvent e){
        TestBody.loveLabel.setVisible(true);
        TestBody.hungryLabel.setVisible(true);
    }
    public void mouseExited(MouseEvent e){
        TestBody.loveLabel.setVisible(false);
        TestBody.hungryLabel.setVisible(false);
    }
}
