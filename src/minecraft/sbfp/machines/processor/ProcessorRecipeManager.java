package sbfp.machines.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import sbfp.machines.Recipe;

public class ProcessorRecipeManager<E extends TileEntityProcessor>{

	private List<Recipe> recipes;

	public ProcessorRecipeManager(){
		recipes = new ArrayList<Recipe>();
	}

	public Recipe getRecipe(ItemStack... inputs){
		for(Recipe i:this.recipes){
			if(matchesAll(Arrays.asList(inputs),i.getInputs())){
				return i;
			}
		}
		return null;
	}

	public Recipe getRecipeByID(int id){
		return this.recipes.get(id);
	}

	private boolean matchesAll(List<ItemStack> inputs, ItemStack[] ri){
		for(ItemStack i:ri){
			if(!matchesAny(i,inputs)) return false;
		}
		return true;
	}

	private boolean matchesAny(ItemStack i, List<ItemStack> inputs){
		for(ItemStack j:inputs){
			if(j!=null&&j.itemID==i.itemID&&j.getItemDamage()==i.getItemDamage()&&ItemStack.areItemStackTagsEqual(i,j)&&j.stackSize>=i.stackSize){
				return true;
			}
		}
		return false;
	}

	public void addRecipe(Recipe recipe){
		if(recipe!=null) this.recipes.add(recipe);
		recipe.id = this.recipes.size()-1;
	}
}