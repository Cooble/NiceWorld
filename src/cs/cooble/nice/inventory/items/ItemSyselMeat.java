package cs.cooble.nice.inventory.items;

import cs.cooble.nice.util.TypPeceni;

/**
 * Created by Matej on 29.3.2015.
 */
public class ItemSyselMeat extends ItemFood implements ItemVypekable {

    public ItemSyselMeat() {
        super("sysel_meat", 10);
        this.textureName="sysel_meat";
    }

    @Override
    public ItemStack getVypecenyItem(TypPeceni typPeceni,ItemStack from) {
        switch (typPeceni){
            case PECENI:
                return new ItemStack(Items.syselMasoUpec,from==null?1:from.getPocet());
            case VARENI:
                return new ItemStack(Items.syselMasoUvar,from==null?1:from.getPocet());
        }
        return null;
    }
}
