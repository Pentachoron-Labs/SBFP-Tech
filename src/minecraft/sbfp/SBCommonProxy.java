package sbfp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sbfp.client.GUISolarCharger;
import sbfp.machines.solar.ContainerSolarCharger;
import sbfp.machines.solar.TileSolarCharger;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;

public class SBCommonProxy implements IGuiHandler{

	public void init(){
		FMLLog.fine("SBFP Common Proxy loading");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
		if(tileEntity!=null){
			if(tileEntity instanceof TileSolarCharger) return new ContainerSolarCharger(player.inventory,(TileSolarCharger) tileEntity);
		}
		FMLLog.info("TileEntity is null");
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
		if(tileEntity!=null){
			if(tileEntity instanceof TileSolarCharger) return new GUISolarCharger(player.inventory,(TileSolarCharger) tileEntity);
		}
		return null;
	}
}