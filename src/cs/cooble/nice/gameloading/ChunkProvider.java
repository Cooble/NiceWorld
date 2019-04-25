package cs.cooble.nice.gameloading;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.util.Location;

import java.util.List;

/**
 * Created by Matej on 28.6.2015.
 */
public interface ChunkProvider {

    /**
     * @param chunk
     * @return chunk which is on this location
     */
    TileChunk getChunk(Location chunk);

    /**
     * calls deinit() of every tileblock in chunk and removes it from renderer
     * removes from world
     * @param chunk
     * @return tilechunk ready to be saved
     */
    TileChunk unloadChunk(Location chunk);

    /**
     * calls deInit() of every entity in chunk and removes it from renderer
     * removes from world
     * @param chunk
     * @return entities ready to be saved
     */
    List<IEntity> unloadEntitiesChunk(Location chunk);



    /**
     * adds chunk
     * @param tileChunk
     */
    void addChunk(TileChunk tileChunk,@Nullable List<IEntity> entities);
}
