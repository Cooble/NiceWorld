package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.graphic.*;
import cs.cooble.nice.util.Location;

import java.awt.*;

/**
 * Created by Matej on 7.7.2015.
 */
public class BleskParticle extends Particle {


    private int type;
    private Rectangle rectangle;
    private int minlife;
    private boolean shouldFade;
    private int dobaZobrazeni;

    private transient IIcon icon;

    public BleskParticle(int type){
        this.type = type;
        isLighting=true;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        icon = loader.getIcon(ImageManager.PARTICLE + "blesk_" + type);
    }

    /**
     *
     * @param maxLife
     * @param minlife atribut udavajici, kdy se ma blesk zvyditelnit
     * @param rectangle
     */
    public void setAtributes(int maxLife,int minlife, Rectangle rectangle,boolean shouldFade) {
        super.setAtributes(maxLife, Location.getRectangleLocation(rectangle));
        this.shouldFade = shouldFade;
        this.minlife = minlife;
        this.rectangle=rectangle;
        assert (minlife>=maxLife);
        dobaZobrazeni=maxLife-minlife;//todo dodelat fading blesku
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        if (time >= minlife) {
            g.drawIcon(icon, (int) posX - Kamera.getXCoord(), (int) posY - Kamera.getYCoord(), rectangle.width, rectangle.height);
        }
    }
}
