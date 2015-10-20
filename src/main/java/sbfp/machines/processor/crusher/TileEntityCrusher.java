package sbfp.machines.processor.crusher;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IChatComponent;
import sbfp.modsbfp;
import sbfp.machines.processor.TileEntityProcessor;

public class TileEntityCrusher extends TileEntityProcessor implements IInventory{

	private ItemStack[] inventory = new ItemStack[10];

	public static final int maxChargeLevel = 100; // FOR NOW

	private int powerLevel = 10;

	@Override
	protected void mergeOutputs(){
		boolean b = this.container.mergeItemStack(this.waitingOutputs.get(0),40,42,false,false);
		if(this.waitingOutputs.size()==2){
			this.container.mergeItemStack(this.waitingOutputs.get(1),42,44,false,false);
		}
	}

	@Override
	protected boolean dryMergeOutputsAndFeed(){
		for(int i = 0; i<4; i++){
			if(this.inventory[i]!=null){
				this.activeRecipe = modsbfp.crushingRegistry.getProcessesByInputs(this.inventory[i]).get(0);
				this.waitingOutputs = this.activeRecipe.getOutputsWithRandomChance(this.worldObj.rand);
				boolean flag = true;
				if(this.activeRecipe!=null){
					flag = flag&&this.container.dryMerge(this.waitingOutputs.get(0),40,42,false)>=this.waitingOutputs.get(0).stackSize;
					if(this.waitingOutputs.size()==2){
						flag = flag&&this.container.dryMerge(this.waitingOutputs.get(1),42,44,false)>=this.waitingOutputs.get(1).stackSize;
					}
					if(flag){
						this.decrStackSize(i,this.activeRecipe.getInputs().get(0).stackSize);
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


	public boolean isInvNameLocalized(){
		return true;
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		return this.worldObj.getTileEntity(this.getPos())!=this ? false : player.getDistanceSq(this.getPos().getX()+0.5D,this.getY()+0.5D,this.getZ()+0.5D)<=64.0D;
	}


	
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
	}

	@Override
	public String getName() {
		return "crusher";
	}

	@Override
	public boolean hasCustomName() {
		return true;
	}

	@Override
	public IChatComponent getDisplayName() {
		return null;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}