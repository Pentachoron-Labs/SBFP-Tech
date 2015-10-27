package sbfp.machines.processor.foundry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import sbfp.machines.processor.TileEntityProcessor;

/**
 *
 * 
 */
public class TileEntityFoundry extends TileEntityProcessor implements IInventory{
    private ItemStack[] inventory = new ItemStack[10]; //0-3 = inputs, 4-7 = outputs, 8&9 = flux slots
    @Override
    protected void mergeOutputs() {
    }

    @Override
    protected boolean feedAndDryMergeOutputs() {
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
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.inventory[slot] != null) {
            ItemStack stack;
            if (this.inventory[slot].stackSize <= amount) {
                stack = this.inventory[slot];
                this.inventory[slot] = null;
                return stack;
            } else {
                stack = this.inventory[slot].splitStack(amount);
                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }
                return stack;
            }
        } else {
            return null;
        }
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
        return 64;
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
        this.inventory = new ItemStack[10];
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }

}
