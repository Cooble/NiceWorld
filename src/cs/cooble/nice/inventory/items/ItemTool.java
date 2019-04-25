package cs.cooble.nice.inventory.items;

import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.game_stats.components.ColorizedText;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Smer;
import cs.cooble.nice.music.MPlayer;
import org.newdawn.slick.Color;


/**
 * Created by Matej on 29.3.2015.
 */
public class ItemTool extends Item {
    protected int maxDamage;
    protected boolean shouldbepaintedjinakinhand=false;

    public ItemTool(String name,ToolType toolType) {
        super(name);
        maxStackSize=1;
        this.toolType=toolType;
        maxDamage=1;
    }
    public ItemTool(String name) {
        this(name,ToolType.NONE);
    }

    protected void setMaxDamage(int maxDamage){
        this.maxDamage=maxDamage;
    }
    private int getHP(ItemStack itemStack){
        if(itemStack.getNBT()==null){
            itemStack.setNBT(new NBT());
            itemStack.getNBT().setIntenger("HP",maxDamage);
        }
        return itemStack.getNBT().getInteger("HP");
    }
    private void setHP(ItemStack itemStack,int hp){
        itemStack.getNBT().setIntenger("HP",hp);
    }

    /**
     * damage itemstack if hp is 0 remove it
     * @param itemStack
     * @param damageCaused
     */
    public void damageIt(ItemStack itemStack, int damageCaused){
        setHP(itemStack,getHP(itemStack)-damageCaused);
        if(getHP(itemStack)<=0) {
            itemStack.setPocet(0);
            MPlayer.playSound(MPlayer.RANDOM + "tool_break");
        }
    }

    @Override
    public String getID(ItemStack itemStack) {
        return ID;
    }

    @Override
    public ColorizedText getColorizedText(ItemStack itemStack) {
        return new ColorizedText(new String[]{textName,(int)(getHP(itemStack)/(maxDamage+0.0)*100)+"%"},new Color[]{Color.white,Color.green});
    }

    @Override
    public boolean shouldBePaintedJinakInHand() {
        return shouldbepaintedjinakinhand;
    }

    @Override
    public void paintItemInHand(int rukax, int rukay, int width, int height, Smer smer, boolean poza, NGraphics g) {
        switch (smer){
            case RIGHT:
                g.drawIcon(image, rukax + 5, rukay - 33, width, height + 30);
                break;
            case LEFT:
                g.drawIcon(image, rukax, rukay - 36, width - 5, height + 30);
                break;
            case UP:
                g.rotate((float) 1.5);
                g.drawIcon(image, rukax + 10, rukay - 30, width, height + 30);
                break;
            case DOWN:
                g.rotate((float) -1.5);
                g.drawIcon(image, rukax - 5, rukay - 35, width, height + 30);
        }
    }

    @Override
    public boolean onItemUse(World world, ItemStack inHand, IEntity entity, IEntity target, boolean right) {
        return super.onItemUse(world, inHand, entity, target, right);
    }

    public void onItemHitted(World world,ItemStack itemStack, IEntity kdo, IEntity koho) {

    }

    public void onItemHitted(World world,ItemStack itemStack, IEntity kdo, Block koho) {
        if (toolType != ToolType.SPEAR && toolType != ToolType.NONE) {
            damageIt(itemStack,1);
        }
    }
}
