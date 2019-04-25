package cs.cooble.nice.entity;

import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 26.1.2015.
 */
public interface IControllable {

    default void key(char key){}
    default void left(){}
    default void right(){}
    default void up(){}
    default void down(){}
    default void space(){}
    default void depressed(){}
    default void pressed(){}
    default void wheeled(Location location,int pocet){}
    default void mouseMoved(Location location){}

    default void deLeft(){}
    default void deRight(){}
    default void deUp(){}
    default void deDown(){}
    default void deSpace(){}

}
