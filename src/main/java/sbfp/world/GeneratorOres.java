package sbfp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.fml.common.IWorldGenerator;
import sbfp.SBFPHelper;
import sbfp.modsbfp;

public final class GeneratorOres implements IWorldGenerator{
        public static final int GENERATOR_WEIGHT = 100; //I dunno what this does, so it's 100 
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
						if(SBFPHelper.getBlock(w, pos)==Blocks.stone){
							if(SBFPHelper.getBlock(w, x,y+1,z)==Blocks.water &&r.nextInt(32)==0){
								w.setBlockState(pos, modsbfp.blockOre.getStateFromMeta(EnumOreType.MONAZITE.getMeta()),2); // Monazite—pools
							}else if(r.nextInt(1536)==0){
								w.setBlockState(pos, modsbfp.blockOre.getStateFromMeta(EnumOreType.MONAZITE.getMeta()),2); // Monazite—other
							}else if(r.nextInt(2560)==0){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.FLUORITE.getMeta(),1).generate(); // Fluorite
							}else if(r.nextInt(1536)==0){
								w.setBlockState(pos,modsbfp.blockOre.getStateFromMeta(EnumOreType.MOLYBDENITE.getMeta()),2); // MoS₂
								if(r.nextBoolean()){
									int q = r.nextInt(3);
									w.setBlockState(new BlockPos(x+(q==0 ? 1 : 0),y+(q==1 ? 1 : 0),z+(q==2 ? 1 : 0)),modsbfp.blockOre.getStateFromMeta(EnumOreType.MOLYBDENITE.getMeta()),2);
								}
							}else if(r.nextInt(10240)==0){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.RUTILE.getMeta(),2).generate(); // Rutile
							}else if(r.nextInt(128)==0&&bordersLava(new BlockPos(x,y,z),w)){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.CINNABAR.getMeta(),1.5).generate(); // Cinnabar—lava
																					// pools
							}else if(r.nextInt(2560)==0){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.CINNABAR.getMeta(),1.5).generate(); // Cinnabar—other
							}else if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.ARSENOPYRITE.getMeta(),1.5).generate(); // Arsenopyrite
							}
						}
					}
					for(int y = 40; y<60; y++){
						if(SBFPHelper.getBlock(w, new BlockPos(x,y,z))==Blocks.stone){
							if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.LIMONITE.getMeta(),1,true).generate(); // Limonite
							}else if(r.nextInt(5120)==0){
								new LargeVeinGenerator(x,y,z,w,r,EnumOreType.PYROLUSITE.getMeta(),1,true).generate(); // Pyrolusite
							}
						}
					}
				}
			}
		}
	}

	private static boolean bordersLava(BlockPos pos, World w){ 
		Block q = Blocks.lava;
		
		return w.getBlockState(new BlockPos(pos.getX(),pos.getY()+1, pos.getZ())).getBlock()==q||w.getBlockState(new BlockPos(pos.getX(), pos.getY()-1, pos.getZ())).getBlock()==q||w.getBlockState(new BlockPos(pos.getX(), pos.getY()-2, pos.getZ())).getBlock()==q;// ||w.getBlockId(x+1,y,z)==q||w.getBlockId(x-1,y,z)==q||w.getBlockId(x,y,z+1)==q||w.getBlockId(x,y,z-1)==q;
	}
}