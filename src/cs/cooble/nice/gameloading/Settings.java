package cs.cooble.nice.gameloading;

import java.io.Serializable;

/**
 * Třída, zajistujici pri behu programu ulozeni nastaveni a pote se ulozi, pri zapnuti programu se nacte.
 */
public class Settings implements Serializable{
    public Settings() {}
    public double Volume_sound=0.5;
    public double Volume_music=0.5;
}
