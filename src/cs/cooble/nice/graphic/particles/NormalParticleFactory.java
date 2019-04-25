package cs.cooble.nice.graphic.particles;

/**
 * Created by Matej on 20.6.2015.
 */
public class NormalParticleFactory implements ParticleFactory{
    @Override
    public Particle getParticle() {
        return new NormalParticle();
    }
}
