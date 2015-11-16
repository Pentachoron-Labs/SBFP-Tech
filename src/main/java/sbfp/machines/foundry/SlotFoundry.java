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
    
    private boolean isUsable = false;
    
    public SlotFoundry(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(ItemStack i){
            return modsbfp.foundrySmeltingRegistry.isItemProcessable(i) && this.isUsable;
    }
    
    @Override
    public boolean canBeHovered(){
        return this.isUsable;
    }
    
    public void setIsUsable(boolean b){
        this.isUsable = b;
    }
    
    public boolean getIsUsable(){
        return this.isUsable;
    }
}
