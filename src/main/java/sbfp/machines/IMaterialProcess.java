package sbfp.machines;

import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;

/**
 * Implement this for every machine. Contains information about how machines will
 * refine raw material.
 *
 */
public interface IMaterialProcess {

    /**
    * Gets the id of the recipe
    * @return the ID
    */
    public abstract String getName();

    /**
     * List of inputs as ItemStacks.
     *
     * @return List of inputs
     */
    public abstract List<ItemStack> getInputs();

    /**
     * List of outputs as ItemStacks. MAKE SURE YOU COPY when returning.
     *
     * @param r The random used for calculating the random chances
     * @return List of outputs
     *
     */
    public abstract List<ItemStack> getOutputsWithRandomChance(Random r);
    
    
    /**
     * Get list of all possible outputs
     * @return List<ItemStack> of all possible outputs. COPY WHEN RETURNING.
     */
    public abstract List<ItemStack> getOutputs();

    /**
     * Amount of flux needed.
     *
     * @return Amount of flux required to complete this process
     */
    public abstract int getFluxInput();

    /**
     * Amount of flux produced.
     *
     * @return Amount of flux generated by this process
     */
    public abstract int getFluxOutput();

    /**
     * Duration, in ticks.
     *
     * @return length of process, in ticks (0.05 seconds)
     */
    public abstract int getDuration();
}
