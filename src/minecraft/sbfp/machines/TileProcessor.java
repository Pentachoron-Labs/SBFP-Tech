package sbfp.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sbfp.IPacketReciever;

/** This class is for machines that have an input and output*/
public abstract class TileProcessor extends TileEntity implements IPacketReciever{
	protected int workTicks = 0;
	
	@Override
	public void updateEntity(){
		this.workTicks++;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("workTicks", this.workTicks);
	}
	
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		this.workTicks = tagCompound.getInteger("workTicks");
	}
	
	
}
