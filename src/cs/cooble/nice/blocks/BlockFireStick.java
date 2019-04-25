package cs.cooble.nice.blocks;

import cs.cooble.nice.graphic.ImageManager;

/**
 * Created by Matej on 30.8.2015.
 */
public class BlockFireStick extends Block{

    public BlockFireStick(){
        super("fire_stick");
        textureName=ImageManager.BLOCK+"fire_tyc";
        maxMeta=2;
        sirka=50;
        vyska=150;
    }


}

