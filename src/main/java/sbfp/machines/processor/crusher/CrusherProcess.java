package sbfp.machines.processor.crusher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.item.ItemStack;
import sbfp.machines.processor.MaterialProcess;

public class CrusherProcess implements MaterialProcess {
    
    private final String name;
    private final ItemStack input;
    private final ItemStack primary;
    private final ItemStack secondary;
    private final float secondaryChance;
    private final int duration;
    private final int fluxInput;

    public CrusherProcess(String name, ItemStack i, ItemStack p, ItemStack s, float c, int d, int flux) {
        this.name = name;
        this.input = i;
        this.primary = p;
        this.secondary = s;
        this.secondaryChance = c;
        this.duration = d;
        this.fluxInput = flux;
    }

    public CrusherProcess(String name, ItemStack i, ItemStack p, int d, int flux) {
        this(name, i, p, null, 0, d, flux);
    }

    @Override
    public List<ItemStack> getInputs() {
        ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
        inputs.add(this.input.copy());
        return inputs;
    }

    @Override
    public List<ItemStack> getOutputsWithRandomChance(Random r) {
        ArrayList<ItemStack> outputs = new ArrayList<ItemStack>();
        outputs.add(this.primary.copy());
        if (r.nextDouble() < this.secondaryChance) {
            outputs.add(this.secondary.copy());
        }
        return outputs;
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
    public int getTime() {
        return this.duration;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public List<ItemStack> getOutputs() {
        return Arrays.asList(this.primary.copy(), this.secondary.copy());
    }
}
