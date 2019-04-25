package cs.cooble.nice.entity.inventory;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.inventory.items.Items;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;


/**
 * Created by Matej on 11.8.2015.
 */
public class KosContainerEntity extends ContainerEntity {

    private static int width=100,height=100,mezera=100;
    private static int posX=NC.WIDTH-width-mezera;
    private static int posY=NC.HEIGHT-height-mezera;

    public KosContainerEntity() {
        super(new Rectangle(posX,posY,width,height), "kos_container_entity", 1);
        containers[0]= new Container(posX+10,posY+10,80,getID(),itemStacks,0);
        itemStacks[0]=new ItemStack(Items.trash);

    }


    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        int mezirkakolem=5;
        g.setColor(new Color(47, 27, 15));
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(new Color(19, 46, 16));
        g.fillRect(rectangle.x + mezirkakolem, rectangle.y + mezirkakolem, rectangle.width - 2 * mezirkakolem, rectangle.height - 2 * mezirkakolem);
    }

    @Override
    public ItemStack prohod(Container container, ItemStack itemStack) {
        return null;
    }

    @Override
    public ItemStack getFromContainer(Container container, int pocet) {
        return null;
    }
}
