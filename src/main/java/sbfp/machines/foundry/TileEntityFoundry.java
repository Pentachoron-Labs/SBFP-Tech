package sbfp.machines.foundry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import sbfp.flux.IFluxSourceItem;
import sbfp.machines.ContainerSB;
import sbfp.machines.IFluxInventory;
import sbfp.machines.IMaterialProcess;
import sbfp.machines.IProcessor;

/**
 *
 *
 */
public class TileEntityFoundry extends TileEntity implements IProcessor, IFluxInventory, IUpdatePlayerListBox {

    private int workTicks = 0;
    private long ticks = 0;
    private ContainerSB container;
    public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
    protected IMaterialProcess activeProcess;
    protected List<ItemStack> waitingOutputs;
    protected boolean hasItem;
    private ItemStack[] inventory = new ItemStack[10]; //0-3 = inputs, 4-7 = outputs, 8&9 = flux slots

    private static final int maxFluxLevel = 400;

    private int fluxLevel = 0;

    @Override
    public void update() {
        if (this.ticks % 60 == 0) {
            this.worldObj.markBlockForUpdate(this.pos);
        }
        if (this.inventory[8] != null && this.fluxLevel < maxFluxLevel) {
            this.fluxLevel += this.drainFluxFromSlot(8, maxFluxLevel - this.fluxLevel);
        } else if (this.inventory[9] != null && this.fluxLevel < maxFluxLevel) {
            this.fluxLevel += this.drainFluxFromSlot(9, maxFluxLevel - this.fluxLevel);
        }
        if (this.fluxLevel >= maxFluxLevel) {
            this.fluxLevel = maxFluxLevel;
        }
    }

    @Override
    public void mergeOutputs() {

    }

    @Override
    public IMaterialProcess getActiveProcess() {
        return this.activeProcess;
    }

    @Override
    public void activate() {
    }

    @Override
    public int getWorkTicks() {
        return this.workTicks;
    }

    @Override
    public boolean dryMergeAndFeed() {
        return false;
    }

    public int getFluxLevel() {
        return this.fluxLevel;
    }

    @Override
    public ContainerSB setContainer(ContainerSB c) {
        this.container = c;
        return this.container;
    }

    @Override
    public int drainFluxFromSlot(int index, int deltaF) throws ClassCastException {
        if (this.inventory[index] == null) {
            return 0;
        }
        IFluxSourceItem fluxItem = (IFluxSourceItem) this.inventory[index].getItem();
        int amount = fluxItem.drainFlux(this.inventory[index], deltaF);
        if (fluxItem.destroyOnDrain()) {
            this.decrStackSize(index, 1);
        }
        return amount;
    }

    @Override
    public int addFluxToSlot(int slotID, int deltaF) {
        return 0;
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

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) != this ? false : player.getDistanceSq(this.getPos().add(0.5D, 0.5D, 0.5D)) <= 64.0D;
    }

}
