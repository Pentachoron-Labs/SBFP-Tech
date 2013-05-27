package sbfp.chemistry;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;
import sbfp.modsbfp;

public class ItemDye extends ItemSub{

	public ItemDye(int id){
		super(id,modsbfp.dyeNames[0]);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
}