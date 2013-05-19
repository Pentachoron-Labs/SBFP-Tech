package sbfp;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.src.ModLoader;

public abstract class BlockSB extends Block{

	public BlockSB(int id, Material material, String name){
		super(id,material);
		ModLoader.addName(this,name);
	}
}
