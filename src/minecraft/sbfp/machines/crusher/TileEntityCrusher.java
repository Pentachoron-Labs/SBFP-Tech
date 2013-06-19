package sbfp.machines.crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StringTranslate;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import sbfp.machines.TileEntityProcessor;

import com.google.common.io.ByteArrayDataInput;


public class TileEntityCrusher extends TileEntityProcessor implements IInventory{
	private ItemStack[] inventory = new ItemStack[8];
	
	public void updateEntity(){
		super.updateEntity();
	}
	@Override
	public void handleData(INetworkManager network, int packetTypeID, Packet250CustomPayload packet, EntityPlayer entityPlayer, ByteArrayDataInput data){
		// TODO Auto-generated method stub
		
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
		return true;
		//return this.container.getSlot(0).isItemValid(is);
	}

}
