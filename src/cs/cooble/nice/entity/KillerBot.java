package cs.cooble.nice.entity;

import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.graphic.particles.BleskParticle;
import cs.cooble.nice.graphic.particles.ParticleSystem;

import java.awt.*;


/**
 * Created by Matej on 5.7.2015.
 */
public class KillerBot extends LivingEntity {

    private int paint_x_sphere;
    private boolean paint_sphere_right;
    private int houpani;
    private boolean houp_nahoru;
    private boolean isChasing;
    private LivingEntity victim;
    private int radius=400;
    private int pauzaMeziChasing;

    private IIcon robotKiller,robotCudlik;

    public KillerBot() {
        super();
        health.setMaxValue(100);
        dimension=new Location(100,50);
        speed=new Stat(0,5);
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        super.loadTextures(loader);
        robotKiller=loader.getIcon(ImageManager.ENTITY+"robot_killer");
        robotCudlik=loader.getIcon(ImageManager.ENTITY+"robot_killer_cudlik");
    }

    int ypsilon;

    @Override
    public void onEntityUpdate(World world) {
        ypsilon=getY()-40+houpani/5;//lokace robota nad zemi
        paint_sphereUpdate();
        paint_houpaniUpdate();
        goForward();
        if(!isChasing){
            pauzaMeziChasing++;
            if(pauzaMeziChasing>20) {
                victim = (LivingEntity) world.getNearestEntity(radius, this, e -> {
                    if(e instanceof Sysel) {
                        LivingEntity c = (LivingEntity) e;
                        return !c.isMarkedDeath();
                    }
                    return false;
                });
                isChasing = victim!=null;
                pauzaMeziChasing = 0;
            }
            else speed.addValue(-0.5F);

        }
        else {
            angle= Mover.getAngleTo(getLocation(),victim.getLocation());
            speed.addValue(0.5F);
            if(Mover.getVzdalenostTo(getLocation(),victim.getLocation())<5){
                if(!victim.isMarkedDeath()) {
                    addBleskParticles(world,ypsilon);
                    victim.causeDamage(world, this, 5);
                    speed.addValue(speed.getMaxValue() - 1);
                    if(victim.isMarkedDeath()){
                        isChasing=false;
                        pauzaMeziChasing=0;
                    }
                }
                else {
                    isChasing=false;
                    victim=null;
                }
            }
        }

    }

    @Override
    public void render(int x, int y, NGraphics g) {
        g.drawIcon(iconStin,getX()-x-dimension.X/2,getY()-y,dimension.X,dimension.Y*3/4);

        g.drawIcon(robotKiller, getX() - x - dimension.X / 2, ypsilon - y - dimension.Y / 2);
        g.drawIcon(robotCudlik,getX()-x+paint_x_sphere,ypsilon-y-8);
    }

    private void paint_sphereUpdate(){
        if(paint_sphere_right) {
            paint_x_sphere++;
            if(paint_x_sphere==20)
                paint_sphere_right=false;
        }
        else {
            paint_x_sphere--;
            if(paint_x_sphere==-34)
                paint_sphere_right=true;
        }
    }
    private void paint_houpaniUpdate(){
        if(houp_nahoru) {
            houpani++;
            if(houpani==40)
                houp_nahoru=false;
        }
        else {
            houpani--;
            if(houpani==-40)
                houp_nahoru=true;
        }
    }
    private void addBleskParticles(World world,int ypsilon){
        int pocet = world.getRandom().nextInt(2)+1;
        for (int i = 0; i < pocet; i++) {
            BleskParticle bleskParticle = new BleskParticle(world.getRandom().nextInt(4));
            bleskParticle.setAtributes(world.getRandom().nextInt(10)+10,world.getRandom().nextInt(10),new Rectangle(getX()-dimension.X/2+world.getRandom().nextInt(25)-12,ypsilon-dimension.Y/2+41,dimension.X,70),true);
            ParticleSystem.addParticle(bleskParticle);
        }
    }
}
