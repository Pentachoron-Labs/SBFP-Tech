package sbfp.flux;

import net.minecraft.item.ItemStack;

/**
 * Items that are flux sources implement this
 * @author atrain99
 */
public interface IFluxSourceItem{
    
    /**
     * Drain flux
     * @param stack ItemStack to drain from
     * @param amount The amount to drain from the ItemStack
     * @return The amount of flux successfully drained
     */
    public abstract int drainFlux(ItemStack stack, int amount);
    
    public abstract boolean isSubtypeValidFluxSource(int meta);
    
    public abstract boolean destroyOnDrain();

}
