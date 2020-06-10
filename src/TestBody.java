import pets.Pet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        this.cgJLabelImg(imgLabel, mypet.getName() + mypet.getLevel_now() +imgUrl);
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

        //显示信息更新线程
        InfoUpdateThread iut = new InfoUpdateThread(this);
        iut.start();

        //亲密度与饱食度自动更新线程
        HL_UpdatingThread hungryThread = new HL_UpdatingThread(300000, 1); //饱食度每5分钟更新一次
        HL_UpdatingThread loveThread = new HL_UpdatingThread(600000, 2); //亲密度每10分钟更新一次
        hungryThread.start();
        loveThread.start();

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


            ImageIcon icon = new ImageIcon(imgDic + trayIconName);
            Image img = icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT);

            TrayIcon trayIcon = new TrayIcon(img, "桌面萌王");
            trayIcon.setImageAutoSize(true);

            JPopupMenu popMenu = new JPopupMenu();

            try {
                SystemTray.getSystemTray().add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(e.isPopupTrigger()){
                        final JPopupMenu pop = new JPopupMenu();
                        JMenuItem itemExit = new JMenuItem("退出");
                        itemExit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Save();
                                System.exit(0);
                            }
                        });

                        JMenuItem itemFeed = new JMenuItem("喂食");
                        itemFeed.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                FeedPetFrame feedFrame = FeedPetFrame.getInstance();
                                feedFrame.setVisible(true);
                            }
                        });

                        JMenuItem itemPiano = new JMenuItem("弹琴");
                        itemPiano.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                PianoFrame pianoFrame = PianoFrame.getInstance();
                                pianoFrame.setVisible(true);
                            }
                        });

                        JMenuItem itemChat = new JMenuItem("对话");
                        itemChat.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                ChatFrame chatFrame = ChatFrame.getInstance();
                                chatFrame.setVisible(true);
                            }
                        });

                        JMenuItem itemEv = new JMenuItem("进化");
                        itemEv.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                EvolutionFrame evFrame = EvolutionFrame.getInstance();
                                evFrame.setVisible(true);
                            }
                        });

                        /*
                         * 其他菜单栏功能在此处添加
                         * */

                        pop.add(itemFeed);
                        pop.add(itemPiano);
                        pop.add(itemChat);
                        pop.add(itemEv);
                        pop.add(itemExit);

                        pop.setLocation(e.getX(),e.getY());
                        pop.setInvoker(pop);
                        pop.setVisible(true);
                    }
                }
            });
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
            String data = mypet.getName() + "\n" + mypet.getLevel_now() + "\n" + mypet.getLove_now() + "\n" + mypet.getHungry_now() + "\n";
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
