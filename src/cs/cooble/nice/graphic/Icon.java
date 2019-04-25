package cs.cooble.nice.graphic;

/**
 * Created by Matej on 29.9.2017.
 */
public class Icon implements IIcon {
    /**
     * coords on big map
     */
    protected int x,y,width,height;
    private String id;

    protected Icon(String id) {
        this.id = id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getID() {
        return id;
    }
}
