package sbfp.machines.solar;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import sbfp.machines.IMaterialProcess;

public class SolarInfusionProcess implements IMaterialProcess {

    private final String name;
    private final ItemStack input;
    private final ItemStack output;
    private final int duration;

    public SolarInfusionProcess(String name, ItemStack i, ItemStack o, int t) {
        this.name = name;
        this.input = i;
        this.output = o;
        this.duration = t;
    }

    @Override
    public List<ItemStack> getInputs() {
        return Arrays.asList(this.input.copy());
    }

    @Override
    public List<ItemStack> getOutputsWithRandomChance(Random r) {
        return this.getOutputs();
    }

    @Override
    public int getFluxInput() {
        return 0;
    }

    @Override
    public int getFluxOutput() {
        return 0;
    }

    @Override
    public int getTime() {
        return this.duration;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return Arrays.asList(this.output.copy());
    }
}
