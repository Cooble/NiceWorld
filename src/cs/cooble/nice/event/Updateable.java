package cs.cooble.nice.event;

/**
 * Interface slou��c� k ozna�en� t��dy, kter� m� b�t updatov�na each tick
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
