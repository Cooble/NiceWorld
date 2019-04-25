package cs.cooble.nice.worldgen;

import cs.cooble.nice.blocks.*;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.gameloading.TileChunk;
import cs.cooble.nice.gameloading.UnderBlock;
import cs.cooble.nice.gameloading.UnderBlocks;
import cs.cooble.nice.util.Location;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matej on 31.1.2015.
 */
public class WorldGen {
    private static WorldGen ourInstance = new WorldGen();

    public static WorldGen getInstance() {
        return ourInstance;
    }

    private WorldGen() {
    }

    private final Random R = NC.RANDOM;

    public void genChunk(World worldsProvider, Location chunkLoc) {
      //  Log.println("Generating chunk at "+loc);
        TileChunk tileChunk = new TileChunk(chunkLoc);
        for (int i = 0; i < tileChunk.size(); i++) {
            tileChunk.setUnderBlock(i%3,i/3,R.nextBoolean()? UnderBlocks.forest: UnderBlocks.grass);
        }

        ArrayList<IEntity> solidablelist = new ArrayList<>();

        //todo dodělat spawnování zvírat.
        for (int i = 0; i < 3; i++) {
            Location l = randLoc(chunkLoc);
            Block trava = new BlockGrass();
            trava.setLocation(l.X,l.Y);
            solidablelist.add(trava);
        }
        int pokus = R.nextInt(4);
        Block block=null;

        for (int i = 0; i < 2; i++) {
            Location l = randLoc(chunkLoc);
            switch (pokus) {
                case 0:
                case 1:
                case 2:
                    block = new BlockTree();
                    block.setLocation(l.X,l.Y);
                    break;
                case 3:
                    block = new BlockGrass();
                    block.setLocation(l.X,l.Y);
                    break;
            }
            solidablelist.add(block);

        }
        if (pokus != 3) {
            addKlacek(tileChunk, solidablelist, chunkLoc);
            addSutr(tileChunk, solidablelist, chunkLoc);
        }
        worldsProvider.addChunk(tileChunk, solidablelist);

    }

    private Location randLoc(Location chunkLoc) {
        int resolution = 80;

        int r = NC.CHUNK_SIZE/resolution;

        int x = R.nextInt(r)*resolution;
        int y = R.nextInt(r)*resolution;
        x += chunkLoc.X * NC.CHUNK_SIZE;
        y += chunkLoc.Y * NC.CHUNK_SIZE;
        return new Location(x, y);
    }

    private void addKlacek(TileChunk tileChunk, ArrayList<IEntity> tileBlocks, Location loc) {
        int o = NC.RANDOM.nextInt(2) - 1;
        for (int i = 0; i < o; i++) {
            Location l = randLoc(loc);
            Block klacek = new BlockStick();
            klacek.setLocation(l.X,l.Y);
            tileBlocks.add(klacek);
        }
    }

    private void addSutr(TileChunk tileChunk, ArrayList<IEntity> tileBlocks, Location loc) {
        int o = NC.RANDOM.nextInt(4) - 2;
        for (int i = 0; i < o; i++) {
            Location l = randLoc(loc);
            Block klacek = new BlockRock();
            klacek.setLocation(l.X,l.Y);
            tileBlocks.add(klacek);
        }
    }

}
