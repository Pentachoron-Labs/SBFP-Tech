package sbfp.world;

import java.util.Random;

import net.minecraft.world.World;


public class WorldGenOres{
	private int maxGenLevel;
	private int minGenLevel;
	private int amountPerChunk;
	private int genID;
	public WorldGenOres(int minGen, int maxGen, int amt, int ID){
		this.maxGenLevel = maxGen;
		this.minGenLevel = minGen;
		this.amountPerChunk = amt;
		this.genID = ID;
	}
	
	public void generate(World w, Random r, int x, int y, int z){
		for (int i = 0; i < this.amountPerChunk; i++)
		{
		int xCoord = x + r.nextInt(16);
		int zCoord = z + r.nextInt(16);
		int yCoord = r.nextInt(Math.max(this.maxGenLevel - this.minGenLevel, 0)) + this.minGenLevel;
		w.setBlock(xCoord,yCoord,zCoord,this.genID);
		}
	}

}
