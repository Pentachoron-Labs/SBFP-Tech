package sbfp.machines.processor.solar;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.SlotFurnace;
import sbfp.machines.processor.ContainerProcessor;

public class ContainerSolarCharger extends ContainerProcessor{

	public ContainerSolarCharger(InventoryPlayer inv, TileEntitySolarCharger tileEntity){
		super(inv,tileEntity);
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,0,8,32));
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,1,26,32));
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,2,8,50));
		this.addSlotToContainer(new SlotSolarCharger(tileEntity,3,26,50));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,4,134,32));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,5,152,32));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,6,134,50));
		this.addSlotToContainer(new SlotFurnace(inv.player,tileEntity,7,152,50));
	}
}