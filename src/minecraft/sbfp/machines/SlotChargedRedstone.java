package sbfp.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;
import sbfp.recipes.ProcessorRecipeManager;


public class SlotChargedRedstone extends Slot{
	public SlotChargedRedstone(IInventory par1iInventory, int par2, int par3, int par4){
		super(par1iInventory,par2,par3,par4);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		return is.itemID == modsbfp.itemRedflux.itemID && is.getItemDamage() == 3;
	}
}
