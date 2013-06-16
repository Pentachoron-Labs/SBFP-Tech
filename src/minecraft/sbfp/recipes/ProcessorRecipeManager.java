package sbfp.recipes;

import java.util.HashMap;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLLog;

public class ProcessorRecipeManager{

	public static ProcessorRecipeManager instance;
	private HashMap<ItemStack,IOutput> recipes;

	public ProcessorRecipeManager(){
		FMLLog.info("Crusher Manager Loading");
		instance = this;
		recipes = new HashMap();
	}

	public IOutput getRecipe(ItemStack input){
		return this.recipes.get(input);
	}

	public void addRecipe(ItemStack in, IOutput out){
		if(in!=null&&out!=null){
			this.recipes.put(in,out);
		}
	}

	/** This should do something in the future... */
	public void initialize(){}
}