package cs.cooble.nice.graphic;

import cs.cooble.nice.core.NiceWorld;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by Matej on 23.9.2017.
 */
public class NGraphics {
    private final Graphics g;
    private final Atlas atlas;
    private Color background;


    public NGraphics(Graphics g, Atlas atlas) {
        this.g = g;
        this.atlas = atlas;
    }

    public void setFont(String fontName, int style, int size) {
        g.setFont(NiceWorld.getNiceWorld().getRenderer().deriveFont(fontName, style, size));
    }


    public Graphics g() {
        return g;
    }

    public void drawIcon(IIcon icon, int x, int y) {
        drawIcon(icon, x, y, false, false);
    }

    public void drawIcon(IIcon icon, int x, int y, boolean horizontal, boolean vertical) {
        drawIcon(icon, x, y, icon.getWidth(), icon.getHeight(), horizontal, vertical);
    }

    public void drawIcon(IIcon icon, int x, int y, int width, int height, boolean horizontal, boolean vertical) {
        int x1, y1, x2, y2;
        x1 = x;
        y1 = y;
        x2 = x + width;
        y2 = y + height;

        if (horizontal) {
            int o = x1;
            x1 = x2;
            x2 = o;
        }
        if (vertical) {
            int o = y1;
            y1 = y2;
            y2 = o;
        }
        Image image = atlas.getTexture(icon);
        if (rotate) {
            if (rx == none)
                image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
            else
                image.setCenterOfRotation(rx, ry);
            image.rotate(degrees);
            rotate = false;
        }
        if (background != null) {
            image.draw(x1, y1, x2, y2, 0, 0, image.getWidth(), image.getHeight(), background);
            background = null;
        } else
            g.drawImage(image, x1, y1, x2, y2, 0, 0, image.getWidth(), image.getHeight());
    }

    public void drawIcon(IIcon icon, int x, int y, int width, int height) {
        drawIcon(icon, x, y, width, height, false);
    }

    public void drawIcon(IIcon icon, int x, int y, int width, int height, boolean horizontal) {
        drawIcon(icon, x, y, width, height, horizontal, false);
    }

    public int fontWidth(String string) {
        return g.getFont().getWidth(string);
    }

    public int fontHeight(String s) {
        if (s == null)
            return g.getFont().getLineHeight();
        return g.getFont().getHeight(s);
    }

    public int fontHeight() {
        return fontHeight(null);
    }


    private boolean rotate;
    private int rx;
    private int ry;
    private float degrees;
    private final int none = Integer.MIN_VALUE;

    /**
     * This makes translation of drawing of future image
     * next drawing of icon will be rotated!
     *
     * @param rx      center of rotation
     * @param ry      center of rotation
     * @param degrees angle
     */
    public void rotate(int rx, int ry, float degrees) {
        this.rx = rx;
        this.ry = ry;
        this.degrees = degrees;
        rotate = true;
    }

    /**
     * This makes translation of drawing of future image
     * next drawing of icon will be rotated!
     * center of rotation will be center of image
     *
     * @param degrees angle
     */
    public void rotate(float degrees) {
        this.rx = none;
        this.ry = none;
        this.degrees = degrees;
        rotate = true;
    }

    /**
     * call this before drawing icon to draw icons nonalpha pixels instead with background color
     * in fact it creates shade
     *
     * @param background
     */
    public void setBackgroundDrawing(Color background) {
        this.background = background;
    }
}
