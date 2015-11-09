package sbfp.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * The container class to use for all sbfp machines 'n stuff
 *
 * @author atrain99
 */
public abstract class ContainerSB extends Container {

    protected IInventory tileEntity;

    public ContainerSB(InventoryPlayer inv, IInventory tileEntity) {
        this.tileEntity = tileEntity;
        //Player Inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
            }
        }
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 198));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift
     * clicking.
     *
     * @param player
     * @param index
     * @return
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack slotstack = slot.getStack();
            stack = slotstack.copy();
            if (index >= 36) { //is part of our container
                if (!this.mergeItemStack(slotstack, 0, 36, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(slotstack, 36, this.inventorySlots.size(), false)) {
                return null;
            }
            if (slotstack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            if (slotstack.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, slotstack);
        }
        return stack;
    }

    /**
     * Note: I redefined this so that it will not merge if the slot is not valid
     * for the stack (this allows us to more easily try to merge an item stack
     * in a range.
     *
     * @param stack Stack to merge
     * @param start Beginning of range to merge into
     * @param end End of range to merge into
     * @param reverse Start at the end?
     * @return Was the merge successful?
     */
    @Override
    public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse) {
        return this.mergeItemStack(stack, start, end, reverse, true);
    }

    /**
     * Merge an @link{ItemStack} into the container
     *
     * @param stack The ItemStack to merge
     * @param start Slot index to start at
     * @param end Slot index to end at
     * @param reverse Start merging at the end?
     * @param checkValidity Check and see if the item can be placed into the
     * slot by the player, not just by code
     * @return Was the merge successful
     */
    public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse, boolean checkValidity) {
        boolean lolzMergeFail = false;
        int i = reverse ? end - 1 : start;
        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!reverse && i < end || reverse && i >= start)) {
                Slot slot = (Slot) this.inventorySlots.get(i);
                ItemStack slotstack = slot.getStack();
                if (slot.getHasStack() && ItemStack.areItemsEqual(slotstack, stack) && ItemStack.areItemStackTagsEqual(stack, slotstack)) {
                    int sum = slotstack.stackSize + stack.stackSize;
                    if (sum <= stack.getMaxStackSize()) {
                        stack.stackSize = 0;
                        slotstack.stackSize = sum;
                        slot.onSlotChanged();
                        lolzMergeFail = true;
                    } else if (slotstack.stackSize < stack.getMaxStackSize()) {
                        stack.stackSize -= stack.getMaxStackSize() - slotstack.stackSize;
                        slotstack.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        lolzMergeFail = true;
                    }
                }
                i += reverse ? -1 : 1;
            }
        }
        if (stack.stackSize > 0) {
            i = reverse ? end - 1 : start;
            while (!reverse && i < end || reverse && i >= start) {
                Slot slot = (Slot) this.inventorySlots.get(i);
                ItemStack slotstack = slot.getStack();
                if (slotstack == null && (!checkValidity || slot.isItemValid(stack))) {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    lolzMergeFail = true;
                    break;
                }
                i += reverse ? -1 : 1;
            }
        }
        return lolzMergeFail;
    }

    /**
     * Does the same as mergeItemStack, except does not actually merge— just
     * returns the number of items that can be merged (usually either
     * stack.stackSize or 0, but can be in between)
     *
     */
    public int dryMerge(ItemStack stack, int start, int end, boolean checkValidity) {
        int quantity = stack.stackSize;
        for (int i = start; i < end; i++) {
            Slot slot = this.getSlot(i);
            if (!slot.getHasStack()) {
                return stack.stackSize;
            }
            ItemStack slotstack = slot.getStack();
            if (ItemStack.areItemsEqual(slotstack, stack) && ItemStack.areItemStackTagsEqual(stack, slotstack) && (!checkValidity || slot.isItemValid(stack))) {
                quantity -= slotstack.getMaxStackSize() - slotstack.stackSize;
                if (quantity <= 0) {
                    return stack.stackSize;
                }
            }
        }
        return stack.stackSize - quantity;
    }
}
