package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.util.Location;

import java.util.Random;

/**
 * Created by Matej on 20.6.2015.
 */
public class ParticleEmmiter {

    Location location;
    ParticleFactory factory;
    Random random;
    private int maxLife;
    private double preVelY;
    private double preVelX;

    public ParticleEmmiter(ParticleFactory factory, Location location, int maxLife, double preVelY, double preVelX){
        this.factory = factory;
        this.location = location;
        this.maxLife = maxLife;
        this.preVelY = preVelY;
        this.preVelX = preVelX;
    }

    public void update(){
        if(random.nextInt(10)==0) {
            Particle p = factory.getParticle();
            //p.setAtributes(preVelX,preVelY,maxLife,location);
            ParticleSystem.addParticle(p);
        }
    }
}
