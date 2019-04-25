package cs.cooble.nice.blocks;

import cs.cooble.nice.graphic.ImageManager;

/**
 * Created by Matej on 28.6.2015.
 */
public class BlockDen extends Block {
    public BlockDen() {
        super("den");
        hardness = -1;
        setMaterial(Block.GRASS);
        textureName = ImageManager.BLOCK + "nora";
        canput = false;
        sirka = 50;
        vyska = 50;
    }
}
