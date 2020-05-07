import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class FeedPet extends JFrame{
    private static FeedPet f = null;
    public static FeedPet getInstance(){
        if(f==null){ f = new FeedPet(); }
        return f;

    }
    public FeedPet() {
        JFrame frame = new JFrame("喂食");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        // 创建 JLabel
        JLabel hungryLabel = new JLabel("饱食度: " + TestBody.mypet.getHungry_now()+ "/" +TestBody.mypet.getHungry_max());    //饱食度
        hungryLabel.setBounds(20,20,80,25);
        panel.add(hungryLabel);

        JLabel foodLabel = new JLabel("请选择食物: ");    //食物
        foodLabel.setBounds(20,50,80,25);
        panel.add(foodLabel);
        JComboBox foodItem=new JComboBox();    //创建JComboBox
        foodItem.addItem("———————");
        foodItem.addItem("布丁");
        foodItem.addItem("巧克力蛋糕");
        foodItem.addItem("珍珠奶茶");
        foodItem.addItem("摩卡星冰乐");
        foodItem.setBounds(100,50,120,25);
        panel.add(foodItem);

        JLabel restFoodLabel = new JLabel();
        restFoodLabel.setBounds(230,50,80,25);
        panel.add(restFoodLabel);
        restFoodLabel.setText("");
        foodItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    restFoodLabel.setText("剩余数量: ");
                    String a = restFoodLabel.getText();
                    System.out.print(a);
                }
            }
        });

        JLabel afterFeedLabel = new JLabel("饱食度增加xx点 ");
        afterFeedLabel.setBounds(20,80,120,25);
        panel.add(afterFeedLabel);
        afterFeedLabel.setVisible(false);

        JButton feedButton = new JButton("点击喂食");  //喂食按钮
        feedButton.setBounds(125, 120, 100, 25);
        panel.add(feedButton);
        feedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afterFeedLabel.setVisible(true);
            }
        });
    }

}
