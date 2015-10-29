package sbfp.flux;

import sbfp.flux.FluxDeviceTypes;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRedFluxDevice extends Item{

	public ItemRedFluxDevice(String name){
		super();
                this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.tabRedstone);
                this.setMaxDamage(0);
                this.setHasSubtypes(true);
	}
        
        @Override
        public int getMetadata(int meta){
            return meta;
        }
        
        @SideOnly(Side.CLIENT)
        @Override
        public void getSubItems(Item itemIn, CreativeTabs tab, List subItems){
            for (FluxDeviceTypes device : FluxDeviceTypes.values()){
                subItems.add(new ItemStack(itemIn, 1, device.getMeta()));
            }
        }
        
        @Override
        public String getUnlocalizedName(ItemStack i){
            return FluxDeviceTypes.dyeTypeByMeta(i.getMetadata()).getName();
        }
        
        
}