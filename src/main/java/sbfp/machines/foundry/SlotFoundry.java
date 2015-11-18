package sbfp.machines.foundry;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

/**
 *
 * 
 */
public class SlotFoundry extends Slot{
    
    public SlotFoundry(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack i){
            return modsbfp.foundrySmeltingRegistry.isItemProcessable(i);
    }
   
}
