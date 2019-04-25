package cs.cooble.nice.game_stats;

import cs.cooble.nice.game_stats.components.Cudlik;
import cs.cooble.nice.game_stats.components.CudlikMap;
import cs.cooble.nice.game_stats.components.ICudlik;
import cs.cooble.nice.game_stats.components.Obrazek;
import cs.cooble.nice.event.Events;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 17.2.2015.
 */
public class IntroStat implements ScreenStat {
    private Obrazek logo;
    public void onStarted() {
        logo=new Obrazek(ImageManager.getImage(ImageManager.GUI+"logo"),NC.WIDTH/2,NC.HEIGHT/4,true);

        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 200, (int) (NC.HEIGHT * 0.5), 400, 50, "Play", "prd",       new ICudlik() {
            @Override
            public void moved(Location location) {
                
            }

            @Override
            public void pressed(Location location) {}

            @Override
            public void released(Location location) {
                NiceWorld.getNiceWorld().getCurrectScreenStatManager().setScreenStat(new WorldChoiceStat());
            }
        }));
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 200, (int) (NC.HEIGHT * 0.6), 400, 50, "Settings", "prd",   new ICudlik() {
                    @Override
                    public void moved(Location location) {

                    }

                    @Override
                    public void pressed(Location location) {}

                    @Override
                    public void released(Location location) {
                        NiceWorld.getNiceWorld().getCurrectScreenStatManager().setScreenStat(new SettingsStat());
                    }
                }));
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 200, (int) (NC.HEIGHT * 0.7), 400, 50, "Quit", "prd",       new ICudlik() {
            @Override
            public void moved(Location location) {

            }

            @Override
            public void pressed(Location location) {

            }

            @Override
            public void released(Location location) {
                Events.onGameQuited();
            }
        }));
        NiceWorld.getNiceWorld().getRenderer().registerGUI(logo);
    }

    public void onUpdate() {

    }

    public void onQuitted() {
        CudlikMap.getInstance().removeAll();
        NiceWorld.getNiceWorld().getRenderer().removeGUI(logo);
    }

    public String toString() {
        return CurrectScreenStatManager.INTRO;
    }
}
