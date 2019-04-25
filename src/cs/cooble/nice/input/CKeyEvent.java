package cs.cooble.nice.input;


import cs.cooble.nice.event.Tickable;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Matej on 16.12.2015.
 */
public final class CKeyEvent implements KeyListener, Tickable {
    Key[] keys;

    public CKeyEvent() {
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
        public Key() {
        }

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

        public void setPressed(boolean pressed) {
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


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()].setPressed(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()].setPressed(false);

    }
}
