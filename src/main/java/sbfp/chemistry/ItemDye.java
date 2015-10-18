package sbfp.chemistry;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDye extends Item{

	public ItemDye(String name){
		super();
                this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.tabDecorations);
                this.setHasSubtypes(true);
                this.setMaxDamage(0);
	}
        
        @Override
        public int getMetadata(int meta){
            return meta;
        }
        
        @SideOnly(Side.CLIENT)
        @Override
        public void getSubItems(Item itemIn, CreativeTabs tab, List subItems){
            for (DyeTypes dye : DyeTypes.values()){
                subItems.add(new ItemStack(itemIn, 1, dye.getMeta()));
            }
        }
        
        @Override
        public String getUnlocalizedName(ItemStack i){
            return DyeTypes.dyeTypeByMeta(i.getMetadata()).getName();
        }
        
}