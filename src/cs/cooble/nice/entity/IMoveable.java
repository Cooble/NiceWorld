package cs.cooble.nice.entity;

import cs.cooble.nice.util.Location;

/**
 * interface sloužící k možnosti posouvání èí získávání objektu
 */
public interface IMoveable {

    int getX();
    int getY();

    void setX(int x);
    void setY(int y);

    default Location getLocation(){
        return new Location(getX(),getY());
    }

    default void setLocation(Location location){
        setX(location.X);
        setY(location.Y);
    }

    default void setIsMoving(boolean isMoving){}
}
