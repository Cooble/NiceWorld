package cs.cooble.nice.blocks;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.*;
import cs.cooble.nice.inventory.ToolType;

/**
 * Created by Matej on 31.1.2015.
 */
public class BlockGrass extends Block {
    private IIcon itemIcon;
    public BlockGrass() {
        super("grass");
        setMaterial(Block.GRASS);
        isFlatty=true;
        maxstacksize =64;
        textureName=ImageManager.BLOCK+"grass";
        maxMeta=2;
        toolType= ToolType.NONE;
        canput=false;
        sirka=40+NC.RANDOM.nextInt(15)-6;
        vyska=50+NC.RANDOM.nextInt(30)-5;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        super.loadTextures(loader);
        itemIcon = loader.getIcon(ImageManager.ITEM+"grass");

    }


    @Override
    public boolean isFlatty() {
        return true;
    }

    @Override
    public IIcon getItemIIcon() {
        return itemIcon;
    }

}
