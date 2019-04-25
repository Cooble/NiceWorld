package cs.cooble.nice.entity.inventory;

import cs.cooble.nice.entity.IMoveable;
import cs.cooble.nice.entity.Mover;
import cs.cooble.nice.core.NiceWorld;
import cs.cooble.nice.graphic.Renderer;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.input.ActionRectangles;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.Inventory;
import cs.cooble.nice.inventory.Okinko;
import cs.cooble.nice.inventory.CraftingRegistry;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.objects.ILocaleable;
import cs.cooble.nice.util.Location;
import org.newdawn.slick.Color;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Matej on 22.3.2015.
 */
public final class CraftingContainerEntity extends ContainerEntity implements ILocaleable, IMoveable {


    private int posX;
    private int posY;
    private int velikost;
    private int mezera;
    private int vyska, sirka;
    private ArrayList<CraftingRegistry.Crafting> list;
    private Okinko[] TabOkinkos;
    private Okinko[] okinkos;
    private int indexofclickedOkinko;
    private Okinko[] surky;
    private CraftingRegistry.CraftingTab[] craftingTabs;
    private CraftingRegistry.CraftingTab currecttab;
    private int vetsivelikost;

    private Mover moverWrapper;
    private boolean isMoving;

    /**
     * @return true if this is moving and cant be switched on or off (show / hide)
     */
    public boolean isMoving() {
        return isMoving;
    }

    public CraftingContainerEntity() {
        super(null, "crafting_container_entity", 0);
        mezera = 10;
        posX = -100;
        posY = mezera;
        velikost = 60;
        sirka = 2 * mezera + velikost;
        indexofclickedOkinko = -1;
        itemStacks = new ItemStack[0];
        containers = new Container[0];
        okinkos = new Okinko[0];
        surky = new Okinko[0];

        ///tabs/////////////////////////////////////////////
        craftingTabs = CraftingRegistry.getCraftingTabs();
        TabOkinkos = new Okinko[craftingTabs.length];
        vetsivelikost = this.velikost + 10;


        vyska = mezera + craftingTabs.length * (mezera + vetsivelikost);

        rectangle = new Rectangle(posX, posY, sirka, vyska);
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        super.loadTextures(loader);
        for (int i = 0; i < craftingTabs.length; i++) {
            TabOkinkos[i] = new Okinko(posX, posY + i * (vetsivelikost + mezera) + mezera, vetsivelikost, craftingTabs[i].NAME, loader.getIcon(craftingTabs[i].textureName), i, this, new Color(0x274479), new Color(0x810500));
        }
    }

    /**
     * nacte grafiku s pomoci presouvace
     */
    @Override
    public void onGuiEntityStartDrawing(Renderer renderer) {
        if (!isMoving) {
            //TODO DO SOMTHING WITH MOVER
            moverWrapper = new Mover(() -> isMoving = false, this, new Location(mezera, mezera), 10);
            NiceWorld.getNiceWorld().getUpdateableObserver().addUpdateable(moverWrapper);
            isMoving = true;

            startDrawing(renderer);
        }
    }

    /**
     * nacte grafiku
     *
     * @param renderer
     */
    private void startDrawing(Renderer renderer) {
        for (Okinko okinko : TabOkinkos) {
            ActionRectangles.getInstance().registerActionRectangle(okinko);
            renderer.registerGUI(okinko);
        }
    }


    /**
     * Ukonci grafiku pomoci presouvace
     */
    @Override
    public void onGuiEntityStopDrawing(Renderer renderer) {
        if (!isMoving) {
            isMoving = true;
            moverWrapper = new Mover(() -> stopDrawing(renderer), this, new Location(-300, mezera), 10);
            NiceWorld.getNiceWorld().getUpdateableObserver().addUpdateable(moverWrapper);
        }
    }

    /**
     * Ukonci grafiku
     */
    private void stopDrawing(Renderer renderer) {
        isMoving = false;
        renderer.removeGUI(this);

        for (Okinko okinko : TabOkinkos) {
            ActionRectangles.getInstance().removeActionRectangle(okinko);
            renderer.removeGUI(okinko);
        }
        for (Okinko okinko : okinkos) {
            ActionRectangles.getInstance().removeActionRectangle(okinko);
            renderer.removeGUI(okinko);
        }
        for (Okinko okinko : surky) {
            ActionRectangles.getInstance().removeActionRectangle(okinko);
            renderer.removeGUI(okinko);
        }
    }

    @Override
    public void clickElsewhere() {

    }

