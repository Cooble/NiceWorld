package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 17.2.2015.
 */
public interface ICudlik {

    default void moved(Location location){}
    default void pressed(Location location){}
    default void released(Location location){}
    default void wheeled(Location location, int pocet){}

    /**
     * called when the cursor is pointing on that cudlik
     */
    default void focused(boolean isFocused){}
    default void clickedOnComponent(Infos component){}

    /**
     * Volá se když chceme uložit nějakou hodnotu objektu(např: Velikost,Volume etc.).
     * @param data data
     */
    default void onSetData(Object data){}

    /**
     * Volá se, pokud se klikne na nějaký jiný než na tento components.
     */
    default void onClickedElswhere(){}
}
