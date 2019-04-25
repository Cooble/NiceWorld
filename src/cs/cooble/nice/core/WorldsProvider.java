package cs.cooble.nice.core;

import cs.cooble.nice.game_stats.components.Infos;
import cs.cooble.nice.util.Location;

import java.util.ArrayList;

/**
 * Created by Matej on 30.6.2015.
 */
public interface WorldsProvider {

    /**
     * @return all world names
     */
    String[] getWorldNames();

    /**
     *
     * @return list of all Infos of worlds
     */
    ArrayList<Infos> getWorldsInfo();


    void deleteSvet(String name);

    /**
     * clear everything in directory niceworld
     */
    void removeEverything();

    void onUpdateChunks(Location location);

}
