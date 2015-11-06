package sbfp.machines.solar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import sbfp.flux.IFluxStorageItem;
import sbfp.machines.ContainerSB;
import sbfp.machines.IFluxInventory;
import sbfp.machines.IMaterialProcess;
import sbfp.machines.IProcessor;
import sbfp.modsbfp;

public class TileEntitySolarCharger extends TileEntity implements IProcessor, IFluxInventory, IUpdatePlayerListBox {

    private final int INPUT_STACK_NUMBER = 4;
    private final int OUTPUT_STACK_NUMBER = 4;
    
    private int workTicks = 0;
    private long ticks = 0;
    private ContainerSB container;
    public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
    protected IMaterialProcess activeProcess;
    protected List<ItemStack> waitingOutputs;
    protected boolean hasItem;
    private ItemStack[] inventory = new ItemStack[10];

    @Override
    public void mergeOutputs() {
        this.container.mergeItemStack(this.waitingOutputs.get(0), 40, 44, false, false);
    }

    @Override
    public boolean dryMergeAndFeed() {
        for (int i = 0; i < INPUT_STACK_NUMBER; i++) {
            if (this.inventory[i] != null) {
                this.activeProcess = modsbfp.solarInfusionRegistry.getProcessesByInputs(this.inventory[i]).get(0);
                this.waitingOutputs = this.activeProcess.getOutputs();
                if (this.activeProcess != null) {
                    if (this.container.dryMerge(this.waitingOutputs.get(0), 40, 44, false) >= this.waitingOutputs.get(0).stackSize) {
                        this.decrStackSize(i, this.activeProcess.getInputs().get(0).stackSize);
                        return true;
                    }
                }
            }
        }
        this.activeProcess = null;
        this.waitingOutputs = null;
        return false;
    }

    @Override
    public void update() {
        if (this.worldObj.canBlockSeeSky(this.getPos()) && this.worldObj.isDaytime() && !this.worldObj.isRaining()) {
            
        }
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }
    
    @Override
    public int getWorkTicks(){
        return this.workTicks;
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
        return this.worldObj.getTileEntity((this.pos)) != this ? false : player.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64.0D;
    }

    public boolean isStackValidForSlot(int i, ItemStack is) {
        return this.container.getSlot(i).isItemValid(is);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound ntc3 = new NBTTagCompound();
                ntc3.setByte("slot", (byte) i);
                this.inventory[i].writeToNBT(ntc3);
                tagList.appendTag(ntc3);
            }
        }
        tagCompound.setTag("items", tagList);

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        if(tagCompound.hasKey("activeRecipe")) this.activeProcess = modsbfp.solarInfusionRegistry.getProcessByName(tagCompound.getString("activeRecipe"));
        NBTTagCompound tag;
        NBTTagList items = tagCompound.getTagList("items", 10);
        for (int i = 0; i < items.tagCount(); i++) {
            tag = items.getCompoundTagAt(i);
            this.inventory[tag.getByte("slot")] = ItemStack.loadItemStackFromNBT(tag);
        }

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
        this.playersUsing.add(player);

    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.playersUsing.remove(player);

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

    @Override
    public IMaterialProcess getActiveProcess() {
        return this.activeProcess;
    }

    @Override
    public void activate() {
        
    }

    @Override
    public int drainFluxFromSlot(int index, int deltaFlux) {
        return 0;
    }

    @Override
    public int addFluxToSlot(int index, int deltaFlux) {
        if(this.inventory[index] == null){
            return 0;
        }
        IFluxStorageItem fluxItem = (IFluxStorageItem) this.inventory[index].getItem();
        
        return fluxItem.addFlux(this.inventory[index], deltaFlux);
    }

    @Override
    public ContainerSB setContainer(ContainerSB container) {
        this.container = container;
        return this.container;
    }
    
}
