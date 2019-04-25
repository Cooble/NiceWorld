package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.Harvester;
import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.entity.IEntity;

/**
 * Created by Matej on 4.4.2015.
 */
public class ItemAxe extends ItemTool {
    public ItemAxe() {
        super("axe", ToolType.AXE);
        this.textName="Zelezna Sekera";
        this.textureName="axe_iron";
        setMaxDamage(30);
        shouldbepaintedjinakinhand=true;

    }

    @Override
    public void onItemHitted(World world,ItemStack itemStack, IEntity kdo, Block koho) {
        super.onItemHitted(world,itemStack, kdo, koho);
        //Harvester.setHarvesting(itemStack, kdo, koho);

    }
}
