package sbfp.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import cpw.mods.fml.common.IWorldGenerator;

public class GeneratorOres implements IWorldGenerator{

	private List<WorldGenOres> ores = new ArrayList<WorldGenOres>();

	public GeneratorOres(){

	}

	@Override
	public void generate(Random r, int cx, int cz, World w, IChunkProvider cg, IChunkProvider cp){
		cx <<= 4;
		cz <<= 4;
		if(cg instanceof ChunkProviderGenerate){
			for(WorldGenOres o:this.ores){
				o.generate(w,r,cx,0,cz);
			}
			//System.out.println("Generating Stuffies");
		}

	}

	public void addOre(WorldGenOres ore){
		this.ores.add(ore);
	}

}
