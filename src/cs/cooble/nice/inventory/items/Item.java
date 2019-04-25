package cs.cooble.nice.inventory.items;

import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.entity.IdAble;
import cs.cooble.nice.graphic.*;
import cs.cooble.nice.game_stats.components.ColorizedText;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.util.Smer;


/**
 * Created by Matej on 14.2.2015.
 */
public class Item implements TextureLoadable,IdAble {
    public final String ID;
    @Deprecated
    protected String textName;
    protected String textureName;
    protected int maxStackSize;
    protected ToolType toolType;

    public static final int HoldStatEQUIPPED = 0;
    public static final int HoldStatHELD = 1;
    public static final int HoldStatUNEQUIPPED = 2;

    protected int maxMeta;
    protected IIcon image;

    public Item(String id) {
        this.ID = id;
        this.textureName=id;
        this.textName=id;
        maxStackSize= 64;
        toolType = ToolType.NONE;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        if (textureName != null) {
            if(maxMeta>0){
                for (int i = 0; i < maxMeta + 1; i++) {
                    image = loader.getIcon(ImageManager.ITEM + textureName+"_"+i);
                }
            }else
            image = loader.getIcon(ImageManager.ITEM + textureName);
        }
    }

    /**
     * called when entity has clicked on the shape of something
     * @param world
     * @param inHand in hand
     * @param entity who is holding item
     * @param target block or entity
     * @param right button or left button
     * @return
     */
    public boolean onItemUse(World world, ItemStack inHand, IEntity entity, IEntity target,boolean right) {
        return false;
    }

    /**
     * determies  if item is instanceof itemblock;
     */
    public boolean isItemBlock() {
        return false;
    }


    //GETTERS=================================================================
    public String getID(ItemStack itemStack) {
        return ID;
    }

    public ColorizedText getColorizedText(ItemStack itemStack) {
        return new ColorizedText(textName);
    }

    public IIcon getIIcon(ItemStack itemStack) {
        return image;
    }

    public int getMaxPocet() {
        return maxStackSize;
    }

    //SETTERS=================================================================

    @Override
    public String toString() {
        return textName;
    }

    public boolean canBreakBlock(Block block) {
        return block.getToolType().equals(toolType);
    }

    public boolean shouldBePaintedJinakInHand() {
        return false;
    }

    public void paintItemInHand(int rukax, int rukay, int width, int height, Smer smer, boolean poza, NGraphics g) {}

    /**
     * called whenever is item held in inventory (player hss clicked on it and has it in hand or this item is located on action tool slot)
     *
     * @param holdStat
     */
    public void onItemHeldInHand(World world, int holdStat) {
    }

    /**
     *
     * @return final string of this item
     */
    @Override
    public String getID() {
        return ID;
    }
}
