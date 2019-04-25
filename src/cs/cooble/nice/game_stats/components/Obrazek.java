package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Image;


/**
 * Created by Matej on 11.4.2015.
 */
public class Obrazek implements IRenderable {
    private Image image;
    private Location location;
    private Location dimension;
    private boolean cetred;

    public Obrazek(Image image,int x,int y,int width,int height,boolean shouldcentred){
        this.image=image;
        location=new Location(x,y);
        dimension=new Location(width,height);
        cetred=shouldcentred;
    }
    public Obrazek(Image image,int x,int y,boolean shouldcentred){
       this(image,x,y,image.getWidth(),image.getHeight(),shouldcentred);
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        int posx = location.X-dimension.X/2;
        int posy = location.Y-dimension.Y/2;
        if(cetred)
            g.g().drawImage(image,posx,posy,posx+dimension.X,posy+dimension.Y,0,0,image.getWidth(),image.getHeight());

        else  g.g().drawImage(image,location.X,location.Y,dimension.X,dimension.Y,0,0,image.getWidth(),image.getHeight());

    }

    @Override
    public int getPriority() {
        return 1;
    }
}
