package cs.cooble.nice.blocks;

import cs.cooble.nice.graphic.ImageManager;

/**
 * Created by Matej on 11.8.2015.
 */
public class BlockBin extends Block {
    public BlockBin(){
        super("bin");
        textureName=ImageManager.BLOCK+"kos";
        sirka=+60;
        vyska=90;
    }

}
