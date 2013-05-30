package sbfp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import sbfp.modsbfp;
import cpw.mods.fml.common.IWorldGenerator;

public class GeneratorOres implements IWorldGenerator{

	public GeneratorOres(){

	}

	@Override
	public void generate(Random r, int cx, int cz, World w, IChunkProvider cg, IChunkProvider cp){
		cx <<= 4;
		cz <<= 4;
		if(cg instanceof ChunkProviderGenerate){
			// First we generate monazite. Monazite likes to generate in underground pools (though it will generate elsewhere.)
			// try to find an underground pool
			for(int x = cx; x<cx+16; x++){
				for(int z = cz; z<cz+16; z++){
					for(int y = 0; y<40; y++){
						if(w.getBlockId(x,y,z)==Block.waterStill.blockID&&w.getBlockId(x,y-1,z)==Block.stone.blockID&&r.nextInt(32)==0){
							//							FMLLog.finest("I haz a SPECIAL monazite at %d %d %d",x,y,z);
							w.setBlock(x,y-1,z,modsbfp.blockOre.blockID,0,2);
						}else if(w.getBlockId(x,y,z)==Block.stone.blockID&&r.nextInt(1536)==0){
							w.setBlock(x,y,z,modsbfp.blockOre.blockID,0,2);
						}
					}
				}
			}
		}
	}
}