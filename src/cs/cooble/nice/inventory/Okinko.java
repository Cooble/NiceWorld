package cs.cooble.nice.inventory;

import cs.cooble.nice.core.NC;
import cs.cooble.nice.entity.inventory.CraftingContainerEntity;
import cs.cooble.nice.game_stats.components.ColorizedText;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.input.IActionRectangle;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.Location;
import cs.cooble.nice.util.Popisky;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Matej on 14.2.2015.
 */
public class Okinko implements Serializable, IActionRectangle, IRenderable {
    private int posX;
    private int posY;
    public final int velikost;
    private ItemStack[] kupaItemStacku;
    private int index;
    private Location cursorLoc;
    private CraftingContainerEntity craftingContainerEntity;
    private boolean shouldPopisek;
    private Color fill, bounds;

    private boolean keinItem;
    private ColorizedText name;
    private transient IIcon texture;

    public Okinko(int posX, int posY, int velikost, ItemStack[] kupaItemStacku1, int index, CraftingContainerEntity tileEntity) {
        this(posX, posY, velikost, kupaItemStacku1, index, tileEntity, new Color(10, 70, 10), new Color(150, 0, 250));
    }

    public Okinko(int posX, int posY, int velikost, ItemStack[] kupaItemStacku1, int index, CraftingContainerEntity tileEntity, Color fill, Color bounds) {
        this.posX = posX;
        this.posY = posY;
        this.velikost = velikost;
        kupaItemStacku = kupaItemStacku1;
        this.index = index;
        craftingContainerEntity = tileEntity;
        shouldPopisek = false;
        this.fill = fill;
        this.bounds = bounds;
        keinItem = false;
    }

    public Okinko(int posX, int posY, int velikost, ColorizedText name, IIcon texture, int index, CraftingContainerEntity tileEntity, Color fill, Color bounds) {
        this.posX = posX;
        this.posY = posY;
        this.velikost = velikost;
        craftingContainerEntity = tileEntity;
        shouldPopisek = false;
        this.fill = fill;
        this.bounds = bounds;
        keinItem = true;
        this.name = name;
        this.texture = texture;
        this.index = index;

    }


    //GETTERS============================================================================
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle getRectangle() {
        return new Rectangle(posX, posY, velikost, velikost);
    }

    @Override
    public void click(Location location, boolean prave_tlacitko) {
        if (craftingContainerEntity != null) craftingContainerEntity.clickedOnOkinko(keinItem, index);
    }

    @Override
    public void mouseMove(Location location) {
        shouldPopisek = getRectangle().contains(location.X, location.Y);
        if (shouldPopisek) cursorLoc = location;
    }

    public ItemStack getItemStack() {
        if (keinItem)
            return null;
        return kupaItemStacku[index];
    }

    public boolean hasItemStack() {
        if (keinItem)
            return false;

        if (kupaItemStacku[index] != null) {
            if (kupaItemStacku[index].getPocet() > 0)
                return true;
            else kupaItemStacku[index] = null;

        }
        return false;
    }

    //SETTERS============================================================================
    public void addPosX(int posX) {
        this.posX += posX;
    }

    public void addPosY(int posY) {
        this.posY += posY;
    }

    public void setItemStack(ItemStack itemStack) {
        if (keinItem)
            return;
        kupaItemStacku[index] = itemStack;

    }

    public int getIndex() {
        return index;
    }

    public void setOdkaz(ItemStack[] kupaItemStacku, int index) {
        this.index = index;
        this.kupaItemStacku = kupaItemStacku;
    }

    @Override
    public void render(int x, int y, NGraphics gg) {
        int mensio = 5;
        Graphics g = gg.g();
        g.setColor(bounds);
        g.fillRect(posX, posY, velikost, velikost);
        g.setColor(fill);
        g.fillRect(posX + mensio, posY + mensio, velikost - 2 * mensio, velikost - 2 * mensio);
        if (hasItemStack()) {
            ItemStack itemStack = getItemStack();
            IIcon icon = itemStack.ITEM.getIIcon(itemStack);
            if (icon != null)
                gg.drawIcon(icon, posX + mensio, posY + mensio, velikost - mensio * 2, velikost - mensio * 2);
            g.setColor(Color.white);
            gg.setFont(NC.fontName,Font.BOLD,15);
            if (itemStack.getPocet() > 1)
                g.drawString(itemStack.getPocet() + "", (int) (posX + 0.71 * velikost), (int) (posY + 0.9 * velikost)-gg.fontHeight());
            if (shouldPopisek)
                Popisky.paintPopisek(gg, cursorLoc, getItemStack().ITEM.getColorizedText(getItemStack()));

        } else if (keinItem) {
            gg.drawIcon(texture, posX + mensio, posY + mensio, velikost - mensio * 2, velikost - mensio * 2);
            if (shouldPopisek) {
                Popisky.paintPopisek(gg, cursorLoc, name);
            }
        }

    }

    @Override
    public int getPriority() {
        return shouldPopisek ? 2 : 0;
    }
}
