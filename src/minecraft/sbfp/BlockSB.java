package sbfp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.registry.LanguageRegistry;

public abstract class BlockSB extends Block{

	public BlockSB(int id, Material material, String name){
		super(id,material);
		LanguageRegistry.addName(this,name);
	}
}
