import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ChatFrame extends JFrame {
    /*
     * 宠物对话类
     * */
    private static ChatFrame chatFrame = null;

    private JTextArea msgArea;
    private JTextArea logArea;
    private JButton sendMsgBtn;
    private JButton clearLogBtn;
    private JPanel logPanel;
    private JPanel sendPanel;
    private static String msg;

    public static ChatFrame getInstance() {
        //单例模式
        if (chatFrame == null) {
            chatFrame = new ChatFrame();
        }
        return chatFrame;
    }

    private ChatFrame() {
        //宠物对话主窗体
        this.setSize(500, 480);
        this.setTitle("宠物对话");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        //显示历史对话信息的Panel设计
        logPanel = new JPanel();
        logPanel.setBorder(BorderFactory.createTitledBorder("历史对话记录"));
        logArea = new JTextArea(20, 30);
        logArea.setBackground(Color.WHITE);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        JScrollPane scrPane = new JScrollPane(logArea);
        logPanel.add(scrPane);

        //发送信息的Panel设计
        sendPanel = new JPanel();
        sendPanel.setBorder(BorderFactory.createTitledBorder("在这里输入"));
        msgArea = new JTextArea(1, 20);
        msgArea.setBackground(Color.WHITE);
        msgArea.setEditable(true);
        msgArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        sendMsgBtn = new JButton("发送");
        clearLogBtn = new JButton("清屏");
        sendPanel.add(msgArea);
        sendPanel.add(sendMsgBtn);
        sendPanel.add(clearLogBtn);
        sendMsgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                msg = msgArea.getText();
                logArea.append("我说：" + msg + "\n");
                Gson gson = new Gson();
                PetAnswer petAnswer = gson.fromJson(HttpGetter.getInstance().sendRequest(msg), PetAnswer.class);
                logArea.append(petAnswer.getAnswer());
                msgArea.setText("");
            }
        });
        clearLogBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logArea.setText("");
            }
        });
        msgArea.addKeyListener(new KeyListener() {
            //对信息输入框进行回车监听，实现回车发送信息
            @Override
            public void keyTyped(KeyEvent e) {
                if ((char) e.getKeyChar() == KeyEvent.VK_ENTER) {
                    msgArea.setText(msgArea.getText().replace("\n", ""));
                    sendMsgBtn.doClick();
                }
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
        this.add(logPanel, BorderLayout.NORTH);
        this.add(sendPanel, BorderLayout.CENTER);
        logArea.setText(TestBody.mypet.getName() + "对你说：我们开始聊天吧！\n");
    }
}

class HttpGetter {
    //用于调用智能对话API的类
    private static String API = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=";
    private static HttpGetter httpGetter = null;

    public static HttpGetter getInstance() {
        if (httpGetter == null) {
            httpGetter = new HttpGetter();
        }
        return httpGetter;
    }

    private HttpGetter() {
    }

    public String sendRequest(String msg) {
        //从API获取返回内容，是一个json格式的String
        if (msg.length() > 0) {
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            try {
                String urlBefore = API + msg;
                URL url = new URL(urlBefore.replace(" ", "+"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String str;
                while ((str = reader.readLine()) != null) {
                    response.append(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                return response.toString();
            }
        }
        return null;
    }
}

class PetAnswer {
    //解析json，获取返回内容的类
    private int result;
    private String content;
    private String answer;

    public PetAnswer() {
    }

    public String getAnswer() {
        if (result == 0) {
            //将所有回答中的换行符和人物名称替换，符合语境
            content = content.replace("{br}", "\n");
            content = content.replace("菲菲", TestBody.mypet.getName());
            answer = TestBody.mypet.getName() + "对你说：" + content + "\n";
        } else {
            answer = TestBody.mypet.getName() + "对你说：" + "你的网好像有点问题哦，我听不到你说话！\n";
        }
        return answer;
    }
}
