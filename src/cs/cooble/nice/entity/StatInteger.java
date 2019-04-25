package cs.cooble.nice.entity;

import java.io.Serializable;

/**
 * Created by Matej on 28.6.2015.
 */
public class StatInteger implements Serializable {

    private int value;
    private int maxvalue;

    public StatInteger(int value, int maxvalue){
        this.value = value;
        this.maxvalue = maxvalue;
    }
    public StatInteger(int maxvalue){
        this(0,maxvalue);
    }

    public double getDouble(){
        return (double)value/maxvalue;
    }

    public int getValue() {
        return value;
    }
    public int getMaxvalue() {
        return maxvalue;
    }

    public void setValue(int value) {
        this.value = value;
        check();
    }
    public void addValue(int added){
        this.value+=added;
        check();
    }
    public void setMaxValue(int maxvalue) {
        this.maxvalue = maxvalue;
        check();
    }

    private void check(){
        if (value>maxvalue)
            value=maxvalue;
        else if(value<0)
            value=0;
    }
    public boolean isMax(){
        return value==maxvalue;
    }
    public boolean isMin() {return value==0;}
}
