package cs.cooble.nice.util;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.game_stats.components.ColorizedText;
import org.newdawn.slick.Color;

import java.awt.*;

/**
 * Created by Matej on 29.3.2015.
 */
public class Popisky {
    public static void paintPopisek(NGraphics g1,Location location,ColorizedText text) {
      g1.setFont(NC.fontGUIName,Font.PLAIN,25);
        for (int i = 0; i < text.getText().length; i++) {
            String txt = text.getText(i);
            Color color = text.getColor(i);
            g1.g().setColor(new Color(0,0,0,125));
            g1.g().fillRoundRect(location.X-g1.fontWidth(txt)/2-2,i* g1.fontHeight()+location.Y-g1.fontHeight()-3,g1.fontWidth(txt)+2,g1.fontHeight()+2,10);
            g1.g().setColor(color);
            g1.g().drawString(txt, location.X - g1.fontWidth(txt) / 2, i* g1.fontHeight()+location.Y-g1.fontHeight()-1);
        }

    }
}
