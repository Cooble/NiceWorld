package cs.cooble.nice.gameloading;


import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.Matej;
import cs.cooble.nice.graphic.*;
import cs.cooble.nice.objects.IPaintLocable;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.util.NBT;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


/**
 * Created by Matej on 4.2.2015.
 */
public class TileChunk implements IPaintLocable {//todo true underblocks
    public final Location poziceChunku;
    private UnderBlock[] underBlocks = new UnderBlock[9];
    private static  BufferedImage bufferedImage = new BufferedImage(NC.CHUNK_SIZE/3,NC.CHUNK_SIZE/3,BufferedImage.TYPE_INT_ARGB);
    private static  Image image =new Image(buildI());

    private static Texture buildI(){
        try {
            return BufferedImageUtil.getTexture(null, bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TileChunk(Location poziceChunku) {
        this.poziceChunku = poziceChunku;
    }

    public void setUnderBlock(int x, int y, UnderBlock underBlock) {
        underBlocks[y * 3 + x] = underBlock;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public Rectangle getShape() {
        return null;
    }

    @Override
    public void render(int xx, int yy, NGraphics g) {
        int dimension = NC.CHUNK_SIZE/3;
        // int pictureWidth = 320;
        int pictureWidth = dimension;

        int posX = poziceChunku.X*NC.CHUNK_SIZE-xx;
        int posY = poziceChunku.Y*NC.CHUNK_SIZE-yy;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                UnderBlock block = underBlocks[y * 3 + x];
                int underX = poziceChunku.X*NC.CHUNK_TILES+x;
                int underY = poziceChunku.Y*NC.CHUNK_TILES+y;
                if (block != null) {
                    g.drawIcon(block.getIcon(),x*dimension+posX,y*dimension+posY,pictureWidth,pictureWidth);

                    //left
                    UnderBlock neightbour = NiceWorld.getNiceWorld().getWorld().getUnderBlock(underX-1,underY);
                    if(neightbour!=null) {
                        if (neightbour.getLevel() < block.getLevel()) {

                            try {
                                Graphics imageG = image.getGraphics();
                                imageG.clear();
                                imageG.clearAlphaMap();

                                GL11.glColorMask(false, false, false, true);
                                GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ZERO);
                                imageG.drawImage(NiceWorld.getNiceWorld().getRenderer().getAtlas().getTexture(UnderBlock.getBorderLeft()),0,0);
                                GL11.glColorMask(true, true, true, true);
                                GL11.glBlendFunc(GL11.GL_DST_ALPHA, GL11.GL_ZERO);
                                imageG.drawImage(NiceWorld.getNiceWorld().getRenderer().getAtlas().getTexture(block.getIcon()), 0, 0);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                                //image.set
                                imageG.flush();

                                g.g().drawImage(image, (x - 1) * dimension + posX, (y) * dimension + posY,pictureWidth,0, 0, pictureWidth);
                            } catch (SlickException e) {
                                e.printStackTrace();
                            }

                            /** setting alpha channel ready so lights add up instead of clipping */

                        }
                    }
                    //right
                  /*  neightbour = NiceWorld.getNiceWorld().getWorld().getUnderBlock(underX+1,underY);
                    if(neightbour!=null) {
                        if (neightbour.getLevel() < block.getLevel()) {
                              g.drawIcon(UnderBlock.getBorderLeft(),(x+1)*dimension+posX,(y)*dimension+posY,pictureWidth,pictureWidth,false,false);
                        }
                    }
                    //up
                    neightbour = NiceWorld.getNiceWorld().getWorld().getUnderBlock(underX,underY-1);
                    if(neightbour!=null) {
                        if (neightbour.getLevel() < block.getLevel()) {
                            g.rotate((float) (-90));
                            g.drawIcon(UnderBlock.getBorderLeft(),(x)*dimension+posX,(y-1)*dimension+posY,pictureWidth,pictureWidth,false,false);
                        }
                    }  //down
                    neightbour = NiceWorld.getNiceWorld().getWorld().getUnderBlock(underX,underY+1);
                    if(neightbour!=null) {
                        if (neightbour.getLevel() < block.getLevel()) {
                            g.rotate((float) 90);
                            g.drawIcon(UnderBlock.getBorderLeft(),(x)*dimension+posX,(y+1)*dimension+posY,pictureWidth,pictureWidth,false,false);
                        }
                    }*/
                }
            }
        }
    }




    public NBT serialize() {
        NBT out = new NBT();
        out.setNBT("location", poziceChunku.serialize());

        for (int i = 0; i < underBlocks.length; i++) {
            out.setString("" + i, underBlocks[i] == null ? "null":underBlocks[i].getID());
        }
        return out;
    }

    public static TileChunk deserialize(NBT nbt) {

        TileChunk tileChunk = new TileChunk(Location.deserialize(nbt.getNBT("location")));
        for (int i = 0; i < tileChunk.underBlocks.length; i++) {
            tileChunk.underBlocks[i] = GameRegistry.getInstance().getUnderBlock(nbt.getString(""+i));
        }
        return tileChunk;
    }

    @Override
    public boolean isFlatty() {
        return true;
    }

    public int size() {
        return underBlocks.length;
    }

    public UnderBlock getUnderBlock(int x, int y) {
        int index = y*3+x;
        if(index>0&&index<underBlocks.length){
            return underBlocks[index];
        }
        return null;
    }
}
