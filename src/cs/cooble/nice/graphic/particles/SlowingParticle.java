package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.graphic.Kamera;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.util.PrekladacRozsahu;
import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 20.6.2015.
 */
public class SlowingParticle extends AngleFloatingParticle {

    float kdyzpomalit;
    double preSpeed;

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(MAX_LIFE!=0)
            if((double)life/MAX_LIFE<kdyzpomalit){
                PrekladacRozsahu prekladacRozsahu = new PrekladacRozsahu(kdyzpomalit*MAX_LIFE,1);
                double d=prekladacRozsahu.get1stValueToSecond(life);
                preSpeed *=d;
                zvetseni =d;
            }
    }

    public void setAtributes(String texture,double speed,double angle, int maxLife, Location location,float kdyzpomalit) {
        super.setAtributes(speed, angle, maxLife, location);
        this.kdyzpomalit = kdyzpomalit;
        imageName=texture;
        preSpeed=speed;

    }
    @Override
    public void render(int x, int y, NGraphics g) {
        g.drawIcon(icon,(int)(posX- Kamera.getXCoord()-icon.getWidth()*zvetseni/2),(int)(posY-Kamera.getYCoord()-icon.getHeight()*zvetseni/2),(int)(icon.getWidth()*zvetseni),(int)(icon.getHeight()*zvetseni));
    }

}
