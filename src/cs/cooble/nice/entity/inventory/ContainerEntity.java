package cs.cooble.nice.entity.inventory;

import cs.cooble.nice.inventory.ContainerRegister;
import cs.cooble.nice.inventory.IInventory;
import cs.cooble.nice.util.ItemstackUtil;
import cs.cooble.nice.util.NBTSaveable;
import cs.cooble.nice.core.World;
import cs.cooble.nice.entity.gui.GuiEntity;
import cs.cooble.nice.entity.Matej;
import cs.cooble.nice.graphic.IIcon;
import cs.cooble.nice.graphic.ImageManager;
import cs.cooble.nice.graphic.TextureLoader;
import cs.cooble.nice.inventory.Container;
import cs.cooble.nice.inventory.Inventory;
import cs.cooble.nice.inventory.items.ItemStack;
import cs.cooble.nice.util.NBT;
import cs.cooble.nice.util.Location;

import java.awt.*;

/**
 * Created by Matej on 17.3.2015.
 */
public abstract class ContainerEntity extends GuiEntity implements IInventory, NBTSaveable {

    protected int posX, posY;
    public Container[] containers;
    protected ItemStack[] itemStacks;
    protected Matej matej;
    protected IIcon iconSlot;
    /**
     * if true -> itemstacks will be saved
     */
    protected boolean defaultItemStackSave=false;

    public ContainerEntity(Rectangle rectangle, String id, int pocetContaineru) {
        super(rectangle, id);
        containers = new Container[pocetContaineru];
        itemStacks = new ItemStack[pocetContaineru];
    }

    @Override
    public void loadTextures(TextureLoader loader) {
        iconSlot = loader.getIcon(ImageManager.GUI + "slot");
    }

    public void setMatej(Matej matej) {
        this.matej = matej;
    }

    @Override
    public void registerContainers(ContainerRegister containerRegister) {
        for (Container container : containers) {
            containerRegister.registerContainer(container);
        }
    }

    @Override
    public void onUpdate(World world) {
    }

    @Override
    public ItemStack addToContainer(Container container, ItemStack itemStack) {
        if (container.hasItemStack()) {
            ItemStack containerStack = container.getItemStack();
            int soucet = containerStack.getPocet() + itemStack.getPocet();
            if (soucet > itemStack.getMaxPocet()) {
                container.getItemStack().setPocet(containerStack.getMaxPocet());
                return itemStack.setPocet(soucet - containerStack.getMaxPocet());
            } else {
                containerStack.addPocet(itemStack.getPocet());
                return null;
            }
        } else {
            container.setItemStack(itemStack);
            return null;
        }
    }

    @Override
    public ItemStack getFromContainer(Container container, int pocet) {
        ItemStack containerStack = container.getItemStack();
        if (container.getItemStack().getPocet() < pocet) {
            itemStacks[container.getIndex()] = null;
            return containerStack;
        } else {
            containerStack.addPocet(-pocet);
            return containerStack.copy().setPocet(pocet);
        }
    }

    @Override
    public ItemStack prohod(Container container, ItemStack itemStack) {
        ItemStack vratim = container.getItemStack().copy();
        container.setItemStack(itemStack);

        return vratim;
    }

    public ItemStack getItemStackInHand() {
        if (Inventory.getInstance().hasActiveItem())
            return Inventory.getInstance().getActiveItem();

        return itemStacks[0];
    }

    @Override
    public ItemStack addToSomeFreeSlot(ItemStack itemStack) {
        for (Container container : containers) {
            if (container.hasItemStack()) {
                if (container.getItemStack().equalsType(itemStack) && !container.getItemStack().isMax()) {
                    ItemStack vracen = addToContainer(container, itemStack);
                    if (vracen == null)
                        return null;

                    else itemStack = vracen;
                }
            }
        }
        for (Container container1 : containers) {
            if (!container1.hasItemStack()) {
                ItemStack vracen = addToContainer(container1, itemStack);
                if (vracen == null)
                    return null;
                else {
                    itemStack = vracen;
                }

            }
        }
        return itemStack;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void click(Location location, boolean prave_tlacitko) {
    }

    @Override
    public void mouseMove(Location location) {

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ContainerEntity) {
            return ((ContainerEntity) o).getID().equals(getID());
        }
        return false;
    }

    @Override
    public int getPriority() {
        return 1;
    }



    public boolean hasTolikItemu(ItemStack item, int pocet) {
        int poc = 0;
        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null)
                if (itemStacks[i].equalsType(item)) {
                    poc += itemStacks[i].getPocet();
                    if (poc >= pocet)
                        return true;
                }
        }
        return false;
    }

    public void odeberTolikItemu(ItemStack item, int pocet) {
        int zbyvaodebrat = pocet;
        for (int i = 0; i < itemStacks.length; i++) {
            ItemStack itemStack = itemStacks[i];
            if (itemStack != null)
                if (itemStack.equalsType(item)) {
                    if (zbyvaodebrat - itemStack.getPocet() < 0) {
                        int rozdil = Math.abs(zbyvaodebrat - itemStack.getPocet());
                        itemStack.setPocet(rozdil);
                        zbyvaodebrat = 0;
                    } else {
                        zbyvaodebrat -= itemStack.getPocet();
                        itemStack.setPocet(0);
                    }
                }
        }
    }

    @Override
    public void writeToNBT(NBT nbt) {
        if(!defaultItemStackSave)
            return;
       nbt.setNBT("data", ItemstackUtil.serialize(itemStacks));
    }

    @Override
    public void readFromNBT(NBT nbt) {
        if(!defaultItemStackSave)
            return;
        itemStacks = ItemstackUtil.deserialize(nbt.getNBT("data"));


    }
}


