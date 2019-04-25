package cs.cooble.nice.objects;

import cs.cooble.nice.graphic.IRenderable;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Matej on 31.1.2015.
 */
public interface IPaintLocable extends ILocaleable,IRenderable {
    /**
     *
     * @return true if render should render this at the bottom
     */
    default boolean isFlatty(){return false;}

    /**
     *
     * @return the lowest point of object (used to determine the layer in which it should be rendered)
     */
    default int getSpodek(){
        Rectangle shape = getShape();
        return shape.y+shape.height;
    }
}
