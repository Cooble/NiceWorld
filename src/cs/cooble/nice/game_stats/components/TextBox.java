package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;

import java.awt.*;


/**
 * Created by Matej on 16.6.2015.
 */
public class TextBox extends Text {
   // private int maxDelka,radku;
    private String[] textik;
    private int pole_index;
    private boolean shouldcentred;



    public TextBox(Color color1, String text, Location pos, Location dimension,int max_delka) {
        super(color1, text, pos, dimension);
        textik=new String[20];
        pole_index=0;

        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i)==' '&&textik[pole_index].length()>=max_delka)
                pole_index++;

            textik[pole_index]+=text.charAt(i);

        }
        shouldcentred=true;
    }
    public TextBox(Color color1, String[] text, Location pos,boolean shouldbecentred) {
        super(color1, "", pos, pos);
        textik=text;
        pole_index=text.length;
        shouldcentred=shouldbecentred;
    }


    @Override
    public void render(int x, int y, NGraphics g) {
        g.g().setColor(color);
        g.setFont(NC.fontName,Font.PLAIN,20);
        for (int i = 0; i < textik.length; i++) {
            if(textik[i]!=null) {
                if (shouldcentred)
                    g.g().drawString(textik[i], posX - g.fontWidth(textik[0]) / 2, posY + i * g.fontHeight());

                else g.g().drawString(textik[i], posX, posY + i * g.fontHeight());
            }

        }
    }
}
