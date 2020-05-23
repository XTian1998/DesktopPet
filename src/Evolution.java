import javax.swing.*;

public class Evolution extends JFrame {
    public String LEVEL = "";
    public Evolution(){
        if(TestBody.mypet.getLove_now()>= 100){
            TestBody.mypet.setLove_now(0);
            if(TestBody.mypet.getLevel() == 1){ LEVEL = "";}
            else if(TestBody.mypet.getLevel() == 2){ LEVEL = "_ev";}
        }
    }


}
