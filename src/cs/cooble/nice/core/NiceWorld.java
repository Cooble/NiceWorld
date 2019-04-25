package cs.cooble.nice.core;

import cs.cooble.nice.entity.gui.ScreenEntitySpawner;
import cs.cooble.nice.event.UpdateableObserver;
import cs.cooble.nice.game_stats.CurrectScreenStatManager;
import cs.cooble.nice.Harvester;
import cs.cooble.nice.gameloading.ChunkLoader;
import cs.cooble.nice.gameloading.Saver;
import cs.cooble.nice.gameloading.Settings;
import cs.cooble.nice.graphic.Renderer;
import cs.cooble.nice.inventory.Inventory;
import cs.cooble.nice.input.UserInput;
import cs.cooble.nice.graphic.particles.ParticleSystem;

import java.io.IOException;

/**
 * Created by Matej on 29.6.2015.
 */
public class NiceWorld {
    private static NiceWorld ourInstance = new NiceWorld();

    public static NiceWorld getNiceWorld() {
        return ourInstance;
    }

    private Saver saver;
    private World world;
    private Spawner spawner;
    private Settings settings;
    private ChunkLoader chunkLoader;
    private UpdateableObserver updateableObserver;
    private CurrectScreenStatManager currectScreenStatManager;
    private Renderer renderer;

    private NiceWorld() {
        renderer=new Renderer();
        ScreenEntitySpawner.setRenderer(renderer);
        spawner = new Spawner(null,renderer);
        settings = new Settings();
        saver = new Saver();
        chunkLoader = new ChunkLoader(saver);
        currectScreenStatManager = new CurrectScreenStatManager();
        updateableObserver = new UpdateableObserver();

    }

    public static String getVersion() {
        return "0.2_ALPHA";
    }

    //public========================================================
    public World getWorld() {
        return world;
    }

    Spawner getSpawner(World world) {
        if (spawner.getWorld() == null)
            spawner = new Spawner(world,renderer);
        return spawner;
    }

    public Settings getSettings() {
        return settings;
    }

    public WorldsProvider getWorldProvider() {
        return saver;
    }

    public UpdateableObserver getUpdateableObserver() {
        return updateableObserver;
    }

    public void checkInput() {
        UserInput.checkInput(world, saver);
    }


    //package-local=================================================
    public ChunkLoader getChunkLoader() {
        return chunkLoader;
    }

    public Saver getSaver() {
        return saver;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    void setWorld(World world) {
        this.world = world;
    }

    public void loadItsWorld(String worldName) throws IOException, ClassNotFoundException {
        chunkLoader = new ChunkLoader(saver);
        world = saver.loadWorld(worldName);
        chunkLoader.onUpdateChunks(saver.getWorldPos());
        world.saveMatejFromEntities();
    }

    public void unLoadItsWorld() throws IOException, ClassNotFoundException {
        chunkLoader.unloadAll();
        saver.unLoadWorld();
        saver = new Saver();
        chunkLoader = null;
        System.gc();
    }

    public void onUpdate() {
        world.onUpdate();
        F3.pocetNactenychChunku = chunkLoader.getLoadedChunks().size();
        //Harvester.onUpdate(world);
        Inventory.getInstance().update(world);
        ParticleSystem.update();
        updateableObserver.update();
    }

    public CurrectScreenStatManager getCurrectScreenStatManager() {
        return currectScreenStatManager;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
