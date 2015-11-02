package sbfp.machines.processor.crusher;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.FMLLog;
import sbfp.flux.IFluxSourceItem;
import sbfp.machines.IFluxContainer;
import sbfp.machines.SlotOutput;
import sbfp.machines.processor.ContainerProcessor;
import sbfp.machines.SlotFluxInput;

public class ContainerCrusher extends ContainerProcessor implements IFluxContainer{

    public ContainerCrusher(InventoryPlayer inv, TileEntityCrusher tileEntity) {
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

    /**
     * 
     * @param slotID
     * @param deltaF
     * @return
     * @throws ClassCastException This means the slots aren't working properly 
     */
    @Override
    public int drainFluxFromSlot(int slotID, int deltaF) throws ClassCastException{
        Slot slot = this.getSlot(36+slotID);
        if (!slot.getHasStack()) {
            FMLLog.info("No stack in slot");
            return 0;
        }
        IFluxSourceItem fluxItem = (IFluxSourceItem) slot.getStack().getItem();
        int amount = fluxItem.drainFlux(slot.getStack(), deltaF);
        if (fluxItem.destroyOnDrain()) {
            ((IInventory)this.tileEntity).decrStackSize(slot.getSlotIndex(), 1);
        }
        return amount;
    }

    @Override
    public int addFluxToSlot(int slotID, int deltaF) {
        return 0;
    }
}
