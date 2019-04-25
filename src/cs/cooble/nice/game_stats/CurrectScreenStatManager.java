package cs.cooble.nice.game_stats;

/**
 * Can be Created only by niceworld
 */
public class CurrectScreenStatManager {
    private ScreenStat curStat;
    private String gameStatName="nothing";

    public static final String GAME="game";
    public static final String ERROR="error_stat";
    public static final String GAME_PAUSE="game_pause";
    public static final String INTRO="intro";
    public static final String SETTINGS="settings";
    public static final String WORLD_CHOICE="world_choice";


    public void setScreenStat(ScreenStat screenStat){
        if(curStat!=null)
            curStat.onQuitted();

        curStat= screenStat;
        gameStatName=curStat.toString();
        curStat.onStarted();
    }
    public void setSubSreenStat(ScreenStat subScreenStat){
        if(curStat!=null)
            curStat.setSubGameStat(subScreenStat);
    }
    public void removeSubGameStat(){
        if(curStat!=null){
            curStat.removeSubGameStat();
        }
    }
    public void onUpdate(){
        curStat.onUpdate();
    }
    public String getScreenStatName()
    {
        if(curStat!=null)
            if(curStat.hasSubGameStat())
                return curStat.getSubGameStatName();
        return gameStatName;
    }

    /**
     *
     * @returns name of currect game stat, not subgamestat
     */
    public String getPureGameStatName() {
        return gameStatName;
    }
    public String getSubGameStatName(){
        if(curStat!=null)
            return curStat.getSubGameStatName();
        else return null;
    }


}
