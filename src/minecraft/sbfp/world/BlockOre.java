package sbfp.world;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import sbfp.BlockSB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOre extends BlockSB{
	
	
	public Icon[] icons = new Icon[256];
	public static final String[] names = {"oreThorium","oreFluorite","oreMoS2"};

	public BlockOre(int type, int id){
		super(id,Material.rock,names[type]);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		icons[0] = register.registerIcon("sbfp:"+this.icons[0]);
		icons[1] = register.registerIcon("sbfp:"+this.icons[1]);
		icons[2] = register.registerIcon("sbfp:"+this.icons[2]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta)
	{
		return icons[meta];
	}
	@Override
	public int damageDropped(int i)
	{
		return i;
	}
}
