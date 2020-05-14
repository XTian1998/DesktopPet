import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FeedPetFrame extends JFrame {
    /*
     * 宠物喂食类，用于实现喂食功能
     * */
    private static HashMap<String, ArrayList<Integer>> foods = new HashMap<String, ArrayList<Integer>>();
    private static FeedPetFrame feedPet = null;

    public static FeedPetFrame getInstance() {
        //单例模式
        if (feedPet == null) {
            feedPet = new FeedPetFrame();
        }
        return feedPet;
    }

    private FeedPetFrame() {
        //主构造方法，设定窗口基本参数
        this.setSize(350, 200);
        this.setTitle("宠物喂食");
        this.setResizable(false);

        JPanel panel = new JPanel();
        placeComponents(panel);
        this.add(panel);

        this.setVisible(true);
        if(foods.size() == 0) {
            initFoods();
        }
        System.out.println(foods);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel hungryLabel = new JLabel("饱食度：" + TestBody.mypet.getHungry_now() + "/" + TestBody.mypet.getHungry_max());
        hungryLabel.setBounds(20, 20, 100, 25);
        panel.add(hungryLabel);

        //选择食物
        JLabel foodLabel = new JLabel("选择食物: ");
        foodLabel.setBounds(20, 50, 80, 25);
        panel.add(foodLabel);

        JComboBox foodItem = new JComboBox();    //创建选择条，用于选择食物
        foodItem.addItem("———————");
        foodItem.addItem("布丁");
        foodItem.addItem("巧克力蛋糕");
        foodItem.setBounds(100, 50, 120, 25);
        panel.add(foodItem);

        //剩余食物数显示
        JLabel restFoodLabel = new JLabel();
        restFoodLabel.setBounds(230, 50, 80, 25);
        panel.add(restFoodLabel);
        restFoodLabel.setText("");
        restFoodLabel.setVisible(true);
        foodItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedFood = (String) foodItem.getSelectedItem();
                    if (selectedFood.equals("———————")) {
                        restFoodLabel.setText("");
                    } else {
                        String restFood = "剩余数量：" + foods.get(selectedFood).get(0);
                        restFoodLabel.setText(restFood);
                    }
                }
            }
        });

        JLabel afterFeedLabel = new JLabel("");
        afterFeedLabel.setBounds(20, 80, 120, 25);
        panel.add(afterFeedLabel);
        afterFeedLabel.setVisible(false);

        //喂食按钮操作
        JButton feedButton = new JButton("点击喂食");
        feedButton.setBounds(125, 120, 100, 25);
        panel.add(feedButton);
        feedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rest = foods.get((String) foodItem.getSelectedItem()).get(0);
                String selectedFood = (String) foodItem.getSelectedItem();
                if (!selectedFood.equals("———————") && foods.get(selectedFood).get(0) > 0) {
                    foods.get(selectedFood).set(0, foods.get(selectedFood).get(0) - 1);
                    restFoodLabel.setText("剩余数量：" + foods.get(selectedFood).get(0));
                    changingImgThread ct = new changingImgThread(2);
                    ct.start();
                    int addHungry = foods.get(selectedFood).get(1);
                    afterFeedLabel.setText("饱食度增加 " + addHungry);
                    afterFeedLabel.setVisible(true);
                    if(addHungry+TestBody.mypet.getHungry_now() <= TestBody.mypet.getHungry_max()){
                        TestBody.mypet.setHungry_now(addHungry+TestBody.mypet.getHungry_now());
                        System.out.println(TestBody.mypet.getHungry_now());
                    }
                    hungryLabel.setText("饱食度：" + TestBody.mypet.getHungry_now() + "/" + TestBody.mypet.getHungry_max());
                }
            }
        });
    }

    private void initFoods() {
        //初始化食物信息
        ArrayList<Integer> pudding = new ArrayList<Integer>();
        ArrayList<Integer> chocolateCake = new ArrayList<Integer>();

        pudding.add(3); //剩余数量
        pudding.add(5); //增加饱食度
        chocolateCake.add(1);
        chocolateCake.add(20);

        foods.put("布丁", pudding);
        foods.put("巧克力蛋糕", chocolateCake);
    }

    public static int[] getFoodNum(){
        int[] num = new int[2];
        int i=0;
        Set<String> keySet = foods.keySet();
        for(String key : keySet){
            ArrayList<Integer> arr = foods.get(key);
            num[i] = arr.get(0);
            i++;
        }
        return num;
    }

    public static void setFoodNum(int[] num){
        ArrayList<Integer> pudding = new ArrayList<Integer>();
        ArrayList<Integer> chocolateCake = new ArrayList<Integer>();

        pudding.add(num[0]); //剩余数量
        pudding.add(5); //增加饱食度
        chocolateCake.add(num[1]);
        chocolateCake.add(20);

        foods.put("布丁", pudding);
        foods.put("巧克力蛋糕", chocolateCake);
    }
}
