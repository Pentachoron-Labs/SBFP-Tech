package sbfp.world;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import sbfp.modsbfp;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;

public final class GeneratorOres implements IWorldGenerator{

	public GeneratorOres(){}

	@Override
	public void generate(Random r, int cx, int cz, World w, IChunkProvider cg, IChunkProvider cp){
//		FMLLog.finest("Chunk (%d,%d)",cx,cz);
		cx <<= 4;
		cz <<= 4;
		if(cg instanceof ChunkProviderGenerate){
			for(int x = cx; x<cx+16; x++){
				for(int z = cz; z<cz+16; z++){
					for(int y = 0; y<40; y++){
						if(w.getBlockId(x,y,z)==Block.stone.blockID){
							if(w.getBlockId(x,y+1,z)==Block.waterStill.blockID&&r.nextInt(32)==0){
								w.setBlock(x,y,z,modsbfp.blockOre.blockID,0,2); //Monazite—pools
							}else if(r.nextInt(1536)==0){
								w.setBlock(x,y,z,modsbfp.blockOre.blockID,0,2); //Monazite—other
							}else if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,1).generate(); //Fluorite
							}else if(r.nextInt(10240)==0){
								new LargeVeinGenerator(x,y,z,w,r,2).generate(); //MoS₂
							}
						}
					}
				}
			}
		}
	}
}