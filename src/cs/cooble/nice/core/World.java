package cs.cooble.nice.core;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.entity.*;
import cs.cooble.nice.gameloading.ChunkProvider;
import cs.cooble.nice.gameloading.TileChunk;
import cs.cooble.nice.gameloading.UnderBlock;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Je t¯Ìda ve kterÈ jsou uloûeny naËtenÈ entity, vöechny tileentity svÏta (vûdy naËtenÈ) a naËtenÈ chunky.
 * Star· se o naËtenÌ chunk˘ a unload chunk˘, implementuje ChunkProvider.
 * NutnÈ volat metodu onUpdate(), kter· updatuje time a entity.
 * Pro ukl·d·nÌ m· subt¯Ìdu WorldSavedData implementujÌcÌ Serializable, kter· uchov·v· vÏci, kterÈ se neukl·dajÌ do chunk˘.
 * (nap¯: TileEntity(protoûe musÌ loadnuta aù je v kterÈmkoli chunku), time, name, )
 */
public final class World implements ChunkProvider {

    private TimeManager timeManager;
    private String name;
    private ArrayList<TileChunk> tileChunks = new ArrayList<>();
    private ArrayList<IEntity> entities = new ArrayList<>();
    private final Random random = new Random();
    private Matej matej;

    //=================WORLD_SAVED_DATA THINGS=======================================

    /**
     * @param worldSavedData
     * @param fresh          if this world non exist before (used to add new Matej if necessary)
     */
    private World(WorldSavedData worldSavedData, boolean fresh) {
        this.name = worldSavedData.name;
        this.timeManager = worldSavedData.timeManager;
        if (fresh || !saveMatejFromEntities()) {
            matej = new Matej();
            matej.loadTextures(NiceWorld.getNiceWorld().getRenderer().getAtlas());
            getSpawner().spawnEntity(matej);
        }

    }

    /**
     * @param worldSavedData
     * @return a new world made from atributes in worldsaveddata
     */
    public static World getLoadedWorld(WorldSavedData worldSavedData) {
        return new World(worldSavedData, false);
    }

    /**
     * @param name
     * @return new world with default atributes and custom name
     */
    public static World getNewWorld(String name) {

        WorldSavedData worldSavedData = new WorldSavedData();
        worldSavedData.timeManager = new TimeManager();
        worldSavedData.name = name;
        return new World(worldSavedData, true);
    }

    /**
     * @return all atributes from which it can be remade
     */
    public WorldSavedData getMyWorldSavedData() {
        WorldSavedData worldSavedData = new WorldSavedData();
        worldSavedData.name = name;
        worldSavedData.timeManager = timeManager;
        return worldSavedData;
    }
    //#############################################################################


    //==================ENTITIES=====================================================

