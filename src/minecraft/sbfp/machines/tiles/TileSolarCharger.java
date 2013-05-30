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
	public ItemStack getStackInSlot(int par1)
	{
		return this.inventory[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2)
	{
		if (this.inventory[par1] != null)
		{
			ItemStack var3;

			if (this.inventory[par1].stackSize <= par2)
			{
				var3 = this.inventory[par1];
				this.inventory[par1] = null;
				return var3;
			}
			else
			{
				var3 = this.inventory[par1].splitStack(par2);

				if (this.inventory[par1].stackSize == 0)
				{
					this.inventory[par1] = null;
				}

				return var3;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if (this.inventory[par1] != null)
		{
			ItemStack var2 = this.inventory[par1];
			this.inventory[par1] = null;
			return var2;
		}
		else
		{
			return null;
		}
}

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.inventory[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
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
