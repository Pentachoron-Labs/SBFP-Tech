package sbfp.machines.tiles;


import com.google.common.io.ByteArrayDataInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;


public class TileSolarCharger extends TileProcessor implements IInventory{
	
	private ItemStack[] inventory = new ItemStack[8];
	public static final int maxWorkTicks = 45*20; //45 Seconds to make 1 piece of charged redstone
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(this.workTicks > maxWorkTicks){
			this.workTicks = 0;
			
		}if(this.canWork()){
			this.workTicks++;
		}
		
		
	}
	
	private boolean canWork(){
		if(!(this.worldObj.isDaytime()&&this.worldObj.canBlockSeeTheSky(this.xCoord,this.yCoord+1,this.zCoord))) return false;
		boolean hasInputs = true;
		boolean hasOutputs = true;
		for(int i = 0; i<4; i++){
			hasInputs = hasInputs&&(this.inventory[i]!=null);
		}
		for(int i = 4; i<8; i++){
			hasOutputs = hasOutputs&&(this.inventory[i] == null);
			if(hasOutputs) break;
			hasOutputs = hasOutputs&&(this.inventory[i].stackSize < this.getInventoryStackLimit());
			if(hasOutputs) break;
		}
		return hasInputs&&hasOutputs;
	}

	public int getWorkTicks(){
		return this.workTicks;
	}
	
	@Override
	public void handleData(INetworkManager network, int packetTypeID, Packet250CustomPayload packet, EntityPlayer entityPlayer, ByteArrayDataInput data){
		
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
	public ItemStack decrStackSize(int i, int j){
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i){
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack){
		
		
	}

	@Override
	public String getInvName(){
		return "Sunlight Collector";
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
	public boolean isUseableByPlayer(EntityPlayer entityplayer){
		return true;
	}

	@Override
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack is){
		if(is.itemID == Item.redstone.itemID && i < 3) return true;
		return false;
	}

}
