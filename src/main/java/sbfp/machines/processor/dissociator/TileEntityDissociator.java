package sbfp.machines.processor.dissociator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import sbfp.machines.processor.TileEntityProcessor;

/**
 *
 * 
 */
public class TileEntityDissociator extends TileEntityProcessor implements IInventory{

    @Override
    protected void mergeOutputs() {
        
    }

    @Override
    protected boolean dryMergeOutputsAndFeed() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }

    

}
