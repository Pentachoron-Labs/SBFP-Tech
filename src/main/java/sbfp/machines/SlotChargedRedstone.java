package sbfp.machines;

import sbfp.flux.FluxDeviceTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class SlotChargedRedstone extends Slot{

	public SlotChargedRedstone(IInventory inv, int index, int x, int y){
		super(inv,index,x,y);
	}

	@Override
	public boolean isItemValid(ItemStack is){
		return is.getItem().equals(modsbfp.itemFluxDevice)&&is.getMetadata() == FluxDeviceTypes.CHARGEDREDSTONE.getMeta();
	}
}