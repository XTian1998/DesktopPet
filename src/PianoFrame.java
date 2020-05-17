import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PianoFrame extends JFrame {
    /*
     * 弹琴游戏类
     * */
    private static PianoFrame pianoFrame = null;

    public static PianoFrame getInstance() {
        // 单例模式
        if (pianoFrame == null) {
            pianoFrame = new PianoFrame();
        }
        return pianoFrame;
    }

    private PianoFrame() {
        //弹琴主窗体
        this.setSize(610, 230);
        this.setTitle("弹琴游戏");
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JLabel pianoLabel = new JLabel();
        ImageIcon pianoImg = new ImageIcon(TestBody.imgDic + "piano.png");
        pianoLabel.setIcon(pianoImg);
        this.addMouseListener(new PianoMouseAdapter());
        this.addKeyListener(new PianoKeyAdapter());
        this.add(pianoLabel);
    }
}

class PianoMouseAdapter extends MouseAdapter {
    //弹琴鼠标按键适配器
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //获取鼠标位置，判断在哪个琴键
        if (y >= 90 && y <= 205) {
            if (x >= 28 && x <= 96) {  //Do
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_do.wav");
                ppt.start();
            } else if (x >= 98 && x <= 166) {  //Re
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_re.wav");
                ppt.start();
            } else if (x >= 168 && x <= 236) {  //Mi
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_mi.wav");
                ppt.start();
            } else if (x >= 238 && x <= 306) {  //Fa
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_fa.wav");
                ppt.start();
            } else if (x >= 308 && x <= 376) {  //Sol
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_sol.wav");
                ppt.start();
            } else if (x >= 378 && x <= 446) {  //La
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_la.wav");
                ppt.start();
            } else if (x >= 448 && x <= 516) {  //Xi
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_xi.wav");
                ppt.start();
            } else if (x >= 518 && x <= 586) {  //Do#
                PianoPlayThread ppt = new PianoPlayThread("./piano/sound_do2.wav");
                ppt.start();
            }
        }
        System.out.println(x + ", " + y);
    }
}

class PianoKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        //获取按键，判断哪个琴键
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_do.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_S){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_re.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_D){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_mi.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_F){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_fa.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_G){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_sol.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_H){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_la.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_J){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_xi.wav");
            ppt.start();
        } else if(key == KeyEvent.VK_K){
            PianoPlayThread ppt = new PianoPlayThread("./piano/sound_do2.wav");
            ppt.start();
        }
    }
}
