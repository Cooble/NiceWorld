package cs.cooble.nice.core;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.entity.gui.GuiEntity;
import cs.cooble.nice.entity.IEntity;
import cs.cooble.nice.entity.KillerBot;
import cs.cooble.nice.entity.Matej;
import cs.cooble.nice.entity.Sysel;
import cs.cooble.nice.entity.inventory.*;
import cs.cooble.nice.entity.tileentity.PecTileEntity;
import cs.cooble.nice.gameloading.UnderBlock;
import cs.cooble.nice.gameloading.UnderBlocks;
import cs.cooble.nice.inventory.items.*;
import cs.cooble.nice.blocks.*;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Matej on 5.4.2015.
 */
public class GameRegistry {
    private static GameRegistry ourInstance = new GameRegistry();

    public static GameRegistry getInstance() {
        return ourInstance;
    }

    private GameRegistry() {}

    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Class> entities = new ArrayList<>();
    private ArrayList<Class> guis = new ArrayList<>();
    private ArrayList<UnderBlock> underBlocks = new ArrayList<>();

    public void registerItem(Item... items) {
        Collections.addAll(this.items, items);
    }

    /**
     *
     * @param entity class which extends IEntity and has blank() constructor
     */
    public void registerEntity(Class... entity) {
        Collections.addAll(this.entities, entity);
    }

    /**
     *
     * @param gui class which extends GUIEntity and has blank() constructor
     */
    public void registerGui(Class... gui) {
        Collections.addAll(this.guis, gui);
    }

    /**
     * Register classes which are polyInstancional -> more instances exist in world at once
     * for example: entity...
     * Entity must have: constructor() - no args
     * @param entity
     */
    public void register(Class... entity) {
        for (Class c : entity) {
            if (IEntity.class.isAssignableFrom(c))
                entities.add(c);
        }
    }

    public void registerVannila() {
        registerItems();
        registerBlocks();
        registerEntities();
        registerGuis();
        registerUnderBlocks();
    }

    private void registerItems() {
        Class aClass = Items.class;
        Field[] fields = aClass.getFields();
        for (Field f : fields) {
            try {
                registerItem((Item) f.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void registerUnderBlocks() {
        Class aClass = UnderBlocks.class;
        Field[] fields = aClass.getFields();
        for (Field f : fields) {
            try {
                underBlocks.add((UnderBlock) f.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerBlocks() {
        try {
            Class[] classes = getClasses("cs.cooble.nice.blocks");
            for(Class c:classes){
                if(Block.class.isAssignableFrom(c)&&!c.equals(Block.class)){
                    try {
                        Block b = (Block) c.newInstance();
                        blocks.add(b);
                        register(c);
                    }catch (Exception ignored){}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerEntities() {
        register(
                Sysel.class,
                Matej.class,
                KillerBot.class
        );

    }

    private void registerGuis() {
        registerGui(
                ChestContainerEntity.class,
                CraftingContainerEntity.class,
                CreativeContainerEntity.class,
                KosContainerEntity.class,
                MatejContainerEntity.class//,
                //PecContainerEntity.class
        );

    }

    @Nullable
    public Block getBlock(String id) {
        for (IEntity block : blocks) {
            if (((Block)block).getID().equals(id))
                return (Block) block;
        }
        Log.println("Invalid blockname: " + id, Log.LogType.ERROR);
        return null;
    }

    @Nullable
    public Item getItem(String id) {
        for (Item item : items) {
            if (item.getID().equals(id))
                return item;
        }
        return null;
    }

    @Nullable
    public UnderBlock getUnderBlock(String id){
        for(UnderBlock u:underBlocks){
            if(u.getID().equals(id))return u;
        }
        return null;
    }


    public ArrayList<Item> getItems() {
        return items;
    }
    public ArrayList<UnderBlock> getUnderBlocks() {
        return underBlocks;
    }
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<IEntity> getEntities() {
        ArrayList<IEntity> entities = new ArrayList<>();
        for(Class c:this.entities){
            try {
                entities.add((IEntity) c.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entities;
    }
    public ArrayList<GuiEntity> getGuis() {
        ArrayList<GuiEntity> out = new ArrayList<>();
        for(Class c:this.guis){
            try {
                out.add((GuiEntity) c.newInstance());
            } catch (Exception e) {
                try {
                    out.add((GuiEntity) c.getConstructor(Location.class).newInstance((Location)null));
                }catch (Exception ee){
                    Log.println("Cannot instatiate gui "+c.getName(), Log.LogType.ERROR);
                    ee.printStackTrace();
                }
             //  e.printStackTrace();
            }
        }
        return out;
    }

    public IEntity buildEntity(NBT nbt){
        String className = nbt.getString("className");
        if(className==null){
            Log.printStackTrace("Entity NBT className is missing, cannot deserialize!");
        }
        for (Class c : entities) {
            if (c.getName().equals(className)) {
                try {
                    IEntity out =(IEntity) c.newInstance();
                    out.readFromNBT(nbt);
                    return out;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.println("Probably entity "+className+" does not have blank() constructor", Log.LogType.ERROR);
                    return null;
                }
            }
        }
        Log.printStackTrace("Entity NBT className is: "+className+", but is not registered in GameRegistry. Cannot deserialize!");
        return null;
    }

    public GuiEntity buildGUI(NBT nbt){
        String className = nbt.getString("className");
        for (Class c : guis) {
            if (c.getName().equals(className)) {
                try {
                    return (GuiEntity) c.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.println("Probably gui "+className+" does not have blank() constructor", Log.LogType.ERROR);
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
