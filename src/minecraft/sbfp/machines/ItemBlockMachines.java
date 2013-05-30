package sbfp.machines;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class ItemBlockMachines extends ItemBlock{

	public ItemBlockMachines(int id){
		super(id);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage){
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack is){
		return modsbfp.blockMachines.names[is.getItemDamage()];
	}

}
