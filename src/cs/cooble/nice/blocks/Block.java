package cs.cooble.nice.blocks;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.entity.IUserInteraction;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.graphic.PolyIcon;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.inventory.ToolType;
import cs.cooble.nice.inventory.items.ItemBlock;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.NBT;

import java.awt.*;
import java.util.*;

/**
 * Created by Matej on 30.1.2015.
 */
public class Block implements IBlock,IEntity,IUserInteraction {

    private final String ID;
    /**
     * normal location. to get chunk you need to divide by chunksize
     */
    protected int posX;
    protected int posY;

    protected int sirka = 150;
    protected int vyska = 200;

    protected int metadata;

    /**
     * how much hits it takes to mine, -1 = can't be digged
     */
    protected int hardness;
    protected String[] placeSound;
    protected String[] digSound;
    protected String[] diggedSound;
    /**
     * whether render will put this totaly at the bottom
     */
    protected boolean isFlatty;
    protected int maxstacksize;
    protected ToolType toolType;
    protected String textureName;

    protected Template template;

    protected PolyIcon image;

    /**
     * if higher number-> automatic load icons from 0 to max meta !exclusively!
     * also textureName must be set
     * default is 1
     */
    protected int maxMeta;

    protected static final Sound ROCK = new Sound(new String[]{"rock_place"}, new String[]{"rock_dig_0", "rock_dig_1", "rock_dig_2", "rock_dig_3", "rock_dig_4", "rock_dig_5"}, new String[]{"rock_digged_0", "rock_digged_1", "rock_digged_2", "rock_digged_3"}, "ROCK");
    protected static final Sound GRASS = new Sound(new String[]{"grass_break"}, null, new String[]{"grass_break"}, "GRASS");
    protected static final Sound WOOD = new Sound(new String[]{"wood_place"}, new String[]{"wood_dig_0", "wood_dig_1", "wood_dig_2"}, new String[]{"wood_break"}, "WOOD");
    private static java.util.List<Sound> sounds = new ArrayList<>();
    private static void loadSounds(){
        sounds.add(ROCK);
        sounds.add(GRASS);
        sounds.add(WOOD);
    }

    protected boolean canput=true;


    public Block(String id) {
        this.ID=id;
        maxMeta = 1;
        placeSound = null;
        digSound = null;
        diggedSound = null;
        toolType = ToolType.OTHER;
        setMetadata(0);
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        if (textureName != null) {
            image = loader.getIcon(textureName, maxMeta);
        }
    }

    @Override
    public IIcon getIIcon() {
        if (maxMeta > 0) {
            return image.getIcon(metadata);
        }
        return image.getIcon(0);
    }


    @Override
    public void onEntityUpdate(World world) {

    }

    /**
     * called when the block is placed into world or just loaded
     */
    public void init(World world) {
    }

    /**
     * called when the block is being removed ( digged or just unloaded)
     */
    public void deInit(World world) {

    }


    /**
     * automaticky nakresli blok bere v potaz getTectureName a rectangle
     *
     * @param x
     * @param y
     * @param g
     */
    public void render(int x, int y, NGraphics g) {
        if (getIIcon() != null)
            g.drawIcon(getIIcon(), getX()- x-sirka/2, getY()- y-vyska, sirka, vyska);
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public Rectangle getShape() {
        return new Rectangle(posX-sirka/2,posY-vyska,sirka,vyska);
    }

    @Override
    public final String getID() {
        return ID;
    }

    @Override
    public boolean click(World world, IEntity entity, @Nullable ItemStack inHand, boolean right) {
        return false;
    }

    public static class Sound {
        public final String[] PLACESOUND;
        public final String[] DIGSOUND;
        public final String[] DIGGEDSOUND;
        public final String id;

        Sound(String[] place, String[] dig, String[] digged, String id) {
            PLACESOUND = place;
            DIGSOUND = dig;
            DIGGEDSOUND = digged;
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
    public static Sound toSound(String id){
        if(sounds.size()==0)
            loadSounds();
        for(Sound s:sounds){
            if(s.id.equals(id)){
                return s;
            }
        }
        return null;
    }


    public ToolType getToolType() {
        return toolType;
    }


    //Setters===========================================


    @Override
    public void setLocation(int x, int y) {
        this.posX=x;
        this.posY=y;
    }

    protected void setMaterial(Sound sounds) {
        placeSound = sounds.PLACESOUND;
        digSound = sounds.DIGSOUND;
        diggedSound = sounds.DIGGEDSOUND;
    }

    public void setMetadata(int meta){
        this.metadata=meta;
        Template t = BlockTemplateParser.get(this.ID,meta);
        if(t!=null){
            toolType=t.toolType;
            hardness=t.hardness;
            setMaterial(t.material);
            template=t;
        }
    }

    public int getMetadata() {
        return metadata;
    }

    //Getters===========================================
    public int getHardness() {
        return hardness;
    }

    public boolean isFlatty() {
        return isFlatty;
    }

    public int getMaxstacksize() {
        return maxstacksize;
    }

    /**
     * @param digger itemstack s kterým to tezim
     * @return defaultne vrati itemstack pomoci metody cs.cooble.nice.blocks.IBlock.getItemFromBlock(). vraci null pokud getItemFromBlock je null.
     */
    public ItemStack[] getDiggedItem(ItemStack digger) {
        if (getItemFromBlock() == null)
            return null;
        return new ItemStack[]{getItemFromBlock()};
    }

    @Override
    public ItemStack getItemFromBlock() {
        return ItemBlock.buildItemBlock(this, canput);
    }

    @Override
    public IIcon getItemIIcon() {
        return image.getIcon(0);
    }

    public boolean canput() {
        return canput;
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    @Override
    public void writeToNBT(NBT nbt) {
        nbt.setString("className",this.getClass().getName());
        nbt.setIntenger("posX", posX);
        nbt.setIntenger("posY", posY);
        nbt.setIntenger("meta", metadata);
    }

    @Override
    public void readFromNBT(NBT nbt) {
        posX = nbt.getInteger("posX");
        posY = nbt.getInteger("posY");
        metadata = nbt.getInteger("meta");
    }
}
