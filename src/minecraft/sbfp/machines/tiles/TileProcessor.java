package sbfp.machines.tiles;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import sbfp.IPacketReciever;
import cpw.mods.fml.common.FMLLog;

/** This class is for machines that have an input and output*/
public abstract class TileProcessor extends TileEntity implements IPacketReciever{

	int workTicks = 0;
	long ticks = 0;
	public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();

	@Override
	public void updateEntity(){
		if(this.ticks==0){
			FMLLog.info("Created TileEntityProcessor");
			this.intialize();
		}
		if(this.ticks>=Long.MAX_VALUE){
			this.ticks = 1;
		}

		this.ticks++;
	}

	private void intialize(){

	}

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

}
