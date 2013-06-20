package sbfp.machines.processor.crusher;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.SlotFurnace;
import sbfp.machines.processor.ContainerProcessor;
import sbfp.machines.processor.SlotChargedRedstone;

public class ContainerCrusher extends ContainerProcessor{

	public ContainerCrusher(InventoryPlayer inv, TileEntityCrusher tileEntity){
		super(inv,tileEntity);
		this.addSlotToContainer(new SlotCrusher(tileEntity,0,8,24));
		this.addSlotToContainer(new SlotCrusher(tileEntity,1,26,24));
		this.addSlotToContainer(new SlotCrusher(tileEntity,2,8,42));
		this.addSlotToContainer(new SlotCrusher(tileEntity,3,26,42));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,4,80,24));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,5,98,24));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,6,80,46));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,7,98,46));
		this.addSlotToContainer(new SlotChargedRedstone(tileEntity,8,152,80));
		this.addSlotToContainer(new SlotChargedRedstone(tileEntity,9,152,98));
	}
}
