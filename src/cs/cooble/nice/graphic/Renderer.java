package cs.cooble.nice.graphic;

import cs.cooble.nice.core.F3;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.logger.Log;
import cs.cooble.nice.objects.IPaintLocable;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.awt.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matej on 25.1.2015.
 */
public final class Renderer implements Renderable {
    private ArrayList<IPaintLocable> list = new ArrayList<>();
    private ArrayList<IRenderable> GUIList = new ArrayList<>();
    private double SIZE_FACTOR = 1;
    private Atlas atlas;
    private Image flat;

    public Renderer() {
        atlas = new Atlas();
        flat = ImageManager.getImage("flat");
    }

    private Map<String, TrueTypeFont> fonts = new HashMap<>();


    public org.newdawn.slick.Font deriveFont(String fontName, int style, int size) {
        String s = fontName + style + "_" + size;
        TrueTypeFont f = fonts.get(s);
        if (f != null)
            return f;
        f = new TrueTypeFont(new Font(fontName, style, size), true);
        fonts.put(s, f);
        return f;
    }


    public void paintGUI(Graphics g, NGraphics nGraphics) {
        //Pracuje se tridenim na priority 0=veprsotred, 1=nejprve, 2=naposled nakreslit
        ArrayList<IRenderable> priorgui = new ArrayList<>();
        ArrayList<IRenderable> secondgui = new ArrayList<>();
        ArrayList<IRenderable> lastgui = new ArrayList<>();
        ArrayList<IRenderable> xlastgui = new ArrayList<>();

        for (IRenderable toPaint : GUIList) {
            switch (toPaint.getPriority()) {
                case 0:
                    secondgui.add(toPaint);
                    break;
                case 1:
                    priorgui.add(toPaint);
                    break;
                case 2:
                    lastgui.add(toPaint);
                    break;
                case 3:
                    xlastgui.add(toPaint);
            }
        }

        for (IRenderable aPriorgui : priorgui) {
            aPriorgui.render(0, 0, nGraphics);
        }
        for (IRenderable aSecondgui : secondgui) {
            aSecondgui.render(0, 0, nGraphics);
        }
        for (IRenderable aLastgui : lastgui) {
            aLastgui.render(0, 0, nGraphics);
        }
        for (IRenderable aXlastgui : xlastgui) {
            aXlastgui.render(0, 0, nGraphics);
        }
    }

    /**
     * Vykreslí všechny objekty přihlášené v CM.
     * Stará se taky o vykreslovaní s F3, a sorting nonFlattyObjektů.
     *
     * @param g
     */
    public void render(Graphics g) {
        NGraphics nGraphics = new NGraphics(g, atlas);
        g.setColor(new Color(255, 190, 70));
        g.fillRect(0, 0, NC.WIDTH, NC.HEIGHT);
        g.setFont(deriveFont("Arial", Font.PLAIN, 10));


        PAINT:
        {
            int nakreslilo = 0;
            int xroh = Kamera.getXCoord();
            int yroh = Kamera.getYCoord();
            int presOkraj = 200;
            ArrayList<IPaintLocable> listik = new ArrayList<>();
            Color backColor = new Color(0, 0, 0, 75);
            for (IPaintLocable thisis : list) {
                Rectangle shape =thisis.getShape();
                if (thisis.isFlatty()) {
                    if (F3.isActive()) {
                        g.setColor(backColor);
                        if(shape!=null)
                        g.fillRect(shape.x - xroh, shape.y-yroh, shape.width, shape.height);
                    }
                    thisis.render(xroh, yroh, nGraphics);
                } else
                    listik.add(thisis);
                nakreslilo++;
            }

          //  Log.println("Sorting listik with size of "+listik.size());
             IPaintLocable[] sorted = sortIt(listik);//todo disabled sorting renderer layers

            for (IPaintLocable thisis : sorted) {
                if (F3.isActive()) {
                    g.setColor(backColor);
                    Rectangle shape = thisis.getShape();
                    g.fillRect(shape.x - xroh, shape.y-yroh, shape.width, shape.height);
                }
                thisis.render(xroh, yroh, nGraphics);

            }
            F3.početKreslenýchVěcí = nakreslilo;
        }
        if (F3.isActive()) {
            drawWeb(g);
            drawStats(g);
        }


        paintGUI(g, nGraphics);


    }

    private void insertionSort(IPaintLocable[] list) {
        int j;
        IPaintLocable item;
        for (int i = 1; i <= (list.length - 1); i++) {
            // ulozeni prvku
            item = list[i];
            j = i - 1;
            while ((j >= 0) && (list[j].getSpodek() > item.getSpodek())) {
                list[j + 1] = list[j];
                j--;
            }
            list[j + 1] = item;
        }
    }

