package cs.cooble.nice.game_stats.components;

import java.util.Date;

/**
 * Created by Matej on 21.2.2015.
 */
public class WorldInfo {
    public final Date date;
    public final String name;

    public WorldInfo(Date date, String name) {
        this.date = date;
        this.name = name;
    }
}
