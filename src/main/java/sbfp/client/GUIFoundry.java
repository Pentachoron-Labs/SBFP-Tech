package sbfp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sbfp.machines.foundry.ContainerFoundry;
import sbfp.machines.foundry.TileEntityFoundry;


public class GUIFoundry extends GuiContainer{
    private TileEntityFoundry tileEntity;
    
    private int originY;
    private int originX;

    public GUIFoundry(InventoryPlayer inv, TileEntityFoundry tileEntity) {
        super(tileEntity.setContainer(new ContainerFoundry(inv,tileEntity)));
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("sbfp", "textures/gui/foundry.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        originX = (this.width-this.xSize)/2;
	originY = (this.height-this.ySize)/2;
        this.drawTexturedModalRect(originX,originY,0,0,this.xSize,this.ySize);
        /*
         GUI COORDINATES
         SMELTING: Source(0, 222)
         SMELTING OUTS: (7, [13, 37, 61, 85])
         BAR 1 SOURCE: (188, 0)
         BAR 2 SOURCE: (192, 0)
         BAR 1 GUI: (145, 107) (LL Corner)
         BAR 2 GUI: (155, 107) (LL Corner)
         Charge Indicator Source (176, [0, 11, 22])
         Charge Indicator GUI: (146, 5)
        */
        if(this.tileEntity.getFluxLevel() > 100){
            this.drawTexturedModalRect(originX + 145, originY+17, 188, 0, 191, 90);
        }else{
            
        }
        for(int i = 0; i<4; i++){
            this.drawTexturedModalRect(originX +7, originY+13+24*i, 0, 222, 99, 239);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.tileEntity.getDisplayName().getUnformattedText(), 40, 6, 4210752);
    }

}
