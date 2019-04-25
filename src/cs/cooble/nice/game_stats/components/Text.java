package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;

import java.awt.*;


/**
 * Created by Matej on 9.4.2015.
 */
public class Text implements IRenderable {
    protected String text;
    protected int posX,posY;
    protected Color color;
    protected int sirka,vyska;
    public Text(Color color1, String text, Location pos, Location dimension){
        this.text=text;
        this.posX=pos.X;
        this.posY=pos.Y;
        this.sirka=dimension.X;
        this.vyska=dimension.Y;
        this.color=color1;
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        g.g().setColor(color);
        g.setFont(NC.fontName, Font.PLAIN, 20);
        g.g().drawString(text,posX-g.fontWidth(text)/2,posY);
    }
}
