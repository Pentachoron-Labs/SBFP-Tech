package sbfp.world;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import sbfp.BlockSB;

public class BlockThoriumOre extends BlockSB{

	public BlockThoriumOre(int id){
		super(id,Material.rock,"Thorium Ore");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.getBlockTextureFromSide(id);
	}

	// And texturing this block is confusing...
	// Newt: That I will concede. They've *entirely* changed the system.
}
