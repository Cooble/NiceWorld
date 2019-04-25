package cs.cooble.nice.inventory.items;

import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.NBTSaveable;
import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.core.World;
import cs.cooble.nice.util.NBT;

/**
 * Created by Matej on 7.3.2015.
 */
public final class ItemStack implements NBTSaveable {
    public final Item ITEM;
    private int pocet;
    private NBT nbt;
    private int metadata;

    public ItemStack(Item item) {
        ITEM = item;
        pocet = 1;
    }
    public ItemStack(NBT nbt) {
        ITEM = GameRegistry.getInstance().getItem(nbt.getString("item_id"));
        readFromNBT(nbt);
    }

    public ItemStack(Item item, int pocet) {
       this(item);
        this.pocet = pocet;
    }

    public ItemStack(Item item, int pocet, int metadata) {
        this(item, pocet);
        this.metadata = metadata;
    }

    public int getPocet() {
        return pocet;
    }

    public ItemStack addPocet(int added) {
        if(pocet+added>getMaxPocet()){
            Log.printStackTrace("Invalid number of items. It is more than maxStackSize!");
            return this;
        }
        pocet += added;
        return this;
    }

    public ItemStack setPocet(int pocet) {
        if(pocet>getMaxPocet()){
            Log.printStackTrace("Invalid number of items. It is more than maxStackSize!");
            return this;
        }
        this.pocet = pocet;
        return this;
    }

    public int getMaxPocet() {
        return ITEM.getMaxPocet();
    }

    public boolean isMax() {
        return ITEM.maxStackSize <= pocet;
    }

    public void setMetadata(int metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return ITEM.toString();
    }

    /**
     * copies itemstack, even with all nbts asociated with.
     * new copy doesnt point at old nbt:D
     * @return
     */
    public ItemStack copy() {
        ItemStack out =  new ItemStack(ITEM, pocet);
        NBT nbt = this.getNBT();
        if(nbt!=null){
            nbt=nbt.copy();
        }
        out.nbt=nbt;
        out.metadata=metadata;
        return out;
    }

    public NBT getNBT() {
        return nbt;
    }

    public void setNBT(NBT nbt) {
        this.nbt = nbt;
    }

    /**
     * called whenever is item held in inventory (player hss clicked on it and has it in hand or this item is located on action tool slot)
     *
     * @param held_event
     */
    public void onItemHeldInHand(World world, int held_event) {
        ITEM.onItemHeldInHand(world, held_event);
    }

    public boolean equalsType(ItemStack another) {
        return ITEM.equals(another.ITEM) && metadata == another.metadata;
    }

    @Override
    public void writeToNBT(NBT nbt) {
        nbt.setString("item_id",ITEM.ID);
        nbt.setIntenger("pocet",pocet);
        nbt.setIntenger("meta",metadata);
        nbt.setNBT("nbt",this.nbt);
    }

    @Override
    public void readFromNBT(NBT nbt) {
        pocet=nbt.getInteger("pocet");
        metadata=nbt.getInteger("meta");
        this.nbt=nbt.getNBT("nbt");
    }
}
