package sbfp;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSub extends ItemSB{

	public Icon[] icons;
	public String[] names;

	public ItemSub(int id, String[] names){
		super(id,names[0]);
		this.names = names;
		this.hasSubtypes = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register){
		icons = new Icon[names.length];
		for(int i = 0; i<names.length; i++){
			icons[i] = register.registerIcon("sbfp:"+this.names[i]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta){
		return icons[meta];
	}

	@Override
	public void getSubItems(int id, CreativeTabs tabs, List list){
		for(int i = 0; i<names.length; ++i){
			list.add(new ItemStack(id,1,i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack is){
		return this.names[is.getItemDamage()];
	}
}