package sbfp.machines;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.ItemStack;

/**
 *
 * @param <E> The type of processes this registry manages
 */
public class MaterialProcessRegistry<E extends IMaterialProcess> {

    private List<E> processes;
    
    private Map<List<ItemStack>, List<ItemStack>> recipes;

    public MaterialProcessRegistry(E... things) {
        this.processes = Lists.newArrayList(things);
        this.recipes = Maps.newHashMap();
        for(E p : things){
            this.recipes.put(p.getInputs(), p.getOutputs());
        }
    }

    public MaterialProcessRegistry() {
        processes = Lists.newArrayList();
        recipes = Maps.newHashMap();
    }

    public boolean addProcess(E newProcess) {
        if (!this.processes.contains(newProcess)) {
            this.processes.add(newProcess);
            return true;
        }
        return false;
    }

    public boolean containsProcess(E process) {
        return this.processes.contains(process);
    }

    /**
     * Gathers processes that contain certain inputs
     *
     * @param inputs Inputs you wish to check
     * @return ArrayList of processes that contain any of the inputs passed to
     * the method
     */
    public List<E> getProcessesByInputs(ItemStack... inputs) {
        ArrayList<E> p = new ArrayList<E>();
        for (E process : this.processes) {
            for (ItemStack i : inputs) {
                for (ItemStack j : process.getInputs()) {
                    if (compareItemStacks(j, i)) {
                        p.add(process);
                    }
                }
            }
        }
        return p;
    }

    /**
     * Get processes that produce certain outputs
     *
     * @param outputs The outputs to check
     * @return List of processes that contain ALL of the outputs
     */
    public List<E> getProcessesByOuputs(ItemStack... outputs) {
        ArrayList<E> p = new ArrayList<E>();
        for (E process : this.processes) {
            if (process.getOutputs().containsAll(Arrays.asList(outputs))) {
                p.add(process);
            }
        }
        return p;
    }
    
    public E getProcessByName(String n){
        for(E p : this.processes){
            if(p.getName().equals(n)){
                return p;
            }
        }
        return null;
    }

    private boolean compareItemStacks(ItemStack is1, ItemStack is2) {
        return (is1.getMetadata() == is2.getMetadata()) && is1.getItem().equals(is2.getItem());
    }

    public boolean isItemProcessable(ItemStack is) {
        for (E process : this.processes) {
            for (ItemStack j : process.getInputs()) {
                if (compareItemStacks(j, is)) {
                    return true;
                }
            }

        }
        return false;
    }
}
