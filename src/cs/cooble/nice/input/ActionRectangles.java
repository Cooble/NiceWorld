package cs.cooble.nice.input;

import cs.cooble.nice.util.Location;
import cs.cooble.nice.entity.IControllable;

import java.awt.*;
import java.util.ArrayList;

/**
 *Třida obstaravajici klikání na rectangles. Pokud se kliklo na neci rectangle
 */
public class ActionRectangles implements IControllable {
    private static ActionRectangles ourInstance = new ActionRectangles();

    public static ActionRectangles getInstance() {
        return ourInstance;
    }

    private ActionRectangles() {}

    private ArrayList<IActionRectangle> rectangles = new ArrayList<>();

    public void registerActionRectangle(IActionRectangle actionRectangle){
        rectangles.add(actionRectangle);
    }

    public void removeActionRectangle(Rectangle rectangle){
        for (int i = 0; i < rectangles.size(); i++) {
            if(rectangles.get(i).getRectangle().equals(rectangle)) {
                rectangles.remove(i);
                return;
            }
        }
    }
    public void removeActionRectangle(IActionRectangle iActionRectangle){
        removeActionRectangle(iActionRectangle.getRectangle());
    }

    public IActionRectangle getActionRectangle(String id){
        for (IActionRectangle rectangle:rectangles){
            if(rectangle.getID().equals(id))
                return rectangle;
        }
        return null;
    }

    public boolean click(Location location,boolean prave_tlacitko){
        boolean b = false;
        for (IActionRectangle rectangle : rectangles) {
            if (rectangle.getRectangle().contains(location.X, location.Y)) {
                rectangle.click(location, prave_tlacitko);
                b = true;
            } else rectangle.clickElsewhere();

        }
        return b;
    }

    public void mousemove(Location location){
        for (IActionRectangle rectangle : rectangles) {
            rectangle.mouseMove(location);
        }
    }

    public void wheeled(Location location,int otocka){
        for (IActionRectangle rectangle : rectangles) {
            rectangle.mousewheeled(location, otocka);
        }
    }

}
