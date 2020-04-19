import java.awt.*;
import javax.swing.*;

public class TestBody extends JFrame {
    public String imgDic = "./image/";
    public TestBody(){
        this.setSize(100,100);
        this.getContentPane().setLayout(null);
        this.setTitle("测试动画");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //动画承载体
        JLabel jLabel = new JLabel();
        //初始化第一张图
        this.cgJLabelImg(jLabel, "kabi_default.png");
        this.add(jLabel);
        //框体透明
        this.setUndecorated(true); //取消窗口标题栏
        this.setBackground(new Color(0,0,0,0)); //背景透明
        this.setAlwaysOnTop(true); //永远保持在最顶端

        MyMouseAdapter adapter = new MyMouseAdapter();
        this.addMouseMotionListener(adapter);
        this.addMouseListener(adapter);

        this.setVisible(true);
    }

    private void cgJLabelImg(JLabel jLabel, String imgUrl){
        ImageIcon icon = new ImageIcon(imgDic + imgUrl);
        int picWidth = icon.getIconWidth();
        int picHeight = icon.getIconHeight();
        icon.setImage(icon.getImage().getScaledInstance(picWidth, picHeight, Image.SCALE_DEFAULT));
        jLabel.setBounds(0,0,picWidth,picHeight);
        jLabel.setIcon(icon);
    }

    public static void main(String[] args){
        new TestBody();
    }
}
