package cs.cooble.nice.entity;

import java.io.Serializable;

/**
 * Created by Matej on 28.6.2015.
 */
public final class Stat implements Serializable {

    private float value;
    private float maxvalue;

    public Stat(float value,float maxvalue){
        this.value = value;
        this.maxvalue = maxvalue;
    }
    public Stat(float maxvalue){
        this(0,maxvalue);
    }

    public double getDouble(){
        return (double)value/maxvalue;
    }

    public float getValue() {
        return value;
    }
    public float getMaxValue() {
        return maxvalue;
    }

    public void setValue(float value) {
        this.value = value;
        check();
    }
    public void addValue(float added){
        this.value+=added;
        check();
    }
    public void setMaxValue(float maxvalue) {
        this.maxvalue = maxvalue;
        check();
    }

    private void check(){
        if (value>maxvalue)
            value=maxvalue;
        else if(value<0)
            value=0;
    }

}

