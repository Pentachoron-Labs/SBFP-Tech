package sbfp;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sbfp.client.GUICrusher;
import sbfp.client.GUISolarCharger;
import sbfp.machines.processor.crusher.ContainerCrusher;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.ContainerSolarCharger;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
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
			if(tileEntity instanceof TileEntitySolarCharger) return new ContainerSolarCharger(player.inventory,(TileEntitySolarCharger) tileEntity);
			if(tileEntity instanceof TileEntityCrusher) return new ContainerCrusher(player.inventory,(TileEntityCrusher) tileEntity);
			FMLLog.warning("An unrecognized TE ('%s') inhabits machine '%s'. Update the Common Proxy.",tileEntity.getClass().getName());
			FMLLog.warning("This warning is for side: server");
			return null;
		}
		FMLLog.warning("The TE at (%d,%d,%d) is null even though our machine '%s' is there. What gives?",x,y,z,Block.blocksList[world.getBlockId(x,y,z)].getLocalizedName());
		FMLLog.warning("This warning is for side: server");
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getBlockTileEntity(x,y,z);
		if(tileEntity!=null){
			if(tileEntity instanceof TileEntitySolarCharger) return new GUISolarCharger(player.inventory,(TileEntitySolarCharger) tileEntity);
			if(tileEntity instanceof TileEntityCrusher) return new GUICrusher(player.inventory,(TileEntityCrusher) tileEntity);
			FMLLog.warning("An unrecognized TE ('%s') inhabits machine '%s'. Update the Common Proxy.",tileEntity.getClass().getName());
			FMLLog.warning("This warning is for side: client");
			return null;
		}
		FMLLog.warning("The TE at (%d,%d,%d) is null even though our machine '%s' is there. What gives?",x,y,z,Block.blocksList[world.getBlockId(x,y,z)].getLocalizedName());
		FMLLog.warning("This warning is for side: client");
		return null;
	}
}