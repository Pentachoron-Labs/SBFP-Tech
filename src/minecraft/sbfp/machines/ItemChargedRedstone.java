package sbfp.machines;

import sbfp.ItemSub;
import net.minecraft.creativetab.CreativeTabs;


public class ItemChargedRedstone extends ItemSub{
	public static final Integer[] chargeLevels = new Integer[]{10, 15, 20, 30, 40, 50, 75, 100, 200}; //TODO Make this the right numbers...
	
	public ItemChargedRedstone(int id, String[] names){
		super(id,names);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
}
