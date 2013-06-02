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
		FMLLog.finest("Chunk (%d,%d)",cx,cz);
		cx <<= 4;
		cz <<= 4;
		if(cg instanceof ChunkProviderGenerate){
			for(int x = cx; x<cx+16; x++){
				for(int z = cz; z<cz+16; z++){
					for(int y = 6; y<40; y++){
						if(w.getBlockId(x,y,z)==Block.stone.blockID){
							if(w.getBlockId(x,y+1,z)==Block.waterStill.blockID&&r.nextInt(32)==0){
								w.setBlock(x,y,z,modsbfp.blockOre.blockID,0,2); //Monazite—pools
							}else if(r.nextInt(1536)==0){
								w.setBlock(x,y,z,modsbfp.blockOre.blockID,0,2); //Monazite—other
							}else if(r.nextInt(10240)==0){
								new LargeVeinGenerator(x,y,z,w,r).generate();
							}
						}
					}
				}
			}
		}
	}

	private static class LargeVeinGenerator{

		private final int seedX, seedY, seedZ;
		private final World world;
		private final Random rand;
		private int X, Y, Z;
		private ArrayList<Integer[]> blocks, blocksNew;

		private LargeVeinGenerator(int seedX, int seedY, int seedZ, World world, Random rand){
			this.seedX = seedX;
			this.seedY = seedY;
			this.seedZ = seedZ;
			this.world = world;
			this.rand = rand;
		}

		private void generate(){
			world.setBlock(seedX,seedY,seedZ,modsbfp.blockOre.blockID,1,2);
			int iter = 0;
			blocks = new ArrayList<Integer[]>();
			blocksNew = new ArrayList<Integer[]>();
			blocks.add(new Integer[]{seedX,seedY,seedZ});
			while(true){
				for(Integer[] i:blocks){
					X = i[0];
					Y = i[1];
					Z = i[2];
					setBlock(1,0,0);
					setBlock(-1,0,0);
					setBlock(0,1,0);
					setBlock(0,-1,0);
					setBlock(0,0,1);
					setBlock(0,0,-1);
					iter++;
				}
				blocks = blocksNew;
				blocksNew = new ArrayList<Integer[]>();
				if(blocks.size()==0){
					FMLLog.finer("I haz a LV deposit at (%d,%d,%d) with %d blocks",seedX,seedY,seedZ,iter);
					break;
				}
			}
		}

		private void setBlock(int x, int y, int z){
			int X = this.X+x, Y = this.Y+y, Z = this.Z+z;
			int dx = X-seedX, dy = Y-seedY, dz = Z-seedZ;
			if(world.getBlockId(X,Y,Z)==Block.stone.blockID&&rand.nextDouble()<1/Math.sqrt(Math.pow(dx,16)+Math.pow(dy,16)+Math.pow(dz,16)+1)){
				world.setBlock(X,Y,Z,modsbfp.blockOre.blockID,1,2);
				blocksNew.add(new Integer[]{X,Y,Z});
			}
		}
	}
}