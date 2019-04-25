package cs.cooble.nice.game_stats;

import cs.cooble.nice.game_stats.components.CudlikMap;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.inventory.Inventory;

/**
 * Created by Matej on 17.2.2015.
 */
public class GamesStat implements ScreenStat {

    private ScreenStat substat=null;

    public void onStarted() {
        NiceWorld.getNiceWorld().getRenderer().registerGUI(Inventory.getInstance());
    }

    @Override
    public boolean hasSubGameStat() {
        return substat!=null;
    }

    @Override
    public String getSubGameStatName() {
     return substat!=null?substat.toString():"none_game_stat";
    }

    public void onUpdate() {
    }

    public void onQuitted() {
        CudlikMap.getInstance().removeAll();
        NiceWorld.getNiceWorld().getRenderer().clearAll();
        removeSubGameStat();
        //Display.setGradientPaintBackround(new GradientPaint(NC.WIDTH / 2, NC.HEIGHT / 2, new Color(0x37983B), NC.WIDTH, NC.HEIGHT, new Color(0x828410)));

    }

    @Override
    public void setSubGameStat(ScreenStat screenStat) {
        substat= screenStat;
        substat.onStarted();
    }

    @Override
    public void removeSubGameStat() {
        if(substat!=null)
            substat.onQuitted();
        substat=null;
    }

    @Override
    public String toString() {
        return CurrectScreenStatManager.GAME;
    }
}
