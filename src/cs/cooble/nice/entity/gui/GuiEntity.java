package cs.cooble.nice.entity.gui;

import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.graphic.Renderer;
import cs.cooble.nice.input.ActionRectangles;
import cs.cooble.nice.input.IActionRectangle;
import cs.cooble.nice.util.Location;

import java.awt.*;

/**
 * Created by Matej on 19.6.2015.
 */
public abstract class GuiEntity implements IActionRectangle,IRenderable {
    private String id;
    protected Rectangle rectangle;

    protected GuiEntity(Rectangle rectangle,String ID){
        this.rectangle = rectangle;
        id = ID;
    }

    public String getID() {
        return id;
    }

    public void onGuiEntityStartDrawing(Renderer renderer){
        ActionRectangles.getInstance().registerActionRectangle(this);
    }

    public void onGuiEntityStopDrawing(Renderer renderer){
        ActionRectangles.getInstance().removeActionRectangle(this);
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void click(Location location, boolean prave_tlacitko) {}

    @Override
    public void render(int x, int y, NGraphics g) {}
}
