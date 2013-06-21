package sbfp.machines;

import sbfp.ItemSub;
import net.minecraft.creativetab.CreativeTabs;


public class ItemChargedRedstone extends ItemSub{
	public ItemChargedRedstone(int id, String[] names){
		super(id,names);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
}
