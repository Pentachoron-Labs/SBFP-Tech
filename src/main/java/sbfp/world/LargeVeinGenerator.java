package sbfp.world;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import sbfp.SBFPHelper;
import sbfp.modsbfp;

class LargeVeinGenerator{

	private final int seedX, seedY, seedZ;
	private final World world;
	private final Random rand;
	private final int meta;
	private final double veinSize;
	private int X, Y, Z;
	private ArrayList<Integer[]> blocks, blocksNew;
	private final boolean dirtFlag;

	LargeVeinGenerator(int seedX, int seedY, int seedZ, World world, Random rand, int meta, double veinSize){
		this(seedX,seedY,seedZ,world,rand,meta,veinSize,false);
	}

	LargeVeinGenerator(int seedX, int seedY, int seedZ, World world, Random rand, int meta, double veinSize, boolean dirtFlag){
		this.seedX = seedX;
		this.seedY = seedY;
		this.seedZ = seedZ;
		this.world = world;
		this.rand = rand;
		this.meta = meta;
		this.veinSize = veinSize;
		this.dirtFlag = dirtFlag;
	}

	void generate(){
		world.setBlockState(new BlockPos(seedX, seedY, seedZ),(IBlockState)modsbfp.blockOre.getBlockState(),2);
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
				// if(this.meta == 5)
				// FMLLog.finer("I haz a %s deposit at (%d,%d,%d) with %d blocks",modsbfp.blockOre.names[this.meta],seedX,seedY,seedZ,iter);
				break;
			}
		}
	}

	private void setBlock(int x, int y, int z){
		int X = this.X+x, Y = this.Y+y, Z = this.Z+z;
		BlockPos newPosition = new BlockPos(this.X+x, this.Y+y, this.Z+z);
		int dx = X-seedX, dy = Y-seedY, dz = Z-seedZ;
		if((SBFPHelper.getBlock(world, newPosition)==Block.getBlockFromName("minecraft:stone")||this.dirtFlag&&SBFPHelper.getBlock(world, newPosition)==Block.getBlockFromName("minecraft:dirt"))&&rand.nextDouble()<Math.pow(Math.pow(dx,16)+Math.pow(dy,16)+Math.pow(dz,16)+1,-this.veinSize)){
			world.setBlockState(newPosition,(IBlockState) modsbfp.blockOre.getBlockState(),2);
			blocksNew.add(new Integer[]{X,Y,Z});
		}
	}
}