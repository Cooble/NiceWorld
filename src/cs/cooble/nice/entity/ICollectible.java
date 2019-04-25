package cs.cooble.nice.entity;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.items.ItemStack;

/**
 * Marked interface která značí, že objekt je schopen sbírat itemy(májí k němu běhat)
 */
public interface ICollectible {
    boolean canCollect(ItemStack item);
    default @Nullable ItemStack giveItemStack(ItemStack item){
        return null;
    }
    default void throwItemStack(World world, ItemStack item){}
}
