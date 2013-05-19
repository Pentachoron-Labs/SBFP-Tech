package sbfp;

import net.minecraft.item.Item;

public abstract class ItemSB extends Item{

	public ItemSB(int id, String name){
		super(id);
		this.setUnlocalizedName(name);
	}
}