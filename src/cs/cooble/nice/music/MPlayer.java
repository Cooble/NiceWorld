package cs.cooble.nice.music;
import cs.cooble.nice.core.NiceWorld;

import java.io.FileNotFoundException;
import java.net.URL;


/**
 * Trida starajici se o prehravani zvuku a muziky.
 */
public class MPlayer {
    private static final String SOUNDS = "/sounds/";
    private static final String PRIPONA = ".wav";
    private static boolean hrajeSong=false;
    private static Music music;
    public static final String MOB = "mobs/";
    public static final String MUSIC = "music/";
    public static final String BLOCK = "block/";
    public static final String CUDLIK="components/";
    public static final String RANDOM = "random/";


    public static void playSound(String jmeno){
        URL url =MPlayer.class.getResource(SOUNDS + jmeno + PRIPONA);
        if(url!=null)
            new Music(url, NiceWorld.getNiceWorld().getSettings().Volume_sound);
        else {
            FileNotFoundException e = new FileNotFoundException("[MPlayer]Nenalezen zvuk: " + SOUNDS + jmeno + PRIPONA);
            e.printStackTrace();
        }
    }
    public static void playSong(String jmeno){
        URL url =MPlayer.class.getResource(SOUNDS + jmeno + PRIPONA);
        refreshMusicPlaying();
        if(url!=null) {
            if(!hrajeSong){
                music = new Music(url,true, NiceWorld.getNiceWorld().getSettings().Volume_music);
                refreshVolume();
                hrajeSong=true;
            }
        }
        else {
            FileNotFoundException e = new FileNotFoundException("[MPlayer]Nenalezen hudba: " + SOUNDS + jmeno + PRIPONA);
            e.printStackTrace();
        }


    }

    public static void refreshMusicPlaying(){
        if(music!=null)
            hrajeSong = music.getClip().isRunning();
    }
    public static void refreshVolume(){
        if(music!=null)
            if(music.getClip().isRunning())
                music.setVolume(NiceWorld.getNiceWorld().getSettings().Volume_music);
    }
    public static void stopMusic(){
        if(music!=null){
            music.clip.stop();
            hrajeSong=false;
        }
    }

    public static void onUpdate(){
      if(music !=null) {
          if (!music.getClip().isRunning()) {
              hrajeSong = false;
          } else {
              music.setVolume(NiceWorld.getNiceWorld().getSettings().Volume_music);
              hrajeSong = true;
          }
      }

      else{
          hrajeSong=false;
      }
    }
}
