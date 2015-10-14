package sbfp.machines;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class ItemBlockMachine extends ItemBlock{

	public ItemBlockMachine(Block machine){
		super(machine);
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
