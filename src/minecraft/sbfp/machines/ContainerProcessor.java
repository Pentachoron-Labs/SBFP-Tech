package sbfp.machines;




import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;


public class ContainerProcessor extends Container{
	TileEntityProcessor tileEntity;

	public ContainerProcessor(InventoryPlayer inv, TileEntityProcessor tileEntity){
		this.tileEntity = tileEntity;
		this.tileEntity.container = this;
		//FMLLog.info("I am being created");
		
		//Player Inventory
		for(int i = 0; i<3; ++i){
			for(int j = 0; j<9; ++j){
				this.addSlotToContainer(new Slot(inv,j+i*9+9,8+j*18,140+i*18));
			}
		}
		for(int i = 0; i<9; ++i){
			this.addSlotToContainer(new Slot(inv,i,8+i*18,198));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer){
		return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift
	 * clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index){
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if(slot!=null&&slot.getHasStack()){
			ItemStack slotstack = slot.getStack();
			stack = slotstack.copy();
			if(index<8){
				if(!this.mergeItemStack(slotstack,8,this.inventorySlots.size(),false)) return null;
			}else if(!this.getSlot(0).isItemValid(slotstack)||!this.mergeItemStack(slotstack,0,4,false)) return null;
			if(slotstack.stackSize==0){
				slot.putStack((ItemStack) null);
			}else{
				slot.onSlotChanged();
			}
			if(slotstack.stackSize==stack.stackSize) return null;
			slot.onPickupFromSlot(player,slotstack);
		}
		return stack;
	}

	@Override
	public boolean mergeItemStack(ItemStack stack, int start, int end, boolean reverse){
		return super.mergeItemStack(stack,start,end,reverse);
	}

	/**
	 * Does the same as mergeItemStack with the same args, except does not
	 * actually merge— just returns the number of items that can be merged
	 * (usually either stack.stackSize or 0, but can be in between)
	 * @param stack
	 * @param start
	 * @param end
	 * @param reverse
	 * @return
	 */
	public int dryMerge(ItemStack stack, int start, int end, boolean reverse){
		boolean flag1 = false;
		int i = start;
		if(reverse){
			i = end-1;
		}
		int quantity = stack.stackSize;
		Slot slot;
		ItemStack slotstack;
		if(stack.isStackable()){
			while(stack.stackSize>0&&(!reverse&&i<end||reverse&&i>=start)){
				slot = this.getSlot(i);
				slotstack = slot.getStack();
				if(slotstack!=null&&slotstack.itemID==stack.itemID&&(!stack.getHasSubtypes()||stack.getItemDamage()==slotstack.getItemDamage())&&ItemStack.areItemStackTagsEqual(stack,slotstack)){
					int l = slotstack.stackSize+stack.stackSize;
					if(l<=stack.getMaxStackSize()){
						quantity -= slotstack.stackSize;
					}else if(slotstack.stackSize<stack.getMaxStackSize()){
						quantity -= stack.getMaxStackSize()-slotstack.stackSize;
					}
				}
				if(reverse) --i;
				else ++i;
			}
		}
		if(stack.stackSize>0){
			if(reverse){
				i = end-1;
			}else{
				i = start;
			}
			while(!reverse&&i<end||reverse&&i>=start){
				slot = (Slot) this.inventorySlots.get(i);
				slotstack = slot.getStack();
				if(slotstack==null){
					quantity = 0;
					break;
				}
				if(reverse){
					--i;
				}else{
					++i;
				}
			}
		}
		return stack.stackSize-quantity;
	}
}
