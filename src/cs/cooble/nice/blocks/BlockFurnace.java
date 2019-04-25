package cs.cooble.nice.blocks;

import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.inventory.ToolType;

/**
 * Created by Matej on 22.2.2015.
 */
public class BlockFurnace extends Block {
    public BlockFurnace(){
        super("furnace");
        this.maxstacksize =10;
        this.hardness=100;
        toolType= ToolType.PICKAXE;

        maxMeta=2;
        textureName=ImageManager.BLOCK+"pec";
        sirka=90;
        vyska=90;
    }
}
