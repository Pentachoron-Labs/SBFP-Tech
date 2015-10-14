package sbfp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.fml.common.IWorldGenerator;
import sbfp.SBFPHelper;
import sbfp.modsbfp;

public final class GeneratorOres implements IWorldGenerator{

	@Override
	public void generate(Random r, int cx, int cz, World w, IChunkProvider cg, IChunkProvider cp){
		// FMLLog.finest("Chunk (%d,%d)",cx,cz);
		cx <<= 4;
		cz <<= 4;
                BlockPos pos;
		if(cg instanceof ChunkProviderGenerate){
			for(int x = cx; x<cx+16; x++){
				for(int z = cz; z<cz+16; z++){
					for(int y = 0; y<40; y++){
                                                pos = new BlockPos(x,y,z);
						if(SBFPHelper.getBlock(w, pos)==Block.getBlockFromName("minecraft:stone")){
							if(SBFPHelper.getBlock(w, x,y+1,z)==Block.getBlockFromName("minecraft:waterStill") &&r.nextInt(32)==0){
								w.setBlockState(pos,(IBlockState) modsbfp.blockOre.getBlockState(),2); // Monazite—pools
							}else if(r.nextInt(1536)==0){
								w.setBlockState(pos,(IBlockState) modsbfp.blockOre.getBlockState(),2); // Monazite—other
							}else if(r.nextInt(2560)==0){
								new LargeVeinGenerator(x,y,z,w,r,1,1).generate(); // Fluorite
							}else if(r.nextInt(1536)==0){
								w.setBlockState(pos,(IBlockState) modsbfp.blockOre.getBlockState(),2); // MoS₂
								if(r.nextBoolean()){
									int q = r.nextInt(3);
									w.setBlockState(new BlockPos(x+(q==0 ? 1 : 0),y+(q==1 ? 1 : 0),z+(q==2 ? 1 : 0)),(IBlockState)modsbfp.blockOre.getBlockState(),2);
								}
							}else if(r.nextInt(10240)==0){
								new LargeVeinGenerator(x,y,z,w,r,3,2).generate(); // Rutile
							}else if(r.nextInt(128)==0&&bordersLava(new BlockPos(x,y,z),w)){
								new LargeVeinGenerator(x,y,z,w,r,4,1.5).generate(); // Cinnabar—lava
																					// pools
							}else if(r.nextInt(2560)==0){
								new LargeVeinGenerator(x,y,z,w,r,4,1.5).generate(); // Cinnabar—other
							}else if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,7,1.5).generate(); // Arsenopyrite
							}
						}
					}
					for(int y = 40; y<60; y++){
						if(SBFPHelper.getBlock(w, new BlockPos(x,y,z))==Block.getBlockFromName("minecraft:stone")){
							if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,5,1,true).generate(); // Limonite
							}else if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,6,1,true).generate(); // Pyrolusite
							}
						}
					}
				}
			}
		}
	}

	private static boolean bordersLava(BlockPos pos, World w){ 
		Block q = Block.getBlockFromName("minecraft:lava_still");
		
		return w.getBlockState(new BlockPos(pos.getX(),pos.getY()+1, pos.getZ())).getBlock()==q||w.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getBlock()==q||w.getBlockState(new BlockPos(pos.getX(), pos.getY()-2, pos.getZ())).getBlock()==q;// ||w.getBlockId(x+1,y,z)==q||w.getBlockId(x-1,y,z)==q||w.getBlockId(x,y,z+1)==q||w.getBlockId(x,y,z-1)==q;
	}
}