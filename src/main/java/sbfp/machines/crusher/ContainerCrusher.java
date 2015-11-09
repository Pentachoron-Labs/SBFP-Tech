package sbfp.machines.crusher;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import sbfp.machines.SlotOutput;
import sbfp.machines.ContainerSB;
import sbfp.machines.SlotFluxInput;

public class ContainerCrusher extends ContainerSB{

    public ContainerCrusher(InventoryPlayer inv, IInventory tileEntity) {
        super(inv, tileEntity);
        this.addSlotToContainer(new SlotCrusher(tileEntity, 0, 8, 24));
        this.addSlotToContainer(new SlotCrusher(tileEntity, 1, 26, 24));
        this.addSlotToContainer(new SlotCrusher(tileEntity, 2, 8, 42));
        this.addSlotToContainer(new SlotCrusher(tileEntity, 3, 26, 42));
        this.addSlotToContainer(new SlotOutput(tileEntity, 4, 80, 24));
        this.addSlotToContainer(new SlotOutput(tileEntity, 5, 98, 24));
        this.addSlotToContainer(new SlotOutput(tileEntity, 6, 80, 46));
        this.addSlotToContainer(new SlotOutput(tileEntity, 7, 98, 46));
        this.addSlotToContainer(new SlotFluxInput(tileEntity, 8, 152, 80));
        this.addSlotToContainer(new SlotFluxInput(tileEntity, 9, 152, 98));
    }
}
