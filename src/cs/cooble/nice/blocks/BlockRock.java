package cs.cooble.nice.blocks;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.Items;
import cs.cooble.nice.util.NBT;

import java.awt.*;

/**
 * Created by Matej on 29.3.2015.
 */
public class BlockRock extends Block {

    public BlockRock(){
        super("sutr");
        this.setMaterial(Block.ROCK);
        textureName= ImageManager.BLOCK+"sutr";
        maxMeta=2;
    }


    @Override
    public int getHardness() {
        return metadata==0?1:100;
    }

    @Override
    public ToolType getToolType() {
        return metadata==0?ToolType.NONE:ToolType.PICKAXE;
    }

    @Override
    public Rectangle getShape() {
        sirka=metadata*50+35;
        vyska=metadata*50+35;
        return super.getShape();
    }

    @Override
    public ItemStack getItemFromBlock() {
        return null;
        //return new ItemStack(Items.kaminek);
    }

    @Override
    public ItemStack[] getDiggedItem(ItemStack digger) {
        if(metadata==0)
            return new ItemStack[]{getItemFromBlock().setPocet(NC.RANDOM.nextInt(2)+1)};
        else {
            return new ItemStack[]{getItemFromBlock().setPocet(NC.RANDOM.nextInt(3)+2),new ItemStack(Items.zeleznaRuda,NC.RANDOM.nextInt(2)+1)};

        }
    }
}
