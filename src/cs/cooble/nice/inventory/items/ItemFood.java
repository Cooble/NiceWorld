package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.entity.Matej;

/**
 * Created by Matej on 29.3.2015.
 */
public class ItemFood extends Item {

    private int fq;

    public ItemFood(String name, int foodquocient) {
        super(name);
        fq=foodquocient;
    }

    public int getFoodQuocient() {
        return fq;
    }

    @Override
    public boolean onItemUse(World world, ItemStack inHand, IEntity entity, IEntity target, boolean right) {
        if(target instanceof Matej){
            ((Matej) target).getStats().addHunger(fq*10);
            inHand.addPocet(-1);
        }
        return true;
    }
}
