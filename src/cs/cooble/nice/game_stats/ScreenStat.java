package cs.cooble.nice.game_stats;

/**
 * Created by Matej on 17.2.2015.
 */
public interface ScreenStat {

    void onStarted();
    void onUpdate();
    void onQuitted();
    default void setSubGameStat(ScreenStat screenStat){}
    default void removeSubGameStat(){}
    default boolean hasSubGameStat(){return false;}
    default String getSubGameStatName(){return "unknown_game_stat";}
}
