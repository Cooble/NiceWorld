package cs.cooble.nice.inventory.items;

import cs.cooble.nice.inventory.ToolType;

/**
 * Created by Matej on 18.6.2015.
 */
public class ItemIgniter extends ItemTool {
    public ItemIgniter() {
        super("igniter", ToolType.OTHER);
        setMaxDamage(30);
    }
}
