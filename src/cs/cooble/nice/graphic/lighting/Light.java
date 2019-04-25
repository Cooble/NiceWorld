package cs.cooble.nice.graphic.lighting;

import cs.cooble.nice.graphic.Kamera;
import cs.cooble.nice.util.Location;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * Created by Matej on 6.6.2015.
 */
public class Light implements Serializable {
    private Location location;
    private int radius;
    private float[] distances;
    private Color[] colors;

    public Light(Location location,int radius){
        this.location=location;
        this.radius=radius;
        distances = new float[]{0.1f, 0.3f, 1.0f};
        colors = new Color[]{new Color(0, 0, 0, 255), new Color(0, 0, 0, 190), new Color(0, 0, 0, 0)};

    }
    public Light(Location location,int radius,float[] distances,Color[] colors){
        this(location,radius);

        this.distances = distances;
        this.colors = colors;
    }

    /**
     * Vytvoøí obrázek svého svìtla.
     */
    public BufferedImage createImage() {
        BufferedImage lightImage = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = lightImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        Point2D center = new Point2D.Float(radius, radius);
        RadialGradientPaint p = new RadialGradientPaint(center, radius, distances, colors);
        g2.setPaint(p);
        g2.fillOval(0, 0, radius * 2, radius * 2);
        return lightImage;
    }

    /**
     * @return lokaci ve svìtì
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return pravou lokaci na plátnì, k tomu používá tøídu Kamera
     */
    public Location getTrueLocation(){
        return new Location(location.X-Kamera.getXCoord(),location.Y-Kamera.getYCoord());
    }


    public void setLocation(Location location) {
        this.location = location;
    }

    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public void addRadius(int radius) {
        this.radius += radius;
    }
}
