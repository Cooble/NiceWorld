package cs.cooble.nice.entity;

import cs.cooble.nice.core.*;
import cs.cooble.nice.entity.gui.ScreenEntitySpawner;
import cs.cooble.nice.entity.inventory.ContainerEntity;
import cs.cooble.nice.entity.inventory.MatejContainerEntity;
import cs.cooble.nice.graphic.*;
import cs.cooble.nice.inventory.items.Item;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.Smer;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;

/**
 * Created by Matej on 30.1.2015.
 */
public class Matej implements ILivingEntity, ICollectible, IMoveable {

    //pozy
    private Smer smer = Smer.DOWN;
    private boolean poza = false;
    private boolean jdu = false;
    private boolean openInvetnory = true;
    private int delkaPozy = 0;
    private static final int MAXDELKAPOZY = 6;

    private boolean levo, pravo, nahoru, dolu;
    private boolean shouldGoToTarget;

    //pohyb
    private int XRoh = 0;
    private int YRoh = 0;
    private int sirka = 90;
    private int vyska = 130;
    private StatInteger speed;
    private int posX = NC.WIDTH / 2;
    private int posY = NC.HEIGHT / 2;

    private Location currectChunk = new Location(Integer.MIN_VALUE, Integer.MIN_VALUE);
    public final int radius = 1000;
    private double angle;
    private boolean superSpeed;

    private boolean canMove;
    private int noclicktime;
    private int maxNoClicktime;
    private MatejContainerEntity containerEntity;
    private MatejScreensControler matejScreensControler;

    //stats
    private MatejStats stats;

    private static CreaturesIcons creaturesIcons;

    @Override
    public void writeToNBT(NBT nbt) {
        nbt.setIntenger("posX", posX);
        nbt.setIntenger("posY", posY);
        nbt.setString("smer", smer.name());
        stats.writeToNBT(nbt);
        containerEntity.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBT nbt) {
        posX = nbt.getInteger("posX");
        posY = nbt.getInteger("posY");
        smer = Smer.valueOf(nbt.getString("smer"));
        stats.readFromNBT(nbt);
        containerEntity.readFromNBT(nbt);
    }

    public Matej() {
        canMove = true;
        matejScreensControler = new MatejScreensControler(this);
        maxNoClicktime = 10;
        noclicktime = 0;
        stats = new MatejStats();
        speed = new StatInteger(7, 7);
        superSpeed = false;
        containerEntity = new MatejContainerEntity();
        containerEntity.setMatej(this);
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        creaturesIcons = new CreaturesIcons(ImageManager.ENTITY + "matej");
        creaturesIcons.setRightLeft(3);
        creaturesIcons.setDown(3);
        creaturesIcons.setUp(3);
        creaturesIcons.loadTextures(loader);
    }

    //=STATS============================================================================================================
    private int mezihlad = 0;

    private void hladovet() {
        if (mezihlad == 0) {
            stats.addHunger(jdu ? -2 : -1);
            mezihlad = 110;
        } else
            mezihlad--;
        if (stats.getHunger() == 0)
            umri();

    }

    public MatejStats getStats() {
        return stats;
    }

    //=OVLÁDÁNÍ=========================================================================================================
    public void left(boolean yes) {
        levo = yes;
    }

    public void right(boolean yes) {
        pravo = yes;
    }

    public void up(boolean yes) {
        nahoru = yes;
    }

    public void down(boolean yes) {
        dolu = yes;
    }


    //PAINT==============================================================================================================
    public void render(int x, int y, NGraphics graphics) {
        Graphics g = graphics.g();
        g.setColor(new Color(0x009800));
        XRoh = posX - NC.WIDTH / 2 + 30;
        YRoh = posY - NC.HEIGHT / 2 + 30;
        if (containerEntity.getItemStackInHand() != null)
            if (smer == Smer.UP || smer == Smer.LEFT)
                paintItemInHand(graphics);

        paintPostavicku(graphics);

        if (containerEntity.getItemStackInHand() != null)
            if (smer == Smer.DOWN || smer == Smer.RIGHT)
                paintItemInHand(graphics);
    }

    private void paintItemInHand(NGraphics g) {
        int x = posX - XRoh-this.sirka/2, y = posY - YRoh-this.vyska, sirka = 30, vyska = 30;
        boolean bx = false;
        switch (smer) {
            case RIGHT:
                x += poza ? 55 : 25;
                y += poza ? 55 : 60;
                break;
            case LEFT:
                x += poza ? 30 : 10;
                y += poza ? 58 : 60;
                bx = true;
                break;
            case UP:
                x += 60;
                y += poza ? 60 : 57;
                bx = true;
                break;
            case DOWN:
                y += poza ? 71 : 65;
                break;
            case NONE:
                y += 68;
                break;
        }
        Item item = containerEntity.getItemStackInHand().ITEM;
        IIcon icon = item.getIIcon(containerEntity.getItemStackInHand());
        if (icon == null)
            new Exception("matej icon null ").printStackTrace();
        if (item.shouldBePaintedJinakInHand())
            item.paintItemInHand(x, y, sirka, vyska, smer, poza, g);
        else
            g.drawIcon(item.getIIcon(containerEntity.getItemStackInHand()), x, y, sirka, vyska, bx);

    }

