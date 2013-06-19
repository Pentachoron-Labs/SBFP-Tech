package sbfp.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import cpw.mods.fml.common.IWorldGenerator;

public class GeneratorMe implements IWorldGenerator{

	@Override
	public void generate(Random r, int cx, int cz, World w, IChunkProvider cg, IChunkProvider cp){
		cx <<= 4;
		cz <<= 4;
		if(cg instanceof ChunkProviderGenerate){
			for(int x=cx;x<cx+16;x++){
				for(int z=cz;z<cz+16;z++){
					for(int y=8;y<24;y++){
						if(r.nextInt(40960)==0){
							new PoolGenerator(x,y,z,w,r).generate();
						}
					}
				}
			}
		}
	}
}