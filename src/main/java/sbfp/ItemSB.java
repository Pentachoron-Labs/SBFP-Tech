package sbfp;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemSB extends Item{

	public ItemSB(String name){
		super();
		this.setUnlocalizedName(name);
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(IconRegister i){
//		this.itemIcon = i.registerIcon("sbfp:"+this.getUnlocalizedName().substring(5));
//	}
}