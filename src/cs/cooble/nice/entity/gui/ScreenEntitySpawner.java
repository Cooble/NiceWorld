package cs.cooble.nice.entity.gui;

import cs.cooble.nice.entity.inventory.ContainerEntity;
import cs.cooble.nice.inventory.IInventory;
import cs.cooble.nice.graphic.Renderer;
import cs.cooble.nice.input.ActionRectangles;
import cs.cooble.nice.inventory.Inventory;

/**
 * Created by Matej on 12.8.2015.
 */
public class ScreenEntitySpawner {
    private static Renderer renderer;

    public static boolean isShown(GuiEntity guiEntity){
        if(guiEntity instanceof ContainerEntity)
            return Inventory.getInstance().existContainerEntity(guiEntity.getID());
        else return ActionRectangles.getInstance().getActionRectangle(guiEntity.getID())!=null;
    }

    public static void showScreenEntity(GuiEntity guiEntity){
        if(guiEntity instanceof ContainerEntity)
            Inventory.getInstance().registerContainerEntity((IInventory) guiEntity);
        guiEntity.loadTextures(renderer.getAtlas());
        ActionRectangles.getInstance().registerActionRectangle(guiEntity);
        renderer.registerGUI(guiEntity);
        guiEntity.onGuiEntityStartDrawing(renderer);
    }

    public static void hideScreenEntity(GuiEntity guiEntity){
        if(guiEntity instanceof ContainerEntity)
            Inventory.getInstance().removeContainerEntity(guiEntity.getID());

        ActionRectangles.getInstance().removeActionRectangle(guiEntity.getRectangle());
        renderer.removeGUI(guiEntity);
        guiEntity.onGuiEntityStopDrawing(renderer);
    }

    public static void setRenderer(Renderer rednerer) {
        ScreenEntitySpawner.renderer = rednerer;
    }
}
