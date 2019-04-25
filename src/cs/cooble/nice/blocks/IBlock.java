package cs.cooble.nice.blocks;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.entity.IdAble;
import cs.cooble.nice.entity.Matej;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.TextureLoadable;
import cs.cooble.nice.inventory.items.ItemStack;


/**
 * Created by Matej on 15.3.2015.
 */
public interface IBlock extends TextureLoadable,IdAble {
    /**
     * @return item which corespond to that block, null if item doesnt exist.
     */
    ItemStack getItemFromBlock();

    /**
     * @return texturu corespondujici s blokem
     */
    IIcon getIIcon();

    /**
     * @return texturu pro kresleni itemu
     */
    IIcon getItemIIcon();

    /**
     * Voláno harvesterem, když je do Bloku bouchnuto.
     * Slouží k hezkým animacím.
     * @param e entita která bouchá. Slouží k nastavení na jakou stranu se má blok ohýbat.
     * @param b
     * @param maxhasdig
     */
    default void buch(IEntity e, boolean b, int maxhasdig){}

    /**
     * Voláno při kliknuti pravym tlacitkem
     * @param e Matej
     * @param itemStack itemstack in hand
     * @return true if can be clicked else false
     */
    default boolean onRightClicked(@Nullable ItemStack itemStack,World world,Matej e){return false;}
}
