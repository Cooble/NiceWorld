package cs.cooble.nice.input;

import cs.cooble.nice.entity.IdAble;
import cs.cooble.nice.util.Location;

import java.awt.*;

/**
 * Created by Matej on 7.3.2015.
 */
public interface IActionRectangle extends IdAble {

    Rectangle getRectangle();

    /**
     * Called when was clicked to this rectangle
     * @param location
     * @param prave_tlacitko
     */
    void click(Location location,boolean prave_tlacitko);

    /**
     * Called when the mouse is moving( doesnt have to be on this rectangle)
     * @param location
     */
    default void mouseMove(Location location){}

    /**
     * Called when was clicked elsewhere (not on this rectangle)
     */
    default void clickElsewhere(){}

    /**
     * Called when is wheeled(doesnt have to be on this rectangle)
     * @param location
     * @param otocka
     */
    default void mousewheeled(Location location,int otocka){}

    default String getID(){return "not specified";}
}