    /**
     * Registruje Objekt u CM pro vykeslování.
     *
     * @param paintable objekt
     */
    public void registerPaintable(IPaintLocable paintable) {
        list.add(paintable);
    }

    /**
     * Odeber objekt paintable.
     *
     * @param paintable
     */
    public void removePaintable(IPaintLocable paintable) {
        for (int i = 0; i < list.size(); i++) {
            IPaintLocable tis = list.get(i);
            if (paintable.equals(tis)) {
                list.remove(i);
                return;
            }
        }
    }

    private IPaintLocable[] sortIt(ArrayList<IPaintLocable> list) {
        IPaintLocable[] out = list.toArray(new IPaintLocable[list.size()]);
        insertionSort(out);
        return out;
    }

    public void addToList(ArrayList<? extends IPaintLocable> list) {
        this.list.addAll(list);
    }

    private void drawWeb(Graphics g) {
       /* int websize = 80;
        g.setColor(new Color(255, 255, 255, 10));
        int width = (int) ((double) NC.WIDTH / websize);
        int height = (int) ((double) NC.HEIGHT / websize);
        for (int i = 0; i < width; i++) {
            for (int u = 0; u < height; u++) {
                g.fillRect(i * websize - Kamera.getXCoord() % websize, u * websize - Kamera.getYCoord() % websize, NC.CHUNK_SIZE, 2);
                g.fillRect(i * websize - Kamera.getXCoord() % websize, u * websize - Kamera.getYCoord() % websize, 2, NC.CHUNK_SIZE);
            }
        }*/

        g.setColor(new Color(0, 0, 0));

        int chunkX =( Kamera.getXCoord()+NC.WIDTH/2)/NC.CHUNK_SIZE;
        int chunkY =( Kamera.getYCoord()+NC.HEIGHT)/ NC.CHUNK_SIZE;

        for(int i = -2;i<3;i++){
            g.fillRect(0,(chunkY+i)*NC.CHUNK_SIZE-Kamera.getYCoord(),NC.WIDTH, 2);
            g.fillRect((chunkX+i)*NC.CHUNK_SIZE-Kamera.getXCoord(),0,2, NC.HEIGHT);
        }
        for(int i = -2;i<3;i++){
            g.fillRect(0,(chunkY+i)*NC.CHUNK_SIZE-Kamera.getYCoord(),NC.WIDTH, 2);
            g.fillRect((chunkX+i)*NC.CHUNK_SIZE-Kamera.getXCoord(),0,2, NC.HEIGHT);
        }
    }


    private void drawStats(Graphics g) {
        int mezi = 20;
        int x = (int) (NC.WIDTH * 0.85);

        g.setFont(deriveFont(NC.fontGUIName, Font.PLAIN, 20));
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(x - mezi, 0, (NC.WIDTH / 10 * 4), 10 * mezi);
        g.setColor(new Color(0xDCD1FF));

        g.drawString("Nactených chunků: " + F3.pocetNactenychChunku, x, mezi);
        g.drawString("Kreslených objek: " + F3.početKreslenýchVěcí, x, 2 * mezi);
        g.drawString("Nactenych objektu: " + F3.početObjektu, x, 3 * mezi);
        g.drawString("Nactenych entit: " + F3.početEntit, x, 4 * mezi);
        g.drawString("Nactených tileChunků: " + F3.pocetNactenychTileChunku, x, 5 * mezi);
        g.drawString("Currect Chunk: " + (F3.curChunk != null ? (F3.curChunk.X + " : " + F3.curChunk.Y) : "None"), mezi, mezi);
        g.drawString("FPS: " + F3.FPS, x, 6 * mezi);
        g.drawString("TPS: " + F3.TPS, x, 7 * mezi);
        g.drawString("" + F3.mouseWorldLoc, x, 8 * mezi);


    }

    public int getPocet() {
        return list.size();
    }

    public void registerGUI(IRenderable paintable) {
        GUIList.add(paintable);
    }

    public boolean removeGUI(IRenderable paintable) {
        for (int i = 0; i < GUIList.size(); i++) {
            if (GUIList.get(i).equals(paintable)) {
                GUIList.remove(i);
                return true;
            }

        }
        return false;
    }
    public void clearAll() {
        list.clear();
        GUIList.clear();
    }

    public Atlas getAtlas() {
        return atlas;
    }
}

