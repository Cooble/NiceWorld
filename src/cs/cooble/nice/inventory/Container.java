package cs.cooble.nice.inventory;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.util.Popisky;
import org.newdawn.slick.Color;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Matej on 14.2.2015.
 */
public class Container implements Serializable {
    private int posX;
    private int posY;
    public final int minVelikost;
    public boolean shouldPaint = false;
    private String ownerID;
    private ItemStack[] kupaItemStacku;
    private int index;
    private Location cursorLoc;
    private int maxVelikost;
    private int localVelikost;
    private boolean shouldgetbigger;

    public Container(int posX, int posY, int velikost, String ownerID, ItemStack[] kupaItemStacku1, int index) {
        this.posX = posX;
        this.posY = posY;
        this.minVelikost = velikost;
        this.ownerID = ownerID;
        kupaItemStacku = kupaItemStacku1;
        this.index = index;
        maxVelikost = velikost + 10;
        localVelikost = minVelikost;
        shouldgetbigger = true;
    }

    public void switchBiggeringOn(boolean b) {
        shouldgetbigger = b;
        if (!b) {
            maxVelikost = minVelikost;
            localVelikost = maxVelikost;
        }
    }

    //GETTERS============================================================================
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle getRectangle() {
        return new Rectangle(posX, posY, localVelikost, localVelikost);
    }

    public ItemStack getItemStack() {
        return kupaItemStacku[index];
    }

    public String getOwnerID() {
        return ownerID;
    }

    public boolean hasItemStack() {
        if (kupaItemStacku[index] != null) {
            if (kupaItemStacku[index].getPocet() > 0)
                return true;
            else kupaItemStacku[index] = null;

        }
        return false;
    }

    public int getVelikost() {
        return minVelikost;
    }

    public void setCursorLocation(Location cursorLocation) {
        cursorLoc = cursorLocation;
    }

    //SETTERS============================================================================
    public void setPos(Location l) {
        posX = l.X;
        posY = l.Y;
    }

    public void setItemStack(ItemStack itemStack) {
        kupaItemStacku[index] = itemStack;
        if (itemStack != null) {
            localVelikost = maxVelikost;
        }

    }

    public void paintPozadi(NGraphics g) {
        if (shouldPaint) {
            g.g().setColor(new Color(255, 255, 255, 100));
            g.g().fillRoundRect(posX - (localVelikost - minVelikost) / 2, posY - (localVelikost - minVelikost) / 2, localVelikost, localVelikost, 20, 20);
        }

    }

    public void paintItem(NGraphics g) {
        int mensio = 5;
        refreshVelikost();
        if (hasItemStack()) {
            ItemStack itemStack = getItemStack();
            IIcon icon = itemStack.ITEM.getIIcon(itemStack);
            if (icon != null)
                g.drawIcon(icon, posX - (localVelikost - minVelikost) / 2 + mensio, posY - (localVelikost - minVelikost) / 2 + mensio, localVelikost - mensio * 2, localVelikost - mensio * 2);
            if (itemStack.getPocet() > 1) {
              /*  int radius = 8;
                g.g().setColor(new Color(0,0,0));
                g.g().fillOval((int) (posX + 0.71 * localVelikost)+g.fontWidth(itemStack.getPocet()+"")/2-radius,(int) (posY + 0.9 * localVelikost - g.fontHeight()/2)-radius,radius*2,radius*2);
               */
                g.setFont(NC.fontName, Font.BOLD, 15);
                g.g().setColor(Color.white);
                g.g().drawString(itemStack.getPocet() + "", (int) (posX + 0.71 * localVelikost), (int) (posY + 0.9 * localVelikost - g.fontHeight()));
            }


            if (shouldPaint) {
                if (cursorLoc != null) {
                    Popisky.paintPopisek(g, cursorLoc, getItemStack().ITEM.getColorizedText(getItemStack()));
                }
            }
            if (itemStack.getPocet() == 0) {
                setItemStack(null);
            }
        }

    }

    public int getIndex() {
        return index;
    }

    private void refreshVelikost() {
        if (shouldgetbigger) {
            if (shouldPaint) {
                if (localVelikost < maxVelikost)
                    localVelikost += 2;
            } else if (localVelikost > minVelikost)
                localVelikost -= 2;
        }
    }
}
