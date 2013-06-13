package sbfp.machines.solar;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StringTranslate;
import sbfp.modsbfp;
import sbfp.machines.TileProcessor;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.FMLLog;

public class TileSolarCharger extends TileProcessor implements IInventory{

	private ItemStack[] inventory = new ItemStack[8];
	private boolean hasItem = false;
	public static final int maxWorkTicks = 45*20; // 45 Seconds to make 1 piece of charged redstone
	private ItemStack waitingOutput = new ItemStack(modsbfp.itemRedflux,1,3);
	ContainerSolarCharger container;

	@Override
	public void updateEntity(){
		super.updateEntity();
		if(!this.worldObj.canBlockSeeTheSky(xCoord,yCoord,zCoord)) return;
		if(this.hasItem){
			this.workTicks++;
			if(this.workTicks==maxWorkTicks){
				this.workTicks = 0;
				this.container.mergeItemStack(this.waitingOutput.copy(),4,8,false);
				this.hasItem = false;
			}
		}
		try{
			if(!this.hasItem&&this.container.dryMerge(this.waitingOutput.copy(),4,8,false)>=this.waitingOutput.stackSize){
				for(int i = 0; i<4; i++){
					if(this.inventory[i]!=null){
						this.inventory[i].stackSize--;
						if(this.inventory[i].stackSize<=0){
							this.inventory[i] = null;
						}
						this.hasItem = true;
						break;
					}
				}
			}
		}catch(NullPointerException e){
			if(this.container==null){
				if(!this.playersUsing.isEmpty()){
					FMLLog.warning("The TileSolarCharger.container at (%d,%d,%d) is null on side %s!",xCoord,yCoord,zCoord,this.worldObj.isRemote ? "client" : "server");
					FMLLog.warning("Players using: %s",this.playersUsing.toArray());
				}
				return;
			}else throw new RuntimeException(e);
		}
	}

	private boolean hasOutputRoom(ItemStack output){
		for(int i = 4; i<8; i++){
			if(this.inventory[i]==null) return true;
			if(this.inventory[i].itemID==output.itemID&&this.inventory[i].getItemDamage()==output.getItemDamage()&&this.inventory[i].stackSize+output.stackSize<output.getItem().getItemStackLimit()) return true;
		}
		return false;
	}

	@Override
	public void handleData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream){
		try{
			if(this.worldObj.isRemote){
				this.workTicks = dataStream.readInt();
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
	public ItemStack getStackInSlot(int index){
		return this.inventory[index];
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
		return StringTranslate.getInstance().translateKey("solarcharger.name");
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
		return this.container.getSlot(0).isItemValid(is);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("workTicks",workTicks);
		if(this.waitingOutput!=null){
			NBTTagCompound ntc2 = new NBTTagCompound();
			this.waitingOutput.writeToNBT(ntc2);
			tagCompound.setTag("waitingOutput",ntc2);
		}
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
		this.workTicks = tagCompound.getInteger("workTicks");
		NBTTagCompound ntc2 = tagCompound.getCompoundTag("waitingOutput");
		this.waitingOutput = ItemStack.loadItemStackFromNBT(ntc2);
		NBTTagList var2 = tagCompound.getTagList("items");
		for(int i = 0; i<var2.tagCount(); ++i){
			NBTTagCompound ntc3 = (NBTTagCompound) var2.tagAt(i);
			byte slot = ntc3.getByte("slot");
			if(slot>=0&&slot<this.inventory.length){
				this.inventory[slot] = ItemStack.loadItemStackFromNBT(ntc3);
			}
		}
	}
}