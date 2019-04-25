package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.LivingEntity;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.inventory.ToolType;

/**
 * Created by Matej on 29.3.2015.
 */
public class ItemPointyStick extends ItemTool{

    int ostrost=10;
    public ItemPointyStick() {
        super("pointy_stick", ToolType.SPEAR);
        this.textureName="pointy_stick";

        setMaxDamage(20);
        shouldbepaintedjinakinhand=true;
    }

    @Override
    public void onItemHitted(World world,ItemStack itemStack, IEntity kdo, IEntity koho) {
        if(koho instanceof LivingEntity) {
            ((LivingEntity) koho).causeDamage(world, kdo, ostrost);
            damageIt(itemStack, 1);
        }
    }
}
