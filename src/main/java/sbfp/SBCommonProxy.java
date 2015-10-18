package sbfp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sbfp.machines.processor.crusher.ContainerCrusher;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.ContainerSolarCharger;
import sbfp.machines.processor.solar.TileEntitySolarCharger;

public class SBCommonProxy implements IGuiHandler{
    
        public void preInit(FMLPreInitializationEvent event){
            
        }
	
	public void init(FMLInitializationEvent event){
		FMLLog.fine("SBFP Common Proxy loading");
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		if(tileEntity!=null){
			if(tileEntity instanceof TileEntitySolarCharger) return new ContainerSolarCharger(player.inventory,(TileEntitySolarCharger) tileEntity);
			if(tileEntity instanceof TileEntityCrusher) return new ContainerCrusher(player.inventory,(TileEntityCrusher) tileEntity);
			FMLLog.info("An unrecognized TE ('%s') inhabits machine '%s'. Update the Common Proxy.",tileEntity.getClass().getName());
			FMLLog.info("This warning is for side: server");
			return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

    public void registerTileEntitySpecialRenderer()
    {

    }
    
    public World getClientWorld(){
    	return null;
    }
}