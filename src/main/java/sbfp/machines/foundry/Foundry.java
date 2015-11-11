

package sbfp.machines.foundry;

import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import sbfp.machines.ContainerSB;
import sbfp.machines.IMaterialProcess;
import sbfp.machines.IProcessor;


public class Foundry implements IProcessor{
    private static final int maxFluxLevel = 400;
    
    private int workTicks = 0;
    
    private ContainerSB container;
    
    private ItemStack input;
    private int inIndex;
    private ItemStack output;
    private int outIndex;
    
    private IMaterialProcess activeProcess;
    private List<ItemStack> waitingOutputs;
    private boolean hasItem;
    
    public Foundry(ItemStack in, ItemStack out, int inIndex, int outIndex){
        this.input = in;
        this.output = out;
        this.inIndex = inIndex;
        this.outIndex = outIndex;
    }
    
    @Override
    public IMaterialProcess getActiveProcess() {
        return this.activeProcess;
    }

    @Override
    public boolean dryMergeAndFeed() {
        return false;
    }

    @Override
    public void mergeOutputs() {
        
    }

    @Override
    public void activate(Object... args) {
        
    }

    @Override
    public int getWorkTicks() {
        return this.workTicks;
    }

    @Override
    public ContainerSB setContainer(ContainerSB container) {
        this.container = container;
        return this.container;
    }

}
