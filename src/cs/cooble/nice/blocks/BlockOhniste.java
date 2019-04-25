package cs.cooble.nice.blocks;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.ItemIgniter;
import cs.cooble.nice.inventory.items.Items;

/**
 * Created by Matej on 18.6.2015.
 */
public class BlockOhniste extends Block {
    private int heat;


    private int MINRAD = 110;
    private int MAXRAD = 130;
    private final int MAXHEAT = 10;

    private int STATE_NONE = 0;
    private int STATE_OFF = 1;
    private int STATE_ON = 2;

    public BlockOhniste() {
        super("fireplace");
        toolType = ToolType.PICKAXE;
        setMaterial(Block.ROCK);
        maxMeta = 3;
        textureName = ImageManager.BLOCK + "ohniste";
        sirka=100;
        vyska=100;
    }
    @Override
    public boolean click(World world, IEntity entity, @Nullable ItemStack inHand, boolean right) {
        if(inHand==null)
            return true;
        if (inHand.ITEM.equals(Items.poleno)) {
            if (metadata == STATE_NONE) {//dame do ohniste polena
                setMetadata(STATE_OFF);
                inHand.addPocet(-1);

            } else if (metadata == STATE_ON) {//dame polena do ohne

                if (heat <= MAXHEAT) {
                    heat++;
                    for (int i = 0; i < 4; i++) {
                        spawnCmoudParticle();
                    }
                    inHand.addPocet(-1);
                }
            }

        } else if (inHand.ITEM.equals(Items.zapalovac))
            if (metadata == STATE_OFF) {
                if (NC.RANDOM.nextInt(4) == 0) {
                  //  LightingMap.addLight(light);
                    setMetadata(STATE_ON);
                }
                ItemIgniter zapalovac = (ItemIgniter) Items.zapalovac;
                zapalovac.damageIt(inHand, 1);
                spawnPlamenParticle();
                heat = 1;
            }
        return true;
    }

    private void spawnPlamenParticle() {

    }

    private void spawnCmoudParticle() {

    }
    int spalovani;
    @Override
    public void onEntityUpdate(World world) {
        if (metadata == STATE_ON) {
            updateSpalovani();
            updateHeat();
        }
    }
    private void updateHeat() {
        MINRAD = 110 + heat * 30;
        MAXRAD = 130 + heat * 30;
    }

    private void updateSpalovani() {
        spalovani++;
        if (heat == 1)
            spalovani += 6;
        if (spalovani >= 300) {
            if (heat != 0) {
                heat--;
            } else {
                spalovani = 0;
                setMetadata(STATE_NONE);
               // LightingMap.removeLight(light);
            }
            spalovani = 0;
        }

    }
}
