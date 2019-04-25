package cs.cooble.nice.inventory.items;

import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.game_stats.components.ColorizedText;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.NBT;

public class ItemBlock extends Item {

    private static final String BLOCK_ID = "block_id";
    private static final String BLOCK_CAN_PUT = "block_can_put";


    protected ItemBlock() {
        super("item_block");
        textureName=null;
    }

    @Override
    public boolean isItemBlock() {
        return true;
    }

    @Override
    public String getID(ItemStack stack) {
        return stack.getNBT().getString(BLOCK_ID);
    }

    @Override
    public IIcon getIIcon(ItemStack itemStack) {
        if (itemStack != null)
            if (itemStack.ITEM.equals(Items.block) && itemStack.getNBT() != null)
                return GameRegistry.getInstance().getBlock(itemStack.getNBT().getString(BLOCK_ID)).getItemIIcon();
        return null;
    }

    /**
     * @param block
     * @param canPut
     * @return itemstack with block
     */
    public static ItemStack buildItemBlock(Block block, boolean canPut) {
        ItemStack stack = new ItemStack(Items.block);
        NBT nbt = new NBT();
        nbt.setString(BLOCK_ID, block.getID());
        nbt.setBoolean(BLOCK_CAN_PUT, canPut);
        stack.setNBT(nbt);
        return stack;
    }

    /**
     * @param itemBlock needs to be ItemBlock class
     * @return true if this block can be placed in world
     */
    public static boolean canput(ItemStack itemBlock) {
        return itemBlock.ITEM.equals(Items.block) && itemBlock.getNBT().getBoolean(BLOCK_CAN_PUT);
    }

    public static Block getBlock(ItemStack itemStack) {
        String name = itemStack.getNBT().getString(BLOCK_ID);
        return GameRegistry.getInstance().getBlock(name);
    }

    @Override
    public ColorizedText getColorizedText(ItemStack itemStack) {
        if (!itemStack.ITEM.equals(Items.block))
            Log.printStackTrace("calling wrong itwmstack for itemblock colorized text");
        NBT nbt = itemStack.getNBT();
        if (nbt == null || itemStack.getNBT().getString(BLOCK_ID) == null)
            return ColorizedText.build(this.textName);
        else return ColorizedText.build(itemStack.getNBT().getString(BLOCK_ID));

    }
}
