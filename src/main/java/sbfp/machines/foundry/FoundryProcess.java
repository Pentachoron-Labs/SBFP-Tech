package sbfp.machines.foundry;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import net.minecraft.item.ItemStack;
import sbfp.machines.IMaterialProcess;

/**
 *
 *
 */
public class FoundryProcess implements IMaterialProcess {

    private final String name;
    private final ItemStack input;
    private final ItemStack output;
    private final int duration;
    private final int fluxInput;

    public FoundryProcess(String n, ItemStack in, ItemStack out, int time, int fluxIn) {
        this.name = n;
        this.input = in;
        this.output = out;
        this.duration = time;
        this.fluxInput = fluxIn;
    }
    
    public FoundryProcess(Entry<ItemStack, ItemStack> e){
        this.input = e.getKey();
        this.output = e.getValue();
        this.duration = 20;
        this.fluxInput = 15;
        this.name = e.getKey().getDisplayName().replace(' ', '_') + "_to_" + e.getValue().getDisplayName().replace(' ', '_');
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
        return Arrays.asList(this.output);
    }

    @Override
    public int getFluxInput() {
        return this.fluxInput;
    }

    @Override
    public int getFluxOutput() {
        return 0;
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

}
