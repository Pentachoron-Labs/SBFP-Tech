package sbfp.chemistry;

import net.minecraft.creativetab.CreativeTabs;
import sbfp.ItemSub;

public class ItemDye extends ItemSub{

	public ItemDye(int id, String[] names){
		super(id,names);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
}