package cs.cooble.nice.graphic.particles;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.Kamera;
import cs.cooble.nice.graphic.NGraphics;

import java.util.ArrayList;

/**
 * Created by Matej on 20.6.2015.
 */
public class ParticleSystem {
    private static final int MaxPocetParticles=1000;//buffer kolik muže mit max particles
    private static int currectPlace=-1;
    private static int currectLightPlace=-1;



    private static Particle[] particles = new Particle[MaxPocetParticles];
    private static Particle[] lightParticles = new Particle[MaxPocetParticles/2];

    private static ArrayList<ParticleEmmiter> emmiterArrayList=new ArrayList<>();

    public static void addParticle(Particle particle){
       if(!particle.isLighting()) {
           if (currectPlace < MaxPocetParticles - 1)
               currectPlace++;
           else currectPlace = 0;

           particles[currectPlace] = particle;
           particle.setIndex(currectPlace);
       }
        else {
           if (currectLightPlace < MaxPocetParticles / 2 - 1)
               currectLightPlace++;
           else currectLightPlace = 0;

           lightParticles[currectLightPlace] = particle;
           particle.setIndex(currectLightPlace);
       }

    }
    public static void removeParticle(int index){
        particles[index]=null;
    }

    public static void removeLightParticle(int index){
        lightParticles[index]=null;
    }

    public static void update(){
        for (ParticleEmmiter emmiter:emmiterArrayList) {
            int screenX,screenY;
            screenX=emmiter.location.X- Kamera.getXCoord();
            screenY=emmiter.location.Y-Kamera.getYCoord();

            if(screenX>-10&&screenX< NC.WIDTH+10&&screenY>-10&&screenY<NC.HEIGHT+10)//pokud je na screenu
                emmiter.update();
        }

        for (Particle p : particles) {
            if(p!=null)
                p.onUpdate();
        }
        for (Particle p : lightParticles) {
            if(p!=null)
                p.onUpdate();
        }
    }

    /**
     * renders particles which are affected by light
     * @param g
     */
    public static void renderPreLight(NGraphics g){
        for (Particle p : particles) {
            if (p != null)
                p.render(0, 0, g);
        }
    }
    /**
     * renders particles which are NOT affected by light
     * (they are still visible originaly)
     * @param g
     */
    public static void renderPostLight(NGraphics g){
        for (Particle p : lightParticles) {
            if (p != null)
                p.render(0, 0, g);
        }
    }

    /**
     * clear all particles
     */
    public static void clear(){particles=new Particle[MaxPocetParticles];}

    public void addEmmiter(ParticleEmmiter emmiter){
        emmiterArrayList.add(emmiter);
    }
    public void removeEmmiter(ParticleEmmiter emmiter){
        for (int i = 0; i < emmiterArrayList.size(); i++) {
            if(emmiterArrayList.get(i).equals(emmiter))
                emmiterArrayList.remove(i);
        }
    }
}
