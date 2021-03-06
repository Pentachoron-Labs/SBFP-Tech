package sbfp.machines.foundry;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.common.FMLLog;
import sbfp.flux.IFluxSourceItem;
import sbfp.machines.ContainerSB;
import sbfp.machines.IFluxInventory;
import sbfp.machines.IMaterialProcess;
import sbfp.machines.IProcessor;
import sbfp.machines.ISubUnitProcessor;
import sbfp.modsbfp;

/**
 *
 *
 */
public class TileEntityFoundry extends TileEntity implements ISubUnitProcessor, IFluxInventory, IUpdatePlayerListBox {

    private static final int maxFluxLevel = 400;

    private long ticks = 0;
    private int fluxLevel = 0;

    private List<Foundry> smelters = Lists.newArrayList();

    private ContainerSB container;
    private FoundryStates state;

    private ItemStack[] inventory = new ItemStack[10]; //0-3 = inputs, 4-7 = outputs, 8&9 = flux slots

    public void activate() {
        for (int i = 0; i < 4; i++) {
            this.smelters.add(new Foundry(i, i + 4));
        }
    }

    @Override
    public void update() {
        if (this.ticks % 20 == 0) {
            this.worldObj.markBlockForUpdate(this.pos);
        }
        this.state = (FoundryStates) this.worldObj.getBlockState(this.pos).getValue(BlockFoundry.STATE);
        switch (this.state) {
            case CONNECTED:
                FMLLog.info(this.state.toString());
                for (Foundry f : this.smelters) {
                    f.tick();                   
                }
                break;
            case DISCONNECTED:
                FMLLog.info(this.state.toString());
                for (Foundry f : this.smelters) {
                    f.reset();
                }  
                break;
        }
        if (this.inventory[8] != null && this.fluxLevel < maxFluxLevel) {
            this.fluxLevel += this.drainFluxFromSlot(8, 2);
        } else if (this.inventory[9] != null && this.fluxLevel < maxFluxLevel) {
            this.fluxLevel += this.drainFluxFromSlot(9, 2);
        }
        if (this.fluxLevel >= maxFluxLevel) {
            this.fluxLevel = maxFluxLevel;
        }
        this.ticks++;
    }

    @Override
    public int getFluxLevel() {
        return this.fluxLevel;
    }

    @Override
    public int getMaxFluxLevel() {
        return maxFluxLevel;
    }

    @Override
    public ContainerSB setContainer(ContainerSB c) {
        for(Foundry f : this.smelters){
            f.setContainer(c);
        }
        return c;
    }

    @Override
    public List<IProcessor> getSubUnits() {
        List<IProcessor> subs = Lists.newArrayList();
        for (Foundry f : this.smelters) {
            subs.add(f);
        }
        return subs;
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
        return "machine.foundry.tile.name";
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
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.getPos()) != this ? false : player.getDistanceSq(this.getPos().add(0.5D, 0.5D, 0.5D)) <= 64.0D;
    }

    private class Foundry implements IProcessor {

        private int workTicks = 0;
        
        private ContainerSB container;

        private int outIndex;
        private int inIndex;

        private IMaterialProcess activeProcess;
        private List<ItemStack> waitingOutputs;
        private boolean isWorking;

        public Foundry(int inIndex, int outIndex) {
            this.outIndex = outIndex;
            this.inIndex = inIndex;
        }

        @Override
        public IMaterialProcess getActiveProcess() {
            return this.activeProcess;
        }

        @Override
        public boolean dryMergeAndFeed() {
            if (inventory[this.inIndex] != null) {
                this.activeProcess = modsbfp.foundrySmeltingRegistry.getProcessesByInputs(inventory[inIndex]).get(0);
                if (this.activeProcess != null) {
                    this.waitingOutputs = this.activeProcess.getOutputs();
                    if (this.container.dryMerge(this.waitingOutputs.get(0), 36 + this.outIndex, 36 + this.outIndex + 1, false) >= this.waitingOutputs.get(0).stackSize) {
                        decrStackSize(inIndex, this.activeProcess.getInputs().get(0).stackSize);
                        return true;
                    }
                }
            }
            this.activeProcess = null;
            this.waitingOutputs = null;
            return false;
        }

        @Override
        public void mergeOutputs() {
            container.mergeItemStack(this.waitingOutputs.get(0), 36 + this.outIndex, 36 + this.outIndex + 1, false, false);
        }

        @Override
        public void activate(Object... args) {

        }

        @Override
        public int getWorkTicks() {
            return this.workTicks;
        }

        @Override
        public ContainerSB setContainer(ContainerSB c) {
            this.container = c;
            return this.container;
        }

        private void tick() {
            if (this.isWorking) {
                this.workTicks++;
                if (this.workTicks == this.activeProcess.getDuration()) {
                    this.workTicks = 0;
                    this.mergeOutputs();
                    this.isWorking = false;
                }
            } else if (this.container != null) {
                this.isWorking = this.dryMergeAndFeed();
            }
        }

        private void reset() {
            this.workTicks = 0;
            this.waitingOutputs = null;
            this.activeProcess = null;
            this.isWorking = false;
        }
    }

}
