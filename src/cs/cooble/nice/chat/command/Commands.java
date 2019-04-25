package cs.cooble.nice.chat.command;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.chat.SlovoManager;
import cs.cooble.nice.logger.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Matej on 5.4.2015.
 */
public class Commands {
    private static Commands ourInstance = new Commands();

    public static Commands getInstance() {
        return ourInstance;
    }

    private Commands() {}

    ArrayList<Command> commands = new ArrayList<>();
    public void addCommands(Command... commands1){
        for (int i = 0; i < commands1.length; i++) {
            commands.add(commands1[i]);
        }
    }
    public void registerCommands(){
        Log.println("**** Loading Commands");
        try {
            Class[] classes = getClasses("cs.cooble.nice.chat.command");
            for (Class aClass : classes) {
                if (isExtendingClass(aClass, Command.class)&&aClass!=Command.class) {
                    String[] subNames = SlovoManager.divideVeta(aClass.getName(),'.');
                    Log.println("* " + subNames[subNames.length-1]);
                    commands.add((Command) aClass.newInstance());
                }
            }
            Log.println("**** Commands loaded");

        } catch (Exception e) {
            Log.println("**** Error has ocured during loading commands:");
        }


    }
    private boolean isExtendingClass(Class potomek,Class predek){
        return predek.isAssignableFrom(potomek);
    }
    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    private Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
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
     * @param packageName The package NAME for classes found inside the base directory
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

    public ArrayList<Command> getCommands() {
        return commands;
    }
    public void action(String command){
        for (int i = 0; i < commands.size(); i++) {
            if(command.startsWith(commands.get(i).NAZEV)) {
                String subcom;
                try {
                    subcom =command.substring(1+commands.get(i).NAZEV.length());
                }
                catch (Exception e){//pokud jsem za nazev commandu nic dalsiho nenapsal
                    commands.get(i).action("");
                    return;
                }

                commands.get(i).action(subcom);
                return;
            }

        }
    }

    @Nullable public Command getCommand(String NAZEV){
        for (int i = 0; i < commands.size(); i++) {
            if(commands.get(i).NAZEV.equals(NAZEV))
                return commands.get(i);
        }
        return null;
    }
}
