package cs.cooble.nice.event;

/**
 * Interface sloužící k oznaèení tøídy, která má být updatována each tick
 */
public interface Updateable {
    /**
     * called each tick
     */
    void update();

    /**
     *
     * @return true, if should be called update()
     * false if should be unregistered and none calling anymore
     */
    boolean isAlive();

}
