package cs.cooble.nice.blocks;

import cs.cooble.nice.core.GameRegistry;
import cs.cooble.nice.entity.inventory.ContainerEntity;
import cs.cooble.nice.util.NBT;

/**
 * Created by Matej on 6.4.2018.
 */
public abstract class BlockContainer extends Block {
    protected ContainerEntity container;

    public BlockContainer(String id) {
        super(id);
    }

    @Override
    public void writeToNBT(NBT nbt) {
        super.writeToNBT(nbt);
        NBT b = new NBT();
        container.writeToNBT(b);
        nbt.setNBT("container_entity",b);
    }

    @Override
    public void readFromNBT(NBT nbt) {
        super.readFromNBT(nbt);
        container = (ContainerEntity) GameRegistry.getInstance().buildGUI(nbt.getNBT("container_entity"));
    }
}
