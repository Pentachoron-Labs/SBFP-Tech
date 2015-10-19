package sbfp.machines.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import net.minecraft.item.ItemStack;

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
     * @return ArrayList of processes that contain ALL of the inputs passed to the method
     */
    public ArrayList<E> getProcessesByInputs(ItemStack... inputs){
        ArrayList<E> p = new ArrayList<E>();
        for(E process : this.processes.values()){
            if(process.getInputs().containsAll(Arrays.asList(inputs))) p.add(process);
        }
        return p;
    }
    /**
     * Get processes that produce certain outputs
     * @param outputs The outputs to check
     * @return List of processes that contain ALL of the outputs
     */
    public ArrayList<E> getProcessesByOuputs(ItemStack... outputs){
        ArrayList<E> p = new ArrayList<E>();
        for(E process : this.processes.values()){
            if(process.getOutputs().containsAll(Arrays.asList(outputs))) p.add(process);
        }
        return p;
    }
}
