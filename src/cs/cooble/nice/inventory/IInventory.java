package cs.cooble.nice.inventory;

import cs.cooble.nice.entity.IdAble;
import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.items.ItemStack;

import java.io.Serializable;

/**
 * Created by Matej on 7.3.2015.
 */
public interface IInventory extends Serializable,IdAble {
    /**
     * Registruje sve containery, pomoci ContainerRegister.
     * @param containerRegister
     */
    void registerContainers(ContainerRegister containerRegister);

    /**
     * Each tick called.
     */
    void onUpdate(World world);

    /**
     * Action on adding itemstack to container
     * @param container to put itemstack in
     * @param itemStack itemstack which wants to be added
     * @return itemstack after added to container if null item went to container
     */
    ItemStack addToContainer(Container container,ItemStack itemStack);

    /**
     * Action on trying to get specified number of items
     * @param container from which wants Itemstack
     * @param pocet if more than there are, return all
     * @return Itemstack got from container if null itemstack cant be got.
     */
    ItemStack getFromContainer(Container container,int pocet);

    /**
     * Prohodi itemstacky v containeru,
     * @param container v cem je prohazovany itemstack
     * @param itemStack s cim to chci prohodit
     * @return if returns itemstack bud se prohodil nebo ne, pokud null item se kterým se klikalo bude removed
     */
    ItemStack prohod(Container container,ItemStack itemStack);

    /**
     * Adds item to some free slot in tileentity
     * @param itemStack which would like to be added
     * @return itemstack after adding if null full itemstack was ad to tileentity.
     */
    ItemStack addToSomeFreeSlot(ItemStack itemStack);

}
