package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 20.6.2015.
 */
public class AngleFloatingParticle extends Particle {
    private double angle;
    protected double speed;


    public void setAtributes(double speed,double angle,int maxLife, Location location) {
        super.setAtributes(maxLife, location);
        this.speed = speed;
        this.angle = angle;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.posX += Math.cos(this.angle) * speed;
        this.posY += Math.sin(this.angle) * speed;
    }
}
