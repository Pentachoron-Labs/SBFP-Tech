package sbfp.machines;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;

public class ItemRedflux extends ItemSub{

	public ItemRedflux(int id, String[] names){
		super(id,names);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
}