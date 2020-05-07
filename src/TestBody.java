import pets.Pet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TestBody extends JFrame {
    public static String imgDic = "./image/";
    public static String imgUrl = "_default.png";
    public static String trayIconName = "sysIcon.png";
    public static JLabel loveLabel;
    public static JLabel hungryLabel;
    public static JLabel imgLabel = new JLabel();
    public static Pet mypet;

    public TestBody(){
        this.setSize(160,135);
        this.getContentPane().setLayout(null);
        this.setTitle("电子宠物");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mypet = new Pet("kabi", 1, 0, 0);

        //初始化图
        this.cgJLabelImg(imgLabel, mypet.getName() + imgUrl);
        this.add(imgLabel);

        //添加亲密度信息
        loveLabel = new JLabel("亲密度: " + mypet.getLove_now()+"/"+mypet.getLove_max());
        loveLabel.setForeground(Color.BLACK);
        loveLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
        loveLabel.setBounds(20,100,120,20);
        loveLabel.setVisible(false);
        this.add(loveLabel);

        //添加饱食度信息
        hungryLabel = new JLabel("饱食度: " + mypet.getHungry_now()+"/"+mypet.getHungry_max());
        hungryLabel.setForeground(Color.BLUE);
        hungryLabel.setFont(new Font("微软雅黑", Font.BOLD, 12));
        hungryLabel.setBounds(20,115,120,20);
        hungryLabel.setVisible(false);
        this.add(hungryLabel);

        //信息更新线程
        new Thread(() -> {
            try{
                Thread.sleep(2000);
                loveLabel.setText("亲密度: " + mypet.getLove_now()+"/"+mypet.getLove_max());
                hungryLabel.setText("饱食度: " + mypet.getHungry_now()+"/"+mypet.getHungry_max());
            }catch(Exception e){
                e.printStackTrace();
            }
        }).start();

        //框体透明
        this.setUndecorated(true); //取消窗口标题栏
        this.setBackground(new Color(0,0,0,0)); //背景透明
        this.setAlwaysOnTop(true); //永远保持在最顶端

        MyMouseAdapter adapter = new MyMouseAdapter();
        this.addMouseMotionListener(adapter);
        this.addMouseListener(adapter);
        setTray();

        this.setVisible(true);
    }

    public static void cgJLabelImg(JLabel jLabel, String imgUrl){
        ImageIcon icon = new ImageIcon(imgDic + imgUrl);
        int picWidth = icon.getIconWidth();
        int picHeight = icon.getIconHeight();
        icon.setImage(icon.getImage().getScaledInstance(picWidth, picHeight, Image.SCALE_DEFAULT));
        jLabel.setBounds(0,0,picWidth,picHeight);
        jLabel.setIcon(icon);
    }

    private void setTray(){
        //任务栏托盘图标显示和功能添加
        if(SystemTray.isSupported()){
            SystemTray tray = SystemTray.getSystemTray();

            PopupMenu popMenu = new PopupMenu();

            MenuItem itemExit = new MenuItem("退出");
            itemExit.addActionListener(e -> System.exit(0));

            MenuItem itemFeed = new MenuItem("喂食");
            itemFeed.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FeedPet f = FeedPet.getInstance();
                    f.setVisible(true);
                }
            });

            /*
            * 其他菜单栏功能在此处添加
            * */

            popMenu.add(itemFeed);
            popMenu.add(itemExit);

            ImageIcon icon = new ImageIcon(imgDic + trayIconName);
            Image img = icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT);

            TrayIcon trayIcon = new TrayIcon(img, "桌面萌王", popMenu);
            trayIcon.setImageAutoSize(true);

            try{
                tray.add(trayIcon);
            }catch(AWTException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        new TestBody();
    }
}
