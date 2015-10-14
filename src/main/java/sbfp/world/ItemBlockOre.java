package sbfp.world;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class ItemBlockOre extends ItemBlock{

	public ItemBlockOre(Block block){
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage){
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack is){
		return modsbfp.blockOre.names[is.getItemDamage()];
	}
}