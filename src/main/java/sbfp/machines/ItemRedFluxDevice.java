package sbfp.machines;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemRedFluxDevice extends Item{

	public ItemRedFluxDevice(String name){
		super();
                this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.tabRedstone);
                this.setMaxDamage(0);
                this.setHasSubtypes(true);
                this.setMaxStackSize(64);
	}
        
        
}