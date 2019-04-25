package cs.cooble.nice.gameloading;

import cs.cooble.nice.core.F3;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.core.World;
import cs.cooble.nice.core.WorldsProvider;
import cs.cooble.nice.game_stats.components.Infos;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Trida, zajistujici vsechen kontakt s ukládáním a nacitanim cehokoliv.
 */
public class Saver implements WorldsProvider {
    public final String APPDATA  = System.getenv("APPDATA");
    private String worldPath;
    public final String NiceWorldPath=APPDATA+"/NiceWorld";
    private final String worldsPath=NiceWorldPath+"/worlds/";
    private String WORLD_CHUNKS;
    private String WORLD_POS;
    private String WORLD_INFO;
    private String WORLDDATA;
    private final String WORLDS_INFO=worldsPath+"WORLDS_INFO.txt";
    private final String WORLD="WORLD.world";
    private final String SETTINGS=NiceWorldPath+"/SETTINGS.txt";
    private ArrayList<Location> chunkList = new ArrayList<>();

    //=PSANÍ A CTENÍ SOUBORŮ============================================================================================
    private Object readObject (String file) throws IOException, ClassNotFoundException {
        File file1 = new File(file);
        Object object=null;

            FileInputStream fileStream = new FileInputStream(file1);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            object = objectStream.readObject();


        return object;
    }
    private void writeObject(String file,Object object) {
        File file1 = new File(file);
        try {
            FileOutputStream fileStream = new FileOutputStream(file1);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

            objectStream.writeObject(object);

            objectStream.close();
            fileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //=VYTVÁŘENÍ SLOŽEK A SOUBORU=======================================================================================
    private void createFile(String cesta){
        PrintWriter writer;
        try {
            writer = new PrintWriter(cesta, "UTF-8");
            writer.close();

        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates folder if doesn't exist.
     * @param file
     * @return true if file was created
     */
    private boolean createFolder(File file) {
        //Log.println("creating folder : "+file.getPath()+"  (if exist)");
        if(!file.exists()) {
            file.mkdirs();
            return true;
        }
        else return false;
    }

    //=EVENTY===========================================================================================================
    public World loadWorld(String World_name) throws IOException, ClassNotFoundException {
        makeDefaultFoldersFiles();
        /*Ukládání dulezitých proměných*/
        worldPath = worldsPath+ World_name +"/";
        WORLD_CHUNKS=worldPath+"world_chunks.txt";
        WORLD_POS=worldPath+"world_pos.txt";
        WORLD_INFO=  worldPath+"WORLD_INFO.txt";
        WORLDDATA=worldPath+WORLD;
        if(!new File(worldPath).exists()) {
            Log.println("(Loading)GENERATING WORLD: " + World_name);
            World world =World.getNewWorld(World_name);
            createFolder(new File(worldPath));
            createFile(WORLD_CHUNKS);
            writeObject(WORLD_CHUNKS, chunkList);
            createFile(WORLD_POS);
            writeObject(WORLD_POS, new Location(0, 0));
            createFile(WORLD_INFO);
            createFile(WORLDDATA);
            writeObject(WORLDDATA, world.getMyWorldSavedData());

               //INFOS===========
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String[] info = {World_name, dateFormat.format(date), NiceWorld.getVersion(),""};
            saveInfo(new Infos(info));
            loadChunkList();
            return world;
        }
        else{
            Log.println("(Loading)LOADING WORLD: "+World_name);
            loadChunkList();
            return World.getLoadedWorld((World.WorldSavedData) readObject(WORLDDATA));
        }

    }

    /**
     * saves all other data except chunks
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void unLoadWorld() throws IOException, ClassNotFoundException {
        writeObject(WORLDDATA,NiceWorld.getNiceWorld().getWorld().getMyWorldSavedData());
        writeObject(WORLD_CHUNKS,chunkList);
        saveWorldPos(F3.curChunk);
        addWorldInfo();
    }
    public void makeDefaultFoldersFiles() {
        try {
            if (!new File(worldsPath).exists()) {
                createFolder(new File(worldsPath));
                createFile(WORLDS_INFO);
                writeObject(WORLDS_INFO, new ArrayList<Infos>());
                createFile(SETTINGS);
                writeObject(SETTINGS, NiceWorld.getNiceWorld().getSettings());
            } else {
                NiceWorld.getNiceWorld().setSettings((Settings) readObject(SETTINGS));
            }
        }catch (Exception ignored){}
    }
    public void saveSettings(){
        writeObject(SETTINGS,NiceWorld.getNiceWorld().getSettings());
    }

    //=PRÁCE S CHUNKY===================================================================================================
    public void saveChunk(Chunk chunk){
        String chunkDir=worldPath+"chunk_"+chunk.posX+"_"+chunk.posY+".txt";
        createFile(chunkDir);
        writeObject(chunkDir, chunk.serialize());
        addToList(chunk.getLocation());
        saveChunkList();
    }
    public Chunk loadChunk(int posX,int posY) {
        String chunkDir=worldPath+"chunk_"+posX+"_"+posY+".txt";
        try {
            return Chunk.deserialize((NBT) readObject(chunkDir));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Chunk loadChunk(Location location){
       return loadChunk(location.X,location.Y);
    }
    public boolean existChunk(int x ,int y){
        return existChunk(new Location(x,y));
    }
    public boolean existChunk(Location chunkLocation){
        assert chunkList != null;
        for (int i = 0; i < chunkList.size(); i++) {
            if (chunkList.get(i).equals(chunkLocation))
                return true;
        }
        return false;
    }

    //=PRÁCE S INFEM====================================================================================================
    private void saveInfo(Infos info){
        writeObject(WORLD_INFO,info);
    }

    @Override
    public ArrayList<Infos> getWorldsInfo() {
        ArrayList<Infos> listik = null;
        try {
            listik = (ArrayList<Infos>) readObject(WORLDS_INFO);
            ArrayList<String> svety = new ArrayList<>(Arrays.asList(getWorldNames()));
            for (int i = listik.size() - 1; i >= 0; i--) {
                if(!svety.contains(listik.get(i).getInfo(0))){
                    listik.remove(i);
                }
            }
            writeObject(WORLDS_INFO,listik);
            return listik;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }
    private void addWorldInfo(){
        ArrayList<Infos> listik;
        try {
            listik = (ArrayList<Infos>) readObject(WORLDS_INFO);
            if(!listik.contains(readObject(WORLD_INFO))) {
                ArrayList<Infos> novylist = new ArrayList<>();
                novylist.add((Infos) readObject(WORLD_INFO));
                novylist.addAll(1,listik);
                writeObject(WORLDS_INFO, novylist);
            }
            else {
                ArrayList<Infos> novylist = new ArrayList<>();
                novylist.add((Infos) readObject(WORLD_INFO));
                int indexprvniho = listik.indexOf(readObject(WORLD_INFO));
                for (int i = 0; i < listik.size(); i++) {
                    if(i!=indexprvniho){
                        novylist.add(listik.get(i));
                    }
                }
                writeObject(WORLDS_INFO, novylist);
            }
        } catch (Exception e) {}
    }

    @Override
    public void deleteSvet(String name) {
        try {
            deleteDirectory(worldsPath+name);
        }
        catch (Exception ignored){}

    }

    @Override
    public void removeEverything() {
        deleteDirectory(NiceWorldPath);
    }

    @Override
    public void onUpdateChunks(Location location) {
        try {
            NiceWorld.getNiceWorld().getChunkLoader().onUpdateChunks(location);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    //=SOUKROMÁ PRÁCE S CHUNK LISTEM====================================================================================
    private void loadChunkList() throws IOException, ClassNotFoundException {
        chunkList = (ArrayList<Location>)readObject(WORLD_CHUNKS);
    }
    private void saveChunkList(){
        writeObject(WORLD_CHUNKS, chunkList);
    }

    //=OSTATNÍ==========================================================================================================
    public boolean existWorld(String worldName){
        return new File(APPDATA +"/NiceWorld/worlds/"+worldName+"/").exists();
    }
    public Location getWorldPos() {
        Location l = null;
        try {
            l = (Location) readObject(WORLD_POS);
        } catch (Exception e) {e.printStackTrace();}

        if(l!=null)
            return l;
        else
            return new Location(0,0);
    }
    private void saveWorldPos(Location location){writeObject(WORLD_POS,location);}
    public void addToList(Location l){
        if(!chunkList.contains(l))
            chunkList.add(l);
    }
    public String[] getWorldNames(){
        File file = new File(worldsPath);
        String[] names = file.list();
        ArrayList<String> stringArrayList = new ArrayList<>();
        for(String name : names)
        {
            if (new File(worldsPath + name).isDirectory()) {
                    stringArrayList.add(name);
            }
        }
        return stringArrayList.toArray(new String[stringArrayList.size()]);
    }

    public boolean deleteDirectory(File dir) {
        Path directory = Paths.get(dir.getAbsolutePath());
        try {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
     /*  if (!dir.exists() || !dir.isDirectory()) {
           return false;
       }
       //t       odo dodelat mazani directories nefunguje pouziva to jiny proces ale jaky nevim

       String[] files = dir.list();
       for (int i = files.length - 1; i >= 0; i--) {
           File f = new File(dir, files[i]);
           if (f.isDirectory()) {
               Log.println(deleteDirectory(f) ? "smazano dir " + f : "nelze smazat dir " + f);
           } else {
               if (!f.delete()) {
                   //Log.println("*Nelze smazat soubor " + f + " ** canread " + f.canRead() + " canwirte " + f.canWrite() + " canexecute " + f.canExecute() + f.isAbsolute()+"isdir "+f.isDirectory());
                   try {
                       String out = f.toString();
                       out=out.replace('\\','/');
                       Log.println("dir.to.path "+Paths.get(new URI("file:/"+out)));

                       Files.delete(Paths.get(new URI("file:/"+out)));
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               } else{ /*Log.println("Smazan soubor " + f);}
           }
       }
       return dir.delete();*/
   }




    public boolean deleteDirectory(String dir){
        return deleteDirectory(new File(dir));
    }

    //SETTINGS==========================================================================================================
    public void setSettings(Settings settings){
        writeObject(SETTINGS,settings);
    }

    public static File[] getResourceFolderFiles (String folder) {
        URL url = Saver.class.getResource(folder);
        String path = url.getPath();
        return new File(path).listFiles();
    }

}