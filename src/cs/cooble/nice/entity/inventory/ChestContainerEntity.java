package cs.cooble.nice.entity.inventory;


import cs.cooble.nice.blocks.Block;
import cs.cooble.nice.blocks.BlockChest;
import cs.cooble.nice.blocks.Blocks;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.Mover;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;


/**
 * Created by Matej on 17.3.2015.
 */
public class ChestContainerEntity extends ContainerEntity {

    public Location location;
    public static int radius=300;
    private  int mezera;
    private int velikost;

    public ChestContainerEntity(Location location) {
        super(null,"chest_tile_entity",9);
        this.location =location;
        mezera= 12;
        velikost = 55;
        posY=200;
        posX=200;
        int kousek=5;

        int op = 0;
        loop:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(op==9)
                    break loop;
                containers[op]=new Container(posX+i*(velikost+mezera)+mezera+kousek,posY+j*(velikost+mezera)+mezera+kousek,velikost-2*kousek,getID(),itemStacks,op);
                op++;
            }
        }
        rectangle = new Rectangle(posX,posY,3*(velikost+mezera)+mezera,3*(velikost+mezera)+mezera);
    }
    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();

        int mezirkakolem=5;
        g.setColor(new Color(47, 27, 15));
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(new Color(19, 46, 16));
        g.fillRect(rectangle.x+mezirkakolem, rectangle.y+mezirkakolem, rectangle.width-2*mezirkakolem, rectangle.height-2*mezirkakolem);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j <3; j++) {
                gg.drawIcon(iconSlot,posX+i*(velikost+mezera)+mezera,posY+j*(mezera+velikost)+mezera,velikost,velikost);

            }
        }

    }

    @Override
    public String getID() {
        return "chest";
    }

    @Override
    public void onUpdate(World world) {
        Block block = world.getBlockAt(location);
        if(block.getMetadata()==1) {
            Location l = NiceWorld.getNiceWorld().getWorld().getMatej().getLocation();
            if (Mover.getVzdalenostTo(l, block.getLocation()) > radius) {
                BlockChest b = (BlockChest) Blocks.chest;
               // b.setOpened(false,this,block);
            }
        }
    }
}
