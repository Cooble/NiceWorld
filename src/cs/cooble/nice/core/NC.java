package cs.cooble.nice.core;

import cs.cooble.nice.util.Location;

import java.awt.*;
import java.util.Random;

/**
 * NiceCore
 */
public class NC {
    public static final Random RANDOM = new Random();
    public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    /**
     * size of chunk in pixels
     */
    public static final int CHUNK_SIZE = 900;
    /**
     * how many tiles in the row of chunk
     */
    public static final int CHUNK_TILES = 3;

    private static String WORLDNAME="svet";
    public static final String fontGUIName = "Cooper Black";
    public static final String fontName = "Serif";
    public static boolean shouldOnUpdate=true;
    private static String enteredText="";
    public static boolean shouldExitWithoutEvent=false;
    public static boolean enteringText=false;
    /**
     * determinates if there on game should be only cudliky active
     */
    private static boolean cudlikMode = false;
    public static Core core;

    public static String getEnteredText() {
        return enteredText;
    }
    public static void setEnteredText(String enteredText) {
        NC.enteredText = enteredText;
    }

    public static String getRandomKoncovka(int maxHranice){
       return "_"+ RANDOM.nextInt(maxHranice);
    }

    public static void setWORLDNAME(String WORLDNAME) {
        NC.WORLDNAME = WORLDNAME;
    }
    public static String getWORLDNAME() {
        return WORLDNAME;
    }

    public static void setCudlikMode(boolean cudlikMode) {
        NC.cudlikMode = cudlikMode;
    }
    public static boolean isCudlikMode() {
        return cudlikMode;
    }
    /**
     *
     * @param chunk
     * @return
     */
    public static boolean isInChunk(int x,int y, Location chunk) {
        Rectangle chunked = new Rectangle(chunk.X*NC.CHUNK_SIZE,chunk.Y*NC.CHUNK_SIZE,NC.CHUNK_SIZE,NC.CHUNK_SIZE);
        return chunked.contains(x,y);
    }
}
