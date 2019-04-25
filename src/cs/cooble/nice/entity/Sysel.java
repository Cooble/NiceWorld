package cs.cooble.nice.entity;

import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.blocks.Blocks;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.CreaturesIcons;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.Items;
import cs.cooble.nice.util.Smer;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.util.Location;

import java.util.ArrayList;

/**
 * Created by Matej on 1.2.2015.
 */
public class Sysel extends LivingEntity implements ICollectible {

    private int chasing = 0;
    private StatInteger jdu;
    private StatInteger energy;
    private boolean isMoving;
    private boolean poza;
    private boolean inNora;
    private boolean isGoingToNora;
    private Location noraLoc;

    private CreaturesIcons creaturesIcons;

    public Sysel() {
        super();
        health.setMaxValue(15);
        health.setValue(15);
        jdu=new StatInteger(30);
        energy=new StatInteger(100);
        speed=new Stat(13);
        smer=getSmerWithAngle(angle);
        dimension=new Location(60,60);
        inNora=false;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        super.loadTextures(loader);
        creaturesIcons = new CreaturesIcons(ImageManager.ENTITY+"sysel");
        creaturesIcons.setUp(2);
        creaturesIcons.setDown(2);
        creaturesIcons.setRightLeft(2);
        creaturesIcons.loadTextures(loader);
    }

    /**
     * Called when is someone near.
     * @param listik who all is near
     */
    private void onImpact(World world,ArrayList<IEntity> listik) {
        for (IEntity tvor : listik) {
            if (tvor instanceof Matej) {

                ArrayList<Block> tileBlocks = world.getBlocksInRadius(this.getLocation(),600);
                for (Block tileBlock : tileBlocks) {
                    if (tileBlock.getID().equals(Blocks.nora.getID())) {
                        noraLoc = tileBlock.getLocation();
                        angle = Mover.getAngleTo(this.getLocation(), noraLoc);
                        isGoingToNora = true;
                        break;
                    }
                }



                if(!isGoingToNora)//nemuze se schovat do nory
                    angle = Mover.getAngleTo(this.getLocation(), tvor.getLocation()) + Math.PI + NC.RANDOM.nextDouble();
                isMoving = true;
                speed.setValue(10 + NC.RANDOM.nextInt(10) - 5);
                smer = getSmerWithAngle(angle);
                onSyselIsChasing();

            } else if (tvor instanceof ItemEntity) {
                if (canCollect(((ItemEntity) tvor).getItemStack())) {
                    angle = Mover.getAngleTo(getLocation(), tvor.getLocation());
                    isMoving = true;
                    speed.setValue(10 + NC.RANDOM.nextInt(10) - 5);
                    smer = getSmerWithAngle(angle);
                }
            }
        }


    }

    private int noraTime;//jak dlouho je v nore
    @Override
    public void onEntityUpdate(World world) {
        super.onEntityUpdate(world);
        if(!inNora) {
            chasingUpdate();
            pozaUpdate();


            if (isGoingToNora) {
                toNoraUpdate();
            }
            if (!isMoving) {
                ArrayList<IEntity> listik = world.getNearEntities(300, getLocation(),null);
                if (!listik.isEmpty()) {
                    onImpact(world, listik);
                }
            }

            jdu.addValue(isMoving ? 1 : 0);
            speed.addValue(isMoving ? 1 : -1);
            if (speed.getValue() != 0 && !jdu.isMax()) {
                goForward();
            } else {
                jdu.setValue(0);
                isMoving = false;
            }
        }
        else {
            noraTime++;
            if(noraTime%100==0){//kazdych 100 ticku zjistime zdali je vzduch kolem nory cisty
                if(world.getRandom().nextBoolean()) {
                    ArrayList<IEntity> listik = world.getNearEntities(300, getLocation(), e -> e instanceof Matej);//není tu nejaky zlý matej co by mì chtel ulovit:D
                    if (listik.isEmpty()) {
                        inNora = false;
                        isMoving=true;
                        speed.setValue(1);
                        noraTime=0;
                        angle+=world.getRandom().nextDouble()+world.getRandom().nextDouble();
                    }
                }

            }
        }
    }

    int pozicka=0;
    private void pozaUpdate() {
        pozicka++;
        if(pozicka>10) {
            poza = !poza;
            pozicka=0;
        }
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        if(!inNora) {//pokud se neschovava v nore
            g.drawIcon(iconStin, getX() - x, getY() - y + dimension.Y * 4 / 5, dimension.X, 15);
            g.drawIcon(creaturesIcons.getIcon(smer,poza?1:0),getX() - x, getY() - y, dimension.X, dimension.Y,smer== Smer.RIGHT);
        }
    }

    protected void onSayed() {
        MPlayer.playSound(MPlayer.MOB + "sysel_say" + NC.getRandomKoncovka(4));
    }

    protected int setRandomSayProdleva() {
        return NC.RANDOM.nextInt(480)+20;
    }

    private void chasingUpdate(){
        if(chasing!=0)
            chasing--;
    }
    private void onSyselIsChasing(){
        chasing+=3;
        if(chasing>200){
            MPlayer.playSong(MPlayer.MUSIC+"song_sysel_chasing");
            chasing=0;
        }
    }
    private void toNoraUpdate(){
        if(Mover.isInRadius(this.getLocation(), noraLoc, 10)){
            inNora=true;
            speed.setValue(0);
            isMoving=false;
            isGoingToNora=false;
        }
    }

    @Override
    public void causeDamage(World world,IEntity entity,int damage) {
        super.causeDamage(world,entity,damage);
        angle = Mover.getAngleTo(getLocation(), entity.getLocation()) + Math.PI+ 2*NC.RANDOM.nextDouble();
        isMoving = true;
        smer=getSmerWithAngle(angle);
        onSyselIsChasing();
        onSayed();
    }

    @Override
    public void onDeath(World world,IEntity kdo) {
        MPlayer.playSound(MPlayer.MOB+"sysel_death");

        world.getSpawner().spawnEntity(ItemEntity.build(getX(), getY(), new ItemStack(Items.syselMaso, 1)));
    }

    @Override
    public boolean canCollect(ItemStack item) {
        return item.equalsType(Blocks.trava.getItemFromBlock());
    }

    @Override
    public ItemStack giveItemStack(ItemStack item) {
        if(isMoving){
            isMoving=false;
            energy.setValue(0);
        }
        return null;
    }
}
