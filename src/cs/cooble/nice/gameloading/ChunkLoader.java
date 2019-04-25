package cs.cooble.nice.gameloading;

import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.worldgen.WorldGen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matej on 31.1.2015.
 */
public final class ChunkLoader {
    Saver saver;

    //needs to be odd
    private static final int loadedChunkWidth=5;
    private static final int loadedChunkHeight=3;

    public ChunkLoader(Saver saver) {
        this.saver = saver;
    }

    private ArrayList<Location> loadedChunks = new ArrayList<>();

    /**
     * called, when there is a change of central chunk. It loads other unloaded chunks and unload chunks which are to far away from central chunk.
     * Defaultly it is called by Matěj, when he enters new chunk and by NiceWorld at WorldLoadingEvent to load all chunks around matěj.
     *
     * @param centralChunk
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void onUpdateChunks(Location centralChunk) throws IOException, ClassNotFoundException {
        for (int i = loadedChunks.size() - 1; i > 0; i--) {//cisteni nepotrebnych chunku
            Location location = loadedChunks.get(i);
            if (!isIn9(location, centralChunk))
                unloadChunk(location);
        }
        for (int i = -loadedChunkWidth/2; i < loadedChunkWidth/2+1; i++) {
            for (int j = -loadedChunkHeight/2; j < loadedChunkHeight/2+1; j++) {
                Location targetLoc = new Location(i + centralChunk.X, j + centralChunk.Y);
                if (!loadedChunks.contains(targetLoc))
                    loadChunk(targetLoc);
            }
        }
        //Log.println("*po nacteni chunku je zde "+loadedChunks.size()+" nactených chunků*");
    }

    private void unloadChunk(Location location) {
        World world = NiceWorld.getNiceWorld().getWorld();
        TileChunk tilechunk = world.unloadChunk(location);
        List<IEntity> entity = world.unloadEntitiesChunk(location);
        Chunk chunk = new Chunk(entity, tilechunk);
        saver.saveChunk(chunk);
        loadedChunks.remove(location);
    }

    private void loadChunk(Location targetLoc) {
        NiceWorld niceWorld = NiceWorld.getNiceWorld();

        if (saver.existChunk(targetLoc)) {
            Chunk chunk = saver.loadChunk(targetLoc);
            try {
                niceWorld.getWorld().addChunk(chunk.tileChunk,chunk.ENTITY_LIST);
            }catch (Exception e){
                Log.println("corrupted save of chunk", Log.LogType.ERROR);
            }
        } else {
            WorldGen.getInstance().genChunk(niceWorld.getWorld(), targetLoc);
        }
        loadedChunks.add(targetLoc);
    }


    private boolean isIn9(Location currectChunk, Location centralChunk) {
        for (int i = -loadedChunkWidth/2; i < loadedChunkWidth/2+1; i++) {
            for (int j = -loadedChunkHeight/2; j < loadedChunkHeight/2+1; j++) {
                if (currectChunk.equals(new Location(i + centralChunk.X, j + centralChunk.Y)))
                    return true;
            }
        }
        return false;
    }

    public boolean existChunk(Location location) {
        for (Location loadedChunk : loadedChunks) {
            if (loadedChunk.equals(location))
                return true;

        }
        return false;
    }

    private boolean contains(Location location) {
        for (Location l : loadedChunks) {
            if (l.equals(location))
                return true;
        }
        return false;
    }

    public void unloadAll(){
        for (int i = loadedChunks.size() - 1; i >= 0; i--) {
            if(loadedChunks.get(i)!=null)
                unloadChunk(loadedChunks.get(i));
        }
    }

    /**
     * @return list of chunks which are currectly loaded.
     */
    public ArrayList<Location> getLoadedChunks() {
        return loadedChunks;
    }
}
