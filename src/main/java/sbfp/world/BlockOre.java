package sbfp.world;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import sbfp.BlockSub;

public class BlockOre extends BlockSub{

	public BlockOre(int id, String[] names){
		super(id,Material.rock,names);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.blockHardness = 3;
	}

}
