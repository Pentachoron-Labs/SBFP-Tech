package sbfp.machines.processor.crusher;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class SlotCrusher extends Slot{

	public SlotCrusher(IInventory par1iInventory, int par2, int par3, int par4){
		super(par1iInventory,par2,par3,par4);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		//return modsbfp.prmCrusher.getRecipe(is)!=null;
            return false; //temporary, until ProcessManager functional
	}
}