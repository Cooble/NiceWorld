package cs.cooble.nice.util;

/**
 * Created by Matej on 20.6.2015.
 */
public class PrekladacRozsahu {
    private double min1;
    private double max1;
    /*private double min2;
    private double max2;*/

    public PrekladacRozsahu(double min1,double max1/*,double min2,double max2*/){
        this.min1 = min1;
        this.max1 = max1;
       // this.min2 = min2;
       // this.max2 = max2;
    }
    public double get1stValueToSecond(double first){
        return first/min1*max1;
    }
}