    private void paintPostavicku(NGraphics g) {
        int okolikmensi = 20;
        //Image image = ImageManager.getImage(ImageManager.ENTITY + "stin");
        int posx = posX - XRoh + okolikmensi;
        int posy = posY - YRoh + vyska - 35 + okolikmensi;
       // g.g().drawImage(image, posx, posy, posx + sirka - okolikmensi * 2, posy + 60 - okolikmensi * 2, 0, 0, image.getWidth(), image.getHeight());
        posx = posX - XRoh;
        posy = posY - YRoh;
        int spriteIndex = 0;
        if (jdu)
            spriteIndex = poza ? 2 : 1;
        g.drawIcon(creaturesIcons.getIcon(smer, spriteIndex), posx-sirka/2, posy-vyska, sirka, vyska, smer == Smer.RIGHT);
    }

    private void onPozaUpdate() {
        if (jdu) {
            if (delkaPozy < MAXDELKAPOZY) {
                delkaPozy++;
            } else if (delkaPozy >= MAXDELKAPOZY) {
                delkaPozy = 0;
                poza = !poza;
            }
        }
    }


    //=OSTATNÍ==========================================================================================================
    public void onEntityUpdate(World world) {
        onChunkUpdate();
        onPozaUpdate();
        hladovet();
        refreshNoClickTime();
        refreshSpeed();
        XRoh = posX - NC.WIDTH / 2 + 30;
        YRoh = posY - NC.HEIGHT / 2 + 30;
        Kamera.setXCoord(XRoh);
        Kamera.setYCoord(YRoh);

        int i = 0;
        if (canMove) {
            if (levo) {
                i++;
                smer = Smer.LEFT;
            }
            if (pravo) {
                i++;
                smer = Smer.RIGHT;
            }
            if (dolu) {
                i++;
                smer = Smer.DOWN;
            }
            if (nahoru) {
                i++;
                smer = Smer.UP;
            }
        }
        if (i > 0) {
            shouldGoToTarget = false;
            jdu = true;
            if (i > 1) {
                if ((levo && pravo) || (nahoru && dolu))
                    jdu = false;
                else if (levo && dolu) {
                    angle = (getAngleWithSmer(Smer.LEFT) + getAngleWithSmer(Smer.DOWN)) / 2;
                } else if (pravo && dolu) {
                    angle = (getAngleWithSmer(Smer.RIGHT) + getAngleWithSmer(Smer.DOWN)) / 2;
                } else if (pravo && nahoru) {
                    angle = (getAngleWithSmer(Smer.RIGHT) + getAngleWithSmer(Smer.UP)) / 2;
                } else if (levo && nahoru) {
                    angle = (getAngleWithSmer(Smer.LEFT) + getAngleWithSmer(Smer.NONE)) / 2;
                } else angle = getAngleWithSmer(smer);

            } else angle = getAngleWithSmer(smer);
        } else
            jdu = false;
        if (jdu)
            go();
        else if (shouldGoToTarget) {
            go();
            smer = getSmerWithAngle(angle);
            jdu = true;
        } else if (speed.getValue() > 0)
            go();

        F3.curChunk = currectChunk;

       /* if(jdu){
            Particle particle = new NormalParticle();
            particle.setAtributes(20,10,50,getLocation());
            ParticleSystem.addParticle(particle);
        }*/

    }

    /**
     * Vrati uhel v radianech
     * pokud Smer=Smer.Zadny return 1.5*Math.PI
     *
     * @param smer
     * @return
     */
    private static double getAngleWithSmer(Smer smer) {
        switch (smer) {
            case UP:
                return -Math.PI / 2;
            case RIGHT:
                return 0;
            case DOWN:
                return Math.PI / 2;
            case LEFT:
                return Math.PI;
            default:
                return 1.5 * Math.PI;
        }
    }

    public void setAngle(double angle) {
        this.angle = angle;
        shouldGoToTarget = true;
    }

    private static Smer getSmerWithAngle(double angle) {
        if (angle >= 0 && angle <= Math.PI / 4)
            return Smer.RIGHT;
        if (angle > Math.PI / 4 && angle <= Math.PI / 4 * 3)
            return Smer.DOWN;
        if (angle > Math.PI / 4 * 3 && angle <= Math.PI + 1 / 3 * Math.PI)
            return Smer.LEFT;
        if (angle > Math.PI + 1 / 3 * Math.PI && angle <= Math.PI + Math.PI * 2 / 3)
            return Smer.UP;
        if (angle > Math.PI / 4 * 3)
            return Smer.RIGHT;
        if (angle < 0)
            return Smer.RIGHT;
        return Smer.NONE;
    }

