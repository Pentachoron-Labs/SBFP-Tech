package sbfp.machines;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;
import sbfp.modsbfp;

public class ItemRedflux extends ItemSub{
	public String[] name;
	public ItemRedflux(int id, String[] names){
		super(id,names);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		for(int i = 0; i<this.names.length; i++){
			modsbfp.getInstance().addDisplayName(names[i], new String[] {"Redstone Flux Amplifier", "Redstone Flux Absorber", "Redstone Flux Stablilizer"}[i]);
		}
	}
}