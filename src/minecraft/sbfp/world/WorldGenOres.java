package sbfp.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class WorldGenOres{
	
	private int maxGenLevel;
	private int minGenLevel;
	private int clusterAmount;
	private int chunkAmount;
	private int minableBlockId;
    private int minableBlockMeta;
	private int replaceID;

	public WorldGenOres(int ID, int meta, int minLevel, int maxLevel, int chunk, int cluster, int replace){
		this.maxGenLevel = maxLevel;
		this.minGenLevel = minLevel;
		this.clusterAmount = cluster;
		this.chunkAmount = chunk;
		this.minableBlockId = ID;
		this.minableBlockMeta = meta;
		this.replaceID = replace;

	}
	
	public void generate(World w, Random r, int x, int z){
		for (int i = 0; i < this.chunkAmount; i++)
		{
			int xCoord = x + r.nextInt(16);
			int zCoord = z + r.nextInt(16);
			int yCoord = r.nextInt(Math.max(this.maxGenLevel - this.minGenLevel, 0)) + this.minGenLevel;
			this.generate(w, r, xCoord, yCoord, zCoord);
		}
	}
	
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		float var6 = par2Random.nextFloat() * (float) Math.PI;
		double var7 = par3 + 8 + MathHelper.sin(var6) * this.clusterAmount / 8.0F;
		double var9 = par3 + 8 - MathHelper.sin(var6) * this.clusterAmount / 8.0F;
		double var11 = par5 + 8 + MathHelper.cos(var6) * this.clusterAmount / 8.0F;
		double var13 = par5 + 8 - MathHelper.cos(var6) * this.clusterAmount / 8.0F;
		double var15 = par4 + par2Random.nextInt(3) - 2;
		double var17 = par4 + par2Random.nextInt(3) - 2;

		for (int var19 = 0; var19 <= this.clusterAmount; ++var19)
			{
			double var20 = var7 + (var9 - var7) * var19 / this.clusterAmount;
			double var22 = var15 + (var17 - var15) * var19 / this.clusterAmount;
			double var24 = var11 + (var13 - var11) * var19 / this.clusterAmount;
			double var26 = par2Random.nextDouble() * this.clusterAmount / 16.0D;
			double var28 = (MathHelper.sin(var19 * (float) Math.PI / this.clusterAmount) + 1.0F) * var26 + 1.0D;
			double var30 = (MathHelper.sin(var19 * (float) Math.PI / this.clusterAmount) + 1.0F) * var26 + 1.0D;
			int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
			int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
			int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
			int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
			int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
			int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);

			for (int var38 = var32; var38 <= var35; ++var38)
			{
				double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);

				if (var39 * var39 < 1.0D)
				{
					for (int var41 = var33; var41 <= var36; ++var41)
					{
						double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);

						if (var39 * var39 + var42 * var42 < 1.0D)
						{
							for (int var44 = var34; var44 <= var37; ++var44)
							{
								double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);

								int block = par1World.getBlockId(var38, var41, var44);
								if (var39 * var39 + var42 * var42 + var45 * var45 < 1.0D && (this.replaceID == 0 || block == this.replaceID))
								{
									par1World.setBlock(var38, var41, var44, this.minableBlockId, this.minableBlockMeta, 2);
									System.out.println("Added an ore");
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

}
