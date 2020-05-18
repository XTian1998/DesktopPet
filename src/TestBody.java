import pets.Pet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
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
        Load();

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
        InfoUpdateThread iut = new InfoUpdateThread(this);
        iut.start();

        //框体透明
        this.setUndecorated(true); //取消窗口标题栏
        this.setBackground(new Color(0,0,0,0)); //背景透明
        this.setAlwaysOnTop(true); //永远保持在最顶端

        BodyMouseAdapter adapter = new BodyMouseAdapter();
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
            itemExit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Save();
                    System.exit(0);
                }
            });

            MenuItem itemFeed = new MenuItem("喂食");
            itemFeed.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    FeedPetFrame feedFrame = FeedPetFrame.getInstance();
                    feedFrame.setVisible(true);
                }
            });

            MenuItem itemPiano = new MenuItem("弹琴");
            itemPiano.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    PianoFrame pianoFrame = PianoFrame.getInstance();
                    pianoFrame.setVisible(true);
                }
            });

            MenuItem itemChat = new MenuItem("对话");
            itemChat.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ChatFrame chatFrame = ChatFrame.getInstance();
                    chatFrame.setVisible(true);
                }
            });

            /*
            * 其他菜单栏功能在此处添加
            * */

            popMenu.add(itemChat);
            popMenu.add(itemPiano);
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

    private void Load(){
        //读档
        try{
            File saveDic = new File("save");
            File saveFile = new File("save/MyPet.sav");
            if(!saveDic.exists()){
                saveDic.mkdir();
            }
            if(!saveFile.exists()){
                saveFile.createNewFile();
                mypet = new Pet("kabi", 1, 50, 50);
            }else {
                BufferedReader br = new BufferedReader(new FileReader(saveFile));
                char[] inf = new char[1024];
                int len = br.read(inf);
                String data = new String(inf, 0, len);
                br.close();
                String[] SplitData = data.split("\n");
                mypet = new Pet(SplitData[0], Integer.parseInt(SplitData[1]), Integer.parseInt(SplitData[2]), Integer.parseInt(SplitData[3]));

                //设定食物数量
                int[] arr = Arrays.asList(Arrays.copyOfRange(SplitData, 4, 6)).stream().mapToInt(Integer::parseInt).toArray();
                FeedPetFrame.setFoodNum(arr);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void Save() {
        //存档
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("save/MyPet.sav"));
            String data = mypet.getName() + "\n" + mypet.getLevel() + "\n" + mypet.getLove_now() + "\n" + mypet.getHungry_now() + "\n";
            for(int i : FeedPetFrame.getFoodNum()){
                data += i + "\n";
            }
            bw.write(data);
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
            new TestBody();
    }

}
