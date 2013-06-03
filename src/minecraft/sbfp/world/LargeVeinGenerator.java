package sbfp.world;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import sbfp.modsbfp;
import cpw.mods.fml.common.FMLLog;

class LargeVeinGenerator{

	private final int seedX, seedY, seedZ;
	private final World world;
	private final Random rand;
	private final int meta;
	private final double veinSize;
	private int X, Y, Z;
	private ArrayList<Integer[]> blocks, blocksNew;
	
	LargeVeinGenerator(int seedX, int seedY, int seedZ, World world, Random rand, int meta, double veinSize){
		this.seedX = seedX;
		this.seedY = seedY;
		this.seedZ = seedZ;
		this.world = world;
		this.rand = rand;
		this.meta = meta;
		this.veinSize = veinSize;
	}

	void generate(){
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
//				FMLLog.finer("I haz a LV deposit at (%d,%d,%d) with %d blocks",seedX,seedY,seedZ,iter);
				break;
			}
		}
	}

	private void setBlock(int x, int y, int z){
		int X = this.X+x, Y = this.Y+y, Z = this.Z+z;
		int dx = X-seedX, dy = Y-seedY, dz = Z-seedZ;
		if(world.getBlockId(X,Y,Z)==Block.stone.blockID&&rand.nextDouble()<Math.pow(Math.pow(dx,16)+Math.pow(dy,16)+Math.pow(dz,16)+1,-this.veinSize)){
			world.setBlock(X,Y,Z,modsbfp.blockOre.blockID,meta,2);
			blocksNew.add(new Integer[]{X,Y,Z});
		}
	}
}