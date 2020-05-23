import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvolutionFrame extends JFrame {
    private static EvolutionFrame evPet = null;
    private static ImageIcon beforeEv;
    private static ImageIcon afterEv;

    public static EvolutionFrame getInstance() {
        //单例模式
        if ( evPet == null) {
            evPet = new EvolutionFrame();
        }
        return evPet;
    }

    private EvolutionFrame() {
        //主构造方法，设定窗口基本参数
        this.setSize(350, 200);
        this.setTitle("宠物进化");
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        placeComponents(panel);
        this.add(panel);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel loveLabel = new JLabel("亲密度：" + TestBody.mypet.getLove_now() + "/" + TestBody.mypet.getLove_max());
        loveLabel.setBounds(10, 10, 100, 25);
        panel.add(loveLabel);

        JLabel bLabel = new JLabel();
        bLabel.setBounds(20, 10, 120, 120);

        beforeEv = new ImageIcon(TestBody.imgDic + "kabi" + TestBody.mypet.getLevel_now() + "_default.png");
        bLabel.setIcon(beforeEv);
        panel.add(bLabel);

        JLabel aLabel = new JLabel();
        aLabel.setBounds(180, 10, 120, 120);
        afterEv = new ImageIcon(TestBody.imgDic + "kabi"+ (TestBody.mypet.getLevel_now()+1) +"_default.png");
        aLabel.setIcon(afterEv);
        panel.add(aLabel);

        JLabel BLabel = new JLabel("进化前");
        BLabel.setBounds(75, 90, 100, 25);
        panel.add(BLabel);

        JLabel ALabel = new JLabel("进化后");
        ALabel.setBounds(235, 90, 100, 25);
        panel.add(ALabel);

        //进化按钮操作
        JButton evButton = new JButton("点击进化");
        evButton.setBounds(125, 120, 100, 25);
        panel.add(evButton);
        evButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changingImgThread ct = new changingImgThread(2);
                if(TestBody.mypet.getLove_now() == TestBody.mypet.getLove_max()){
                    if(TestBody.mypet.getLevel_now() < 2){
                        ct.start();
                        TestBody.mypet.setLove_now(0);
                        TestBody.mypet.setLevel_now(TestBody.mypet.getLevel_now()+1);
                        loveLabel.setText("亲密度：" + TestBody.mypet.getLove_now() + "/" + TestBody.mypet.getLove_max());
                        beforeEv = new ImageIcon(TestBody.imgDic + "kabi" + TestBody.mypet.getLevel_now() + "_default.png");
                        bLabel.setIcon(beforeEv);
                        afterEv = new ImageIcon(TestBody.imgDic + "kabi"+ (TestBody.mypet.getLevel_now()+1) +"_default.png");
                        aLabel.setIcon(afterEv);
                        JOptionPane.showMessageDialog(null,"你的宠物进化啦^-^");
                    }
                    else if(TestBody.mypet.getLevel_now() == TestBody.mypet.getLevel_max()){
                        JOptionPane.showMessageDialog(null,"已经满级啦！！！！！");
                    }
                }
                else if(TestBody.mypet.getLove_now() < TestBody.mypet.getLove_max()){
                    JOptionPane.showMessageDialog(null,"亲密度还不够哦QAQ");
                }
            }
        });
    }
}
