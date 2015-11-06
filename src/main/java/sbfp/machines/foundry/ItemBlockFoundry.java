package sbfp.machines.foundry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import sbfp.modsbfp;

public class ItemBlockFoundry extends ItemBlock {

    public ItemBlockFoundry(Block foundry) {
        super(foundry);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return "machine.foundry."+FoundryStates.stateFromMeta(is.getMetadata()).getName();
    }
    
    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[]{modsbfp.tabSBFP, this.getCreativeTab()};
    }

}
