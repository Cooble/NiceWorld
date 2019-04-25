package cs.cooble.nice.graphic.lighting;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.util.Location;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Matej on 6.6.2015.
 */
public final class LightingMap {
    private static ArrayList<Light> lights = new ArrayList<>();
    private static ArrayList<BufferedImage> images = new ArrayList<>();

    private static boolean on=true;

    public static void allowLight(boolean on){
        LightingMap.on=on;
    }
    public static boolean isLightAllowed(){return on;}

    public static BufferedImage getMap(){

        BufferedImage map = new BufferedImage(NC.WIDTH,NC.HEIGHT,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = map.createGraphics();
        if(on) {
            double lightValue = getLightValue();
            if (lightValue > 0.9)
                lightValue = 0.9;
            g.setColor(new Color(0, 0, 0, (int) ((double) 255 * lightValue)));
            g.fillRect(0, 0, NC.WIDTH, NC.HEIGHT);
            g.setComposite(AlphaComposite.DstOut);

            //comment this line down
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < images.size(); i++) {
                Location trueLoc = lights.get(i).getTrueLocation();
                int radius = lights.get(i).getRadius() / 2;
                g.drawImage(images.get(i), trueLoc.X - radius, trueLoc.Y - radius,2*radius,2*radius, null);
            }
            //comment this line down
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        else {
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(0,0,map.getWidth(),map.getHeight());
        }

        return map;
    }

    public static void addLight(Light l){
        lights.add(l);
        images.add(l.createImage());
    }

    public static boolean removeLight(Light light){
        for (int i = 0; i < lights.size(); i++) {
            if(lights.get(i).equals(light)){
                lights.remove(i);
                images.remove(i);
                return true;
            }
        }
        return false;
    }

    private static ArrayList<Light> getLights() {
        return lights;
    }

    private static ArrayList<BufferedImage> getLightImages() {
        return images;
    }

    private static double getLightValue(){
        double currectHodina= NiceWorld.getNiceWorld().getWorld().getTimeManager().getHours();

        if(currectHodina>6&&currectHodina<=12) {//dopoledne 6 az 12
            return 1-(currectHodina-6)/6;
        }
        if(currectHodina>12&&currectHodina<=18) {//odpoledne 12 az 18
            return 0;
        }
        if(currectHodina>18&&currectHodina<=22) {//vecer 18 az 22hod
            return (currectHodina-18)/(22-18);
        }
        if(currectHodina>0&&currectHodina<=6) {//noc 0 az 6
            return 1;
        }

        else return 1;


    }
}
