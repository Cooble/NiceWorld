package cs.cooble.nice.entity;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.event.Updateable;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.util.Position2;
import cs.cooble.nice.util.Vector2;


/**
 * Used to move IMoveable to another location in Location
 * Needs to call update()
 */
public final class Mover2 implements Updateable {

    private IMoveable MOVEABLE;
    private final Position2 movPosition;
    private final Vector2 vector;
    private final Runnable run;
    private int time;
    private final int MAX_TIME;
    private boolean done;

    /**
     * @param runnable      called when object is on movPosition
     * @param moveable      object, which is moved
     * @param finalPosition location to reach
     * @param speed         speed of moving is forbiden to be 0
     */
    public Mover2(IMoveable moveable, Location finalPosition, double speed, @Nullable Runnable runnable) {
        this.MOVEABLE = moveable;
        this.movPosition = new Position2(moveable.getX(), moveable.getY());//odsazeni protoze joe ma trochu jinou pozici
        run = runnable;
        vector=Vector2.getVectorFromPositions(movPosition,new Position2(finalPosition.X,finalPosition.Y));

        speed=speed==0?1:speed;//speed cannot be 0

        MAX_TIME = (int) Math.ceil(vector.getMagnitude() / speed);

        if(vector.getMagnitude()==0.0){
            done=true;
            MOVEABLE=null;
        }


    }

    public Mover2(Location finalPosition, @Nullable Runnable runnable) {
        this(NiceWorld.getNiceWorld().getWorld().getMatej(), finalPosition, NiceWorld.getNiceWorld().getWorld().getMatej().getStats().getSpeed(), runnable);
    }

    private void sync(){
        MOVEABLE.setX((int) movPosition.getPosX());
        MOVEABLE.setY((int) movPosition.getPosY());
    }

    public void update() {
        if (done) {
            done = false;
            if (run != null) {
                run.run();
                MOVEABLE=null;
            }
            return;
        }
        if (MOVEABLE != null)
            if (move()) {
                MOVEABLE.setIsMoving(false);
                MOVEABLE = null;
                if (run != null)
                    run.run();
            }

    }

    @Override
    public boolean isAlive() {
        return !done;
    }

    /**
     * @return true if should be removed
     */
    private boolean move() {
        time++;
        MOVEABLE.setIsMoving(true);
        double time=(double)this.time/MAX_TIME;
        movPosition.goWithVector(vector,time);
        sync();
        return (int)Math.floor(time)==1;
    }
}
