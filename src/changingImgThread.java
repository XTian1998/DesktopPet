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
                    imgUrl = "_drag"+ i +".png";
                    TestBody.cgJLabelImg(TestBody.imgLabel, "kabi"+imgUrl);
                    i++;
                    if(i>2) i=1;
                    Thread.sleep(200);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
