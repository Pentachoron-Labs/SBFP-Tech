package sbfp.machines.processor.solar;

import net.minecraft.item.ItemStack;
import sbfp.machines.Recipe;
import sbfp.machines.processor.TileEntityProcessor;

public class RecipeSolar extends Recipe{

	private final ItemStack input;
	private final ItemStack output;
	private final int duration;

	public RecipeSolar(ItemStack i, ItemStack o, int t){
		this.input = i;
		this.output = o;
		this.duration = t;
	}

	@Override
	public ItemStack[] getInputs(){
		return new ItemStack[]{this.input};
	}

	@Override
	public ItemStack[] getOutputs(TileEntityProcessor te){
		return new ItemStack[]{this.output};
	}

	@Override
	public int getFluxComponent(){
		return 0;
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