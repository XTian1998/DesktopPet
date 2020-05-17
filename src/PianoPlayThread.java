import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PianoPlayThread extends Thread {
    String url;
    private void playPiano(String url) {
        //播放琴音方法
        try {
            //获取音频输入流
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(url));
            //获取音频编码对象
            AudioFormat audioFormat = audioInputStream.getFormat();
            //设置数据输入
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();
            //从输入流中读取数据发送到混音器
            int count;
            byte tempBuffer[] = new byte[1024];
            while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (count > 0) {
                    sourceDataLine.write(tempBuffer, 0, count);
                }
            }
            sourceDataLine.drain();
            sourceDataLine.close();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public PianoPlayThread(String url){
        this.url = url;
    }
    public void run() {
        playPiano(url);
    }
}