    public void stop() {
        shouldGoToTarget = false;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    //=GETTERY==========================================================================================================
    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public Location getLocation() {
        return new Location(posX, posY);
    }

    public ContainerEntity getContainerEntity() {
        return containerEntity;
    }

    public Rectangle getShape() {
        return new Rectangle(posX-sirka/2, posY-vyska, sirka, vyska);
    }

    @Override
    public void setLocation(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public MatejScreensControler getMatejScreensControler() {
        return matejScreensControler;
    }

    /**
     * @param location
     * @return true pokud na to dosáhne a pokud muze znovu klikat
     */
    public boolean canClick(Location location) {
        if (noclicktime == 0)
            return Mover.getVzdalenostTo(Location.getTrueMouseLocation(getLocation()), location) <= radius;
        return false;
    }


    //=SETTERY==========================================================================================================
    public void setX(int x) {
        this.posX = x;

        XRoh = posX - NC.WIDTH / 2;
        YRoh = posY - NC.HEIGHT / 2;
    }

    public void setY(int y) {
        this.posY = y;

        XRoh = posX - NC.WIDTH / 2;
        YRoh = posY - NC.HEIGHT / 2;
    }

    public void setHasClicked() {
        noclicktime = maxNoClicktime;
    }

    private void go() {
        int speed = superSpeed ? 20 : this.speed.getValue();
        double DX = posX;
        DX += Math.cos(this.angle) * speed;
        double DY = posY;
        DY += Math.sin(this.angle) * speed;
        this.posX = (int) Math.round(DX);
        this.posY = (int) Math.round(DY);
    }


    //=OVERRIDE=========================================================================================================
    @Override
    public ItemStack giveItemStack(ItemStack item) {
        ItemStack vraceno = containerEntity.addToSomeFreeSlot(item);
        if (vraceno == null)
            MPlayer.playSound(MPlayer.RANDOM + "pop");
        return vraceno;
    }

    @Override
    public void throwItemStack(World world, ItemStack item) {
        ItemEntity itemEntity = ItemEntity.build(posX - sirka / 2, posY -vyska/2, smer.ID * Math.PI / 2, jdu ? speed.getValue() : 0, item, this);
        world.getSpawner().spawnEntity(itemEntity);
    }

    @Override
    public boolean canCollect(ItemStack item) {
        return true;
    }

    /**
     * @returns itestack in hand if kein item return item ruka
     */
    public ItemStack getItemStackInHand() {
        return containerEntity.getItemStackInHand();
    }

    @Override
    public void causeDamage(World world, IEntity kdo, int damage) {
        stats.addHealth(-damage);
        if (stats.getHealth() <= 0)
            umri();
    }

    @Override
    public void init(World world) {
        if (containerEntity.getItemStackInHand() != null)
            containerEntity.getItemStackInHand().onItemHeldInHand(world, Item.HoldStatEQUIPPED);
        dolu = false;
        nahoru = false;
        levo = false;
        pravo = false;
        shouldGoToTarget = false;
    }

    @Override
    public void deInit(World world) {
        matejScreensControler = null;//I really dont want to save this
        if (containerEntity.getItemStackInHand() != null)
            containerEntity.getItemStackInHand().onItemHeldInHand(world, Item.HoldStatUNEQUIPPED);
    }
    //=SOUKROMÉ POMOCNÉ METODY==========================================================================================

    /**************************************************************************************************
     * Voláno pro vygenerování či načtení chunků.
     * Stará se o načtení vygenerování, zjistuje, jestli nevesel do jineho chunku if true, pozada LoadedChunks k loadnutí
     */
    private void onChunkUpdate() {
        Location chunkLoc = Location.getChunkLocation(getLocation());
        if (!currectChunk.equals(chunkLoc)) {
            currectChunk = chunkLoc;
            NiceWorld.getNiceWorld().getWorldProvider().onUpdateChunks(chunkLoc);
        }

    }

    private void refreshNoClickTime() {
        if (noclicktime != 0)
            noclicktime--;
    }

    private void refreshSpeed() {
        speed.addValue(jdu ? 1 : -1);
    }

    public void refreshMatejInventory() {
        openInvetnory = !openInvetnory;
        if (openInvetnory) {
            ScreenEntitySpawner.hideScreenEntity(containerEntity);
        } else {
            ScreenEntitySpawner.showScreenEntity(containerEntity);
        }
    }

    private void umri() {
        // Log.println("UMíram");
    }

    public void refreshBoost() {
        superSpeed = !superSpeed;
    }


}
