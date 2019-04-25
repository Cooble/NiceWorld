package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.core.NC;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.Font;


/**
 * Created by Matej on 18.2.2015.
 */
public class ShiftCudlik extends Cudlik {
    private int mezi;
    private double Velikost;
    private int X;
    private boolean isActive=false;
    private int velikostKoule;
    private IIcon latka,koulicka;
    public ShiftCudlik(int posX, int posY, int sirka, int vyska, String name, double velikost, ICudlik cudlik) {
        super(posX, posY, sirka, vyska, name, "", cudlik);
        mezi=sirka-40;
        Velikost=velikost;
        velikostKoule=vyska;
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        super.loadTextures(loader);
        latka=loader.getIcon("gui/latka");
        koulicka=loader.getIcon("gui/koulicka");

    }

    @Override
    public void pressed(Location location) {
        isActive=true;
        update(location);
        cudlik.pressed(location);
        MPlayer.playSound(MPlayer.CUDLIK+"click");
        cudlik.onSetData(Velikost);

    }

    @Override
    public void moved(Location location) {
        if(isActive) {
            update(location);
            cudlik.moved(location);
            cudlik.onSetData(Velikost);
        }
    }

    @Override
    public void released(Location location) {
        if(isActive) {
            update(location);
            cudlik.onSetData(Velikost);
            cudlik.released(location);
        }
        isActive=false;
    }
    private void update(Location location){
        if(isActive) {
            X = location.X - posX-velikostKoule/2;
            double velko = (double) X / mezi;
            if (velko > 1.000)
                Velikost = 1;
            else if (velko < 0.000)
                Velikost = 0;
            else
                Velikost = velko;
        }
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        Graphics gg = g.g();
        g.drawIcon(latka,posX,posY,sirka,vyska);
        g.drawIcon(koulicka,20 + posX + (int) (mezi * Velikost) - velikostKoule / 2, posY, velikostKoule, velikostKoule);
        g.setFont(NC.fontName,Font.BOLD,30);
        gg.setColor(Color.blue);
        gg.drawString(name, posX - g.fontWidth(name)-5, posY +vyska/2+g.fontHeight()/4);
        gg.setColor(Color.white);
        gg.drawString((int)(Velikost*100)+"%",posX+sirka, posY +vyska/2+g.fontHeight()/4);
    }
}
