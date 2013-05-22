package sbfp;


import sbfp.world.BlockOre;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemSB extends Item{
	
	public static final String[] names = {"redFluxAmp", "redFluxAbsorber"};	
	public static Icon[] icons = new Icon[16];
	public ItemSB(int id){
		super(id);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack is){
		return ItemSB.names[is.getItemDamage()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register){
		icons[0] = register.registerIcon("sbfp:"+BlockOre.names[0]);
		icons[1] = register.registerIcon("sbfp:"+BlockOre.names[1]);
		icons[2] = register.registerIcon("sbfp:"+BlockOre.names[2]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta){
		return icons[meta];
	}
}