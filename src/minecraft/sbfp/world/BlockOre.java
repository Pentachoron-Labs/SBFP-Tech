package sbfp.world;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import sbfp.BlockSB;

public class BlockOre extends BlockSB{
	
	public static final String[] names = {"oreThorium","oreFluorite","oreMoS2"};

	public BlockOre(int type, int id){
		super(id,Material.rock,names[type]);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
}
