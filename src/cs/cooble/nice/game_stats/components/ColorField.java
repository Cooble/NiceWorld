package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;


/**
 * Created by Matej on 9.4.2015.
 */
public class ColorField implements IRenderable {
    private Color color;
    private Location location;
    private Location dimension;
    public ColorField(Color color1,Location location,Location dimension1){
        color=color1;
        this.location=location;
        this.dimension=dimension1;
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        g.g().setColor(color);
        g.g().fillRect(location.X, location.Y, dimension.X, dimension.Y);
    }
}
