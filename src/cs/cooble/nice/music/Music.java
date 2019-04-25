package cs.cooble.nice.music;

import javax.sound.sampled.*;
import java.net.URL;
import java.applet.*;

/**
 * Created by Matej on 2.2.2015.
 */
class Music {

    private Mixer mixer;
    public Clip clip=null;
    private FloatControl floatControl;

    public Music(URL cesta,double volume){
        Thread vlakno = new Thread(){
            @Override
            public void run() {
                Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
                mixer = AudioSystem.getMixer(mixInfo[0]);
                DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, null);
                try {
                    clip = (Clip) mixer.getLine(dataLineInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(cesta);
                    clip.open(audioInputStream);
                    floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    setVolume(volume);
                    clip.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        vlakno.start();

    }
    public Music(URL cesta,boolean isSong,double volume){
            Mixer.Info[] mixInfo = AudioSystem.getMixerInfo();
            mixer = AudioSystem.getMixer(mixInfo[0]);
            DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, null);
            try {
                clip = (Clip) mixer.getLine(dataLineInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(cesta);
                clip.open(audioInputStream);
                floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                setVolume(volume);
                clip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public void setVolume(double volume){
        if(volume==0){
            floatControl.setValue(floatControl.getMinimum());
            return;
        }
        else floatControl.setValue(floatControl.getMaximum()+floatControl.getMinimum());

        float c = (float)(Math.log(volume)/Math.log(10.0));
        floatControl.setValue(c * 20+floatControl.getMaximum()<0?c*30+floatControl.getMaximum():c * 20+floatControl.getMaximum());
    }

    public Clip getClip() {
        return clip;
    }
}
