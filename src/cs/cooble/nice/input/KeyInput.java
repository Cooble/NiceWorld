package cs.cooble.nice.input;

import cs.cooble.nice.event.Tickable;
import javafx.scene.input.KeyCode;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

/**
 * Created by Matej on 2.1.2017.
 */
public class KeyInput implements KeyListener,Tickable {
    @Override
    public void keyPressed(int i, char c) {
        keys[i].setPressed(true);
    }

    @Override
    public void keyReleased(int i, char c) {
        keys[i].setPressed(false);
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
    Key[] keys;

    public KeyInput() {
        keys = new Key[1024];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new Key();
        }
    }

    @Override
    public void tick() {
        for (Key key : keys) {
            if (key.tickspressed != -1)
                key.tickspressed++;
        }
    }

    public static class Key {
        public Key() {}

        public boolean isPressed;
        public short tickspressed = -1;

        public boolean wasFreshlyReleased() {
            return tickspressed == -2;
        }

        public boolean isPressed() {
            return isPressed;
        }

        public int getTickspressed() {
            return tickspressed;
        }

        protected void setPressed(boolean pressed) {
            if (pressed) {
                if (tickspressed == -1)
                    tickspressed = 0;
            } else {
                if (tickspressed > 0) {//was switch on
                    tickspressed = -3;//freshly switched off
                } else {
                    tickspressed = -1;
                }
            }
            isPressed = pressed;
        }

        public boolean isFreshedPressed() {
            return tickspressed == 1;
        }
    }

    public boolean isPressed(int keycode) {
        return keys[keycode].isPressed();
    }

    public boolean isfreshedPressed(int keycode) {
        return keys[keycode].isFreshedPressed();
    }

    public int getTicksOn(int keycode) {
        return keys[keycode].getTickspressed();
    }

}
