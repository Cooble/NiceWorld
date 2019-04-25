package cs.cooble.nice.graphic;


import cs.cooble.nice.logger.Log;
import org.newdawn.slick.Image;
import org.newdawn.slick.util.BufferedImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matej on 25.1.2015.
 */
public class ImageManager {

    Map<String, Image> map = new HashMap<>();

    private static final String TEXTURES =     "/textures/";
    private static final String SUFFIX =      ".png";

    public static final String BLOCK=   "block/";
    public static final String ENTITY=  "entity/";
    public static final String GUI =    "gui/";
    public static final String ITEM =   "item/";
    public static final String PARTICLE="particle/";


    public void setEntityTextureName(String name){
       map.put(name,getFullImage(TEXTURES + name + SUFFIX));
    }

    public void setObjektTextureName(String name){
        map.put(name,getFullImage(TEXTURES + name + SUFFIX));
    }

    public static Image getImage(String name) {
       return getFullImage(TEXTURES+name+SUFFIX);
    }

    public static Image getFullImage(String fullURL){
        try {
           // InputStream is = ImageManager.class.getResourceAsStream("/textures/block/kos.png");
           InputStream is = ImageManager.class.getResourceAsStream(fullURL);
           // System.out.println("inputstream is:"+is);
          //  System.out.println("wantishbutthis:"+fullURL);
            BufferedImage bufferedImage = ImageIO.read(is);
            return new Image(BufferedImageUtil.getTexture(fullURL, bufferedImage));

        } catch (Exception e) {
            Log.println("Cannot load image from "+fullURL, Log.LogType.ERROR);
           // e.printStackTrace();
        }
        return null;
    }

}
