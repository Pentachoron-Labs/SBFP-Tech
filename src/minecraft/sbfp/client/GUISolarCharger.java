package sbfp.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import sbfp.modsbfp;
import sbfp.machines.container.ContainerSolarCharger;
import sbfp.machines.tiles.TileSolarCharger;


public class GUISolarCharger extends GuiContainer{

	private TileSolarCharger tileEntity;
	private int containerWidth;
	private int containerHeight;

	public GUISolarCharger(InventoryPlayer par1InventoryPlayer, TileSolarCharger tileEntity)
	{
		super(new ContainerSolarCharger(par1InventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 175;
		this.ySize = 221;
	}

	/**
* Draw the background layer for the GuiContainer (everything behind the items)
*/
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(modsbfp.guiDirectory + "sun_collector.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
		this.drawTexturedModalRect(this.containerWidth+48, this.containerHeight+47, 177, 0, (79*this.tileEntity.getWorkTicks())/this.tileEntity.maxWorkTicks, 5);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(this.tileEntity.getInvName(), 55, 6, 4210752);
		
	}

}
