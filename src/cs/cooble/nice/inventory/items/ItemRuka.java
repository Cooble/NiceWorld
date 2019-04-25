package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.Harvester;
import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.entity.IEntity;

/**
 * Created by Matej on 11.3.2015.
 */
@Deprecated
public class ItemRuka extends ItemTool {
    public ItemRuka() {
        super("ruka" ,ToolType.NONE);
        textureName=null;
    }

    @Override
    public void onItemHitted(World world,ItemStack itemStack, IEntity kdo, Block koho) {
        //Harvester.setHarvesting(itemStack,kdo,koho);
    }
}
