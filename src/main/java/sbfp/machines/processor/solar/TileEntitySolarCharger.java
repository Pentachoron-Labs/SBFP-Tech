package sbfp.machines.processor.solar;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import sbfp.modsbfp;
import sbfp.machines.processor.TileEntityProcessor;

public class TileEntitySolarCharger extends TileEntityProcessor implements IInventory {
    
    private final int INPUT_STACK_NUMBER = 4;
    private final int OUTPUT_STACK_NUMBER = 4;
    private ItemStack[] inventory = new ItemStack[8];

    @Override
    protected void mergeOutputs() {
        this.container.mergeItemStack(this.waitingOutputs.get(0), 40, 44, false, false);
    }

    @Override
    protected boolean feedAndDryMergeOutputs() {
        for (int i = 0; i < INPUT_STACK_NUMBER; i++) {
            if (this.inventory[i] != null) {
                this.activeRecipe = modsbfp.solarInfusionRegistry.getProcessesByInputs(this.inventory[i]).get(0);
                this.waitingOutputs = this.activeRecipe.getOutputs();
                if (this.activeRecipe != null) {
                    if (this.container.dryMerge(this.waitingOutputs.get(0), 40, 44, false) >= this.waitingOutputs.get(0).stackSize) {
                        this.decrStackSize(i, this.activeRecipe.getInputs().get(0).stackSize);
                        return true;
                    }
                }
            }
        }
        this.activeRecipe = null;
        this.waitingOutputs = null;
        return false;
    }

    @Override
    public void update() {
        if (this.worldObj.canBlockSeeSky(this.getPos())&&this.worldObj.isDaytime() && !this.worldObj.isRaining()) {
            super.update();
        }
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int slot, int num) {
        if (this.inventory[slot] != null) {
            ItemStack stack;
            if (this.inventory[slot].stackSize <= num) {
                stack = this.inventory[slot];
                this.inventory[slot] = null;
                return stack;
            } else {
                stack = this.inventory[slot].splitStack(num);
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
            ItemStack stack = this.inventory[index];
            this.inventory[index] = null;
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }
        this.inventory[index] = stack;
    }

    public boolean isInvNameLocalized() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity((this.pos)) != this ? false : player.getDistanceSq(this.getX() + 0.5D, this.getY() + 0.5D, this.getZ() + 0.5D) <= 64.0D;
    }

    public boolean isStackValidForSlot(int i, ItemStack is) {
        return this.container.getSlot(i).isItemValid(is);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

    }

    @Override
    public boolean hasCustomName() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

    }

    @Override
    public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getField(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setField(int id, int value) {
		// TODO Auto-generated method stub

    }

    @Override
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clear() {
		// TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        return "solar collector";
    }
}
