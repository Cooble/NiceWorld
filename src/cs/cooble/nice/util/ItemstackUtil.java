package cs.cooble.nice.util;

import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.inventory.items.Item;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.logger.Log;
import org.w3c.dom.Node;

/**
 * Created by Matej on 6.4.2018.
 */
public class ItemstackUtil {

    public static NBT serialize(ItemStack[] itemStacks) {
        NBT out = new NBT();
        out.setIntenger("length", itemStacks.length);
        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                ItemStack itemStack = itemStacks[i];
                NBT itemNBT = new NBT();
                itemStack.writeToNBT(itemNBT);
                out.setNBT("itemstack_" + i, itemNBT);
            }
        }
        return out;
    }

    public static ItemStack[] deserialize(NBT nbt) {
        ItemStack[] out = new ItemStack[nbt.getInteger("length")];
        for (int i = 0; i < out.length; i++) {
            NBT itemNBT = nbt.getNBT("itemstack_" + i);
            if (itemNBT != null) {
                out[i] = new ItemStack(itemNBT);
            }
        }
        return out;
    }

    public static ItemStack parseXML(Node node) {
        String id = node.getAttributes().getNamedItem("id").getTextContent();
        String meta = null;
        NBT nbt = null;
        int number = 1;
        try {
            number = Integer.parseInt(node.getAttributes().getNamedItem("number").getTextContent());
        } catch (Exception ignored) {
        }

        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node child = node.getChildNodes().item(i);
            if (child.getNodeName().equals("meta")) {
                meta = child.getTextContent();
            }
            if (child.getNodeName().equals("nbt")) {
                nbt = NBTUtil.parseXML(child);
            }
        }
        Item item  =GameRegistry.getInstance().getItem(id);
        if(item==null){
            Log.println("Invalid item id! "+id, Log.LogType.ERROR);
            return null;
        }
        ItemStack out = new ItemStack(item, number);
        out.setNBT(nbt);
        if (meta != null)
            out.setMetadata(Integer.parseInt(meta));
        return out;
    }


}
