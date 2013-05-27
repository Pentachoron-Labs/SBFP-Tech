package sbfp.machines;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;
import sbfp.modsbfp;

public class ItemRedflux extends ItemSub{

	public ItemRedflux(int id){
		super(id,modsbfp.redFluxNames[0]);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
}