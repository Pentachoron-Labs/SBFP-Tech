package sbfp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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