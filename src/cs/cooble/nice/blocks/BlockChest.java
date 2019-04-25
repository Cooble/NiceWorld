package cs.cooble.nice.blocks;

import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.inventory.ToolType;

/**
 * Created by Matej on 22.2.2015.
 */
public class BlockChest extends Block {


    public BlockChest() {
        super("chest");
        this.maxstacksize = 10;
        toolType = ToolType.AXE;
        this.setMaterial(Block.WOOD);
        maxMeta=2;

        sirka = 60;
        vyska = 72;
        textureName=ImageManager.BLOCK + "chest";
    }
}
