package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.util.Location;

import java.util.ArrayList;

/**
 * Created by Matej on 17.2.2015.
 */
public class CudlikMap {
    private static CudlikMap ourInstance = new CudlikMap();

    public static CudlikMap getInstance() {
        return ourInstance;
    }

    private CudlikMap() {
    }
    private ArrayList<Cudlik> list = new ArrayList<>();

    /**
     * přidá čudlík ke klikání a registruje ho u CM.
     * @param cudlik
     */
    public void addCudlik(Cudlik cudlik){
        list.add(cudlik);
        NiceWorld.getNiceWorld().getRenderer().registerGUI(cudlik);
    }
    public void removeAll(){
        for(Cudlik cudlik:list){
            NiceWorld.getNiceWorld().getRenderer().removeGUI(cudlik);
        }
        list.clear();
    }

    public boolean onPressed(Location location){
        int poradiPressed=0;
        boolean b=false;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getRectangle().contains(location.X,location.Y)) {
                list.get(i).pressed(location);
                poradiPressed=i;
                b=true;
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if(i!=poradiPressed) {
                list.get(i).onClickedElswhere();
            }
        }
        return b;
    }
    public boolean onReleased(Location location){
        for (int i=0;i<list.size();i++) {
            list.get(i).released(location);
        }
        return false;
    }
    public void onMouseMoved(Location mouseLoc){
        for (int i = 0; i < list.size(); i++) {
            Cudlik c = list.get(i);
            c.focused(c.getRectangle().contains(mouseLoc.X, mouseLoc.Y));
            c.moved(mouseLoc);
        }
    }
    public void onMouseWheeled(Location mouseLoc,int pocet){
        for (Cudlik c:list){
            c.wheeled(mouseLoc,pocet);
        }
    }
}