    public void addEntity(IEntity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(IEntity entity) {
        entities.remove(entity);
    }


    public ArrayList<IEntity> getNearEntities(int radius, Location location,@Nullable CheckableEntity checkableEntity) {
        ArrayList<IEntity> listik = new ArrayList<>();

        for (IEntity e : entities) {
            Location eLoc = new Location(e.getX(), e.getY());
            if (!location.equals(eLoc))
                if (Mover.getVzdalenostTo(location, eLoc) <= radius && (checkableEntity==null||checkableEntity.isValid(e)))
                    listik.add(e);
        }
        return listik;
    }

    /**
     * vracÌ nejbliûöÌ entitu v danÈm radiusu entity a splÚujÌcÌ danÈ podmÌnky podle CheckableEntity.
     *
     * @param radius
     * @param checkable
     * @return
     */
    public IEntity getNearestEntity(int radius, IEntity entity, @Nullable CheckableEntity checkable) {
        Location location = new Location(entity.getX(), entity.getY());
        int currad = radius;
        IEntity nejblizsi = null;
        for (IEntity e : entities) {
            Location eLoc = e.getLocation();
            double distance = Mover.getVzdalenostTo(location, eLoc);
            if (distance <= currad) {
                if (!e.equals(entity) && (checkable==null||checkable.isValid(entity))) {
                    nejblizsi = e;
                    currad = (int)distance;
                }
            }
        }
        return nejblizsi;
    }


    public Matej getMatej() {
        return matej;
    }

    /**
     * Nacte MatÏje z entit, kterÈ jsou ve worldlistu
     */
    public boolean saveMatejFromEntities() {
        boolean success = false;
        for (IEntity e : entities) {
            if (e instanceof Matej) {
                matej = (Matej) e;
                success = true;
                break;
            }
        }
        return success;
    }

    /**
     * Vr·tÌ list s entitami v danÈm chunku
     *
     * @return list
     */
    public List<IEntity> getEntitiesInChunk(Location chunk) {
        ArrayList<IEntity> output = new ArrayList<>();
        for (int i = entities.size() - 1; i >= 0; i--) {
            IEntity e = entities.get(i);
            if (NC.isInChunk(e.getX(), e.getY(), chunk)) {
                output.add(e);
            }
        }
        return output;
    }


    public Block getBlockAt(Location location) {
        for (IEntity entity : entities) {
            if (entity.getLocation().equals(location)&&entity.isBlock())
                return (Block) entity;
        }
        return null;
    }

    public void removeBlock(Block block) {
        removeEntity(block);
    }

    public void addBlock(Block block) {
        addEntity(block);
    }


    //#############################################################################

    public Spawner getSpawner() {
        return NiceWorld.getNiceWorld().getSpawner(this);
    }

    public Random getRandom() {
        return random;
    }

    /**
     * @param worldLoc lokace kliknutÌ(svÏtov· = nap¯. 5126)
     * @param right    click
     */
    public boolean onClickedTo(Location worldLoc, boolean right) {
        //finding out what chunk it is
        for (IEntity entity : entities) {
            if (entity.getShape().contains(worldLoc.X, worldLoc.Y)) {
                ItemStack inHand = matej.getItemStackInHand();
                if(inHand==null||!inHand.ITEM.onItemUse(this, inHand, matej, entity,right)){
                    if(entity instanceof IUserInteraction){
                        ((IUserInteraction) entity).click(this,matej,inHand,right);
                    }
                }
                matej.setHasClicked();
                return true;
            }
        }
        Location chunkLoc = transformToChunkLoc(worldLoc);
        // Checking if clicked on solid block
        for (TileChunk chunk : tileChunks) {
            if (chunk.poziceChunku.equals(chunkLoc)) {
               /* if (chunk.onClicked(worldLoc, right, this, matej)) {
                    matej.setHasClicked();
                    return true;
                }*/
                return false;
            }
        }
        return false;
    }

    /**
     * Ze svÏtovÈ pozice zÌsk· pozici chunku ve kterÈm se svÏt poz nach·zÌ
     *
     * @param worldLoc
     * @return
     */
    private Location transformToChunkLoc(Location worldLoc) {
        int chunkX = (int) Math.floor(worldLoc.X / ((double) NC.CHUNK_SIZE));
        int chunkY = (int) Math.floor(worldLoc.Y / ((double) NC.CHUNK_SIZE));
        return new Location(chunkX, chunkY);
    }

    @Override
    public TileChunk getChunk(Location chunk) {
        for (TileChunk chunk1 : tileChunks) {
            if (chunk1.poziceChunku.equals(chunk)) {
                return chunk1;
            }
        }
        Log.println("[World::getChunk()] chci zÌskat tileChunk " + chunk + " ale neexistuje, vracÌm nov˝", Log.LogType.ERROR);
        return new TileChunk(chunk);
    }

    public UnderBlock getUnderBlock(Location location) {
        Location chunkLoc = new Location(location.X/NC.CHUNK_TILES,location.Y/NC.CHUNK_TILES);
        Location tileLoc = new Location(location.X%NC.CHUNK_TILES,location.Y%NC.CHUNK_TILES);
        for (TileChunk chunk1 : tileChunks) {
            if (chunk1.poziceChunku.equals(chunkLoc)) {
                return chunk1.getUnderBlock(tileLoc.X,tileLoc.Y);
            }
        }
        return null;
    }
    public UnderBlock getUnderBlock(int x,int y) {
     return getUnderBlock(new Location(x,y));
    }



    @Override
    public TileChunk unloadChunk(Location chunk) {
        for (int i = 0; i < tileChunks.size(); i++) {
            TileChunk tileChunk = tileChunks.get(i);
            if (tileChunk.poziceChunku.equals(chunk)) {
                tileChunks.remove(i);
                NiceWorld.getNiceWorld().getRenderer().removePaintable(tileChunk);
                return tileChunk;
            }
        }
        return null;
    }

    @Override
    public List<IEntity> unloadEntitiesChunk(Location chunk) {
        List<IEntity> out = new ArrayList<>();
        for (int i = entities.size() - 1; i >= 0; i--) {
            IEntity e = entities.get(i);
            if (NC.isInChunk(e.getX(), e.getY(), chunk)) {
                getSpawner().despawnEntity(e);
                out.add(e);
            }
        }
        return out;
    }

    @Override
    public void addChunk(TileChunk tileChunk, @Nullable List<IEntity> entityList) {
        this.tileChunks.add(tileChunk);
        NiceWorld.getNiceWorld().getRenderer().registerPaintable(tileChunk);
        F3.pocetNactenychTileChunku = tileChunks.size();
        if(entityList!=null)
        for (IEntity e : entityList)
            getSpawner().spawnEntity(e);
    }


    /**
     * called for update time and entities
     */
    public void onUpdate() {
        timeManager.update();
        if (entities.size() != 0)
            for (int i = entities.size() - 1; i >= 0; i--) {
                entities.get(i).onEntityUpdate(this);
            }
    }


    public String getWorldName() {
        return name;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }


    /**
     * !Warning really hard for CPU!
     *
     * @param worldLoc
     * @param radius
     * @return all blocks in radius of worldLoc
     */
    public ArrayList<Block> getBlocksInRadius(Location worldLoc, int radius) {
        ArrayList<Block> output = new ArrayList<>();
        for (IEntity e : entities) {
            if (e.isBlock() && Mover.isInRadius(worldLoc, e.getLocation(), radius))
                output.add((Block) e);
        }
        return output;
    }

    public interface CheckableEntity {
        boolean isValid(IEntity e);
    }

    public static class TimeManager implements Serializable {
        private double time;
        private final int HODINA = 60;
        private final int MAX_TIME = 24 * HODINA;

        private long worldTicks;

        private TimeManager() {
            setHodin(8);
        }

        private void update() {
            worldTicks++;
            time += 0.1;
            if (time >= MAX_TIME)
                time = 0;
        }


        public double getHours() {
            return time / HODINA;
        }

        public int getMinut() {
            return (int) (((getHours() - (int) getHours())) * getHodina());
        }

        public int getTime() {
            return (int) time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public void setHodin(double hodin) {
            time = (double) HODINA * hodin;
           // Log.println("hofdin " + time);
        }

        public void addHodin(double hodin) {
            time += (double) HODINA * hodin;
        }

        public int getMaxTime() {
            return MAX_TIME;
        }

        public int getHodina() {
            return HODINA;
        }

        public long getWorldTicks() {
            return worldTicks;
        }
    }

    public static class WorldSavedData implements Serializable {
        private String name;
        private TimeManager timeManager;
    }
}
