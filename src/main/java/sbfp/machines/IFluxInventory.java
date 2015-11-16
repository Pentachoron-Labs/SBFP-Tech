package sbfp.machines;

import net.minecraft.inventory.IInventory;

/**
 *
 * 
 */
public interface IFluxInventory extends IInventory {
    
    public abstract int getFluxLevel();
    
    public abstract int getMaxFluxLevel();
    /**
     * Drain flux from a slot in the inventory.
     * @param index The slot to drain from
     * @param deltaFlux How much flux is needed from this slot
     * @return The amount of flux drained from the slot
     */
    public abstract int drainFluxFromSlot(int index, int deltaFlux);
    
    /**
     * Add flux to a slot in the inventory
     * @param index The slot to add to
     * @param deltaFlux The amount to add
     * @return How much flux is left over--flux the slot could not hold
     */
    public abstract int addFluxToSlot(int index, int deltaFlux);
    
}
