package sbfp.machines.foundry;

import net.minecraft.block.Block;
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
        return modsbfp.blockFoundry.getStateFromMeta(is.getItemDamage()).getValue(BlockFoundry.STATE).toString();
    }

}
