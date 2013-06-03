package sbfp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import sbfp.modsbfp;
import cpw.mods.fml.common.IWorldGenerator;

public final class GeneratorOres implements IWorldGenerator{

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
							}else if(r.nextInt(2560)==0){
								new LargeVeinGenerator(x,y,z,w,r,1,1).generate(); //Fluorite
							}else if(r.nextInt(1536)==0){
								w.setBlock(x,y,z,modsbfp.blockOre.blockID,2,2); //MoS₂
								if(r.nextBoolean()){
									int q = r.nextInt(3);
									w.setBlock(x+(q==0 ? 1 : 0),y+(q==1 ? 1 : 0),z+(q==2 ? 1 : 0),modsbfp.blockOre.blockID,2,2);
								}
							}else if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,3,1).generate(); //Rutile
							}else if(r.nextInt(1280)==0&&bordersLava(x,y,z,w)){
								new LargeVeinGenerator(x,y,z,w,r,3,1).generate(); //Cinnabar—lava pools
							}else if(r.nextInt(7680)==0){
								new LargeVeinGenerator(x,y,z,w,r,4,1).generate(); //Cinnabar—other
							}else if(r.nextInt(7680)==0){
								new LargeVeinGenerator(x,y,z,w,r,7,1).generate(); //Arsenopyrite
							}
						}
					}
					for(int y = 40; y<80; y++){
						if(w.getBlockId(x,y,z)==Block.stone.blockID||w.getBlockId(x,y,z)==Block.dirt.blockID){
							if(r.nextInt(7680)==0){
								new LargeVeinGenerator(x,y,z,w,r,5,1).generate(); //Limonite
							}else if(r.nextInt(7680)==0){
								new LargeVeinGenerator(x,y,z,w,r,6,1).generate(); //Pyrolusite
							}
						}
					}
				}
			}
		}
	}

	private static boolean bordersLava(int x, int y, int z, World w){
		int q = Block.lavaStill.blockID;
		return w.getBlockId(x,y+1,z)==q||w.getBlockId(x,y-1,z)==q||w.getBlockId(x,y-2,z)==q||w.getBlockId(x+1,y,z)==q||w.getBlockId(x-1,y,z)==q||w.getBlockId(x,y,z+1)==q||w.getBlockId(x,y,z-1)==q;
	}
}