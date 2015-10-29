package sbfp.flux;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemFluxCell extends Item {

    public ItemFluxCell(String name, int maxCharge) {
        super();
        this.setUnlocalizedName(name);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setMaxDamage(maxCharge == 0 ? 50 : maxCharge);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
    
    
}
