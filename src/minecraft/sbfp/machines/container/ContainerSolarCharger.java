package sbfp.machines.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sbfp.machines.tiles.TileSolarCharger;
import sbfp.machines.tiles.slot.SlotSolarCharger;

public class ContainerSolarCharger extends Container{

	private TileSolarCharger tileEntity;

	public ContainerSolarCharger(InventoryPlayer par1InventoryPlayer, TileSolarCharger tileEntity){
		this.tileEntity = tileEntity;
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,0,8,32));
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,1,26,32));
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,2,8,50));
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,3,26,50));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,tileEntity,4,134,32));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,tileEntity,5,152,32));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,tileEntity,6,134,50));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,tileEntity,7,152,50));
		int var3;

		for(var3 = 0; var3<3; ++var3){
			for(int var4 = 0; var4<9; ++var4){
				this.addSlotToContainer(new Slot(par1InventoryPlayer,var4+var3*9+9,8+var4*18,140+var3*18));
			}
		}

		for(var3 = 0; var3<9; ++var3){
			this.addSlotToContainer(new Slot(par1InventoryPlayer,var3,8+var3*18,198));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer){
		return this.tileEntity.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par1){
		ItemStack var2 = null;
		Slot var3 = (Slot) this.inventorySlots.get(par1);

		if(var3!=null&&var3.getHasStack()){
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();

			if(par1!=0){
				if(var4.itemID==Item.coal.itemID){
					if(!this.mergeItemStack(var4,0,1,false)){
						return null;
					}
				}else if(par1>=30&&par1<37&&!this.mergeItemStack(var4,3,30,false)){
					return null;
				}
			}else if(!this.mergeItemStack(var4,3,37,false)){
				return null;
			}

			if(var4.stackSize==0){
				var3.putStack((ItemStack) null);
			}else{
				var3.onSlotChanged();
			}

			if(var4.stackSize==var2.stackSize){
				return null;
			}

			var3.onPickupFromSlot(par1EntityPlayer,var4);
		}

		return var2;
	}

}
