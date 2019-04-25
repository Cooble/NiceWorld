package cs.cooble.nice.entity.tileentity;

import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.entity.gui.ScreenEntitySpawner;
import cs.cooble.nice.util.TypPeceni;
import cs.cooble.nice.entity.inventory.PecContainerEntity;
import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.lighting.Light;
import cs.cooble.nice.graphic.lighting.LightingMap;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.items.ItemFuel;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.ItemVypekable;
import cs.cooble.nice.entity.Mover;
import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 19.6.2015.
 */
public class PecTileEntity{
  /*  public PecContainerEntity pecContainerEntity;
    private Light light;

    private boolean opened;
    /**
     * if furnace is on
     */
 /*   private boolean on;
    /**
     * Maximální cas k upecení
     */
/*    private int vypectime;
    /**
     * Jak dlouho uz se pece
     */
 /*   private int currectvypectime;
    /**
     * Jak dlouho hori zmensuje se a pokud nula = znova spalit poleno
     */
 /*   private int currectburntime;
    /**
     * Cas po kterej to bude vyhasinat(jak rychle) cim vetsi tim pomaleji
     */
/*    private int vyhasinattime;


    public static final int INDEXSOURCE =0;
    public static final int INDEXFINISH=1;
    public static final int INDEXPALIVO=2;
    public int radius=300;

    private Container[] containers;



    public PecTileEntity(Location location) {
        super("pec_tile_enity", location);
        pecContainerEntity=new PecContainerEntity();

        containers=pecContainerEntity.containers;
        light=new Light(location,100);

        on=false;
        vypectime=300;
        currectvypectime=0;
        currectburntime=0;
    }

    @Override
    public void onUpdate(World world) {
        if(containers[INDEXSOURCE].hasItemStack()&&(((containers[INDEXPALIVO].hasItemStack()||currectburntime>0))||(containers[INDEXPALIVO].hasItemStack()&&currectburntime>0))){//pokud mame co vypalovat a mam cim to vypalit pokud mame nejake poleno nebo alespon nejake hori
            ItemVypekable item = (ItemVypekable) containers[INDEXSOURCE].getItemStack().ITEM;
            ItemStack vypecenyItem = item.getVypecenyItem(TypPeceni.TAVENI,containers[INDEXSOURCE].getItemStack());

            if(containers[INDEXFINISH].getItemStack()==null||containers[INDEXFINISH].getItemStack().equalsType(vypecenyItem)) {
                on=true;
                if (currectvypectime < vypectime) {
                    currectvypectime++;
                    if(currectburntime>0)
                        currectburntime--;
                    else {
                        ItemFuel fuel=(ItemFuel)containers[INDEXPALIVO].getItemStack().ITEM;
                        currectburntime=fuel.getFuelValue();
                        containers[INDEXPALIVO].getItemStack().addPocet(-1);
                    }
                }
                else {
                    currectvypectime = 0;
                    containers[INDEXSOURCE].getItemStack().addPocet(-1);

                    if(containers[INDEXFINISH].getItemStack()!=null){
                        containers[INDEXFINISH].getItemStack().addPocet(1);
                    }
                    else {
                        containers[INDEXFINISH].setItemStack(vypecenyItem.setPocet(1));
                    }
                }
            }
            else on=false;
        }
        else if(currectvypectime>1){
            on=true;
            if(vyhasinattime>0)
                vyhasinattime--;
            else {
                currectvypectime--;
                vyhasinattime=3;
            }
        }
        else on=false;

        if(Mover.getVzdalenostTo(world.getMatej().getLocation(), location)>radius){
            opened=false;
        }

        setLight(on,world);

    }

    @Override
    public void deInit() {
        super.deInit();
        LightingMap.removeLight(light);
    }

    @Override
    public void init() {
        super.init();
        if (on)
            setLight(true, NiceWorld.getNiceWorld().getWorld());
    }

    private void setLight(boolean on,World world){
        if(on) {
            LightingMap.addLight(light);
            world.getBlockAt(this.location).setMetadata(1);
        }
        else {
            LightingMap.removeLight(light);
            world.getBlockAt(this.location).setMetadata(0);
        }
    }

    public boolean isOn() {
        return on;
    }

    public void switchOpen(World world) {
        opened=!opened;
        if(!opened) {
            ScreenEntitySpawner.hideScreenEntity(pecContainerEntity);
        }
        else {
            ScreenEntitySpawner.showScreenEntity(pecContainerEntity);
        }
    }*/
}
