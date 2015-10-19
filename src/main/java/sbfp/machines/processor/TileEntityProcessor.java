package sbfp.machines.processor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.FMLLog;

/** This class is for machines that have an input and output */
public abstract class TileEntityProcessor extends TileEntity implements IUpdatePlayerListBox{

	public int workTicks = 0;
	protected long ticks = 0;
	public ContainerProcessor container;
	public final Set<EntityPlayer> playersUsing = new HashSet<EntityPlayer>();
	protected MaterialProcess activeRecipe;
	protected List<ItemStack> waitingOutputs;
	protected boolean hasItem;

	public MaterialProcess getActiveRecipe(){
		return this.activeRecipe;
	}
	@Override
	public void update(){
		if(this.ticks==0){
			this.initialize();
		}
		if(this.ticks>=Long.MAX_VALUE){
			this.ticks = 1;
		}
		this.ticks++;
		this.process();
	}

	protected void process(){
		if(this.hasItem){
			this.workTicks++;
			if(this.workTicks==this.activeRecipe.getTime()){
				this.workTicks = 0;
				this.mergeOutputs();
				this.hasItem = false;
			}
		}
		try{
			if(!this.hasItem){
				if(this.dryMergeOutputsAndFeed()){
					this.hasItem = true;
				}
			}
		}catch(NullPointerException e){
			if(this.container==null){
				if(!this.playersUsing.isEmpty()){
					FMLLog.warning("The %s.container at (%d,%d,%d) is null on side %s!",this.getClass().getName(),this.getX(), this.getY(), this.getZ() ,this.worldObj.isRemote ? "client" : "server");
					FMLLog.warning("Players using: %s",this.playersUsing.toArray());
				}
				return;
			}else throw new RuntimeException(e);
		}
	}

	/**
	 * Merges this.waitingOutput as the individual machine
	 * sees fit. (you're basically calling
	 * <code>for(ItemStack i:this.waitingOutputs)
	 * this.container.mergeItemStack(i,start,end,false);
	 * </code>but there are exceptions (*this* output goes *here*)
	 * and it's just as much work as asking for the output
	 * range.
	 */
	protected abstract void mergeOutputs();

	/**
	 * Does a dry merge (sub-call to your container as you
	 * see fit.) Then decreases inputs, if possible and
	 * they match, and sets both waitingOutput and
	 * activeRecipe.
	 * @return The recipe to be used, or null if EITHER
	 * there's no room for the outputs OR no inputs match.
	 * (Both need to be checked here; they're both
	 * machine-dependent.)
	 */
	protected abstract boolean dryMergeOutputsAndFeed();

	protected void initialize(){}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setLong("ticks",this.ticks);
		tagCompound.setInteger("workTicks",this.workTicks);
		if(this.activeRecipe!=null) tagCompound.setString("recipeID",this.activeRecipe.getName());
		NBTTagList tagList = new NBTTagList();
		if(this.waitingOutputs==null) return;
		for(int i = 0; i<this.waitingOutputs.size(); ++i){
			if(this.waitingOutputs.get(i)!=null){
				NBTTagCompound ntc3 = new NBTTagCompound();
				ntc3.setByte("slot",(byte) i);
				this.waitingOutputs.get(i).writeToNBT(ntc3);
				tagList.appendTag(ntc3);
			}
		}
		tagCompound.setTag("waitingOutputs",tagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		
	}

	public boolean isUseableByPlayer(EntityPlayer player){
		return this.getWorld().getTileEntity(new BlockPos(this.getX(), this.getY(), this.getZ()))!=this ? false : player.getDistanceSq(this.getX()+0.5D,this.getY()+0.5D,this.getZ()+0.5D)<=64.0D;
	}
	public int getX(){
		return this.getPos().getX();
	}
	public int getY(){
		return this.getPos().getY();
	}
	public int getZ(){
		return this.getPos().getZ();
	}
}