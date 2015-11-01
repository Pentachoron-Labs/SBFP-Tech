package sbfp.flux;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRedFluxDevice extends Item implements IFluxSourceItem{

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

    @Override
    public int drainFlux(ItemStack stack, int amount) {
        if(stack.getMetadata() != FluxDeviceTypes.CHARGEDREDSTONE.getMeta()) return 0;
        return 10;
    }

    @Override
    public boolean isSubtypeValidFluxSource(int meta) {
        return meta == FluxDeviceTypes.CHARGEDREDSTONE.getMeta();
    }

    @Override
    public boolean destroyOnDrain() {
        return true;
    }
        
        
}