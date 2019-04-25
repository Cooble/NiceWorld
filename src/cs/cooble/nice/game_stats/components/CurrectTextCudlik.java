package cs.cooble.nice.game_stats.components;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.music.MPlayer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Created by Matej on 20.2.2015.
 */
public class CurrectTextCudlik implements KeyListener {
    private static TextCudlik textCudlik;
    private static CurrectTextCudlik instance = new CurrectTextCudlik();

    public static CurrectTextCudlik getInstance() {
        return instance;
    }

    public static void setTextCudlik(TextCudlik Cudlik) {
        if (textCudlik != null)
            textCudlik.isSelected = false;

        textCudlik = Cudlik;

        if (textCudlik != null) {
            textCudlik.isSelected = true;
        }
        NC.enteringText = isActive();
    }

    public static TextCudlik getTextCudlik() {
        return textCudlik;
    }

    public static boolean hasTextCudlik() {
        return textCudlik != null;
    }

    public static boolean isActive() {
        if (textCudlik != null)
            return textCudlik.isSelected;
        return false;
    }

    public static void setToControlText(int keyCode,char c) {
        if (textCudlik != null) {
            String cudlikT = textCudlik.getText();
            switch (keyCode) {

                case Input.KEY_BACK:
                    if (textCudlik instanceof OnlyTextCudlik)
                        textCudlik.onSetData("backspace");
                    else if (cudlikT.length() > 0) {
                        textCudlik.onSetData(cudlikT.substring(0, cudlikT.length() - 1));
                    }
                    break;
                case Input.KEY_ENTER:
                    if (textCudlik instanceof OnlyTextCudlik)
                        textCudlik.onSetData("enter");
                    setTextCudlik(null);
                    MPlayer.playSound(MPlayer.CUDLIK + "click");
                    break;

                case Input.KEY_TAB:
                    if (textCudlik instanceof OnlyTextCudlik)
                        textCudlik.onSetData("tab");
                    break;
                default:
                   // Log.println("charackty "+c+" int="+(int)c+" keyCode "+keyCode);
                    if ((int)c!=0) {
                        if (textCudlik.text.length() <= textCudlik.getMaxLength() || textCudlik.getMaxLength() == -1) {
                           // Log.println("setting text "+textCudlik.getText()+c);
                            textCudlik.onSetData(textCudlik.getText() + c);
                        }
                    }
            }

        }
    }

    @Override
    public void keyPressed(int i, char c) {
        if(isActive()){
            setToControlText(i,c);
        }
    }

    @Override
    public void keyReleased(int i, char c) {

    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
