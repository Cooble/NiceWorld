package cs.cooble.nice;

import cs.cooble.nice.core.World;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.entity.ItemEntity;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.util.Location;

import java.awt.*;

/**
 * Stara se o vykopani bloku (cekani nez vykope, prehravani muziky pri kopani, a nakonec vykopani), pri nastaveni noveho harvestu se stary prerusi,stru se taky prerusi kdyz dokope tileBlock.
 */
public final class Harvester {//todo redo

   /* private void harvest(World world){
        TILEBLOCK.BLOCK.buch(KDOE, true, 4,TILEBLOCK);
        KDO.setCanMove(true);

        ItemStack[] digged =  TILEBLOCK.BLOCK.getDiggedItem(ITEMSTACK,TILEBLOCK);
        for (ItemStack currdig : digged) {
            int max = currdig.getPocet();
            Rectangle shape = TILEBLOCK.getShape();

            for (int i = 0; i < max; i++) {
                world.getSpawner().spawnEntity(ItemEntity.build(TILEBLOCK.getX() + shape.width / 2 + NC.RANDOM.nextInt(20) - 10, TILEBLOCK.getY() + shape.height + NC.RANDOM.nextInt(20) - 10, currdig.copy().setPocet(1)));
            }
        }
        world.getSpawner().killBlock(TILEBLOCK);
        if(TILEBLOCK.BLOCK.getDiggedSound()!=null)
            MPlayer.playSound(MPlayer.BLOCK+ TILEBLOCK.BLOCK.getDiggedSound()[NC.RANDOM.nextInt(TILEBLOCK.BLOCK.getDiggedSound().length)]);
    }
    static class Harvy{

    }
*/



}

