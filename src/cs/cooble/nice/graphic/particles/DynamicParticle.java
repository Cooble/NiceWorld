package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 20.6.2015.
 */
public class DynamicParticle extends Particle {

    protected double timeX,timeY;
    protected double GRAVITY,ODPOR_VZDUCHU;
    protected double preVelocityX;
    protected double preVelocityY;



    public void setAtributes(double preVelocityX, double preVelocityY, int maxLife, Location location) {
        super.setAtributes(maxLife, location);
        this.preVelocityX = preVelocityX;
        this.preVelocityY = preVelocityY;

        preX=location.X;
        preY=location.Y;
    }

    public void onUpdate(){
        timeX++;
        timeY++;
        life--;
        if(life==0)
            ParticleSystem.removeParticle(index);
        posX=preY + preVelocityY * getObjectsTimeY()+0.5*GRAVITY    *Math.pow(getObjectsTimeY(),2);
        posY=preX + preVelocityX * getObjectsTimeX()+0.5*ODPOR_VZDUCHU *Math.pow(getObjectsTimeX(), 2);
    }
    private double getObjectsTimeX() {
        return timeX;
    }

    private double getObjectsTimeY() {
        return timeY;
    }
}
