package cs.cooble.nice.game_stats;

import cs.cooble.nice.game_stats.components.Cudlik;
import cs.cooble.nice.game_stats.components.CudlikMap;
import cs.cooble.nice.game_stats.components.ICudlik;
import cs.cooble.nice.game_stats.components.ShiftCudlik;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.gameloading.Settings;


/**
 * Created by Matej on 17.2.2015.
 */
public class SettingsStat implements ScreenStat {
    private Settings settings;
    public SettingsStat(){
        settings=NiceWorld.getNiceWorld().getSettings();
    }

    @Override
    public void onStarted() {
        CudlikMap.getInstance().addCudlik(new ShiftCudlik(NC.WIDTH / 2-200, (int) (NC.HEIGHT * 0.2), 400, 50, "Volume Sound", settings.Volume_sound, new ICudlik() {
            public void onSetData(Object velikost) {
                settings.Volume_sound= (double) velikost;
            }
        }));
        CudlikMap.getInstance().addCudlik(new ShiftCudlik(NC.WIDTH / 2-200, (int) (NC.HEIGHT * 0.3), 400, 50, "Volume Music",settings.Volume_music, new ICudlik() {
            @Override
            public void pressed(Location location) {
                MPlayer.refreshMusicPlaying();
            }

            @Override
            public void released(Location location) {
                MPlayer.refreshMusicPlaying();
            }

            public void onSetData(Object velikost) {
                settings.Volume_music= (double) velikost;
                MPlayer.refreshVolume();
                MPlayer.playSong(MPlayer.MUSIC+"muzika");
            }
        }));
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2-200, (int) (NC.HEIGHT * 0.8), 400, 50, "Return", "prd", new ICudlik() {

            @Override
            public void released(Location location) {
                NiceWorld.getNiceWorld().getCurrectScreenStatManager().setScreenStat(new IntroStat());
            }
        }));
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onQuitted() {
        //Saver.setSettings(Settings.get()); todo bude to fungovat  bez toho?
        MPlayer.stopMusic();
        CudlikMap.getInstance().removeAll();
    }

    @Override
    public String toString() {
        return CurrectScreenStatManager.SETTINGS;
    }
}
