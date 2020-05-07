import pets.Pet;

public class InfoUpdateThread extends Thread {
    TestBody tb;
    public InfoUpdateThread(TestBody tb){
        this.tb = tb;
    }
    public void run(){
        try{
            while(true){
                Thread.sleep(1000);
                tb.loveLabel.setText("亲密度: " + tb.mypet.getLove_now()+"/"+tb.mypet.getLove_max());
                tb.hungryLabel.setText("饱食度: " + tb.mypet.getHungry_now()+"/"+tb.mypet.getHungry_max());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
