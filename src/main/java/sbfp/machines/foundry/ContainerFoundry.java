package sbfp.machines.foundry;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import sbfp.machines.ContainerSB;
import sbfp.machines.SlotFluxInput;
import sbfp.machines.SlotOutput;

/*
Container Coordinates
SMELTING OUTS: (7, [13, 37, 61, 85])
Smelting input slots - Down one, over one
(8, 14) , (8, 38) , (8, 62) , (8, 86)
Smelting output slots - Down one, over 83
(90, 14) , (90, 38) , (90, 62) , (90, 86)
Flux Slots
(135, 111) , (153, 111)
*/
public class ContainerFoundry extends ContainerSB{

    public ContainerFoundry(InventoryPlayer inv, IInventory tileEntity) {
        super(inv, tileEntity);
        
        this.addSlotToContainer(new SlotFoundry(tileEntity, 0, 8, 14));
        this.addSlotToContainer(new SlotFoundry(tileEntity, 1, 8, 38));
        this.addSlotToContainer(new SlotFoundry(tileEntity, 2, 8, 62));
        this.addSlotToContainer(new SlotFoundry(tileEntity, 3, 8, 86));
        this.addSlotToContainer(new SlotOutput(tileEntity, 4, 90, 14));
        this.addSlotToContainer(new SlotOutput(tileEntity, 5, 90, 38));
        this.addSlotToContainer(new SlotOutput(tileEntity, 6, 90, 62));
        this.addSlotToContainer(new SlotOutput(tileEntity, 7, 90, 86));
        this.addSlotToContainer(new SlotFluxInput(tileEntity, 8, 135, 111));
        this.addSlotToContainer(new SlotFluxInput(tileEntity, 9, 153, 111));
    }

}
