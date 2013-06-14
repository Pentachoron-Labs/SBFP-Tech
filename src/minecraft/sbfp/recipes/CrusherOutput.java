package sbfp.recipes;

import net.minecraft.item.ItemStack;


public class CrusherOutput implements IOutput{
	private ItemStack primary;
	private ItemStack secondary;
	private float secondaryChance = 0.0F;

	public CrusherOutput(ItemStack p, ItemStack s, float c){
		this.primary = p;
		this.secondary = s;
		this.secondaryChance = c;
	}
	
	public CrusherOutput(ItemStack i){
		this.primary = i;
	}
	
	public Object[] getData(){
		return new Object[]{this.primary, this.secondary, this.secondaryChance};
	}
}
