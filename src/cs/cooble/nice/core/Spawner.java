package cs.cooble.nice.core;

import cs.cooble.nice.graphic.Renderer;
import cs.cooble.nice.entity.IEntity;

/**
 * Created by Matej on 1.2.2015.
 */
public final class Spawner {
    private final Renderer CANVAS;
    private final World world;

    Spawner(World world, Renderer renderer) {
        this.world = world;
        this.CANVAS = renderer;
    }

    World getWorld() {
        return world;
    }

    //=ENTITY===========================================================================================================
    public void spawnEntity(IEntity e) {
        e.init(world);
        world.addEntity(e);
        e.loadTextures(CANVAS.getAtlas());
        CANVAS.registerPaintable(e);


    }

    public void despawnEntity(IEntity e) {
        CANVAS.removePaintable(e);
        world.removeEntity(e);
        e.deInit(world);
    }
}
