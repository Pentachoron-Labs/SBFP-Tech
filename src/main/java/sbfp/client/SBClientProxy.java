package sbfp.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sbfp.SBCommonProxy;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
import sbfp.modsbfp;
import sbfp.world.EnumOreType;


@SideOnly(Side.CLIENT)
public class SBClientProxy extends SBCommonProxy{
    
    
        @EventHandler
        public void preInit(FMLPreInitializationEvent event){
            for(EnumOreType ores :EnumOreType.values()){
                
                ModelBakery.addVariantName(Item.getItemFromBlock(modsbfp.blockOre), "sbfp:block"+ores.getName().substring(0,1).toUpperCase()+ores.getName().substring(1));
            }
        }
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event){
		FMLLog.fine("SBFP Client Proxy Loading");
                for(EnumOreType ores : EnumOreType.values()){
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(modsbfp.blockOre), ores.getMeta(), new ModelResourceLocation("sbfp:"+ores.toString(), "inventory"));
                }
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z));
		if(tileEntity!=null){
			if(tileEntity instanceof TileEntitySolarCharger) return new GUISolarCharger(player.inventory,(TileEntitySolarCharger) tileEntity);
			if(tileEntity instanceof TileEntityCrusher) return new GUICrusher(player.inventory,(TileEntityCrusher) tileEntity);
			FMLLog.warning("An unrecognized TE ('%s') inhabits machine '%s'. Update the Common Proxy.",tileEntity.getClass().getName());
			FMLLog.warning("This warning is for side: client");
			return null;
		}
		return null;
	}
}