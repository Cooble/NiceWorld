package cs.cooble.nice.inventory.items;

import cs.cooble.nice.core.NC;

/**
 * Created by Matej on 14.2.2015.
 */
public class ItemLog extends Item implements ItemFuel {
    public ItemLog() {
        super("log");
        this.maxStackSize =32;
    }

    @Override
    public int getFuelValue() {
        return NC.core.TARGET_TPS*10;
    }
}
