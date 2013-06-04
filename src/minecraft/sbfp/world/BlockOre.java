package sbfp.world;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import sbfp.BlockSub;

public class BlockOre extends BlockSub{

	public BlockOre(int id, String[] names){
		super(id,Material.rock,names);
		this.setCreativeTab(CreativeTabs.tabBlock);
		MinecraftForge.setBlockHarvestLevel(this,"pickaxe",2);
		this.blockHardness = 3;
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World w, int x, int y, int z, int metadata, int fortune){
		ArrayList<ItemStack> q = new ArrayList<ItemStack>();
		q.add(new ItemStack(this.blockID,1,metadata));
		return q;
	}
}
