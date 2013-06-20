package sbfp.machines.processor.crusher;

import net.minecraft.item.ItemStack;
import sbfp.machines.Recipe;
import sbfp.machines.processor.TileEntityProcessor;

public class RecipeCrusher extends Recipe{

	private final ItemStack input;
	private final ItemStack primary;
	private final ItemStack secondary;
	private final float secondaryChance;
	private final int duration;

	public RecipeCrusher(ItemStack i, ItemStack p, ItemStack s, float c, int d){
		this.input = i;
		this.primary = p;
		this.secondary = s;
		this.secondaryChance = c;
		this.duration = d;
	}

	public RecipeCrusher(ItemStack i, ItemStack p, int d){
		this(i,p,null,0,d);
	}

	@Override
	public ItemStack[] getInputs(){
		return new ItemStack[]{this.input};
	}

	@Override
	public ItemStack[] getOutputs(TileEntityProcessor te){
		if(te.worldObj.rand.nextDouble()<this.secondaryChance){
			return new ItemStack[]{this.primary.copy(),this.secondary.copy()};
		}else{
			return new ItemStack[]{this.primary.copy()};
		}
	}

	@Override
	public int getFluxInput(){
		return 1; //TODO: fix this
	}

	@Override
	public int getFluxOutput(){
		return 0;
	}

	@Override
	public int getTime(){
		return this.duration;
	}
}