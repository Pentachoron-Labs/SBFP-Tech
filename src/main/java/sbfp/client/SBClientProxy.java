package sbfp.client;

import sbfp.client.gui.GUISolarCharger;
import sbfp.client.gui.GUICrusher;
import sbfp.client.gui.GUIFoundry;
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
import sbfp.SBProxy;
import sbfp.chemistry.DyeTypes;
import sbfp.flux.FluxDeviceTypes;
import sbfp.machines.MachineTypes;
import sbfp.machines.crusher.TileEntityCrusher;
import sbfp.machines.foundry.BlockFoundry;
import sbfp.machines.foundry.FoundryStates;
import sbfp.machines.foundry.TileEntityFoundry;
import sbfp.machines.solar.TileEntitySolarCharger;
import sbfp.world.OreTypes;

@SideOnly(Side.CLIENT)
public class SBClientProxy extends SBProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        FMLLog.info("SBFP Client Proxy: PreInitialing \t Registering Block and Item Variants");
        //Register Ore Variants
        Item registerThis = GameRegistry.findItem("sbfp", "blockOre");
        ModelBakery.addVariantName(registerThis, OreTypes.ARSENOPYRITE.getModelResourceName(), OreTypes.CINNABAR.getModelResourceName(), OreTypes.FLUORITE.getModelResourceName(),
                OreTypes.LIMONITE.getModelResourceName(), OreTypes.MOLYBDENITE.getModelResourceName(), OreTypes.MONAZITE.getModelResourceName(), OreTypes.PYROLUSITE.getModelResourceName(),
                OreTypes.RUTILE.getModelResourceName());
        //Register Machine Variants
        registerThis = GameRegistry.findItem("sbfp", "blockMachine");
        ModelBakery.addVariantName(registerThis, MachineTypes.SOLARCHARGER.getModelResourceName(), MachineTypes.CRUSHER.getModelResourceName());
        //Register Foundry Variants
        registerThis = GameRegistry.findItem("sbfp", "blockFoundry");
        ModelBakery.addVariantName(registerThis, FoundryStates.DISCONNECTED.getModelResourceName(), FoundryStates.CONNECTED.getModelResourceName());

        //Register Item Variants:Dye
        registerThis = GameRegistry.findItem("sbfp", "itemDye");
        ModelBakery.addVariantName(registerThis, DyeTypes.TIO2.getModelResourceName(), DyeTypes.MNO2.getModelResourceName(),
                DyeTypes.OCHRE.getModelResourceName(), DyeTypes.VERMILLION.getModelResourceName(), DyeTypes.ULTRAMARINE.getModelResourceName());

        //Register Item Variants:Flux-related devices
        registerThis = GameRegistry.findItem("sbfp", "itemFluxDevice");
        ModelBakery.addVariantName(registerThis, FluxDeviceTypes.ABSORBER.getModelResourceName(), FluxDeviceTypes.AMPLIFIER.getModelResourceName(),
                FluxDeviceTypes.STABILIZER.getModelResourceName(), FluxDeviceTypes.CHARGEDREDSTONE.getModelResourceName());
        registerThis = GameRegistry.findItem("sbfp", "itemLowFluxCell");
        ModelBakery.addVariantName(registerThis, "sbfp:fluxCell");
    }

    @Override
    public void init(FMLInitializationEvent event) {

        FMLLog.info("SBFP Client Proxy Loading \t Registering Block and Item Models");
        //Register Ore Model Locations
        Item registerThis = GameRegistry.findItem("sbfp", "blockOre"); //WHO'S THE IDIOT NOW, HUH?
        for (OreTypes ores : OreTypes.values()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, ores.getMeta(), new ModelResourceLocation(ores.getModelResourceName(), "inventory"));
        }

        //Register Machine Model Locations
        registerThis = GameRegistry.findItem("sbfp", "blockMachine");
        for (MachineTypes machine : MachineTypes.values()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, machine.getMeta(), new ModelResourceLocation(machine.getModelResourceName(), "inventory"));
        }
        //Register Foundry Model Locations
        registerThis = GameRegistry.findItem("sbfp", "blockFoundry");
        for (FoundryStates state : FoundryStates.values()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, state.getMeta(), new ModelResourceLocation(state.getModelResourceName(), "inventory"));
        }

        //Register Item Model Locations
        registerThis = GameRegistry.findItem("sbfp", "itemDye");
        for (DyeTypes dye : DyeTypes.values()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, dye.getMeta(), new ModelResourceLocation(dye.getModelResourceName(), "inventory"));
        }

        registerThis = GameRegistry.findItem("sbfp", "itemFluxDevice");
        for (FluxDeviceTypes device : FluxDeviceTypes.values()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, device.getMeta(), new ModelResourceLocation(device.getModelResourceName(), "inventory"));
        }
        registerThis = GameRegistry.findItem("sbfp", "itemLowFluxCell");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(registerThis, 0, new ModelResourceLocation("sbfp:fluxCell", "inventory"));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity != null) {
            if (tileEntity instanceof TileEntitySolarCharger) {
                return new GUISolarCharger(player.inventory, (TileEntitySolarCharger) tileEntity);
            }
            if (tileEntity instanceof TileEntityCrusher) {
                return new GUICrusher(player.inventory, (TileEntityCrusher) tileEntity);
            }
            if (tileEntity instanceof TileEntityFoundry) {
                return new GUIFoundry(player.inventory, (TileEntityFoundry) tileEntity);
            }
            FMLLog.warning("An unrecognized TE ('%s') inhabits machine '%s'. Update the Common Proxy.", tileEntity.getClass().getName());
            FMLLog.warning("This warning is for side: client");
            return null;
        }
        return null;
    }
}
