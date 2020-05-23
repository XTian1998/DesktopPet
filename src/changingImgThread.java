import pets.Pet;

public class changingImgThread extends Thread {
    int actionType;
    String imgUrl;
    public changingImgThread(int actionType){
        this.actionType = actionType;
    }
    public void run(){
        int i=1;
        try{
            while(true){
                if(actionType == 1){
                    //拖拽动作
                    imgUrl = "_drag"+ i +".png";
                    TestBody.cgJLabelImg(TestBody.imgLabel, "kabi"+TestBody.mypet.getLevel_now()+imgUrl);
                    i++;
                    if(i>2) i=1;
                    Thread.sleep(200);
                }
                else if(actionType == 2){
                    //进食动作
                    imgUrl = "_eat" + i + ".png";
                    TestBody.cgJLabelImg(TestBody.imgLabel, "kabi"+TestBody.mypet.getLevel_now()+imgUrl);
                    i++;
                    if(i>6) {
                        TestBody.cgJLabelImg(TestBody.imgLabel, "kabi"+TestBody.mypet.getLevel_now()+"_default.png");
                        break;
                    }
                    Thread.sleep(200);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