    @Override
    public void mousewheeled(Location location, int otocka) {

    }

    @Override
    public String getID() {
        return "craftingtileentity";
    }

    public void clickedOnOkinko(boolean isCraftingTab, int index) {
        Renderer renderer = NiceWorld.getNiceWorld().getRenderer();
        if (isCraftingTab) {
            currecttab = craftingTabs[index];
            loadCraftings(CraftingRegistry.getCraftings(currecttab));
            return;
        }

        if (!Inventory.getInstance().hasActiveItem()) {
            list = CraftingRegistry.getCraftings(currecttab);
            CraftingRegistry.Crafting crafting = list.get(index);
            Okinko zdroj = okinkos[index];
            if (index == indexofclickedOkinko) {
                ContainerEntity matej = this.matej.getContainerEntity();
                for (int i = 0; i < crafting.RESOURCES.length; i++) {
                    if (!matej.hasTolikItemu(crafting.RESOURCES[i], crafting.RESOURCES[i].getPocet()))
                        return;
                }
                for (int i = 0; i < crafting.RESOURCES.length; i++) {
                    matej.odeberTolikItemu(crafting.RESOURCES[i], crafting.RESOURCES[i].getPocet());
                }
                Inventory.getInstance().setActiveItem(crafting.OUTPUT.copy());
            } else {//surky
                indexofclickedOkinko = index;
                for (Okinko aSurky : surky) {//vymaze stare surky
                    ActionRectangles.getInstance().removeActionRectangle(aSurky);
                    renderer.removeGUI(aSurky);
                }
                surky = new Okinko[crafting.RESOURCES.length];
                int velikost = this.velikost - 15;
                for (int i = 0; i < surky.length; i++) {//nahradi je novymi
                    surky[i] = new Okinko(zdroj.getPosX() + (i + 1) * (velikost + mezera) + 2 * mezera, zdroj.getPosY() + 7, velikost, crafting.RESOURCES, i, null, new Color(0x5EA33B), new Color(0x885A31));
                    ActionRectangles.getInstance().registerActionRectangle(surky[i]);
                    renderer.registerGUI(surky[i]);
                }
            }
        }
    }

    /***
     * nacte vsechny craftingy z listiku (vytvori okinka pole zalozi itemstaky pole, registruje je)
     *
     * @param listik craftingy které se mají registrovat
     */
    private void loadCraftings(ArrayList<CraftingRegistry.Crafting> listik) {
        Renderer renderer = NiceWorld.getNiceWorld().getRenderer();
        stopDrawing(renderer);//odstrani vsechny okinka odregistruje
        startDrawing(renderer);//prida okinka tabcraftingu

        okinkos = new Okinko[listik.size()];//nová okinka
        itemStacks = new ItemStack[listik.size()];//nove itemtsacky

        for (int i = 0; i < itemStacks.length; i++) {//strcit itemstaky do pole
            itemStacks[i] = listik.get(i).OUTPUT;

        }
        for (int i = 0; i < okinkos.length; i++) {//strcit do pole nová okinka
            okinkos[i] = new Okinko(posX + mezera + vetsivelikost, posY + i * (mezera + velikost) + mezera, velikost, itemStacks, i, this, new Color(0xFFC693), new Color(0x813500));

            ActionRectangles.getInstance().registerActionRectangle(okinkos[i]);//registrovat je
            renderer.registerGUI(okinkos[i]);
        }
        indexofclickedOkinko = -1;//aby se resetoval vyber crafting okinek
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    public void setX(int x) {
        int x1 = x - posX;
        posX = x;

        for (Okinko tabOkinko : TabOkinkos) {
            tabOkinko.addPosX(x1);

        }
        for (Okinko okinko : okinkos) {
            okinko.addPosX(x1);

        }
        for (Okinko okinko : surky) {
            okinko.addPosX(x1);
        }
    }

    @Override
    public void setY(int y) {
        int y1 = y - posY;
        posY = y;

        for (Okinko tabOkinko : TabOkinkos) {
            tabOkinko.addPosY(y1);

        }
        for (Okinko okinko : okinkos) {
            okinko.addPosY(y1);

        }
        for (Okinko okinko : surky) {
            okinko.addPosY(y1);
        }
    }

    @Override
    public Rectangle getShape() {
        return rectangle;
    }

    @Override
    public void setLocation(int x, int y) {

    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void setLocation(Location location) {

    }

    @Override
    public void setIsMoving(boolean isMoving) {

    }
}
