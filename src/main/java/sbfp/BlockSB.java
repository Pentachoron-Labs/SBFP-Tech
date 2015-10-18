package sbfp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockSB extends Block{

	public BlockSB(Material material, String name){
		super(material);
		this.setUnlocalizedName(name);
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister i){
//		this.blockIcon = i.registerIcon("sbfp:"+this.getUnlocalizedName2());
//	}
}