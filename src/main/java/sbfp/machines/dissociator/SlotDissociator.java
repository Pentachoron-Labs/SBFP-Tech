package sbfp.machines.dissociator;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 * 
 */
public class SlotDissociator extends Slot{

    public SlotDissociator(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack i){
        return false;
    }

}
