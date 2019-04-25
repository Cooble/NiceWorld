package cs.cooble.nice.game_stats;

import cs.cooble.nice.game_stats.components.*;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;


/**
 * Created by Matej on 9.4.2015.
 */
public class ErrorStat implements ScreenStat {
    private Text errortext;
    private ColorField colorField;

    public ErrorStat(String[] error){
        //errortext=new TextBox(Color.white,error,new Location(NC.WIDTH/2,NC.HEIGHT/3),new Location(100,100),40);
        errortext=new TextBox(Color.white,error,new Location(10,20),false);

        colorField=new ColorField(new Color(0,0,0,220),new Location(0,0),new Location(NC.WIDTH,NC.HEIGHT));
    }
    public ErrorStat(Exception e){
        String[] hlaska=new String[e.getStackTrace().length+1];
        hlaska[0]="Sorry, it looks like the world was badly saved and can't be reloaded :( (It could be caused by wrong version of game or the file was damaged)";
        hlaska[1]=" ";
        for (int i = 2; i < e.getStackTrace().length; i++) {
            hlaska[i]=e.getStackTrace()[i].toString();
        }
        errortext=new TextBox(Color.white,hlaska,new Location(10,20),false);

        colorField=new ColorField(new Color(0,0,0,220),new Location(0,0),new Location(NC.WIDTH,NC.HEIGHT));
    }


    @Override
    public void onStarted() {
        NiceWorld.getNiceWorld().getRenderer().registerGUI(colorField);
        NiceWorld.getNiceWorld().getRenderer().registerGUI(errortext);
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 400 / 2, NC.HEIGHT/5*4, 400, 50, "Go to menu", "prd", new ICudlik() {
            @Override
            public void released(Location location) {
                NiceWorld.getNiceWorld().getCurrectScreenStatManager().setScreenStat(new WorldChoiceStat());
            }
        }));

    }

    @Override
    public void onUpdate() {}

    @Override
    public void onQuitted() {
        NiceWorld.getNiceWorld().getRenderer().removeGUI(errortext);
        NiceWorld.getNiceWorld().getRenderer().removeGUI(colorField);
        CudlikMap.getInstance().removeAll();
    }

    @Override
    public String toString() {
        return CurrectScreenStatManager.ERROR;
    }
}
