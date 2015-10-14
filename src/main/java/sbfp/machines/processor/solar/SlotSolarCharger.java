package sbfp.machines.processor.solar;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class SlotSolarCharger extends Slot{

	public SlotSolarCharger(IInventory par1iInventory, int par2, int par3, int par4){
		super(par1iInventory,par2,par3,par4);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		return is.getItem() == Item.getByNameOrId("minecraft:redstone");
	}
}