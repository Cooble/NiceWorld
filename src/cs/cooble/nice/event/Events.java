package cs.cooble.nice.event;

import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.blocks.BlockTemplateParser;
import cs.cooble.nice.chat.command.Commands;
import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.entity.gui.GuiEntity;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.game_stats.*;
import cs.cooble.nice.gameloading.UnderBlock;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.inventory.CraftingRegistry;
import cs.cooble.nice.inventory.items.Item;
import cs.cooble.nice.input.UserInput;
import cs.cooble.nice.inventory.items.ItemTemplateParser;
import cs.cooble.nice.logger.Log;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Matej on 3.2.2015.
 */
public class Events {

    private static CurrectScreenStatManager gameStatManager = NiceWorld.getNiceWorld().getCurrectScreenStatManager();

    private static final String EXCEPTION_LOADING_WORLD = "Exception during loading the world: ";

    public static void onGameStarted() {
        Log.println("Game started");
        Log.println("============");

        gameStatManager.setScreenStat(new IntroStat());
        NiceWorld.getNiceWorld().getSaver().makeDefaultFoldersFiles();

        CraftingRegistry.registerRecipes();
        GameRegistry.getInstance().registerVannila();
        ItemTemplateParser.load();
        Commands.getInstance().registerCommands();
        BlockTemplateParser.load();
        UserInput.onProgramStarted();
        loadTextures();

    }

    public static void loadTextures() {
        TextureLoader loader = NiceWorld.getNiceWorld().getRenderer().getAtlas();
        for (Block block : GameRegistry.getInstance().getBlocks())
            block.loadTextures(loader);

        for (UnderBlock block : GameRegistry.getInstance().getUnderBlocks())
            block.loadTextures(loader);

        for (Item item : GameRegistry.getInstance().getItems())
            item.loadTextures(loader);

        for (IEntity entity : GameRegistry.getInstance().getEntities())
            entity.loadTextures(loader);

        for (GuiEntity gui : GameRegistry.getInstance().getGuis())
            gui.loadTextures(loader);




        //baking into big map
        NiceWorld.getNiceWorld().getRenderer().getAtlas().bake();

    }

    public static void onWorldLoaded(String worldName) {
        try {
            NiceWorld.getNiceWorld().loadItsWorld(worldName);
            gameStatManager.setScreenStat(new GamesStat());
        } catch (Exception e) {
            Log.println(EXCEPTION_LOADING_WORLD + e.getMessage(), Log.LogType.ERROR);
            e.printStackTrace();
            NiceWorld.getNiceWorld().getRenderer().clearAll();
            gameStatManager.setScreenStat(new ErrorStat(e));
        }
    }

    public static void onWorldUnLoaded() {
        try {
            NiceWorld.getNiceWorld().unLoadItsWorld();
            gameStatManager.setScreenStat(new WorldChoiceStat());
            Log.println("(Unloading)WORLD WAS UNLOADED");
        } catch (Exception e) {
            Log.println(EXCEPTION_LOADING_WORLD + e.getMessage(), Log.LogType.ERROR);
            e.printStackTrace();
            gameStatManager.setScreenStat(new ErrorStat(e));
        }
    }

    public static void onGameQuited() {
        if (gameStatManager.getPureGameStatName().equals(CurrectScreenStatManager.GAME))
            onWorldUnLoaded();
        NiceWorld.getNiceWorld().getSaver().saveSettings();
        NC.shouldExitWithoutEvent = true;//aby se to nezacyklilo exit-> onclosing -> ongamequited ->exit....
        Log.println("Game quitted");
        Log.println("============");
        System.exit(0);
    }
}
