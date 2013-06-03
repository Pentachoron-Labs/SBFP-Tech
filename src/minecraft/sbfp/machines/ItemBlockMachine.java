package sbfp.machines;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class ItemBlockMachine extends ItemBlock{

	public ItemBlockMachine(int id){
		super(id);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage){
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack is){
		return modsbfp.blockMachine.names[is.getItemDamage()];
	}

}
