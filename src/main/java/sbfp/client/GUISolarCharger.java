package sbfp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import sbfp.machines.IProcessor;

import sbfp.machines.solar.ContainerSolarCharger;
import sbfp.machines.solar.TileEntitySolarCharger;

public class GUISolarCharger extends GuiContainer {

    private IProcessor tileEntity;
    private int containerWidth;
    private int containerHeight;

    public GUISolarCharger(InventoryPlayer inv, TileEntitySolarCharger tileEntity) {
        super(tileEntity.setContainer(new ContainerSolarCharger(inv,tileEntity)));
        this.tileEntity = tileEntity;
        this.xSize = 175;
        this.ySize = 221;
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the
     * items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("sbfp", "textures/gui/sun_collector.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        containerWidth = (this.width - this.xSize) / 2;
        containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        if (tileEntity.getActiveProcess() != null) {
            this.drawTexturedModalRect(this.containerWidth + 48, this.containerHeight + 47, 177, 0, 79 * this.tileEntity.getWorkTicks() / tileEntity.getActiveProcess().getDuration(), 5);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString("Solar Collector", 40, 6, 4210752);
    }
}
