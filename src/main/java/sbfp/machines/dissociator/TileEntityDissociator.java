package sbfp.machines.dissociator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
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
public class TileEntityDissociator extends TileEntity implements IProcessor, IFluxInventory, IUpdatePlayerListBox {

    private int workTicks = 0;
    private long ticks = 0;
    public ContainerSB container;
    public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
    protected IMaterialProcess activeProcess;
    protected List<ItemStack> waitingOutputs;
    protected boolean hasItem;

    private ItemStack[] inventory = new ItemStack[10];

    @Override
    public void update() {
    }

    @Override
    public void mergeOutputs() {

    }

    @Override
    public boolean dryMergeAndFeed() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
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
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return this.container.getSlot(i).isItemValid(stack);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) != this ? false : player.getDistanceSq(this.getPos().add(0.5D, 0.5D, 0.5D)) <= 64.0D;
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
        return "machines.dissociator.tile.name";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
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
    public ContainerSB setContainer(ContainerSB container) {
        this.container = container;
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

}
