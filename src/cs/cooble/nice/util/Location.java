package cs.cooble.nice.util;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.Kamera;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

/**
 * Created by Matej on 27.1.2015.
 */
public class Location implements Serializable{
    public int X;
    public int Y;

    public Location(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Location)
            if(((Location) obj).X==this.X && ((Location) obj).Y==this.Y)
                return true;
        return false;
    }
    public void setLocation(int x, int y){
        X=x;
        Y=y;
    }
    public void setLocation(Location location){
        this.setLocation(location.X,location.Y);
    }

    /**
     * seète lokaci s druhou lokací
     * @param location druhá loc
     * @return this
     */
    public Location addLocation(Location location){
        X+=location.X;
        Y+=location.Y;
        return this;
    }
    /**
     * seète lokaci s druhou lokací
     * @param location druhá loc
     * @return this
     */
    public Location addLocationNew(Location location){
        return new Location(X+location.X,Y+location.Y);
    }

    @Override
    public String toString() {
        return "[Location: x="+X+" y="+Y + "]";
    }
    public Location getCopy(){
        return new Location(X,Y);
    }
    public static Location toLocation(MouseEvent event){
        return new Location(event.getX(), event.getY());
    }
    public static Location blank(){return new Location(0,0);}
    public static Location getRectangleLocation(Rectangle rectangle){
        return new Location(rectangle.x,rectangle.y);
    }
    public static Location getRectangleDimension(Rectangle rectangle){
        return new Location(rectangle.width,rectangle.height);
    }
    public static Location getChunkLocation(Location worldLoc){
        double x = (double)worldLoc.X/ NC.CHUNK_SIZE;
        double y =(double)worldLoc.Y/NC.CHUNK_SIZE;
        if(x<0){
            x--;
        }
        if(y<0){
            y--;
        }
        return new Location((int)x,(int)y);
    }


    /**
     *
     * @param worldLoc pos ve svìtì
     * @return pravou pozici objektu na obazovce
     */
    public static Location getTrueMouseLocation(Location worldLoc){
        return new Location(worldLoc.X-Kamera.getXCoord(),worldLoc.Y-Kamera.getYCoord());
    }

    /**
     * @param mouseLocation pos na obrazovce
     * @return pravou pozici objektu ve svetì
     */
    public static Location getTrueWorldLocation(Location mouseLocation){
        return mouseLocation.addLocationNew(Kamera.getLocation());
    }

    public NBT serialize(){
        NBT nbt = new NBT();
        nbt.setIntenger("posX",this.X);
        nbt.setIntenger("posY",this.Y);
        return nbt;
    }


    public static Location deserialize(NBT nbt) {
        return new Location(nbt.getInteger("posX"),nbt.getInteger("posY"));
    }
}
