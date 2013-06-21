package sbfp.machines.processor.crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StringTranslate;
import sbfp.modsbfp;
import sbfp.machines.Recipe;
import sbfp.machines.processor.TileEntityProcessor;

import com.google.common.io.ByteArrayDataInput;

public class TileEntityCrusher extends TileEntityProcessor implements IInventory{

	private ItemStack[] inventory = new ItemStack[10];

	public static final int maxChargeLevel = 100; // FOR NOW

	private int powerLevel = 56;

	@Override
	protected void mergeOutputs(){
		boolean b = this.container.mergeItemStack(this.waitingOutputs[0],40,42,false,false);
		if(this.waitingOutputs.length==2){
			this.container.mergeItemStack(this.waitingOutputs[1],42,44,false,false);
		}
	}

	@Override
	protected boolean dryMergeOutputsAndFeed(){
		for(int i = 0; i<4; i++){
			if(this.inventory[i]!=null){
				this.activeRecipe = modsbfp.prmCrusher.getRecipe(this.inventory[i]);
				this.waitingOutputs = this.activeRecipe.getOutputs(this);
				boolean flag = true;
				if(this.activeRecipe!=null){
					flag = flag&&this.container.dryMerge(this.waitingOutputs[0],40,42,false)>=this.waitingOutputs[0].stackSize;
					if(this.waitingOutputs.length==2){
						flag = flag&&this.container.dryMerge(this.waitingOutputs[1],42,44,false)>=this.waitingOutputs[1].stackSize;
					}
					if(flag){
						this.decrStackSize(i,this.activeRecipe.getInputs()[0].stackSize);
						return true;
					}
				}
			}
		}
		this.activeRecipe = null;
		this.waitingOutputs = null;
		return false;
	}

	public int getPowerLevel(){
		return this.powerLevel;
	}

	@Override
	public void handleData(INetworkManager network, int packetTypeID, Packet250CustomPayload packet, EntityPlayer entityPlayer, ByteArrayDataInput dataStream){
		try{
			if(this.worldObj.isRemote){
				this.workTicks = dataStream.readInt();
				this.powerLevel = dataStream.readInt();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public int getSizeInventory(){
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i){
		return this.inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int num){
		if(this.inventory[slot]!=null){
			ItemStack stack;
			if(this.inventory[slot].stackSize<=num){
				stack = this.inventory[slot];
				this.inventory[slot] = null;
				return stack;
			}else{
				stack = this.inventory[slot].splitStack(num);
				if(this.inventory[slot].stackSize==0){
					this.inventory[slot] = null;
				}
				return stack;
			}
		}else return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index){
		if(this.inventory[index]!=null){
			ItemStack var2 = this.inventory[index];
			this.inventory[index] = null;
			return var2;
		}else return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack){
		this.inventory[index] = stack;
		if(stack!=null&&stack.stackSize>this.getInventoryStackLimit()){
			stack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName(){
		return StringTranslate.getInstance().translateKey("crusher.name");
	}

	@Override
	public boolean isInvNameLocalized(){
		return true;
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		return this.worldObj.getBlockTileEntity(this.xCoord,this.yCoord,this.zCoord)!=this ? false : player.getDistanceSq(this.xCoord+0.5D,this.yCoord+0.5D,this.zCoord+0.5D)<=64.0D;
	}

	@Override
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is){
		return this.container.getSlot(i).isItemValid(is);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i<this.inventory.length; ++i){
			if(this.inventory[i]!=null){
				NBTTagCompound ntc3 = new NBTTagCompound();
				ntc3.setByte("slot",(byte) i);
				this.inventory[i].writeToNBT(ntc3);
				tagList.appendTag(ntc3);
			}
		}
		tagCompound.setTag("items",tagList);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		NBTTagList var2 = tagCompound.getTagList("items");
		for(int i = 0; i<var2.tagCount(); ++i){
			NBTTagCompound ntc3 = (NBTTagCompound) var2.tagAt(i);
			byte slot = ntc3.getByte("slot");
			if(slot>=0&&slot<this.inventory.length){
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(ntc3);
			}
		}
	}

	@Override
	protected Recipe getRecipeByID(int i){
		return modsbfp.prmCrusher.getRecipeByID(i);
	}
}