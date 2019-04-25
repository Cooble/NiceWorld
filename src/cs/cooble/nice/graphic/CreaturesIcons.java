package cs.cooble.nice.graphic;

import cs.cooble.nice.util.Smer;

/**
 * Stores normal icons for creature (like left right up down etc);
 */
public class CreaturesIcons implements TextureLoadable{
    private final String NAME;

    private int left,up,down;
    private PolyIcon iconLeft,iconUp,iconDown;


    public CreaturesIcons(String name) {
        this.NAME = name;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void setRightLeft(int left) {
        this.left = left;
    }

    public void setUp(int up) {
        this.up = up;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        if(down>0)
            iconDown=loader.getIcon(NAME+"/"+Smer.DOWN.name(),down);
        if(left>0)
            iconLeft=loader.getIcon(NAME+"/"+Smer.LEFT.name(),left);
         if(up>0)
            iconUp=loader.getIcon(NAME+"/"+Smer.UP.name(),up);
    }

    public IIcon getIcon(Smer smer,int index){
        switch (smer){
            case RIGHT:
            case LEFT:
                return iconLeft.getIcon(index);
            case UP:
                return iconUp.getIcon(index);
            case DOWN:
                return iconDown.getIcon(index);
        }
        return null;
    }
}
