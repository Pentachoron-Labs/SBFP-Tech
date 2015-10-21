package sbfp.machines.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ProcessManager<E extends TileEntityProcessor>{

	private List<IMaterialProcess> recipes;

	public ProcessManager(){
		recipes = new ArrayList<IMaterialProcess>();
	}

	public void addProcess(IMaterialProcess recipe){
		if(recipe!=null) this.recipes.add(recipe);
		//recipe.getID() = this.recipes.size()-1;
	}
}