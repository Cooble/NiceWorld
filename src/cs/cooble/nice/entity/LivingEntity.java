package cs.cooble.nice.entity;

import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.util.Smer;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;


/**
 * Created by Matej on 28.6.2015.
 */
public class LivingEntity implements ILivingEntity {

    protected double posX,posY;
    protected Location dimension;
    protected StatInteger health;
    protected Stat speed;
    protected double angle;
    protected Smer smer;
    private boolean markedDeath;
    protected IIcon iconStin;

    public LivingEntity(){
        health=new StatInteger(10,10);
        dimension=Location.blank();
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        iconStin=loader.getIcon(ImageManager.ENTITY+"stin");
    }

    @Override
    public void causeDamage(World world,IEntity kdo, int damage) {
        health.addValue(-damage);
        if(health.isMin()) {
            onDeath(world, kdo);
            markDeath();
        }
    }

    @Override
    public void onEntityUpdate(World world) {
        if(isMarkedDeath()) {
            world.getSpawner().despawnEntity(this);
        }
    }

    @Override
    public final int getX() {
        return (int) posX;
    }

    @Override
    public final int getY() {
        return (int) posY;
    }

    public final Location getLocation(){
        return new Location(getX(),getY());
    }

    @Override
    public Rectangle getShape() {
        return new Rectangle(getX(),getY(),dimension.X,dimension.Y);
    }

    @Override
    public void setLocation(int x, int y) {
        this.posX=x;
        this.posY=y;
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        g.setColor(Color.black);
        g.fillRect(getX(),getY(),dimension.X,dimension.Y);
    }

    protected final void goForward(){
        if(this.speed.getValue()!=0) {
            this.posX += Math.cos(this.angle) * this.speed.getValue();
            this.posY += Math.sin(this.angle) * this.speed.getValue();
        }
    }
    protected static Smer getSmerWithAngle(double angle){
        int uhel = (int)Math.toDegrees(angle);
        if(uhel<=45&&uhel>0)
            return Smer.RIGHT;
        if(uhel<=0&&uhel>315)
            return Smer.RIGHT;
        if(uhel<=90&&uhel>45)
            return Smer.DOWN;
        if(uhel<=135&&uhel>90)
            return Smer.DOWN;
        if(uhel<=180&&uhel>135)
            return Smer.LEFT;
        if(uhel<=225&&uhel>180)
            return Smer.LEFT;
        if(uhel<=270&&uhel>225)
            return Smer.UP;
        if(uhel<=315&&uhel>270)
            return Smer.UP;
        if(uhel<=360&&uhel>315)
            return Smer.RIGHT;
        return Smer.RIGHT;

    }
    public final void markDeath(){
        markedDeath=true;
    }
    public final boolean isMarkedDeath() {
        return markedDeath;
    }

    @Override
    public void writeToNBT(NBT nbt) {
        nbt.setString("className",getClass().getName());
    }

    @Override
    public void readFromNBT(NBT nbt) {

    }
}
