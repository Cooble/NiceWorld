package cs.cooble.nice.gameloading;

import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.entity.IEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matej on 3.2.2015.
 * Its designed as box for saving
 */
public class Chunk {
    public final List<IEntity> ENTITY_LIST;
    public final TileChunk tileChunk;
    public final int posX;
    public final int posY;

    public Chunk(List<IEntity> entity_list, TileChunk chunk) {
        ENTITY_LIST = entity_list;
        tileChunk = chunk;
        this.posX = tileChunk.poziceChunku.X;
        this.posY = tileChunk.poziceChunku.Y;
    }
    public final Location getLocation() {
        return new Location(posX, posY);
    }

    public NBT serialize() {
        NBT nbt = new NBT();
        for (int i = 0; i < ENTITY_LIST.size(); i++) {
            IEntity entity = ENTITY_LIST.get(i);
            NBT entityNBT = new NBT();
            entity.writeToNBT(entityNBT);
            nbt.setNBT("entity_" + i, entityNBT);
        }
        nbt.setIntenger("posX", posX);
        nbt.setIntenger("posY", posY);
        nbt.setNBT("tileChunk",tileChunk.serialize());
        return nbt;
    }

    public static Chunk deserialize(NBT nbt) {
        List<IEntity> entities = new ArrayList<>();
        int index = 0;
        NBT entityNBT = nbt.getNBT("entity_" + index);
        while (entityNBT != null) {
            entities.add(GameRegistry.getInstance().buildEntity(entityNBT));
            index++;
            entityNBT = nbt.getNBT("entity_" + index);
        }

        return new Chunk(entities, TileChunk.deserialize(nbt.getNBT("tileChunk")));
    }
}
