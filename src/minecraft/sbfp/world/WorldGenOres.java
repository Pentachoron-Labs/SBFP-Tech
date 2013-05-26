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
		float f = par2Random.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(par3 + 8) + MathHelper.sin(f) * (float)this.clusterAmount / 8.0F);
        double d1 = (double)((float)(par3 + 8) - MathHelper.sin(f) * (float)this.clusterAmount / 8.0F);
        double d2 = (double)((float)(par5 + 8) + MathHelper.cos(f) * (float)this.clusterAmount / 8.0F);
        double d3 = (double)((float)(par5 + 8) - MathHelper.cos(f) * (float)this.clusterAmount / 8.0F);
        double d4 = (double)(par4 + par2Random.nextInt(3) - 2);
        double d5 = (double)(par4 + par2Random.nextInt(3) - 2);

        for (int l = 0; l <= this.clusterAmount; ++l)
        {
            double d6 = d0 + (d1 - d0) * (double)l / (double)this.clusterAmount;
            double d7 = d4 + (d5 - d4) * (double)l / (double)this.clusterAmount;
            double d8 = d2 + (d3 - d2) * (double)l / (double)this.clusterAmount;
            double d9 = par2Random.nextDouble() * (double)this.clusterAmount / 16.0D;
            double d10 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.clusterAmount) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)l * (float)Math.PI / (float)this.clusterAmount) + 1.0F) * d9 + 1.0D;
            int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
            int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int k2 = i1; k2 <= l1; ++k2)
            {
                double d12 = ((double)k2 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int l2 = j1; l2 <= i2; ++l2)
                    {
                        double d13 = ((double)l2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int i3 = k1; i3 <= j2; ++i3)
                            {
                                double d14 = ((double)i3 + 0.5D - d8) / (d10 / 2.0D);

                                int block = par1World.getBlockId(k2, l2, i3);
                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && (this.replaceID == 0 || block == this.replaceID))
                            	{
                            		par1World.setBlock(k2, l2, i3, this.minableBlockId, this.minableBlockMeta, 2);
                            		System.out.println("Added an ore at" +k2+" "+l2+" "+i3);
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
