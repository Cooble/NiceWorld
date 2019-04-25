package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.graphic.*;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;


/**
 * Created by Matej on 19.6.2015.
 */
public abstract class Particle implements IRenderable {

    protected double posX,posY;
    protected int    life;
    protected int MAX_LIFE;
    protected int index;
    protected double time;

    protected int preX,preY;
    protected String imageName;
    protected double zvetseni;
    protected boolean isLighting;

    protected transient IIcon icon;

    @Override
    public void loadTextures(TextureLoader loader) {
        if(imageName!=null){
            icon=loader.getIcon(imageName);
        }
    }

    public void setAtributes(int maxLife,Location location){
        life = maxLife;
        MAX_LIFE=maxLife;
        posX=location.X;
        posY=location.Y;
        preX=location.X;
        preY=location.Y;
        zvetseni=1;

    }

    public final void setZvetseni(double zvetseni) {
        this.zvetseni = zvetseni;
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        g.g().setColor(Color.cyan);
        g.g().fillRect((int) posX - Kamera.getXCoord(), (int) posY - Kamera.getYCoord(), (int) (10 * zvetseni), (int) (10 * zvetseni));
    }

    public void onUpdate(){
        time++;
        life--;
        if(life<=0) {
            if(isLighting)
                ParticleSystem.removeLightParticle(index);
            else
                ParticleSystem.removeParticle(index);
        }
    }

    public final void setIndex(int index) {
        this.index = index;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    public boolean isLighting() {
        return isLighting;
    }
    public Particle setIsLighting(boolean isLighting) {
        this.isLighting = isLighting;
        return this;
    }
}
