package sbfp.machines.dissociator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import sbfp.machines.IMaterialProcess;

/**
 *
 * 
 */
public class DissociationProcess implements IMaterialProcess{
    private final String name;
    private final ItemStack input;
    private final ItemStack primary;
    private final int fluxOutput;
    private final int duration;
    private final int fluxInput;
    
    public DissociationProcess(String name, ItemStack in, ItemStack out, int time, int fluxIn, int fluxOut){
        this.name = name;
        this.input = in;
        this.primary = out;
        this.duration = time;
        this.fluxInput = fluxIn;
        this.fluxOutput = fluxOut;
    }
    public DissociationProcess(String name, ItemStack in, ItemStack out, int time){
        this(name, in, out, time, 0 ,0);
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<ItemStack> getInputs() {
        return Arrays.asList(this.input);
    }

    @Override
    public List<ItemStack> getOutputsWithRandomChance(Random r) {
        return this.getOutputs();
    }

    @Override
    public List<ItemStack> getOutputs() {
        return Arrays.asList(this.primary);
    }

    @Override
    public int getFluxInput() {
        return this.fluxInput;
    }

    @Override
    public int getFluxOutput() {
        return this.fluxOutput;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

}
