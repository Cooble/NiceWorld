package cs.cooble.nice.entity;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;

import java.awt.*;


/**
 * Tato třída je vlastně entitová přepravka na Item(doručí object majiteli(ten co instanceof ICollectible)).
 */
public class ItemEntity implements IEntity {
    protected int posX, posY;
    private double DX, DY;
    protected int sirka, vyska;
    protected double angle;
    protected double speed, maxspeed;
    protected int radius = 110;
    protected boolean isMoving = true;
    protected IEntity majitel = null;
    protected IEntity neMajitel = null;
    private int nemajitelCas, maxNemajitelCas = 60;

    protected ItemStack itemInv;

    public ItemEntity() {
        sirka = 30;
        vyska = 30;
        setRandomAngle();
        speed = 5;
        maxspeed = speed;
    }

    public void setItemstack(ItemStack itemInv) {
        this.itemInv = itemInv;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setNeMajitel(IEntity neMajitel) {
        this.neMajitel = neMajitel;
    }

    //=GETTERS==========================================================================================================
    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public int getVyska() {
        return vyska;
    }

    public Rectangle getShape() {
        return new Rectangle(posX, posY, vyska, sirka);
    }

    @Override
    public void setLocation(int x, int y) {
        this.DX = x;
        this.DY = y;
        setX(x);
        setY(y);
    }

    public ItemStack getItemStack() {
        return itemInv;
    }

    //=SETTERS==========================================================================================================
    public void setX(int x) {
        posX = x;
    }

    public void setY(int y) {
        posY = y;
    }

    //=OSTATNI==========================================================================================================
    public void onEntityUpdate(World world) {
        onNemajitelUpdate();
        if (isMoving)
            if (speed > 0.0) {
                speed -= maxspeed / 50;
                goForward();
            } else isMoving = false;
        goToNearestMajitel(world);
    }

    public void render(int x, int y, NGraphics g) {
        IIcon icon = itemInv.ITEM.getIIcon(itemInv);
        if (icon != null) {
            g.drawIcon(itemInv.ITEM.getIIcon(itemInv), posX - x, posY - y, sirka, vyska);
            if (itemInv.getPocet() > 1)
                g.drawIcon(itemInv.ITEM.getIIcon(itemInv), posX - x + sirka / 10 * 2, posY - y + vyska / 10 * 2, sirka, vyska);
        } else new Exception("iicon is null " + itemInv.ITEM.toString());
    }

    public void onClicked(IEntity e) {
        majitel = e;
    }

    //=SOUKROME=========================================================================================================
    protected void goForward() {
        this.DX += Math.cos(this.angle) * this.speed;
        this.DY += Math.sin(this.angle) * this.speed;
        this.posX = (int) Math.round(this.DX);
        this.posY = (int) Math.round(this.DY);
    }

    protected void setRandomAngle() {
        angle = Math.toRadians(NC.RANDOM.nextInt(360)) - Math.PI;
    }

    protected void goToNearestMajitel(World world) {
        if (majitel != null) {
            goToMajitel(world, majitel);
        } else {
            IEntity soused = world.getNearestEntity(radius, this,null);
            if (soused != null) {
                if (soused instanceof ICollectible) {
                    if (((ICollectible) soused).canCollect(itemInv))
                        goToMajitel(world, soused);
                }

            }
        }

    }

    protected void goToMajitel(World world, IEntity e) {
        if (e != null) {
            if (!e.equals(neMajitel) && e instanceof ICollectible) {
                if (speed < 12)
                    speed += 0.8;
                isMoving = true;
                Location souLoc = new Location(e.getX() + 1, e.getY() + 1);
                Location mujLoc = new Location(posX, posY);
                angle = Mover.getAngleTo(mujLoc, souLoc);
                if (Mover.getVzdalenostTo(mujLoc, souLoc) < 14) {//Smazání itemu + podání ho majiteli.
                    if (mujLoc.equals(souLoc))//pokud je majitel a item na stejné pozici(nesmí se prece smazat majitel)
                        posX++;
                    ((ICollectible) e).giveItemStack(itemInv);
                    world.getSpawner().despawnEntity(this);
                }
            }
        }
    }

    protected void onNemajitelUpdate() {
        if (nemajitelCas < maxNemajitelCas)
            nemajitelCas++;
        else if (nemajitelCas == maxNemajitelCas) {
            nemajitelCas = maxNemajitelCas + 1;
            neMajitel = null;
        }
    }


    @Override
    public void writeToNBT(NBT nbt) {
        nbt.setIntenger("posX", posX);
        nbt.setIntenger("posY", posY);
        if (itemInv != null) {
            NBT itemNBT = new NBT();
            itemInv.writeToNBT(itemNBT);
            nbt.setNBT("itemstack", itemNBT);
        }
    }

    @Override
    public void readFromNBT(NBT nbt) {
        posX = nbt.getInteger("posX");
        posY = nbt.getInteger("posY");
        NBT itemNBT = nbt.getNBT("itemstack");
        if(itemNBT!=null){
            itemInv=new ItemStack(itemNBT);
        }
    }

    public static ItemEntity build(int posX, int posY, ItemStack itemStack) {
        ItemEntity out = new ItemEntity();
        out.setLocation(posX, posY);
        out.setItemstack(itemStack);
        return out;
    }

    public static ItemEntity build(int posX, int posY, double angle, int speed, ItemStack item, IEntity neMajitel) {
        ItemEntity out = new ItemEntity();
        out.setLocation(posX, posY);
        out.setAngle(angle);
        out.setSpeed(speed);
        out.setItemstack(item);
        out.setNeMajitel(neMajitel);
        return out;
    }
}
