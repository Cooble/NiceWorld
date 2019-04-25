package cs.cooble.nice.entity;

import cs.cooble.nice.event.Updateable;
import cs.cooble.nice.util.Location;

/**
 * Tøída implementující Updateable, jejíž instance slouží k wrapperu objectù implementujících Imoveable, musí být pøihlašena k nìjakému updateru.
 */
public class Mover implements Updateable {

    public static double getAngleTo(Location from,Location to){
        int px=from.X;
        int py = from.Y;
        int nx = to.X;
        int ny = to.Y;
        int x= px-nx;
        int y=ny-py;
        return (Math.atan2(x, y)+Math.PI/2);
    }
    public static double getVzdalenostTo(Location from,Location to){
        int x= from.X - to.X;
        int y= from.Y - to.Y;
        return Math.sqrt(x*x+y*y);
    }
    public static boolean isInRadius(Location objekt,Location radiusObject,int radius){
        return objekt.X>radiusObject.X-radius&&objekt.X<radiusObject.X+radius&&
               objekt.Y>radiusObject.Y-radius&&objekt.Y<radiusObject.Y+radius;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    public final IMoveable MOVEABLE;
    public final Location FINALOCAITON;
    public final int speed;
    public double xd;
    public double yd;
    public final Runnable run;
    private boolean isAlive=true;


    /**
     *
     * @param runnable called when object is on finalLocation
     * @param moveable object, which is moved
     * @param FINALOCAITON location to reach
     * @param speed speed of moving
     */
    public Mover(Runnable runnable,IMoveable moveable, Location FINALOCAITON, int speed) {
        this.MOVEABLE = moveable;
        this.FINALOCAITON = FINALOCAITON;
        this.speed = speed;
        run = runnable;

        int x = FINALOCAITON.X-moveable.getX();
        int y = FINALOCAITON.Y-moveable.getY();

        xd = x/speed;
        yd = y/speed;

    }
    /**
     *
     * @return true if should be removed
     */
    public void update(){
        if(move())
            isAlive=false;

    }

    private boolean move(){
        MOVEABLE.setX((int) (MOVEABLE.getX() + xd));
        MOVEABLE.setY((int) (MOVEABLE.getY() + yd));
        boolean hasReached = MOVEABLE.getLocation().equals(FINALOCAITON);
        if(hasReached)
            if(run!=null)
                run.run();
        return hasReached;
    }



    //Moving======================================================================
   /* public void moveTo(ILocaleable localeable,Location location,int speed){
        movings.add(new Moving(null,localeable,location,speed));

    }
    public void moveTo(Runnable runnable,ILocaleable localeable,Location location,int speed){
        movings.add(new Moving(runnable,localeable,location,speed));

    }
    public void onUpdate(){
        for (int i = movings.size() - 1; i >= 0; i--) {
            if(movings.get(i).move())
                movings.remove(i);
        }

    }
    class Moving{
        public final ILocaleable MOVEABLE;
        public final Location FINALOCAITON;
        public final int speed;
        public double xd;
        public double yd;
        public final Runnable run;

        Moving(Runnable runnable,ILocaleable localeable, Location FINALOCAITON, int speed) {
            MOVEABLE = localeable;
            this.FINALOCAITON = FINALOCAITON;
            this.speed = speed;
            run = runnable;

            int x = FINALOCAITON.X-localeable.getX();
            int y = FINALOCAITON.Y-localeable.getY();
             xd = x/speed;
             yd = y/speed;
        }

        /**
         *
         * @return true if should be removed
         */
       /* public boolean move(){
            MOVEABLE.setX((int) (MOVEABLE.getX()+xd));
            MOVEABLE.setY((int) (MOVEABLE.getY()+yd));
            if(MOVEABLE.getX()==FINALOCAITON.X)
                if(run!=null)
                    run.run();
            return MOVEABLE.getX()==FINALOCAITON.X;
        }
    }*/
}
