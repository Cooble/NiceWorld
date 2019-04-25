package cs.cooble.nice.graphic;

import cs.cooble.nice.logger.Log;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.imageout.ImageIOWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by Matej on 22.9.2017.
 */
public class Atlas implements TextureLoader {

    Map<String, Icon> icons;
    Image map;
    private boolean raw = true;

    public Atlas() {
        icons = new HashMap<>();
    }


    /**
     * loads and bakes all textures to one big map
     */
    public void bake() {
        //loading images and counting needed dimensions for bigmap
        HashMap<String, Image> loaded = new HashMap<>();
        Icon[] sorted = new Icon[icons.size()];
        final int[] index = {0};
        icons.forEach(new BiConsumer<String, Icon>() {
            @Override
            public void accept(String s, Icon iIcon) {
                String fileName = iIcon.getID();

                try {
                    Image image = ImageManager.getImage(fileName);
                    iIcon.width = image.getWidth();
                    iIcon.height = image.getHeight();
                    loaded.put(s, image);
                    sorted[index[0]]=iIcon;
                    index[0]++;


                } catch (Exception e) {
                    Log.println("CANNOT LOAD IMAGE: " + fileName + "\n", Log.LogType.ERROR);
                   // e.printStackTrace();
                }

            }
        });
        Sorter.sort(sorted);
        Sorter.invert(sorted);
        try {
            map=buildAtlas(sorted,loaded,900,900);
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.println("Atlas baked with dimensions: ["+map.getWidth()+", "+map.getHeight()+"]");
        pictureExport(map, "mapka");
        raw = false;//it has been baked, cannot load more icons
    }
    private static Image buildAtlas(Icon[] array,Map<String,Image> images,int width,int height) throws SlickException {
        Nod nod = new Nod(0, 0, width, height);
        for (Icon icon : array) {
            if (!fit(icon, nod)) {
               // Log.println("Too small atlas dimensions!", Log.LogType.ERROR);
                return buildAtlas(array,images,width+50,height+50);
            }
        }
        Image image = new Image(width, height);
        Graphics g = image.getGraphics();
        for (Icon icon : array) {
            g.drawImage(images.get(icon.getID()), icon.x, icon.y);
        }
        g.flush();
        return image;
    }
    private static boolean fit(Icon icon,Nod nod){
        if(!nod.isDead()&!nod.isFinal()){
            if(nod.isOccupied()){
                if(fit(icon,nod.n1))
                    return true;
                else return fit(icon,nod.n2);

            }else{
                return nod.fit(icon.width,icon.height,icon);
            }
        }
        return false;
    }
    static class Nod{
        private final Icon IMAGE;
        Rectangle main;
        Nod n1,n2;
        Nod(int x,int y,int width,int height){
            this(x, y, width, height,null);
        }
        Nod(int x,int y,int width,int height,Icon image){
            main = new Rectangle(x,y,width,height);
            this.IMAGE = image;
            if(IMAGE!=null){
                IMAGE.x=main.x;
                IMAGE.y=main.y;
            }
        }

        /**
         *
         * @param width
         * @param height
         * @param IMAGE
         * @return false if it cannot fit, true if it was put in
         */
        boolean fit(int width,int height,Icon IMAGE){
            if(width>main.width||height>main.height)
                return false;
            Nod up = new Nod(main.x, main.y, main.width,height);
            Nod down = new Nod(main.x, main.y+height, main.width,main.height-height);
            n1=up;
            n2=down;
            Nod left = new Nod(main.x,main.y,width,height,IMAGE);
            Nod right = new Nod(main.x+width,main.y,main.width-width,height);
            up.n1=left;
            up.n2=right;
            return true;

        }
        boolean isOccupied(){
            return n1!=null;
        }
        boolean isFinal(){
            return IMAGE!=null;
        }
        boolean isDead(){
            return main.width<1||main.height<1;
        }
    }

    private static void pictureExport(Image image, String name) {
        ImageIOWriter writer = new ImageIOWriter();
        File imageOutputFile = new File(new File("C:\\Users\\Matej\\Desktop"), name + ".png");
        FileOutputStream imageOutput = null;
        try {
            imageOutput = new FileOutputStream(imageOutputFile);
            writer.saveImage(image, "png", imageOutput, true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                imageOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public IIcon getIcon(String imagePath) {
        if (icons.containsKey(imagePath)) {
            return icons.get(imagePath);
        }
        if (raw) {
            Icon icon = new Icon(imagePath);
            icons.put(imagePath, icon);
            //new Exception("getting icon: "+imagePath).printStackTrace();
            return icon;
        } else {
            Log.println("Getting non existing icon: "+imagePath, Log.LogType.ERROR);
            return null;
        }
    }

    @Override
    public PolyIcon getIcon(String imagePath, int size) {

        PolyIcon icon = new PolyIcon(imagePath, size);
        ArrayList<IIcon> icons = new ArrayList<>();
        if (size == 1) {
            icons.add(getIcon(imagePath));
        } else
            for (int i = 0; i < size; i++) {
                icons.add(getIcon(imagePath + "_" + i));
            }
        if (icons.size() == 0)
            Log.println("Getting polyicon which does not exist!", Log.LogType.ERROR);
        icon.setList(icons);
        return icon;
    }

    public Image getMap() {
        return map;
    }


    public Image getTexture(IIcon icon) {
        return map.getSubImage(icon.getX(), icon.getY(), icon.getWidth(), icon.getHeight());
    }

    public Image getTexture(String icon) {
        return getTexture(icons.get(icon));
    }

    public Image getImage() {
        return map;
    }
}
