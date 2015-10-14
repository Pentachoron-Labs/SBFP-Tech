/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sbfp;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author 641803
 */
public class SBFPHelper {
   public static Block getBlock(World w, BlockPos pos){
		return w.getBlockState(pos).getBlock();
	}
   public static Block getBlock(World w, int x, int y, int z){
       return SBFPHelper.getBlock(w, new BlockPos(x,y,z));
   }
}
