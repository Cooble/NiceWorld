package cs.cooble.nice.game_stats;

import cs.cooble.nice.game_stats.components.Cudlik;
import cs.cooble.nice.game_stats.components.CudlikMap;
import cs.cooble.nice.game_stats.components.ICudlik;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.event.Events;
import cs.cooble.nice.core.NC;

/**
 * Created by Matej on 18.2.2015.
 */
public class GamePauseStat implements ScreenStat {

    public void onStarted() {
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 400 / 2, (int) (NC.HEIGHT * 0.6), 400, 50, "Return", "prd", new ICudlik() {
            @Override
            public void moved(Location location) {

            }

            @Override
            public void pressed(Location location) {}

            @Override
            public void released(Location location) {
                CudlikMap.getInstance().removeAll();
                NiceWorld.getNiceWorld().getCurrectScreenStatManager().removeSubGameStat();
            }
        }));
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 400 / 2, (int) (NC.HEIGHT * 0.75), 400, 50, "Save and Quit", "prd", new ICudlik() {
            @Override
            public void moved(Location location) {

            }

            @Override
            public void pressed(Location location) {}

            @Override
            public void released(Location location) {
                CudlikMap.getInstance().removeAll();
                Events.onWorldUnLoaded();
            }
        }));
        NC.setCudlikMode(true);
        NC.shouldOnUpdate=false;
    }

    public void onUpdate() {}

    public void onQuitted() {
        NC.shouldOnUpdate=true;
        CudlikMap.getInstance().removeAll();
        NC.setCudlikMode(false);
    }

    @Override
    public String toString() {
        return CurrectScreenStatManager.GAME_PAUSE;
    }
}
