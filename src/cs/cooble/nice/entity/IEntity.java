package cs.cooble.nice.entity;

import cs.cooble.nice.util.NBTSaveable;
import cs.cooble.nice.core.World;
import cs.cooble.nice.objects.IPaintLocable;
import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 26.1.2015.
 */
public interface IEntity extends IPaintLocable,NBTSaveable {

    /**
     * called each tick when playing
     * @param world
     */
    void onEntityUpdate(World world);

    /**
     * called, when entity is loaded or created
     */
    default void init(World world){}

    /**
     * called when entity is unloaded or killed
     */
    default void deInit(World world){}


   default boolean isBlock(){return false;}
}
