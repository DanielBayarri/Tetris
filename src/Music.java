import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;
public class Music {

    Clip clip;
    URL[] musicFile = new URL[3];

    public Music() {
        musicFile[0] = getClass().getResource("Sounds/TetrisMusic.wav");
        musicFile[1] = getClass().getResource("Sounds/GameOver.wav");
        musicFile[2] = getClass().getResource("Sounds/Notification.wav");//Not Works


    }

    public void setFile(int i) {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile[i]);
            clip = AudioSystem.getClip();//AudioStream from audioInput
            clip.open(audioInput);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }
    public void stop(){
        clip.stop();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}




