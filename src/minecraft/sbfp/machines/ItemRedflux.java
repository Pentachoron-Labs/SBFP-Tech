package sbfp.machines;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;
import sbfp.modsbfp;

public class ItemRedflux extends ItemSub{
	public static String[] names = new String[]{"redFluxAmp","redFluxAbsorber", "redFluxStablizer"};
	public ItemRedflux(int id){
		super(id,names);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		for(int i = 0; i<this.names.length; i++){
			modsbfp.getInstance().addDisplayName(names[i], new String[] {"Redstone Flux Amplifier", "Redstone Flux Absorber", "Redstone Flux Stablilizer"}[i]);
		}
	}
}