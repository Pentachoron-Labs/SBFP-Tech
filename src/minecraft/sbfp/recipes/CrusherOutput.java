package sbfp.recipes;

import net.minecraft.item.ItemStack;

public class CrusherOutput implements IOutput{

	private ItemStack primary;
	private ItemStack secondary;
	private float secondaryChance;

	public CrusherOutput(ItemStack p, ItemStack s, float c){
		this.primary = p;
		if(c!=0F){
			this.secondary = s;
		}
		this.secondaryChance = c;
	}

	@Override
	public Object[] getData(){
		return new Object[]{this.primary,this.secondary,this.secondaryChance};
	}
}
