package cs.cooble.nice.inventory.items;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.util.TypPeceni;

/**
 * Created by Matej on 4.4.2015.
 */
public interface ItemVypekable {

    /**
     * vraci prislusny itemstack k typu peceni
     * @param typPeceni
     * @param from
     * @return pokud null nelze vypéct
     */
    @Nullable ItemStack getVypecenyItem(TypPeceni typPeceni,@Nullable ItemStack from);
}
