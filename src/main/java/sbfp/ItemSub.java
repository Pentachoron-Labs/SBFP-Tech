package sbfp;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemSub extends ItemSB{

	public Icon[] icons;
	public String[] names;

	public ItemSub(int id, String[] names){
		super(names[0]);
		this.names = names;
		this.hasSubtypes = true;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister register){
//		icons = new Icon[names.length];
//		for(int i = 0; i<names.length; i++){
//			icons[i] = register.registerIcon("sbfp:"+this.names[i]);
//		}
//	}


	@Override
	public String getUnlocalizedName(ItemStack is){
		return this.names[is.getItemDamage()];
	}
}