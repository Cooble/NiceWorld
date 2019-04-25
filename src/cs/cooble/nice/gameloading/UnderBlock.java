package cs.cooble.nice.gameloading;

import cs.cooble.nice.entity.IdAble;
import cs.cooble.nice.graphic.*;
import cs.cooble.nice.util.NBT;

import java.awt.*;

/**
 * Created by Matej on 14.4.2018.
 */
public class UnderBlock implements TextureLoadable,IdAble{
    private IIcon icon;
    private static IIcon border_left;
    private static IIcon border_corner;

    private String texture;
    private String ID;
    //the bigger the more up it is
    private int level;

    public UnderBlock(String id) {
        ID = id;
        texture=ID;
    }

    public UnderBlock setTexture(String texture) {
        this.texture ="underblock/"+ texture;
        return this;
    }
    public UnderBlock setLevel(int level){
        this.level=level;
        return this;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        icon = loader.getIcon(texture);
        border_corner = loader.getIcon("underblock/border_corner");
        border_left = loader.getIcon("underblock/border_left");
    }

    public IIcon getIcon() {
        return icon;
    }

    public String getID() {
        return ID;
    }

    public int getLevel() {
        return level;
    }

    public static IIcon getBorderCorner() {
        return border_corner;
    }

    public static IIcon getBorderLeft() {
        return border_left;
    }
}
