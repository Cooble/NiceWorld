package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;


/**
 * Created by Matej on 20.2.2015.
 */
public class TextCudlik extends Cudlik {

    public boolean isSelected=false;
    protected String text = "";
    protected int maxLength=-1;
    public TextCudlik(int posX, int posY, int sirka, int vyska, String name, String textureName,ICudlik cudlik) {
        super(posX, posY, sirka, vyska, name, textureName, cudlik);
    }

    public void setMaxLength(int maxLength){
        this.maxLength = maxLength;
    }

    /**
     *
     * @return -1 if does not matter
     */
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public void pressed(Location location) {
        isSelected=true;
        CurrectTextCudlik.setTextCudlik(this);
    }

    @Override
    public void onSetData(Object data) {
        text=(String)data;
        cudlik.onSetData(text);
    }

    int bliktime;
    boolean blik;
    @Override
    public void render(int x, int y, NGraphics gg) {
        Graphics g = gg.g();
        refreshBlikTime();
        int sirka=this.sirka;
        int fontsirka=gg.fontWidth(text);
       if(fontsirka>sirka){
           sirka=fontsirka-10;//roztahování textboxu když zadavat delší text
       }
        g.setColor(Color.black);
        int prevys=5;
        g.fillRoundRect(posX - prevys, posY - prevys, sirka + 2 * prevys, vyska + 2 * prevys, 20, 20);
        g.setColor(isSelected?Color.white:Color.gray);
        g.fillRoundRect(posX, posY, sirka, vyska, 10, 10);
        g.setColor(new Color(0));
        g.setFont(textFont);
        g.drawString(text+(isSelected&&blik?"|":""),posX+5,posY/*+(int)(vyska*0.75)*/);
        g.drawString(text+(isSelected&&blik?"|":""),posX+5,posY/*+(int)(vyska*0.75)*/);
    }

    /**
     * blik je blikání symbolu '|' když je textcudlik selected
     */
    private void refreshBlikTime(){
        if(isSelected) {
            bliktime++;
            if (bliktime == 10) {
                bliktime = 0;
                blik = !blik;
            }
        }
    }

    @Override
    public void onClickedElswhere() {
        if(isSelected)
            if(CurrectTextCudlik.getTextCudlik().equals(this)) {
                CurrectTextCudlik.setTextCudlik(null);
                isSelected=false;
            }
    }

    public String getText() {
        return text;
    }
}
