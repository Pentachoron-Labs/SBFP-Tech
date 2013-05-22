package sbfp.machines;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;

public class ItemRedflux extends ItemSub{

	public ItemRedflux(int id){
		super(id,new String[]{"redFluxAmp","redFluxAbsorber"});
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}
}