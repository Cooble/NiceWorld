package cs.cooble.nice.inventory;

import cs.cooble.nice.core.World;
import cs.cooble.nice.graphic.IRenderable;
import cs.cooble.nice.graphic.NGraphics;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Matej on 14.2.2015.
 */
public class Inventory implements Serializable, IRenderable, ContainerRegister {

    public static Inventory getInstance() {
        return ourInstance;
    }

    public static Inventory createInstance() {
        ourInstance = new Inventory();
        return getInstance();

    }

    private Inventory() {
        containers = new ArrayList<>();
        containerEntities = new ArrayList<>();
        ItemStack[] policko = new ItemStack[1];
        policko[0] = null;
        activeSlot = new Container(0, 0, 70, "active_slot", policko, 0);
        activeSlot.switchBiggeringOn(false);
    }

    private static Inventory ourInstance = new Inventory();


    private ArrayList<Container> containers;
    private ArrayList<IInventory> containerEntities;
    private Container activeSlot;


    //TILE=ENTITY======================================================================
    public void registerContainerEntity(IInventory tileEntity) {
        for (IInventory iTileEntity : containerEntities) {
            if (iTileEntity.getID().equals(tileEntity.getID()))
                return;
        }
        containerEntities.add(tileEntity);
        tileEntity.registerContainers(this);
    }

    public IInventory getContainerEntity(String ID) {
        for (int i = 0; i < containerEntities.size(); i++) {
            if (containerEntities.get(i).getID().equals(ID))
                return containerEntities.get(i);
        }
        return null;
    }

    public void removeContainerEntity(String ID) {
        for (int i = 0; i < containerEntities.size(); i++) {
            if (containerEntities.get(i).getID().equals(ID)) {//pokud existuje tileentity s id
                containerEntities.remove(i);

                for (int j = containers.size() - 1; j >= 0; j--) {//vymazat vsechny tileentyitovy conejnery
                    if (containers.get(j).getOwnerID().equals(ID))
                        containers.remove(j);
                }
                return;
            }
        }
    }

    public boolean existContainerEntity(String ID) {
        for (int i = 0; i < containerEntities.size(); i++) {
            if (containerEntities.get(i).getID() == ID)
                return true;
        }
        return false;
    }

    public void clearAllContainerEntities() {
        containerEntities.clear();
    }


    //PRIVATE===================================================================
    private Container getContainer(Location location) {
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).getRectangle().contains(location.X, location.Y)) {
                return containers.get(i);
            }
        }
        return null;
    }

    private IInventory getContainerEntity(Container container) {
        for (int i = 0; i < containerEntities.size(); i++) {
            if (containerEntities.get(i).getID().equals(container.getOwnerID()))
                return containerEntities.get(i);
        }
        System.err.println("INVENTORY getContainerEntity() chyba trying to get container entity which coresponds to foreign container");
        return null;
    }


    //MISCELANIUS==================================================================

    /**
     * called every tick in the game
     *
     * @param l
     */
    public void onMouseMoved(Location l) {
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).getRectangle().contains(l.X, l.Y)) {
                containers.get(i).shouldPaint = true;
                containers.get(i).setCursorLocation(l);
            } else containers.get(i).shouldPaint = false;
        }
        if (hasActiveItem()) {
            activeSlot.setPos(new Location(l.X - activeSlot.getVelikost() / 2, l.Y - activeSlot.getVelikost() / 2));
        }
    }

    @Override
    public void render(int h, int h1, NGraphics g) {
        for (int i = 0; i < containers.size(); i++) {
            Container container = containers.get(i);
            container.paintPozadi(g);
            container.paintItem(g);
        }
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).shouldPaint) {
                Container container = containers.get(i);
                container.paintPozadi(g);
                container.paintItem(g);
            }
        }
        activeSlot.paintPozadi(g);
        activeSlot.paintItem(g);
    }

    /**
     * Voláno jako první při kliknutí.
     *
     * @param l kamseclicklo.
     * @return pokud se kliklo na container return true.
     */
    public boolean click(World world, Location l, boolean right) {
        if (getContainer(l) == null)
            return false;

        activeSlot.setPos(new Location(l.X - activeSlot.getVelikost() / 2, l.Y - activeSlot.getVelikost() / 2));
        Container container = getContainer(l);
        IInventory entityContainer = getContainerEntity(container);

        if (hasActiveItem()) {//pokud klikám s itemem
            if (container.hasItemStack()) {//pokud klikam s itemem na item
                if (container.getItemStack().equalsType(getActiveItem())) {//pokud klikám s itemem na ten samý typ itemu
                    if (right) {
                        if (!container.getItemStack().isMax()) {
                            ItemStack copy = getActiveItem().copy();
                            copy.setPocet(1);
                            ItemStack vraceno = entityContainer.addToContainer(container, copy);//dám mu jeden kousek z toho co mam v ruce
                            if (vraceno == null) {
                                getActiveItem().addPocet(-1);//pokud prijmul, odeberu si kousek ze svého
                            }
                        }

                    } //else if (clickCount == 2) {}

                    else
                        setActiveItem(entityContainer.addToContainer(container, getActiveItem()));//dám mu vsechno co mam v ruce

                } else {//prohodím je mezi sebou
                    ItemStack vraceno = entityContainer.prohod(container, getActiveItem());
                    setActiveItem(vraceno);

                }
            } else {//pokud nebude klikat na item(ale v ruce bude mit item)
                if (right) {
                    ItemStack actionCopy = getActiveItem().copy();
                    ItemStack vraceno = entityContainer.addToContainer(container, actionCopy.setPocet(1));
                    if (vraceno == null) {
                        getActiveItem().addPocet(-1);
                    }
                } else {
                    /*if (clickCount == 2) {
                        if (!getActiveItem().isMax()) {
                            //addItemsToMax(activeSlot, getActiveItem());
                        }
                    } else */
                    setActiveItem(entityContainer.addToContainer(container, getActiveItem()));
                }
            }
        } else {//pokud klikám bez itemu
            if (container.hasItemStack()) {//pokud klikám s prázddnou rukou na item
                if (right) {
                    int chcipocet = container.getItemStack().getPocet() / 2;
                    ItemStack vraceno = entityContainer.getFromContainer(container, chcipocet == 0 ? 1 : chcipocet);
                    setActiveItem(vraceno);

                } //else if (clickCount == 2) {
                //,addItemsToMax(activeSlot, container.getItemStack().copy());
                else {
                    setActiveItem(entityContainer.getFromContainer(container, 1000));
                }
            }
        }
        return true;
    }

    public void clickToNothing(World world, Location l, boolean right) {
        if (right) {
            if (hasActiveItem())
                if (getActiveItem().getPocet() > 0) {
                    getActiveItem().addPocet(-1);
                    ItemStack throwe = getActiveItem().copy();
                    throwe.setPocet(1);
                    world.getMatej().throwItemStack(world, throwe);
                }
        }
    }

    //ACTIVE=ITEM========================================================================
    public boolean hasActiveItem() {
        return activeSlot.hasItemStack();
    }

    public ItemStack getActiveItem() {
        return activeSlot.getItemStack();
    }

    public void setActiveItem(ItemStack itemStack) {
        activeSlot.setItemStack(itemStack);
    }

    @Override
    public void registerContainer(Container container) {
        containers.add(container);
    }

    @Override
    public int getPriority() {
        return 3;
    }

    public void update(World w) {
        for (IInventory containerEntity : containerEntities) {
            containerEntity.onUpdate(w);
        }
    }
}
