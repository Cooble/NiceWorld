package cs.cooble.nice.chat.command;

import cs.cooble.nice.core.World;

/**
 * Created by Matej on 5.4.2015.
 */
public class Command {
    public final String NAZEV;
    protected World world;
    public Command(String nazev){
        NAZEV=nazev;
    }

    public String dopln(int index, String cely){
        return cely;
    }
    public void setWorld(World world) {
        this.world = world;
    }

    public void action(String s){}
}
