package cs.cooble.nice.blocks;

import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.core.NC;

/**
 * Created by Matej on 20.3.2015.
 */
public class BlockStick extends Block {

    public BlockStick(){
        super("stick");
        maxstacksize =16;
        toolType= ToolType.NONE;
        textureName=ImageManager.BLOCK+"klacek";
        sirka=25+ NC.RANDOM.nextInt(5);
        vyska=25;
    }
}
