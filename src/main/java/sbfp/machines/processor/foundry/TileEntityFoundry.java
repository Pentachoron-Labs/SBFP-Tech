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
    
    private static final int maxChargeLevel = 400;
    
    private int powerLevel = 0;
    private int fluxOverflow = 0;
    
    @Override
    public void update() {
        super.update();
        if (this.ticks % 20 == 0) {
            this.worldObj.markBlockForUpdate(this.pos);
        }
        if (this.inventory[8] != null && this.powerLevel < maxChargeLevel) {
            this.decrStackSize(8, 1);
            this.powerLevel += 10;
        } else if (this.inventory[9] != null && this.powerLevel < maxChargeLevel) {
            this.decrStackSize(9, 1);
            this.powerLevel += 10;
        }
        if (this.powerLevel >= maxChargeLevel) {
            this.fluxOverflow = powerLevel - maxChargeLevel;
            this.powerLevel = maxChargeLevel;
        }
    }
    
    @Override
    protected void mergeOutputs() {
        
    }

    @Override
    protected boolean feedAndDryMergeOutputs() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return 10;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
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
        if (this.inventory[index] != null) {
            ItemStack var2 = this.inventory[index];
            this.inventory[index] = null;
            return var2;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.inventory[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
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
        return this.container.getSlot(index).isItemValid(stack);
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
        return "Foundry";
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
