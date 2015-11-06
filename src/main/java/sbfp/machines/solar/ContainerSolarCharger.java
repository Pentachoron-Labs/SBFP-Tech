package sbfp.machines.solar;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import sbfp.machines.SlotOutput;
import sbfp.machines.ContainerSB;

public class ContainerSolarCharger extends ContainerSB{

    public ContainerSolarCharger(InventoryPlayer inv, TileEntitySolarCharger tileEntity) {
        super(inv, tileEntity);
        this.addSlotToContainer(new SlotSolarCharger(tileEntity, 0, 8, 32));
        this.addSlotToContainer(new SlotSolarCharger(tileEntity, 1, 26, 32));
        this.addSlotToContainer(new SlotSolarCharger(tileEntity, 2, 8, 50));
        this.addSlotToContainer(new SlotSolarCharger(tileEntity, 3, 26, 50));
        this.addSlotToContainer(new SlotOutput(tileEntity, 4, 134, 32));
        this.addSlotToContainer(new SlotOutput(tileEntity, 5, 152, 32));
        this.addSlotToContainer(new SlotOutput(tileEntity, 6, 134, 50));
        this.addSlotToContainer(new SlotOutput(tileEntity, 7, 152, 50));
        this.addSlotToContainer(new Slot(tileEntity, 8, 134, 72));
        this.addSlotToContainer(new Slot(tileEntity, 9, 152, 72));
    }
}