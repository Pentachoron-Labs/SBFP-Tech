package sbfp.recipes;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;

public class ProcessorRecipeManager{

	public static ProcessorRecipeManager instance;
	private HashMap<ItemStack, IMachineOutput> recipes;
	
	public ProcessorRecipeManager(){
		instance = this;
		recipes = new HashMap();
	}
	
	public IMachineOutput getRecipe(ItemStack input){
		return this.recipes.get(input);
	}
	
	public void addRecipe(ItemStack in, IMachineOutput out){
		if(in != null && out != null) this.recipes.put(in, out);
	}

	/** This should do something in the future... */
	public void initialize(){}
}
