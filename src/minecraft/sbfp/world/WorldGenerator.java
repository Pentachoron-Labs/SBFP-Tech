package sbfp.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator{

	private List<WorldGenOres> ores = new ArrayList<WorldGenOres>();

	public WorldGenerator(){

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider){
		chunkX = chunkX<<4;
		chunkZ = chunkZ<<4;
		if(chunkGenerator instanceof ChunkProviderGenerate){
			assert this.ores.get(0)!=null;
			for(WorldGenOres o:this.ores){
				o.generate(world,random,chunkX,0,chunkZ);
			}
			//System.out.println("Generating Stuffies");
		}

	}

	public void addOre(WorldGenOres ore){
		this.ores.add(ore);
	}

}
