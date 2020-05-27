import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HL_UpdatingThread extends Thread{
    private Lock lock = new ReentrantLock();
    private long time;
    private int type;

    public HL_UpdatingThread(long time, int type){
        this.time = time;
        this.type = type;
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(time);
                lock.lock();
                if(type == 1){
                    //type=1对应饱食度更新线程
                    hungryChanging();
                } else if(type == 2){
                    //type=2对应亲密度更新线程
                    loveChanging();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void hungryChanging() {
        int value = TestBody.mypet.getHungry_now();
        if(value > 0){
            TestBody.mypet.setHungry_now(value-1);
        }

    }

    private void loveChanging() {
        int value = TestBody.mypet.getLove_now();

        //饱食度大于60，亲密度增加；否则亲密度减少
        if(TestBody.mypet.getHungry_now()>=60){
            if(value < 100){
                TestBody.mypet.setLove_now(value+1);
            }
        } else {
            if(value > 0){
                TestBody.mypet.setLove_now(value-1);
            }
        }
    }
}
