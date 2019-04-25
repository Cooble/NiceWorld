package cs.cooble.nice.graphic;

import cs.cooble.nice.util.Location;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Statická třída, starající se o práci s obrázky, umí je otocit, prevratit
 */
public final class NCPainter {

    /**
     *
     * @param image
     * @param x prevratit podle osy x
     * @param y prevratit podle osy y
     * @return prevraceny obrázek
     */
    public static Image getPrevracenyObrazek(Image image, boolean x, boolean y){
        if(!x&&!y)
            return image;

      return createFlipped(image,x);
    }
    /**
     *
     * @param image
     * @return obrzek prevraceny v ose x
     */
    public static Image getPrevracenyObrazek(Image image){
        return getPrevracenyObrazek(image, true, false);
    }

    /**
     *
     * @param zdroj
     * @param radians
     * @param otocPoint
     * @return otoceny obrazek o radians
     */
    public static Image otocHo(Image zdroj,double radians,Location otocPoint){
        return createRotated(zdroj,(float)(radians*180),otocPoint);
    }
    /**
     *
     * @param image
     * @param radians
     * @return otoceny obrazek o radians
     */
    public static Image otocHo(Image image,double radians){
        return otocHo(image,radians,new Location(image.getWidth()/2,image.getHeight()/2));
    }

   /* public static Image getPostavu(Image image,Color postavaColor){
        BufferedImage clearBufferedImage = getClearBufferedImage(image);

        Raster raster = toBufferedImage(image).getAlphaRaster();
        if (raster != null) {
            int[] alphaPixel = new int[raster.getNumBands()];
            for (int x = 0; x < raster.getWidth(); x++) {
                for (int y = 0; y < raster.getHeight(); y++) {
                    raster.getPixel(x, y, alphaPixel);

                    if(alphaPixel[0] != 0x00){
                        clearBufferedImage.setRGB(x, y, postavaColor.getRGB());
                    }
                }
            }
        }
        return clearBufferedImage;
    }
*/
    public static Image orezejObrazek(Image image,int x,int y, int width,int height){
        boolean valid=true;
        if(width<1)
            valid=false;
        if(height<1)
            valid=false;
        if(!valid)
            throw new IllegalArgumentException("Width ("+width+") and Height ("+height+") cannot be 0");
        try {
            Image out = new Image(width,height);
            Graphics g = out.getGraphics();
            g.drawImage(image,x,y,x+width,y+height,0,0,width,height);
            return out;
        } catch (SlickException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static Image createRotated(Image image, float angle,Location origin) {
        try {
            Image out = new Image(image.getWidth(), image.getHeight());
            Graphics g = out.getGraphics();
            g.rotate(origin.X,origin.Y, angle);
            g.drawImage(image, 0, 0);
            g.flush();
            return out;

        } catch (SlickException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Image createFlipped(Image image, boolean vertical) {
        image = image.copy();
        image.setFilter(0);
        return image.getFlippedCopy(!vertical, vertical);
    }
}
