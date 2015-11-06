package sbfp;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabSBFP extends CreativeTabs {

    public TabSBFP() {
        super("sbfp");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        ItemStack iStack = new ItemStack(GameRegistry.findItem("sbfp", "blockMachine"), 1, 1);
        return iStack.getItem();
    }
}
