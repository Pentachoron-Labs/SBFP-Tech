package sbfp.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import sbfp.modsbfp;
import sbfp.machines.processor.crusher.ContainerCrusher;
import sbfp.machines.processor.crusher.TileEntityCrusher;

public class GUICrusher extends GuiContainer{

	private TileEntityCrusher tileEntity;
	private int containerWidth;
	private int containerHeight;

	public GUICrusher(InventoryPlayer inv, TileEntityCrusher tileEntity){
		super(new ContainerCrusher(inv,tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 175;
		this.ySize = 221;
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		this.mc.renderEngine.bindTexture(modsbfp.guiDirectory+"crusher.png");
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		containerWidth = (this.width-this.xSize)/2;
		containerHeight = (this.height-this.ySize)/2;
		this.drawTexturedModalRect(containerWidth,containerHeight,0,0,this.xSize,this.ySize);
		if(tileEntity.activeRecipe!=null){
			this.drawTexturedModalRect(this.containerWidth+47,this.containerHeight+39,176,0,29,12*this.tileEntity.workTicks/tileEntity.activeRecipe.getTime());
			int drawHeight = 52-12*this.tileEntity.workTicks/tileEntity.activeRecipe.getTime();
			int sourceHeight = 23-12*this.tileEntity.workTicks/tileEntity.activeRecipe.getTime();
			this.drawTexturedModalRect(this.containerWidth+47,this.containerHeight+drawHeight,176,sourceHeight,29,23-sourceHeight);
		}
		if(this.tileEntity.getChargeLevel()<=TileEntityCrusher.maxChargeLevel/3&&this.tileEntity.getChargeLevel()!=0){
			this.drawTexturedModalRect(this.containerWidth+140,this.containerHeight+10,205,0,11,10);
		}else if(this.tileEntity.getChargeLevel()<=TileEntityCrusher.maxChargeLevel*(2/3)){
			this.drawTexturedModalRect(this.containerWidth+140,this.containerHeight+10,205,11,11,10);
		}else if(this.tileEntity.getChargeLevel()>TileEntityCrusher.maxChargeLevel*(2/3)){
			this.drawTexturedModalRect(this.containerWidth+140,this.containerHeight+10,205,22,11,10);
		}
		int drawHeight = 113-89*(this.tileEntity.getChargeLevel()/TileEntityCrusher.maxChargeLevel);
		int sourceHeight = 89-89*(this.tileEntity.getChargeLevel()/TileEntityCrusher.maxChargeLevel);
		this.drawTexturedModalRect(this.containerWidth+144,this.containerHeight+drawHeight,217,sourceHeight,4,89-sourceHeight);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		this.fontRenderer.drawString(this.tileEntity.getInvName(),33,6,4210752);
	}
}