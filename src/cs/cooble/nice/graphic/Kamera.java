package cs.cooble.nice.graphic;

import cs.cooble.nice.util.Location;

/**
 * Created by Matej on 10.2.2015.
 */
public class Kamera {
    private static int XCoord;
    private static int YCoord;

    public static int getXCoord() {
        return XCoord;
    }
    public static int getYCoord() {
        return YCoord;
    }

    public static void setXCoord(int XCoord) {
        Kamera.XCoord = XCoord;
    }
    public static void setYCoord(int YCoord) {
        Kamera.YCoord = YCoord;
    }

    public static Location getLocation(){
        return new Location(XCoord,YCoord);
    }
}
