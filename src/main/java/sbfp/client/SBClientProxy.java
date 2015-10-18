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
import sbfp.machines.processor.crusher.TileEntityCrusher;
import sbfp.machines.processor.solar.TileEntitySolarCharger;
import sbfp.world.EnumOreType;


@SideOnly(Side.CLIENT)
public class SBClientProxy extends SBCommonProxy{
    
    
        @Override
        public void preInit(FMLPreInitializationEvent event){
            Item itemBlockOre = GameRegistry.findItem("sbfp", "blockOre");
            ModelBakery.addVariantName(itemBlockOre, EnumOreType.ARSENOPYRITE.getModelResourceName(), EnumOreType.CINNABAR.getModelResourceName(), EnumOreType.FLUORITE.getModelResourceName(),
                    EnumOreType.LIMONITE.getModelResourceName(), EnumOreType.MOLYBDENITE.getModelResourceName(), EnumOreType.MONAZITE.getModelResourceName(), EnumOreType.PYROLUSITE.getModelResourceName(),
                    EnumOreType.RUTILE.getModelResourceName());
        }
	@Override
	public void init(FMLInitializationEvent event){
            
		FMLLog.fine("SBFP Client Proxy Loading");
                Item itemBlockOre = GameRegistry.findItem("sbfp", "blockOre"); //WHO'S THE IDIOT NOW, HUH?
                for(EnumOreType ores : EnumOreType.values()){
                    FMLLog.info(ores.getModelResourceName()+" "+ores.getMeta());
                    Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlockOre, ores.getMeta(), new ModelResourceLocation(ores.getModelResourceName(), "inventory"));
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