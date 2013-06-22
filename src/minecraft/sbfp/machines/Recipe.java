package sbfp.machines;

import net.minecraft.item.ItemStack;
import sbfp.machines.processor.TileEntityProcessor;

/**Extend this for every machine.*/
public abstract class Recipe{

	public int id;

	/**List of inputs as ItemStacks.*/
	public abstract ItemStack[] getInputs();

	/**List of outputs as ItemStacks. MAKE SURE YOU COPY when returning.*/
	public abstract ItemStack[] getOutputs(TileEntityProcessor te);

	/**Amount of flux needed.*/
	public abstract int getFluxComponent();

	/**Amount of flux produced.*/
	public abstract int getFluxOutput();

	/**Duration, in ticks.*/
	public abstract int getTime();
}