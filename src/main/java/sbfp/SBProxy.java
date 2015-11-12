package sbfp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sbfp.machines.IProcessor;
import sbfp.machines.crusher.ContainerCrusher;
import sbfp.machines.crusher.TileEntityCrusher;
import sbfp.machines.foundry.ContainerFoundry;
import sbfp.machines.foundry.TileEntityFoundry;
import sbfp.machines.solar.ContainerSolarCharger;
import sbfp.machines.solar.TileEntitySolarCharger;

public class SBProxy implements IGuiHandler {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {
        FMLLog.fine("SBFP Common Proxy loading");
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity != null) {
            if (tileEntity instanceof TileEntitySolarCharger) {
                return ((IProcessor) tileEntity).setContainer(new ContainerSolarCharger(player.inventory, (TileEntitySolarCharger) tileEntity));
            }
            if (tileEntity instanceof TileEntityCrusher) {
                return ((IProcessor) tileEntity).setContainer(new ContainerCrusher(player.inventory, (TileEntityCrusher) tileEntity));
            }
            if (tileEntity instanceof TileEntityFoundry) {
                return ((TileEntityFoundry) tileEntity).setContainer(new ContainerFoundry(player.inventory, (TileEntityFoundry) tileEntity));
            }
            FMLLog.info("An unrecognized TE ('%s') inhabits machine '%s'. Update the Common Proxy.", tileEntity.getClass().getName());
            FMLLog.info("This warning is for side: server");
            return null;
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void registerTileEntitySpecialRenderer() {

    }

    public World getClientWorld() {
        return null;
    }
}
