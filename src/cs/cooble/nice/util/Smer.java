package cs.cooble.nice.util;

/**
 * Tento výčtový typ nemá vůbec nic, kromě 4instanci, které jsou s id.
 */
public enum Smer {
    RIGHT(0), LEFT(2), UP(3), DOWN(1), NONE(-1);

    public final int ID;

    Smer(int id) {
        ID = id;
    }
}
