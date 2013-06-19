package sbfp.world;

import java.util.ArrayList;
import java.util.Random;

import sbfp.modsbfp;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

class PoolGenerator extends LargeVeinGenerator{
	
	PoolGenerator(int seedX, int seedY, int seedZ, World world, Random rand){
		super(seedX,seedY,seedZ,world,rand,0,0,true);
		FMLLog.info("I haz a methane pool at (%d,%d,%d)",seedX,seedY,seedZ);
	}
	
	protected void setBlock(int x,int y, int z){
		int X = this.X+x, Y=this.Y+y, Z = this.Z +z;
		int dx = X-seedX, dy = Y-seedY, dz = Z-seedZ;
		if((world.getBlockId(X,Y,Z)==Block.stone.blockID||this.dirtFlag&&(world.getBlockId(X,Y,Z)==Block.dirt.blockID||this.world.getBlockId(X,Y,Z)==Block.gravel.blockID))&&rand.nextDouble()<Math.pow(Math.pow(dx,2)+Math.pow(dy,4)+Math.pow(dz,2)+1,-this.veinSize)){
			world.setBlock(X,Y,Z,0,0,3);
			blocksNew.add(new Integer[]{X,Y,Z});
		}
	}
}