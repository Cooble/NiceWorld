package cs.cooble.nice.objects;

import cs.cooble.nice.util.Location;

import java.awt.*;

/**
 * Created by Matej on 26.1.2015.
 */
public interface ILocaleable {
    int getX();
    int getY();

    Rectangle getShape();

    default void setLocation(int x, int y){}
    default Location getLocation(){
        return new Location(getX(),getY());
    }
}
