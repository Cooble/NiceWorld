package cs.cooble.nice.blocks;

import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.inventory.items.ItemStack;

/**
 * Created by Matej on 6.4.2018.
 */
public class Template {
    public Block.Sound material;
    public int hardness;
    public ToolType toolType;
    public ItemStack[] diggedItems;
    public String[] numberChances;


   public Template copy(){
       Template out = new Template();
       out.material=material;
       out.hardness=hardness;
       out.toolType=toolType;
       out.diggedItems=diggedItems;
       out.numberChances=numberChances;
       return out;
   }
}
