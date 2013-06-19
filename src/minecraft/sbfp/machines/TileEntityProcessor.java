package sbfp.machines;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sbfp.IPacketReciever;

/** This class is for machines that have an input and output */
public abstract class TileEntityProcessor extends TileEntity implements IPacketReciever{

	public int workTicks = 0;
	protected long ticks = 0;
	public ContainerProcessor container;
	public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

	@Override
	public void updateEntity(){
		if(this.ticks==0){
			this.initialize();
		}
		if(this.ticks>=Long.MAX_VALUE){
			this.ticks = 1;
		}
		this.ticks++;
	}

	protected void initialize(){}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setLong("ticks",this.ticks);
		tagCompound.setInteger("workTicks",this.workTicks);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		this.ticks = tagCompound.getLong("ticks");
		this.workTicks = tagCompound.getInteger("workTicks");
	}

	public int getWorkTicks(){
		return this.workTicks;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player){
		return this.worldObj.getBlockTileEntity(this.xCoord,this.yCoord,this.zCoord)!=this ? false : player.getDistanceSq(this.xCoord+0.5D,this.yCoord+0.5D,this.zCoord+0.5D)<=64.0D;
	}
}