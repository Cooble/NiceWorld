package cs.cooble.nice.entity.inventory;


import cs.cooble.nice.util.TypPeceni;
import cs.cooble.nice.entity.tileentity.PecTileEntity;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.items.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;


/**
 * Created by Matej on 17.3.2015.
 */
public class PecContainerEntity/* extends ContainerEntity */{
/*
    //createImage
    private  int mezera;
    private int velikost;



    public PecContainerEntity() {
        super(null,"pec_tile_entity",3);
        mezera= 12;
        velikost = 55;
        posY=200;
        posX=600;
        int kousek=5;

        for (int i = 0; i < 3; i++) {
            containers[i]=new Container(posX+mezera+kousek,posY+i*(velikost+mezera)+mezera+kousek,velikost-2*kousek,getID(),itemStacks,i);
        }
        rectangle = new Rectangle(posX,posY,velikost+mezera+mezera,containers.length*(velikost+mezera)+mezera);
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        int mezirkakolem=5;
        g.setColor(new Color(69, 70, 61));
        g.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height,15,15);
        g.setColor(new Color(130, 132, 114));
        g.fillRoundRect(rectangle.x+mezirkakolem, rectangle.y+mezirkakolem, rectangle.width-2*mezirkakolem, rectangle.height-2*mezirkakolem,10,10);


        int mensio=10;
        Color karikatura =new Color(100,100,100,100);
        int velkost=velikost-2*mensio;
        Item currectpainted=null;
        for (int j = 0; j < 3; j++) {
            gg.drawIcon(iconSlot,posX+mezera,posY+j*(mezera+velikost)+mezera,velikost,velikost);
            switch (j){
                case 0:
                    currectpainted=Items.zeleznaRuda;
                    break;
                case 1:
                    currectpainted=Items.zeleznyIngot;
                    break;
                case 2:
                    currectpainted=Items.poleno;
                    break;
            }
            gg.setBackgroundDrawing(new Color(0,0,0));
            gg.drawIcon(currectpainted.getIIcon(new ItemStack(currectpainted)),posX+mezera+mensio,posY+j*(mezera+velikost)+mezera+mensio,velkost,velkost);
        }

    }

    @Override
    public String getID() {
        return "pec";
    }


    @Override
    public ItemStack addToContainer(Container container, ItemStack itemStack) {
        if(container.getIndex()== PecTileEntity.INDEXFINISH)
            return itemStack;
        if(container.hasItemStack()){
            ItemStack containerStack = container.getItemStack();
                if(container.getItemStack().equalsType(itemStack)) {//pokud do slotu strkam to samy
                    int soucet = containerStack.getPocet()+itemStack.getPocet();
                    if(soucet>itemStack.getMaxPocet()){
                        container.getItemStack().setPocet(containerStack.getMaxPocet());
                        return itemStack.setPocet(soucet-containerStack.getMaxPocet());
                    }
                    else {
                        containerStack.addPocet(itemStack.getPocet());
                        return null;
                    }
                }
        }
        else {
            if(container.getIndex()==PecTileEntity.INDEXPALIVO) { //na fuel slot
                if (itemStack.ITEM instanceof ItemFuel) {
                    container.setItemStack(itemStack);
                    return null;
                }
            }
            else {
                if(itemStack.ITEM instanceof ItemVypekable&&((ItemVypekable) itemStack.ITEM).getVypecenyItem(TypPeceni.TAVENI,null)!=null){
                    container.setItemStack(itemStack);
                    return null;
                }
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack prohod(Container container, ItemStack itemStack) {
        return null;
    }*/
}
