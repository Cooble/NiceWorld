package cs.cooble.nice.entity.inventory;

import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.items.Item;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.Items;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Matej on 21.6.2015.
 */
public class CreativeContainerEntity extends ContainerEntity {

    private int velikost_kontejneru=70;
    private int mezera=20;
    private int itemArrayListSize;
    private int blockArrayListSize;

    public CreativeContainerEntity() {
        super(null, "creative_container_entity", GameRegistry.getInstance().getItems().size()+GameRegistry.getInstance().getBlocks().size()+1);
        GameRegistry gameRegistry = GameRegistry.getInstance();
        ArrayList<Item> itemArrayList = gameRegistry.getItems();
        ArrayList<Block> blockArrayList = gameRegistry.getBlocks();
        itemArrayListSize=itemArrayList.size();
        blockArrayListSize=blockArrayList.size();

        posX= NC.WIDTH/2-(itemArrayListSize<blockArrayListSize?blockArrayListSize*(velikost_kontejneru+mezera)+mezera:itemArrayListSize*(velikost_kontejneru+mezera)+mezera)/2;
        posY=5;

        for (int i = 0; i < itemArrayListSize; i++) {
            itemStacks[i]= new ItemStack(itemArrayList.get(i));
            containers[i]=new Container(posX+i*(velikost_kontejneru+mezera)+mezera,posY+mezera,velikost_kontejneru,getID(),itemStacks,i);
        }
        posY+=velikost_kontejneru+mezera;
        for (int i = itemArrayListSize; i < itemArrayListSize+blockArrayListSize; i++) {
            itemStacks[i]= (blockArrayList.get(i-itemArrayList.size()).getItemFromBlock());
            containers[i]=new Container(posX+(i-itemArrayList.size())*(velikost_kontejneru+mezera)+mezera,posY+mezera,velikost_kontejneru,getID(),itemStacks,i);
        }
        posY+=velikost_kontejneru+mezera;

        itemStacks[itemStacks.length-1]=new ItemStack(Items.trash);
        containers[containers.length-1]=new Container(posX+mezera,posY+mezera,velikost_kontejneru,getID(),itemStacks,containers.length-1);

        posY-=2*(velikost_kontejneru+mezera);
        rectangle = new Rectangle(posX,posY,itemArrayListSize<blockArrayListSize?blockArrayListSize*(velikost_kontejneru+mezera)+mezera:itemArrayListSize*(velikost_kontejneru+mezera)+mezera,2*(velikost_kontejneru+mezera)+mezera);
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        g.setColor(new Color(47, 27, 15));
        g.fillRoundRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 5, 5);
        g.setColor(new Color(19, 46, 16));
        int meziokraj=5;
        g.fillRoundRect(rectangle.x + meziokraj, rectangle.y + meziokraj, rectangle.width - meziokraj * 2, rectangle.height - meziokraj * 2, 5, 5);
        int posY=this.posY;
        for (int i = 0; i < itemArrayListSize; i++) {
            g.setColor(new Color(47, 27, 15));
            g.fillRoundRect(posX + i * (velikost_kontejneru + mezera) + mezera-meziokraj, posY + mezera-meziokraj, velikost_kontejneru+meziokraj*2, velikost_kontejneru+meziokraj*2, 8, 8);
            g.setColor(new Color(19, 50, 16));
            g.fillRoundRect(posX + i * (velikost_kontejneru + mezera) + mezera, posY + mezera, velikost_kontejneru, velikost_kontejneru, 5, 5);
        }
        posY+=velikost_kontejneru+mezera;
        for (int i = itemArrayListSize; i < itemArrayListSize+blockArrayListSize; i++) {
            g.setColor(new Color(47, 27, 15));
            g.fillRoundRect(posX + (i - itemArrayListSize) * (velikost_kontejneru + mezera) + mezera - meziokraj, posY + mezera - meziokraj, velikost_kontejneru + meziokraj * 2, velikost_kontejneru + meziokraj * 2, 8, 8);
            g.setColor(new Color(48, 50, 49));
            g.fillRoundRect(posX + (i - itemArrayListSize) * (velikost_kontejneru + mezera) + mezera, posY + mezera, velikost_kontejneru, velikost_kontejneru, 5, 5);


        }
    }

    @Override
    public ItemStack addToContainer(Container container, ItemStack itemStack) {
        if(!itemStack.isMax())
            return itemStack.addPocet(1);
        return itemStack;
    }

    @Override
    public ItemStack getFromContainer(Container container, int pocet) {
        if(container.getIndex()==containers.length-1){
            return null;//item z kose nelze
        }
        return container.getItemStack().copy().setPocet(1);
    }

    @Override
    public ItemStack prohod(Container container, ItemStack itemStack) {
        if(container.getIndex()==containers.length-1){
            return null;//vyhodit item
        }
        return itemStack;
    }
}
