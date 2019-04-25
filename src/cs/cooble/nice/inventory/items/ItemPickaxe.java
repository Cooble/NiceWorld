package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.Harvester;
import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.entity.IEntity;

/**
 * Created by Matej on 3.4.2015.
 */
public class ItemPickaxe extends ItemTool {

    public ItemPickaxe() {
        super("pickaxe", ToolType.PICKAXE);
        textureName="pickaxe_iron";
        textName = "pickaxe_iron";

        shouldbepaintedjinakinhand=true;
        setMaxDamage(25);
    }

    @Override
    public void onItemHitted(World world,ItemStack itemStack, IEntity kdo, Block koho) {
        super.onItemHitted(world,itemStack, kdo, koho);
       //Harvester.setHarvesting(itemStack,kdo,koho);

    }
}
