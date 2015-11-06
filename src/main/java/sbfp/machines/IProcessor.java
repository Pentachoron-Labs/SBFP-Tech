package sbfp.machines;

public interface IProcessor{
    
    /**
     * Gets the running process
     * @return The active process object
     */
    public abstract IMaterialProcess getActiveProcess();
    
    /**
     * Does a dry merge Then
     * decreases inputs, if possible and they match, and sets both waitingOutput
     * and activeProcess.
     *
     * @return If the dry merge and process feed was successful
     */
    public abstract boolean dryMergeAndFeed();
    
    /**
     * Merges this.waitingOutput as the individual machine sees fit. (you're
     * basically calling      <code>for(ItemStack i:this.waitingOutputs)
	 * this.container.mergeItemStack(i,start,end,false);
     * </code>but there are exceptions (*this* output goes *here*) and it's just
     * as much work as asking for the output range.
     */
    public abstract void mergeOutputs();
    
    /**
     * Called on the first tick the processor ticks on
     */
    public abstract void activate();
    
    /**
     * 
     * @return  time in ticks of progress on current process
     */
    public abstract int getWorkTicks();
    
    /**
     * Sets the container of this processor -- but do all processors need containers?
     * @param container The container to set it to
     * @return The container.
     */
    public abstract ContainerSB setContainer(ContainerSB container);
}
