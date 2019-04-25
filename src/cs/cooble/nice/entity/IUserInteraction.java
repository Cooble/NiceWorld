package cs.cooble.nice.entity;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.items.ItemStack;

/**
 * Created by Matej on 6.4.2018.
 */
public interface IUserInteraction {
    /**
     * when entity clicks on this in world
     * @param world
     * @param entity who clicked
     * @param inHand what has entity in hand
     * @param right or left button
     * @return
     */
    boolean click(World world,IEntity entity,@Nullable ItemStack inHand,boolean right);
}
