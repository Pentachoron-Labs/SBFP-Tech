

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
    /**
     * Formate for this is is : input stack, input index, output stack, output index
     */
    public void activate(Object... args) {
        try{
            this.input = (ItemStack) args[0];
            this.inIndex = (Integer) args[1];
            this.output = (ItemStack) args[2];
            this.outIndex = (Integer) args[3];
        }catch(ClassCastException ce){
            FMLLog.bigWarning("Foundry was initialized wrong, data list format incorrect");
            throw ce;
        }catch(IndexOutOfBoundsException iobe){
            FMLLog.bigWarning("Foundry was initalized wrong, not enough data in input list");
            throw iobe;
        }catch(Exception e){
            FMLLog.bigWarning("You fucked up");
        }
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
