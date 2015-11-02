package sbfp.machines;

/**
 *
 * 
 */
public interface IFluxContainer {
    
    public abstract int drainFluxFromSlot(int slotID, int deltaF);
    
    public abstract int addFluxToSlot(int slotID, int deltaF);
    
}
