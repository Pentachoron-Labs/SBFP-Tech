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
     * @param amount amount of flux to add
     * @return The overflow -- flux that cannot be added
     */
    public abstract int addFlux(ItemStack stack, int amount);
    
    /**
     * Metadata-sensitive method to determine if an item is a flux storage unit thing
     * @param meta The metadata
     * @return 
     */
    public abstract boolean canSubtypeStoreFlux(int meta);
}
