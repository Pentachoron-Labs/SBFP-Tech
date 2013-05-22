package sbfp;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSB extends Item{

	public ItemSB(int id, String name){
		super(id);
		this.setUnlocalizedName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister i){
		this.itemIcon = i.registerIcon("sbfp:"+this.getUnlocalizedName().substring(5));
	}
}