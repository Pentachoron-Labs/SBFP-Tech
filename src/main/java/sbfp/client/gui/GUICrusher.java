package sbfp.client.gui;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import sbfp.machines.crusher.ContainerCrusher;
import sbfp.machines.crusher.TileEntityCrusher;

public class GUICrusher extends GuiContainer{

	private TileEntityCrusher tileEntity;
	private int originX;
	private int originY;

	public GUICrusher(InventoryPlayer inv, TileEntityCrusher tileEntity){
		super(tileEntity.setContainer(new ContainerCrusher(inv,tileEntity)));
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
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("sbfp", "textures/gui/crusher.png"));
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		originX = (this.width-this.xSize)/2;
		originY = (this.height-this.ySize)/2;
		this.drawTexturedModalRect(originX,originY,0,0,this.xSize,this.ySize);
		if(tileEntity.getActiveProcess()!=null){
			this.drawTexturedModalRect(this.originX+47,this.originY+29,176,0,29,12*this.tileEntity.getWorkTicks()/tileEntity.getActiveProcess().getDuration());
			int drawHeight = 53-12*this.tileEntity.getWorkTicks()/tileEntity.getActiveProcess().getDuration();
			int sourceHeight = 23-12*this.tileEntity.getWorkTicks()/tileEntity.getActiveProcess().getDuration();
			this.drawTexturedModalRect(this.originX+47,this.originY+drawHeight,176,sourceHeight,29,23-sourceHeight);
		}
		if(this.tileEntity.getFluxLevel()<=this.tileEntity.getMaxFluxLevel()/3&&this.tileEntity.getFluxLevel()!=0){
			this.drawTexturedModalRect(this.originX+140,this.originY+10,205,0,12,11);
		}else if(this.tileEntity.getFluxLevel()<=this.tileEntity.getMaxFluxLevel()*2/3 && this.tileEntity.getFluxLevel() != 0){
			this.drawTexturedModalRect(this.originX+140,this.originY+10,205,11,12,11);
		}else if(this.tileEntity.getFluxLevel()>this.tileEntity.getMaxFluxLevel()*2/3){
			this.drawTexturedModalRect(this.originX+140,this.originY+10,205,22,12,11);
		}
                //ll corner of bar in gui is 144,113
                //ll corener of bar source image is 217,89
		int drawHeight = this.tileEntity.getFluxLevel() != 0 ? 90*this.tileEntity.getFluxLevel()/this.tileEntity.getMaxFluxLevel() : 0;
		this.drawTexturedModalRect(this.originX+144,this.originY+114-drawHeight,217,0,4,drawHeight);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2){
		this.fontRendererObj.drawString(this.tileEntity.getDisplayName().getUnformattedText(), 40, 6, 4210752);
	}
}