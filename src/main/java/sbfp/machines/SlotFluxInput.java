package sbfp.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sbfp.flux.IFluxSourceItem;

public class SlotFluxInput extends Slot {

    public SlotFluxInput(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        if (!(is.getItem() instanceof IFluxSourceItem)) {
            return false;
        }
        return ((IFluxSourceItem) is.getItem()).isSubtypeValidFluxSource(is.getMetadata());
    }
    
}
