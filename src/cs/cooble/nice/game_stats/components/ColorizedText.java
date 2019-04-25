package cs.cooble.nice.game_stats.components;


import org.newdawn.slick.Color;

/**
 * Created by Matej on 29.3.2015.
 */
public class ColorizedText {
    private String[] text;
    private Color[] colors;
    public ColorizedText(String[] text,Color[] colors){
        this.text=text;
        this.colors=colors;
    }
    public static ColorizedText build(String s){
        return new ColorizedText(new String[]{s});
    }
    public ColorizedText(String... text){
        this.text=text;
        colors=new Color[text.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i]=new Color(255,255,255);

        }
    }

    public Color[] getColors() {
        return colors;
    }
    public String[] getText() {
        return text;
    }

    public Color getColor(int line){
        return colors[line];
    }
    public String getText(int line){
        return text[line];
    }
}
