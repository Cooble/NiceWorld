package cs.cooble.nice.game_stats.components;

import com.sun.istack.internal.Nullable;
import cs.cooble.nice.core.NC;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.music.MPlayer;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;

import java.awt.*;


/**
 * Created by Matej on 17.2.2015.
 */
public class Cudlik implements ICudlik,IRenderable {

    protected int posX;
    protected int posY;
    protected int sirka;
    protected int vyska;
    protected String name;
    protected final String textureName;
    protected ICudlik cudlik;
    protected boolean isActive=false;
    protected Color color;
    protected Color textColor;
    protected boolean isFocused;
    protected org.newdawn.slick.Font textFont;





    public Cudlik(int posX, int posY, int sirka, int vyska, String name, String textureName, ICudlik cudlik){
        this.posX = posX;
        this.posY = posY;
        this.sirka = sirka;
        this.vyska = vyska;
        this.name = name;
        this.textureName = textureName;
        this.cudlik=cudlik;
        color=new Color(0x04DE09);
        textColor=Color.blue;
        textFont= NiceWorld.getNiceWorld().getRenderer().deriveFont(NC.fontGUIName, Font.BOLD,27);
    }
    public Cudlik(int posX, int posY, int sirka, int vyska, String name, String textureName, Color color,ICudlik cudlik){
       this(posX,posY,sirka,vyska,name,textureName,cudlik);
        this.color=color;
    }
    public Cudlik(int posX, int posY, int sirka, int vyska, String name, String textureName, @Nullable Color color,Color textcolor,ICudlik cudlik){
        this(posX,posY,sirka,vyska,name,textureName,color,cudlik);
        this.textColor=textcolor;
        if(color==null)
            this.color=new Color(0x04DE09);
    }
    public Rectangle getRectangle(){
        return new Rectangle(posX,posY,sirka,vyska);
    }

    @Override
    public void render(int x, int y, NGraphics g) {
        //g.setColor(new Color(0x35BA3B));
        g.g().setColor(new Color(0,0,0,100));
        for (int i = 4; i > 1; i--) {
            int o = i;
            g.g().fillRoundRect(posX-o,posY-o,sirka+o*2,vyska+o*2,30,30);
        }


        g.g().setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(),200));
        for (int i = 3; i > 1; i--) {
            int o = i;
            g.g().fillRoundRect(posX+o,posY+o,sirka-o*2,vyska-o*2,30,30);
        }
        g.g().fillRoundRect(posX+(isFocused?2:0), posY+(isFocused?2:0), sirka, vyska, 30, 30);
        g.g().setColor(textColor);
        g.g().setFont(textFont);
        g.g().drawString(name, posX+sirka/2-g.fontWidth(name)/2, posY + vyska/4);

    }

    @Override
    public void moved(Location location) {
        if(isActive)
            cudlik.moved(location);
    }

    @Override
    public void pressed(Location location) {
        isActive=true;
        cudlik.pressed(location);
        MPlayer.playSound(MPlayer.CUDLIK+"click");
    }

    @Override
    public void released(Location location) {
       if(isActive) {
           cudlik.released(location);
           isActive=false;
       }
    }

    @Override
    public void focused(boolean isFocused) {
        this.isFocused=isFocused;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cudlik)
            if(((Cudlik) obj).getRectangle().equals(this.getRectangle()))
                return true;
        return false;
    }

}
