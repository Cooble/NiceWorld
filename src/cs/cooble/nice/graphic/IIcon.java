package cs.cooble.nice.graphic;

import java.util.function.Supplier;

/**
 * Created by Matej on 22.9.2017.
 */
public interface IIcon extends Supplier<Integer>{

    int getX();

   int getY();

    int getWidth();

    int getHeight();

    /**
     *
     * @return path where texture is located
     */
    String getID();

    default Integer get() {
        return getWidth()*getHeight();
    }
}
