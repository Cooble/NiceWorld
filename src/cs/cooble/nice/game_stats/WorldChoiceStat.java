package cs.cooble.nice.game_stats;

import cs.cooble.nice.chat.SlovoManager;
import cs.cooble.nice.game_stats.components.*;
import cs.cooble.nice.event.Events;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;


/**
 * Created by Matej on 18.2.2015.
 */
public class WorldChoiceStat implements ScreenStat {

    public void onStarted() {
        //Create new Button
        CudlikMap.getInstance().addCudlik(new Cudlik(10, (int) (NC.HEIGHT * 0.85), 200, 50, "Create new", "prd", new ICudlik() {
            @Override
            public void pressed(Location location) {
            }

            @Override
            public void released(Location location) {
                if (NC.getEnteredText() == "") {
                    String[] svety = NiceWorld.getNiceWorld().getWorldProvider().getWorldNames();
                    final String fin = "New World";
                    String curSvet = fin;

                    int baselengh = curSvet.length();

                    for (int i = 0; i < svety.length; i++) {
                        String svet = svety[i];
                        if (curSvet.equals(svet)) {
                            if (baselengh == curSvet.length())
                                curSvet += "  1";
                            else curSvet = fin + " " + ((Integer.parseInt(SlovoManager.getSlovoFrom(curSvet, 2))) + 1);
                        }
                    }
                    Events.onWorldLoaded(curSvet);
                } else Events.onWorldLoaded(NC.getEnteredText());
            }
        }));
        //play button


        //Box with worlds
        BoxCudlik boxCudlik = new BoxCudlik(NC.WIDTH / 2 - 250, 100, 500, 700, "WorldBox", new ICudlik() {
            @Override
            public void clickedOnComponent(Infos component) {
                NC.setWORLDNAME(component.getInfo(0));
                Events.onWorldLoaded(NC.getWORLDNAME());
            }
        });

        boxCudlik.setWorldInfo(NiceWorld.getNiceWorld().getWorldProvider().getWorldsInfo());
        CudlikMap.getInstance().addCudlik(boxCudlik);

        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH / 2 - 200, 20, 400, 50, "Play", "prd", new ICudlik() {
            @Override
            public void pressed(Location location) {
                if (boxCudlik.getSelected() != null) {
                    Events.onWorldLoaded(boxCudlik.getSelected().getInfo(0));
                }
            }
        }));
        //DELETE
        CudlikMap.getInstance().addCudlik(new Cudlik(NC.WIDTH - 10 - 200, (int) (NC.HEIGHT * 0.85), 200, 50, "Delete", "prd", null, new Color(255, 30, 80), new ICudlik() {
            @Override
            public void released(Location location) {
                if (boxCudlik.getSelected() != null) {
                    NiceWorld.getNiceWorld().getWorldProvider().deleteSvet(boxCudlik.getSelected().getInfo(0));
                    boxCudlik.setWorldInfo(NiceWorld.getNiceWorld().getWorldProvider().getWorldsInfo());
                }
            }
        }));


        //TextBox for new world names
        TextCudlik textCudlik = new TextCudlik((int) (NC.WIDTH * 0.5 - 200), (int) (NC.HEIGHT * 0.85), 400, 30, "WorldNameTextBox", "prd", new ICudlik() {
            @Override
            public void onSetData(Object data) {
                NC.setEnteredText((String) data);
            }
        });
        textCudlik.setMaxLength(23);

        //RETURN
        CudlikMap.getInstance().addCudlik(textCudlik);
    }

    public void onUpdate() {

    }

    public void onQuitted() {
        CudlikMap.getInstance().removeAll();
    }

    @Override
    public String toString() {
        return CurrectScreenStatManager.WORLD_CHOICE;
    }
}
