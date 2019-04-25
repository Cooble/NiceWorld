package cs.cooble.nice.game_stats.components;

import java.io.Serializable;

/**
 * Created by Matej on 21.2.2015.
 */
public class Infos implements Serializable {
    String[] infos;

    public Infos(String[] infos){
        this.infos=infos;
    }

    public String[] getInfos(){
        return infos;
    }
    public String getInfo(int index){
        return infos[index];
    }

    @Override
    public String toString() {
        String out="[Infos: ";
        for (int i = 0; i < infos.length - 1; i++) {
            out+="_"+infos[i];
        }
        return out+"]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Infos)
            if(((Infos) obj).getInfo(0).equals(this.getInfo(0)))
                return true;
        return false;
    }
}
