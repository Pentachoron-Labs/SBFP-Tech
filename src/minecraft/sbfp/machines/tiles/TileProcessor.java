package sbfp.machines.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sbfp.IPacketReciever;

/** This class is for machines that have an input and output*/
public abstract class TileProcessor extends TileEntity implements IPacketReciever{

	int workTicks = 0;
	int ticks;

	@Override
	public void updateEntity(){ //number of ticks into the second, for packet sending purposes
		this.ticks++;
		if(this.ticks>20){
			this.ticks = 0;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("workTicks",this.workTicks);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		this.workTicks = tagCompound.getInteger("workTicks");
	}

}
