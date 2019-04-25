package cs.cooble.nice.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;

/**
 * Created by Matej on 8.4.2015.
 * Trida k ukladani vsech moznych promenych.
 * Je zde i moznost ulozeni tileentity
 */
public class NBT implements Serializable{

    private LinkedHashMap<String,Integer> intmap;
    private LinkedHashMap<String,Boolean> boolmap;
    private LinkedHashMap<String,String> stringmap;
    private LinkedHashMap<String,Double> doublemap;
    private LinkedHashMap<String,NBT> nbtmap;

    public NBT(){
        doublemap   =   new LinkedHashMap<>();
        boolmap     =   new LinkedHashMap<>();
        stringmap   =   new LinkedHashMap<>();
        intmap      =   new LinkedHashMap<>();
        nbtmap      =   new LinkedHashMap<>();
    }

    public void setIntenger(String key,int i){
        intmap.put(key,i);
    }
    public void setBoolean(String key,boolean i){
        boolmap.put(key, i);
    }
    public void setString(String key,String i){
        stringmap.put(key,i);
    }
    public void setDouble(String key,double i){
        doublemap.put(key,i);
    }
    public void setNBT(String key,NBT i){
        nbtmap.put(key,i);
    }



    public int getInteger(String key){
        return intmap.get(key)==null?0:intmap.get(key);
    }
    public boolean getBoolean(String key){
        return boolmap.get(key)==null?false:boolmap.get(key);
    }
    public String getString(String key){
        return stringmap.get(key)==null?"":stringmap.get(key);
    }
    public double getDouble(String key){
        return doublemap.get(key)==null?0:doublemap.get(key);
    }
    public NBT getNBT(String key){
        return nbtmap.get(key);
    }


    /**
     *
     * @return exact copy of ints doubles strings and bools
     */
    public NBT copy() {
        LinkedHashMap<String,Double> doubles = copyMap(doublemap);
        LinkedHashMap<String,Boolean> bools = copyMap(boolmap);
        LinkedHashMap<String,String> strings = copyMap(stringmap);
        LinkedHashMap<String,Integer> ints = copyMap(intmap);
        LinkedHashMap<String,NBT> nbts = copyMap(nbtmap);
        NBT out = new NBT();
        out.intmap=ints;
        out.doublemap=doubles;
        out.boolmap=bools;
        out.stringmap=strings;
        out.nbtmap=nbts;

        return out;

    }

    private static  <T> LinkedHashMap<String,T> copyMap(LinkedHashMap<String,T> m){
        LinkedHashMap<String,T> output= new LinkedHashMap<>();
        m.forEach(new BiConsumer<String, T>() {
            @Override
            public void accept(String s, T t) {
                T out = null;
                if(t instanceof String){
                    out = (T) t.toString();
                }
                else if(t instanceof Boolean){
                    boolean b = ((Boolean) t).booleanValue();
                    out = (T) (new Boolean(b));
                }
                else if(t instanceof Integer){
                    int i = ((Integer) t).intValue();
                    out = (T) new Integer(i);
                }
                else if(t instanceof Double){
                    double d = ((Double) t).doubleValue();
                    out = (T) new Double(d);
                }
                else if(t instanceof NBT){
                   out= (T) ((NBT) t).copy();
                }
                output.put(s,out);
            }
        });
        return output;
    }


}
