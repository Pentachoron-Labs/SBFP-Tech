package sbfp.machines;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;


public class TileSolarCharger extends TileProcessor implements IInventory{
	
	private ItemStack[] inventory = new ItemStack[6];
	@Override
	public void handleData(INetworkManager network, int packetTypeID, Packet250CustomPayload packet, EntityPlayer entityPlayer, ByteArrayDataInput data){
		
	}

	@Override
	public int getSizeInventory(){
		return this.inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i){
		return null;
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
		return false;
	}

	@Override
	public void openChest(){}

	@Override
	public void closeChest(){}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack){
		return false;
	}

}
