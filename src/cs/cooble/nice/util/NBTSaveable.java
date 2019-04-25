package cs.cooble.nice.util;

/**
 * Created by Matej on 18.2.2018.
 * Possibility to save current state of instance and then load it back.
 *
 * All classes need to have:
 *                          non-parametric constructor
 *                          in writeToNBT() set string "className" with name of the class
 */
public interface NBTSaveable {
    /**
     * NBT in which everything is saved
     *
     * !
     * important is to set into NBT special String "className" with value = name of the class of instance
     * (otherwise it won't be possible to create the instance back)
     * !
     */
    void writeToNBT(NBT nbt);

    /**
     * loads all data back to instance
     * @param nbt
     */
    default void readFromNBT(NBT nbt){}
}
