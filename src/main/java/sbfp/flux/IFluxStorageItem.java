package sbfp.flux;

import net.minecraft.item.ItemStack;

/**
 *
 * 
 */
public interface IFluxStorageItem {
    
    /**
     * Adds flux to an ItemStack
     * @param stack the ItemStack to add to
     * @return The overflow -- flux that cannot be added
     */
    public abstract int addFlux(ItemStack stack, int amount);
    
    public abstract boolean isSubtypeValidFluxContainer(int meta);
}
