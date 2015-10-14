package sbfp;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockSub extends BlockSB{

	public Icon[] icons;
	public final String[] names;

	public BlockSub(int id, Material material, String[] names){
		super(material,names[0]);
		this.names = names;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister register){
//		icons = new Icon[names.length];
//		for(int i = 0; i<names.length; i++){
//			icons[i] = register.registerIcon("sbfp:"+this.names[i]);
//		}
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public Icon getIcon(int side, int meta){
//		return icons[meta];
//	}
//
//	@Override
//	public int damageDropped(int meta){
//		return meta;
//	}
//
//	@Override
//	public void getSubBlocks(int id, CreativeTabs tabs, List list){
//		for(int i = 0; i<names.length; ++i){
//			list.add(new ItemStack(id,1,i));
//		}
//	}
}