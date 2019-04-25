package cs.cooble.nice.inventory.items;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.util.TypPeceni;

/**
 * Created by Matej on 4.4.2015.
 */
public class ItemIronOre extends Item implements ItemVypekable {
    public ItemIronOre() {
        super("iron_ore");
    }

    @Override
    public ItemStack getVypecenyItem(TypPeceni typPeceni, @Nullable ItemStack from) {
        if(typPeceni==TypPeceni.TAVENI)
            return new ItemStack(Items.zeleznyIngot,from==null?1:from.getPocet());

        return null;
    }
}
