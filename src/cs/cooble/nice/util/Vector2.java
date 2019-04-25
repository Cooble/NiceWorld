package cs.cooble.nice.util;

/**
 * Created by Matej on 27.9.2016.
 */
public class Vector2 {
    private double posX;
    private double posY;
    private double angle;
    private double magnitude;

    public static Vector2 createFromXY(double X, double Y) {
        Vector2 out = new Vector2();
        out.posX = X;
        out.posY = Y;
        out.transformToAM();
        return out;
    }

    public static Vector2 createFromAM(double angle, double magnitude) {
        Vector2 out = new Vector2();
        out.angle = angle;
        out.magnitude = magnitude;
        out.transformToXY();
        return out;
    }

    private Vector2() {
    }

    private void transformToAM() {
        angle = Math.tan(posY / posX);
        magnitude = Math.sqrt(posX * posX + posY * posY);
    }

    private void transformToXY() {
        posY = magnitude * Math.sin(angle);
        posX = magnitude * Math.cos(angle);
    }


    public double getX() {
        return posX;
    }

    public double getY() {
        return posY;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getAngle() {
        return angle;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        transformToAM();
    }

    public void setPosY(double posY) {
        this.posY = posY;
        transformToAM();
    }

    public void setAngle(double angle) {
        this.angle = angle;
        transformToXY();
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
        transformToXY();
    }

    public static Vector2 add(Vector2 vector2, Vector2 vector22) {
        return createFromXY(vector2.getX() + vector22.getX(), vector2.getY() + vector22.getY());
    }

    public static Vector2 subtract(Vector2 vector2, Vector2 vector22) {
        return createFromXY(vector2.getX() - vector22.getX(), vector2.getY() - vector22.getY());
    }

    public static Vector2 getVectorFromPositions(Position2 position, Position2 position2){
        double posX = position2.getPosX()-position.getPosX();
        double posY = position2.getPosY()-position.getPosY();
        return createFromXY(posX,posY);
    }
}
