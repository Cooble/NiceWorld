package cs.cooble.nice.graphic;


/**
 * Created by Matej on 26.1.2015.
 */
public interface IRenderable extends TextureLoadable {
    /**
     * Nech se nakreslit.
     * @param x Coord
     * @param y Coord
     * @param g Graphics
     * Použij při zadávání souřadnic na (xKVykreslení = SVOJE_SOURADNICE-x)
     */
    void render(int x, int y, NGraphics g);

    /**
     * determines if should be painted for first or last
     * @return 0=if doesnt matter , 1 if should be painted for first 2=should be painted for last,3=for ultralast
     */
    default int getPriority(){return 0;}
}
