package cs.cooble.nice.core;

import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 9.2.2015.
 */
public class F3 {
    public static int pocetNactenychChunku=0;
    public static int pocetNactenychTileChunku=0;
    public static int početKreslenýchVěcí=0;
    public static int početEntit=0;
    public static int FPS;
    public static int TPS;
    public static int početObjektu=0;
    public static Location mouseLoc=Location.blank();
    public static Location mouseWorldLoc=Location.blank();
    public static Location curChunk;
    private static boolean isActive=false;
    public static boolean isActive(){return isActive;}
    public static void setActive(boolean b){isActive=b;}
}
