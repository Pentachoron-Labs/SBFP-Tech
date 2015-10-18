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
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sbfp.SBCommonProxy;
import sbfp.chemistry.DyeTypes;
import sbfp.machines.FluxDeviceTypes;
import sbfp.machines.MachineTypes;
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
import sbfp.world.OreTypes;


@SideOnly(Side.CLIENT)
public class SBClientProxy extends SBCommonProxy{
    
    
        @Override
        public void preInit(FMLPreInitializationEvent event){
            FMLLog.info("SBFP Client Proxy: PreInitialing \t Registering Block and Item Variants");
            //Register Ore Variants
            Item registerThis = GameRegistry.findItem("sbfp", "blockOre");
            ModelBakery.addVariantName(registerThis, OreTypes.ARSENOPYRITE.getModelResourceName(), OreTypes.CINNABAR.getModelResourceName(), OreTypes.FLUORITE.getModelResourceName(),
                    OreTypes.LIMONITE.getModelResourceName(), OreTypes.MOLYBDENITE.getModelResourceName(), OreTypes.MONAZITE.getModelResourceName(), OreTypes.PYROLUSITE.getModelResourceName(),
                    OreTypes.RUTILE.getModelResourceName());
            //Register Machine Variants
            registerThis = GameRegistry.findItem("sbfp", "blockMachine");
            ModelBakery.addVariantName(registerThis, MachineTypes.SOLARCHARGER.getModelResourceName(), MachineTypes.CRUSHER.getModelResourceName());
            
            //Register Item Variants:Dye
            registerThis = GameRegistry.findItem("sbfp", "itemDye");
            ModelBakery.addVariantName(registerThis, DyeTypes.TIO2.getModelResourceName(), DyeTypes.MNO2.getModelResourceName(),
                    DyeTypes.OCHRE.getModelResourceName(), DyeTypes.VERMILLION.getModelResourceName(), DyeTypes.ULTRAMARINE.getModelResourceName());
            
            //Register Item Variants:Flux-related devices
            registerThis = GameRegistry.findItem("sbfp", "itemFluxDevice");
            ModelBakery.addVariantName(registerThis, FluxDeviceTypes.ABSORBER.getModelResourceName(), FluxDeviceTypes.AMPLIFIER.getModelResourceName(),
                    FluxDeviceTypes.STABILIZER.getModelResourceName(), FluxDeviceTypes.CHARGEDREDSTONE.getModelResourceName());
        }
	@Override
	public void init(FMLInitializationEvent event){
            
		FMLLog.info("SBFP Client Proxy Loading \t Registering Block and Item Models");
                //Register Ore Model Locations
                Item registerThis = GameRegistry.findItem("sbfp", "blockOre"); //WHO'S THE IDIOT NOW, HUH?
                for(OreTypes ores : OreTypes.values()){
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, ores.getMeta(), new ModelResourceLocation(ores.getModelResourceName(), "inventory"));
                }
                
                //Register Machine Model Locations
                registerThis = GameRegistry.findItem("sbfp", "blockMachine");
                for(MachineTypes machine : MachineTypes.values()){
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, machine.getMeta(), new ModelResourceLocation(machine.getModelResourceName(), "inventory"));
                }
                
                registerThis = GameRegistry.findItem("sbfp", "itemDye");
                for (DyeTypes dye : DyeTypes.values()){
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, dye.getMeta(), new ModelResourceLocation(dye.getModelResourceName(), "inventory"));
                }
                
                registerThis = GameRegistry.findItem("sbfp", "itemFluxDevice");
                for (FluxDeviceTypes device : FluxDeviceTypes.values()){
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, device.getMeta(), new ModelResourceLocation(device.getModelResourceName(), "inventory"));
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