package cs.cooble.nice.util;

/**
 * Created by Matej on 27.9.2016.
 */
public class Position2 {
    private double posX, posY;
    private final double POSX, POSY;


    public Position2(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        POSX = posX;
        POSY = posY;
    }

    /**
     *
     * @param vector
     * @param time between 0->1.0
     *             1.0 means vector's magnitude from beginning
     */
    public void goWithVector(Vector2 vector,double time){
        setPosX(POSX+vector.getX()*time);
        setPosY(POSY+vector.getY()*time);
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX1) {
        this.posX = posX1;
    }

    public void setPosY(double posY1) {
        this.posY = posY1;
    }

    public boolean isInRadius(Position2 position2,double radius){
        return this.posX + radius > position2.posX && this.posX - radius < position2.posX && this.posY + radius > position2.posY && this.posY - radius < position2.posY;
    }

    public double getFinalX(){return POSX;}
    public double getFinalY(){return POSY;}



}
