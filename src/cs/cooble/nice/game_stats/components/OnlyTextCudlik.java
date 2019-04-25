package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.NGraphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;


/**
 * Created by Matej on 5.4.2015.
 */
public class OnlyTextCudlik extends TextCudlik {


    /**
     * determines, if this can be set data to first time. Used when you dont want to add first letter after creating instance (second will be read).
     */
    boolean canStartReading;

    public OnlyTextCudlik(int posX, int posY, int sirka, int vyska, String name, String textureName, boolean canStartReading, ICudlik cudlik) {
        super(posX, posY, sirka, vyska, name, textureName, cudlik);
        this.canStartReading=canStartReading;
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        gg.setFont(NC.fontName,Font.BOLD,27);
        g.setColor(new Color(0, 0, 0, 80));
        g.fillRect(posX, posY, gg.fontWidth(text)<350?350:gg.fontWidth(text)+1, vyska);
        g.setColor(new Color(0x00001F));
        g.drawString(text,posX + 5, posY + (int) (vyska * 0.75));
    }
    public void setText(String name){
        text=name;
    }

    @Override
    public void onSetData(Object data) {
        if (canStartReading) {
            if (!data.equals("tab") && !data.equals("enter")) {
                if (data.equals("backspace")) {
                    if (text.length() != 0)
                        text = text.substring(0, text.length() - 1);
                    cudlik.onSetData(data);
                } else {
                    text = (String) data;
                    cudlik.onSetData(text);
                }
            } else {
                cudlik.onSetData(data);

            }
        }
        else canStartReading=true;
    }

}
