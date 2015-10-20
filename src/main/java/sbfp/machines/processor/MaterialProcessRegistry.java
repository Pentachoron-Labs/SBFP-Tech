package sbfp.machines.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

/**
 * 
 * @param <E> The type of processes this registry manages
 */
public class MaterialProcessRegistry<E extends MaterialProcess> {
    
    private HashMap<String, E> processes;
    
    public MaterialProcessRegistry(E... things){
        this();
        for(E p : things){
            this.processes.put(p.getName(), p);
        }
    }
    public MaterialProcessRegistry(){
        processes = new HashMap<String, E>();
    }
    
    public E getProcessByName(String name){
        return processes.get(name);
    }
    
    public boolean addProcess(E newProcess){
        if(!this.processes.containsValue(newProcess)){
            this.processes.put(newProcess.getName(), newProcess);
            return true;
        }
        return false;
    }
    
    public boolean containsProcess(E process){
        return this.processes.containsValue(process);
    }
    
    public boolean containsProcess(String name){
        return this.processes.containsKey(name);
    }
    /**
     * Gathers processes that contain certain inputs
     * @param inputs Inputs you wish to check
     * @return ArrayList of processes that contain any of the inputs passed to the method
     */
    public List<E> getProcessesByInputs(ItemStack... inputs) {
        ArrayList<E> p = new ArrayList<E>();
        for (E process : this.processes.values()) {
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
     * @param outputs The outputs to check
     * @return List of processes that contain ALL of the outputs
     */
    public List<E> getProcessesByOuputs(ItemStack... outputs){
        ArrayList<E> p = new ArrayList<E>();
        for(E process : this.processes.values()){
            if(process.getOutputs().containsAll(Arrays.asList(outputs))) p.add(process);
        }
        return p;
    }
    
    private boolean compareItemStacks(ItemStack is1, ItemStack is2){
        return (is1.getMetadata() == is2.getMetadata()) && is1.getItem().equals(is2.getItem());
    }
    
    public boolean isItemProcessable(ItemStack is){
        for (E process : this.processes.values()) {
                for (ItemStack j : process.getInputs()) {
                    if (compareItemStacks(j, is)) {
                        return true;
                    }
                }
 
        }
        return false;
    }
   
}
