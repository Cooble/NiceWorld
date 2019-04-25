package cs.cooble.nice.entity;

import cs.cooble.nice.entity.gui.ChatGuiEntity;
import cs.cooble.nice.entity.gui.ScreenEntitySpawner;
import cs.cooble.nice.entity.inventory.CraftingContainerEntity;
import cs.cooble.nice.entity.inventory.CreativeContainerEntity;

/**
 * Created by Matej on 12.8.2015.
 */
public class MatejScreensControler{
    private boolean openCreative=true;
    private boolean openCrafting=true;
    private boolean openChat=true;
    private CreativeContainerEntity creativeContainerEntity;
    private CraftingContainerEntity craftingContainerEntity;
    private ChatGuiEntity chatGuiEntity;

    public MatejScreensControler(Matej matej){
        creativeContainerEntity=new CreativeContainerEntity();
        craftingContainerEntity=new CraftingContainerEntity();
        chatGuiEntity=new ChatGuiEntity();

        creativeContainerEntity.setMatej(matej);
        craftingContainerEntity.setMatej(matej);
    }

    public void refreshMatejCreative(){
        openCreative=!openCreative;
        if(openCreative) {
            ScreenEntitySpawner.hideScreenEntity(creativeContainerEntity);
        }
        else {
            ScreenEntitySpawner.showScreenEntity(creativeContainerEntity);
        }
    }
    public void refreshMatejCrafting(){
        openCrafting=!openCrafting;
        if(openCrafting) {
            ScreenEntitySpawner.hideScreenEntity(craftingContainerEntity);
        }
        else {
            ScreenEntitySpawner.showScreenEntity(craftingContainerEntity);
        }
    }
    public void refreshMatejChat(){
        openChat=!openChat;
        if(openChat) {
            ScreenEntitySpawner.hideScreenEntity(chatGuiEntity);
        }
        else {
            ScreenEntitySpawner.showScreenEntity(chatGuiEntity);
        }
    }
}
